package com.ems.CategoryManagement;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    Optional<CategoryEntity> findByCategoryName(String categoryName);

    // Fetch all active and non-deleted products
    List<CategoryEntity> findByIsActiveTrueAndIsDeletedFalse();

    // Fetch a single product by ID (regardless of active/deleted status)
    Optional<CategoryEntity> findByCategoryId(Long categoryId);

    // Fetch only active and non-deleted categories with pagination
    Page<CategoryEntity> findByIsActiveTrueAndIsDeletedFalse(Pageable pageable);

}
