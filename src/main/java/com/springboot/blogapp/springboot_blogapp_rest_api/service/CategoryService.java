package com.springboot.blogapp.springboot_blogapp_rest_api.service;

import com.springboot.blogapp.springboot_blogapp_rest_api.payload.CategoryDTO;

import java.util.List;

public interface CategoryService {

    CategoryDTO addCategory(CategoryDTO categoryDTO);

    CategoryDTO getCategory(Long categoryId);

    List<CategoryDTO> getAllCategories();

    CategoryDTO updateCategory(CategoryDTO categoryDTO,Long categoryId);

    void deleteCategory(Long categoryId);
}
