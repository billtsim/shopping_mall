package com.a88.mapper;

import com.a88.Pojo.products;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface productMapper {

    List<products> allProducts(String name, String categories, String tags, Double minPrice, Double maxPrice, String sortBy, int offset, Integer limit);

    void update(products pro);

    void delete(ArrayList<Integer> ids);

    void add(products pro);

    int countProducts(String name, String categories, String tags, Double minPrice, Double maxPrice);

    ArrayList<String> allCategories();

    ArrayList<String> allTags();

    @Select("select id from products where name=#{name}")
    int getProductByName(String name);
}
