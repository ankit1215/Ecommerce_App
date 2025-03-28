package com.ems.CategoryManagement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // Create a category
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO savedCategory = categoryService.createCategory(categoryDTO);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Category created successfully!");
        response.put("date", savedCategory);
        return ResponseEntity.ok(response);
    }

    // Update existing category
    @PutMapping("/{categoryId}")
    public ResponseEntity<Map<String, Object>> updateCategory(@PathVariable Long categoryId,
            @Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO updateCategory = categoryService.updateCategory(categoryId, categoryDTO);
        Map<String, Object> response = new HashMap<>();
        response.put("success", "1");
        response.put("message", "Category Updated successfully!");
        response.put("data", updateCategory);
        return ResponseEntity.ok(response);
    }

    // Soft delete a category marks as deleted
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Map<String, Object>> deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
        Map<String, Object> response = new HashMap<>();
        response.put("success", "1");
        response.put("message", "Category Deleted successfully!");
        return ResponseEntity.ok(response);
    }

    // Fetch category by id
    @GetMapping("/{categoryId}")
    public ResponseEntity<Map<String, Object>> getCategoryById(@PathVariable Long categoryId) {
        CategoryDTO categoryById = categoryService.getCategoryById(categoryId);
        Map<String, Object> response = new HashMap<>();
        response.put("success", "1");
        response.put("data", categoryById);
        return ResponseEntity.ok(response);
    }

    // get All category by List
    @GetMapping("/getAllCategories")
    public ResponseEntity<Map<String, Object>> getAllCategories() {
        List<CategoryDTO> categories = categoryService.getAllCategories();
        Map<String, Object> response = new HashMap<>();
        response.put("success", "1");
        response.put("data", categories);
        return ResponseEntity.ok(response);

    }

    @GetMapping("/getCategoriesWithPagination")
    public ResponseEntity<Map<String, Object>> getCategoriesWithPagination(Pageable pageable) {
        Page<CategoryDTO> categories = categoryService.getAllCategoriesWithPagination(pageable);
        Map<String, Object> response = new HashMap<>();
        response.put("success", "1");
        response.put("totalPages", categories.getTotalPages());
        response.put("totalElement", categories.getTotalElements());
        response.put("data", categories.getContent());
        return ResponseEntity.ok(response);

    }

}
