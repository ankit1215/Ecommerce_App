package com.ems.ProductManagement;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    // Fetch all active and non-deleted products
    List<ProductEntity> findByIsActiveTrueAndIsDeletedFalse();

    /**
     * Fetches a list of active and non-deleted products belonging to the specified
     * category.
     * 
     * @param categoryId The ID of the category whose products should be retrieved.
     * @return A list of active (isActive = true) and non-deleted (isDeleted =
     *         false) products.
     */
    List<ProductEntity> findByCategoryCategoryIdAndIsActiveTrueAndIsDeletedFalse(Long categoryId);

    /**
     * Fetches a single product by ID, ensuring it is active and not deleted.
     * 
     * @param productId The ID of the product to retrieve.
     * @return An Optional containing the product if found and active, otherwise
     *         empty.
     */
    Optional<ProductEntity> findByProductIdAndIsActiveTrueAndIsDeletedFalse(Long productId);

    // Fetch a single product by ID (regardless of active/deleted status)
    Optional<ProductEntity> findByProductId(Long productId);

    /**
     * Fetches a paginated list of active and non-deleted products.
     * 
     * @param pageable Pagination and sorting information.
     * @return A paginated list of active (isActive = true) and non-deleted
     *         (isDeleted = false) products.
     */
    Page<ProductEntity> findByIsActiveTrueAndIsDeletedFalse(Pageable pageable);

}
