package com.springboot.blog.controller;

import com.springboot.blog.dto.CategoryDto;
import com.springboot.blog.service.CategoryService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * Get All categories
     * @return list of categories
     */
    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        LOGGER.info("Inside CategoryController.class getAllCategories()");
        return new ResponseEntity<>(categoryService.getAllCategories(), HttpStatus.OK);
    }

    /**
     * Get Category detail by id
     * @param id id of the category details
     * @return Category object with data
     */
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable(value = "id") Long id) {
        LOGGER.info("Inside CategoryController.class getCategory");
        return new ResponseEntity<>(categoryService.getCategory(id), HttpStatus.OK);
    }

    /**
     * Add new category
     * @param categoryDto Request object with category details
     * @return Category object response
     */
    @PreAuthorize("hasRole('ADMIN')") // only accessible by ADMIN
    @PostMapping
    public ResponseEntity<CategoryDto> addCategory(@Valid @RequestBody CategoryDto categoryDto) {
        LOGGER.info("Inside CategoryController.class addCategory()");
        return new ResponseEntity<>(categoryService.addCategory(categoryDto), HttpStatus.CREATED);
    }

    /**
     * Update category details
     * @param id id of the category to be updated
     * @param categoryDto Category request object with data
     * @return Category object with updated data
     */
    @PreAuthorize("hasRole('ADMIN')") // only accessible by ADMIN
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable(value = "id") Long id,
            @Valid @RequestBody CategoryDto categoryDto) {
        LOGGER.info("Inside CategoryController.class addCategory()");
        return new ResponseEntity<>(categoryService.updateCategory(id, categoryDto), HttpStatus.CREATED);
    }


    /**
     * Delete category by id
     * @param id id of the category to be deleted
     * @return success message
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable(value = "id") Long id) {
        LOGGER.info("Inside CategoryController.class deleteCategory()");
        categoryService.deleteCategory(id);
        return new ResponseEntity<>("Category deleted successfully", HttpStatus.OK);

    }
}
