package com.a88.service.impl;

import com.a88.Pojo.SystemRequirements;
import com.a88.Pojo.products;
import com.a88.utils.uploadFileUtil;
import com.a88.mapper.productMapper;
import com.a88.service.inter.productService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class productsServiceImp implements productService {

    @Autowired
    private productMapper PM;

    @Autowired
    private uploadFileUtil ULF;
    @Override
    public List<products> allProducts(String name, String categories, String tags, Double minPrice, Double maxPrice, String sortBy, Integer page, Integer limit) {
        if (page != null ) {
            int offset = (page - 1) * limit;
            return PM.allProducts(name, categories, tags, minPrice, maxPrice, sortBy, offset, limit);
        } else {
            int offset = -1;
            return PM.allProducts(name, categories, tags, minPrice, maxPrice, sortBy, offset, limit);
        }

    }

    @Override
    public void update(Long id, String name, String description, Double originalPrice, String categories, String tags, Double discount, String existingImages, MultipartFile[] images, String[] oldImageUrl, MultipartFile mainImage, String minRequirementsJson, String recRequirementsJson, String existingMainImage) throws IOException {
        // 处理 tags，去除多余空格
        String[] tagsArray = tags.split(",");
        String trimmedTags = Arrays.stream(tagsArray)
                .map(String::trim)
                .collect(Collectors.joining(","));

        // 处理 categories，去除多余空格
        String[] cateArray = categories.split(",");
        String trimmedCate = Arrays.stream(cateArray)
                .map(String::trim)
                .collect(Collectors.joining(","));
        // new product object
        products pro = new products();
        pro.setId(id);
        pro.setName(name);
        pro.setDescription(description);
        pro.setOriginalPrice(originalPrice);
        pro.setCategories(trimmedCate);
        pro.setTags(trimmedTags);
        pro.setDiscount(discount);
        pro.setUpdateTime(LocalDateTime.now());
        // old file的刪除
        if (oldImageUrl != null && oldImageUrl.length > 0 ) {
            ULF.deleteFiles(oldImageUrl);
        }

        // 拼接用
        StringBuilder imageUrl2 = new StringBuilder();
        // 這樣可以把新upload的file的url放在後面
        if (existingImages != null && !existingImages.trim().isEmpty()) {
            imageUrl2.append(existingImages);
            imageUrl2.append(",");
        }
        // upload file再拿url
        if (images != null && images.length > 0) {
            // only one image file
            if (images.length == 1) {
                for (MultipartFile multipartFile : images) {
                    imageUrl2.append(ULF.uploadFile(multipartFile));
                }
            } else {
                // 多於一個的image file
                for (MultipartFile multipartFile : images) {
                    imageUrl2.append(ULF.uploadFile(multipartFile));
                    imageUrl2.append(",");
                }
            }
        }

        // 移除最后的逗号（如果有）
        String finalImageUrl = imageUrl2.toString();
        if (finalImageUrl.endsWith(",")) {
            finalImageUrl = finalImageUrl.substring(0, finalImageUrl.length() - 1);
        }

        pro.setImageUrl(finalImageUrl.isEmpty() ? null : finalImageUrl);
        if (mainImage == null) {
            if (existingMainImage != null) {
                pro.setMainImage(existingMainImage);
            } else {
                pro.setMainImage(null);
            }
        } else {
            pro.setMainImage(ULF.uploadFile(mainImage));
        }

        ObjectMapper objectMapper = new ObjectMapper();
        SystemRequirements minRequirements = objectMapper.readValue(minRequirementsJson, SystemRequirements.class);
        SystemRequirements recRequirements = objectMapper.readValue(recRequirementsJson, SystemRequirements.class);

        pro.setMinRequirements(minRequirements);
        pro.setRecRequirements(recRequirements);

        PM.update(pro);
    }

    @Override
    public void delete(ArrayList<Integer> ids, String imageFileName) {
        ULF.deleteFile(imageFileName);
        PM.delete(ids);
    }

    @Override
    public void add(String name, String description, Double originalPrice, String categories, String tags, Double discount, MultipartFile[] image, MultipartFile mainImage, String minRequirementsJson, String recRequirementsJson) throws IOException {
        products pro = new products();
        pro.setName(name);
        pro.setDescription(description);
        pro.setOriginalPrice(originalPrice);
        pro.setCategories(categories);
        pro.setTags(tags);
        pro.setDiscount(discount);
        pro.setUpdateTime(LocalDateTime.now());
        pro.setCreateTime(LocalDateTime.now());

        StringBuilder imageUrl = new StringBuilder();
        if (image != null && image.length > 0) {
            // only one image file
            if (image.length == 1) {
                for (MultipartFile multipartFile : image) {
                    imageUrl.append(ULF.uploadFile(multipartFile));
                }
            } else {
                // 多於一個的image file
                for (int i = 0; i < image.length; i++) {
                    if (i == (image.length -1)) {
                        imageUrl.append(ULF.uploadFile(image[i]));
                    } else {
                        imageUrl.append(ULF.uploadFile(image[i]));
                        imageUrl.append(",");
                    }

                }
            }
        }
        pro.setImageUrl(imageUrl.toString());
        pro.setMainImage(ULF.uploadFile(mainImage));

        ObjectMapper objectMapper = new ObjectMapper();
        SystemRequirements minRequirements = objectMapper.readValue(minRequirementsJson, SystemRequirements.class);
        SystemRequirements recRequirements = objectMapper.readValue(recRequirementsJson, SystemRequirements.class);

        pro.setMinRequirements(minRequirements);
        pro.setRecRequirements(recRequirements);
        PM.add(pro);
    }

    @Override
    public int countProducts(String name, String categories, String tags, Double minPrice, Double maxPrice) {
        return PM.countProducts(name, categories, tags, minPrice, maxPrice);
    }

    @Override
    public ArrayList<String> allCategories() {
        return PM.allCategories();
    }

    @Override
    public ArrayList<String> allTags() {
        return PM.allTags();
    }
}
