package com.a88.controller;

import com.a88.Pojo.dept;
import com.a88.Pojo.products;
import com.a88.Pojo.result;
import com.a88.service.inter.productService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/product")
@RestController
@Slf4j
public class productController {

    @Autowired
    private productService PS;

    @GetMapping
    public result allProducts(@RequestParam(required = false) String name,
                              @RequestParam(required = false) String categories,
                              @RequestParam(required = false) String tags,
                              @RequestParam(required = false) Double minPrice,
                              @RequestParam(required = false) Double maxPrice,
                              @RequestParam(required = false) String sortBy,
                              @RequestParam(value = "page", required = false) Integer page,
                              @RequestParam(required = false) Integer limit
                              ) {
        log.info("get all products info from database");
        List<products> products = PS.allProducts(name, categories, tags, minPrice, maxPrice, sortBy, page, limit);

        return result.success(products);
    }

    @GetMapping("/count")
    public result countProducts(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "categories", required = false) String categories,
            @RequestParam(value = "tags", required = false) String tags,
            @RequestParam(value = "minPrice", required = false) Double minPrice,
            @RequestParam(value = "maxPrice", required = false) Double maxPrice
    ) {
        return result.success(PS.countProducts(name, categories, tags, minPrice, maxPrice)) ;
    }

    @GetMapping("/categories")
    public result allCategories() {
        return result.success(PS.allCategories());
    }

    @GetMapping("/tags")
    public result allTags() {
        return result.success(PS.allTags());
    }

    @PutMapping
    public result update(@RequestParam("id") Long id,
                         @RequestParam("name") String name,
                         @RequestParam("description") String description,
                         @RequestParam("originalPrice") Double originalPrice,
                         @RequestParam("categories") String categories,
                         @RequestParam("tags") String tags,
                         @RequestParam("discount") Double discount,
                         @RequestParam("existingImages") String existingImages, // 旧图片 URL
                         @RequestParam(value = "images", required = false) MultipartFile[] images,
                         @RequestParam(value = "oldImageUrl", required = false) String[] oldImageUrl,
                         @RequestParam(value = "mainImage", required = false) MultipartFile mainImage,
                         @RequestParam(value = "minRequirements") String minRequirementsJson,
                         @RequestParam(value = "recRequirements") String recRequirementsJson,
                         @RequestParam(value = "existingMainImage", required = false) String existingMainImage) throws IOException {
        log.info("update game: {}, {}, {}, {}, {}, {}, {}, {}, {},{},{},{},{},{}", id, name, description, originalPrice, categories, tags, discount,existingImages, images, oldImageUrl, mainImage,minRequirementsJson, recRequirementsJson,existingMainImage);

        PS.update(id,
                name,
                description,
                originalPrice,
                categories,
                tags,
                discount,
                existingImages,
                images,
                oldImageUrl,
                mainImage,
                minRequirementsJson,
                recRequirementsJson,
                existingMainImage);
        return result.success();
    }

    @DeleteMapping("/{ids}")
    public result delete(@PathVariable ArrayList<Integer> ids, @RequestParam("imageFileName") String imageFileName) {
        log.info("delete operation, ids:{}", ids);
        PS.delete(ids, imageFileName);
        return result.success();
    }

    @PostMapping
    public result add(
                      @RequestParam("name") String name,
                      @RequestParam("description") String description,
                      @RequestParam("originalPrice") Double originalPrice,
                      @RequestParam("categories") String categories,
                      @RequestParam("tags") String tags,
                      @RequestParam("discount") Double discount,
                      @RequestParam(value = "image", required = false) MultipartFile[] image,
                      @RequestParam(value = "mainImage", required = false) MultipartFile mainImage,
                      @RequestParam(value = "minRequirements") String minRequirementsJson,
                      @RequestParam("recRequirements") String recRequirementsJson
                      ) throws IOException {
        log.info("add game: {}, {}, {}, {}, {}, {}, {}, {}, {}, {}", name, description, originalPrice, categories, tags, discount, image, mainImage, minRequirementsJson, recRequirementsJson);
        PS.add(name, description, originalPrice, categories, tags, discount, image, mainImage, minRequirementsJson, recRequirementsJson);
        return result.success();
    }

}
