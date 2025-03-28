package com.ems.CategoryManagement;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ems.ExceptionHandling.ResourceAlreadyExistsException;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Optional<CategoryEntity> existingCategory = categoryRepository
                .findByCategoryName(categoryDTO.getCategoryName());
        if (existingCategory.isPresent()) {
            throw new ResourceAlreadyExistsException("Category is already exists, please add another.");
        }
        CategoryEntity categoryEntity = convertToEntity(categoryDTO);
        categoryEntity.setActive(true);
        categoryEntity.setDeleted(false);
        CategoryEntity savedCategory = categoryRepository.save(categoryEntity);

        return convertToDTO(savedCategory);
    }

    // update exisiting category
    public CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDTO) {
        CategoryEntity existingCategory = categoryRepository.findByCategoryId(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found with ID : " + categoryId));
        existingCategory.setCategoryName(categoryDTO.getCategoryName());
        CategoryEntity updateCategory = categoryRepository.save(existingCategory);
        return convertToDTO(updateCategory);
    }

    // Soft delete a category (mark as deleted)
    public void deleteCategory(Long categoryId) {
        CategoryEntity categoryEntity = categoryRepository.findByCategoryId(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + categoryId));
        categoryEntity.setDeleted(true);
        categoryEntity.setActive(false);
        categoryRepository.save(categoryEntity);
    }

    // fetch Category by Id
    public CategoryDTO getCategoryById(Long categoryId) {
        CategoryEntity categoryEntity = categoryRepository.findByCategoryId(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + categoryId));
        return convertToDTO(categoryEntity);
    }

    // get list of all product including active or deleted
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findByIsActiveTrueAndIsDeletedFalse()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Fetch paginated active and non-deleted categories
    public Page<CategoryDTO> getAllCategoriesWithPagination(Pageable pageable) {
        return categoryRepository.findByIsActiveTrueAndIsDeletedFalse(pageable)
                .map(this::convertToDTO);
    }

    // Convert Entity to DTO
    private CategoryDTO convertToDTO(CategoryEntity categoryEntity) {
        return new CategoryDTO(categoryEntity.getCategoryId(), categoryEntity.getCategoryName());
    }

    // Convert DTO to Entity
    private CategoryEntity convertToEntity(CategoryDTO categoryDTO) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setCategoryName(categoryDTO.getCategoryName());
        return categoryEntity;
    }

}
