package com.ems.ProductManagement;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ems.CategoryManagement.CategoryEntity;
import com.ems.CategoryManagement.CategoryRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository; // Add this

    // get list of all product including active or deleted
    public List<ProductEntity> getAllProducts() {
        return productRepository.findByIsActiveTrueAndIsDeletedFalse();
    }

    // fetch Product by id
    public Optional<ProductEntity> getProductById(Long productId) {
        return productRepository.findByProductId(productId);

    }

    // Create a new product
    public ProductEntity createProduct(ProductEntity productEntity) {
        // Fetch the full category from the database
        CategoryEntity category = categoryRepository.findById(productEntity.getCategory().getCategoryId())
                .orElseThrow(() -> new RuntimeException(
                        "Category Not Found with ID: " + productEntity.getCategory().getCategoryId()));

        productEntity.setCategory(category); // Set the full category object
        productEntity.setActive(true);
        productEntity.setDeleted(false);

        return productRepository.save(productEntity);
    }

    // update exisiting product
    public ProductEntity updateProduct(Long productId, ProductEntity updateProduct) {
        ProductEntity existingProduct = productRepository.findByProductIdAndIsActiveTrueAndIsDeletedFalse(productId)
                .orElseThrow(() -> new RuntimeException("Product Not Found with Id : " + productId));
        existingProduct.setProductName(updateProduct.getProductName());
        existingProduct.setProductDescription(updateProduct.getProductDescription());
        existingProduct.setProductPrice(updateProduct.getProductPrice());
        existingProduct.setProductStock(updateProduct.getProductStock());
        existingProduct.setProductImageURL(updateProduct.getProductImageURL());
        existingProduct.setCategory(updateProduct.getCategory());
        return productRepository.save(updateProduct);
    }

    // soft delete a product(mark as delete)
    public void deleteProduct(Long productId) {
        ProductEntity productEntity = productRepository.findByProductIdAndIsActiveTrueAndIsDeletedFalse(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));
    
        productEntity.setDeleted(true); 
        productEntity.setActive(false);  
        productRepository.save(productEntity);
    }
    

    // Fetch paginated active and non-deleted products
    public Page<ProductEntity> getAllProductsWithPagination(Pageable pageable) {
        return productRepository.findByIsActiveTrueAndIsDeletedFalse(pageable);
    }

}
