package com.springboot.blog.controller;

import com.springboot.blog.dto.CategoryDto;
import com.springboot.blog.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "CRUD REST APIs for Category Resource")
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
    @Operation(summary = "Get categories REST API", description = "Get categories REST API to get all categories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "HttpStatus 201 created"),
            @ApiResponse(responseCode = "500", description = "HttpStatus 500 internal server error")
    })
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
    @Operation(summary = "Get category by id REST API", description = "Get category REST API to get category by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "HttpStatus 201 success"),
            @ApiResponse(responseCode = "500", description = "HttpStatus 500 internal server error")
    })
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
    @Operation(summary = "Create category REST API", description = "Create category REST API to create new categories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "HttpStatus 201 created"),
            @ApiResponse(responseCode = "500", description = "HttpStatus 500 internal server error")
    })
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
    @Operation(summary = "Update category by id REST API", description = "Update category REST API to update category by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "HttpStatus 201 success"),
            @ApiResponse(responseCode = "500", description = "HttpStatus 500 internal server error")
    })
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
    @Operation(summary = "Delete category by id REST API", description = "Delete category REST API to delete category by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "HttpStatus 200 success"),
            @ApiResponse(responseCode = "500", description = "HttpStatus 500 internal server error")
    })
    @PreAuthorize("hasRole('ADMIN')") // only accessible by ADMIN
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable(value = "id") Long id) {
        LOGGER.info("Inside CategoryController.class deleteCategory()");
        categoryService.deleteCategory(id);
        return new ResponseEntity<>("Category deleted successfully", HttpStatus.OK);

    }
}
