package com.a88.service.inter;

import com.a88.Pojo.products;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public interface productService {



    List<products> allProducts(String name, String categories, String tags, Double minPrice, Double maxPrice, String sortBy, Integer page, Integer limit);

    void update(Long id, String name, String description, Double originalPrice, String categories, String tags, Double discount, String existingImages, MultipartFile[] images, String[] oldImageUrl, MultipartFile mainImage, String minRequirementsJson, String recRequirementsJson, String existingMainImage) throws IOException;

    void delete(ArrayList<Integer> ids, String imageFileName);

    void add(String name, String description, Double originalPrice, String categories, String tags, Double discount, MultipartFile[] image, MultipartFile mainImage, String minRequirementsJson, String recRequirementsJson) throws IOException;

    int countProducts(String name, String categories, String tags, Double minPrice, Double maxPrice);

    ArrayList<String> allCategories();

    ArrayList<String> allTags();
}
