package com.springboot.blog.service.impl;

import com.springboot.blog.dto.CategoryDto;
import com.springboot.blog.entity.Category;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.repository.CategoryRepository;
import com.springboot.blog.service.CategoryService;
import com.springboot.blog.util.DataConvertor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        LOGGER.info("Inside CategoryServiceImpl.class addCategory()");
        return DataConvertor
                .categoryEntityToDto(categoryRepository.save(DataConvertor.categoryDtoToEntity(categoryDto)));
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        LOGGER.info("Inside CategoryServiceImpl.class getAllCategories");
        return DataConvertor.categoryEntitiesToDto(categoryRepository.findAll());
    }

    @Override
    public CategoryDto getCategory(Long id) {
        LOGGER.info("Inside CategoryServiceImpl.class getCategory()");
        Category category = getCategoryById(id);
        return DataConvertor.categoryEntityToDto(category);
    }


    @Override
    public CategoryDto updateCategory(Long id, CategoryDto categoryDto) {
        LOGGER.info("Inside CategoryServiceImpl.class updateCategory()");
        Category category = getCategoryById(id);
        return DataConvertor.categoryEntityToDto(categoryRepository
                .save(DataConvertor
                        .updateCategoryEntity(category, categoryDto)));
    }

    @Override
    public void deleteCategory(Long id) {
        LOGGER.info("Inside CategoryServiceImpl.class deleteCategory()");
        Category category = getCategoryById(id);
        categoryRepository.delete(category);
    }

    private Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Category.class.getSimpleName(), "id", String.valueOf(id)));
    }

}
