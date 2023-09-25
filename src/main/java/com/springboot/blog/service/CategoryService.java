package com.springboot.blog.service;

import com.springboot.blog.dto.CategoryDto;

import java.util.List;

public interface CategoryService {

    /**
     * Save category details
     * @param categoryDto Category request object with details
     * @return Category object with data
     */
    CategoryDto addCategory(CategoryDto categoryDto);

    /**
     * Get All category details
     * @return list of category details
     */
    List<CategoryDto> getAllCategories();

    /**
     * Get Category By id
     * @param id id of the category details to be fetched
     * @return Category object with data
     */
    CategoryDto getCategory(Long id);


    /**
     * Update Category
     * @param id id of the category
     * @param categoryDto Category request object with data
     * @return upated Category object
     */
    CategoryDto updateCategory(Long id, CategoryDto categoryDto);

    /**
     * Delete category by id
     * @param id id of the category to be deleted
     */
    void deleteCategory(Long id);
}
