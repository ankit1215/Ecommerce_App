package com.ems.ProductManagement;

import java.time.LocalDateTime;
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
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

   
 

    // create Product
    @PostMapping("create")
    public ResponseEntity<Map<String, Object>> createProduct(@Valid @RequestBody ProductEntity productEntity) {
        ProductEntity savedProduct = productService.createProduct(productEntity);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Product created successfully!");
        response.put("data", savedProduct);
        response.put("timestamp", LocalDateTime.now());
        return ResponseEntity.ok(response);
    }
    // update exisiting products
    @PutMapping("/{productId}")
    public ResponseEntity<Map<String, Object>> updateProduct(@PathVariable Long productId,
            @Valid @RequestBody ProductEntity updateProduct) {
        ProductEntity updatedProduct = productService.updateProduct(productId, updateProduct);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Product updated successfully!");
        response.put("data", updatedProduct);
        response.put("timestamp", LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

    // Soft Delete a product(mark as deleted instead of removing from db)
    @DeleteMapping("/{productId}")
    public ResponseEntity<Map<String, Object>> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Product deleted successfully!");
        response.put("timestamp", LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

    // Get a sigle product by ID
    @GetMapping("/{productId}")
    public ResponseEntity<Map<String, Object>> getProductById(@PathVariable Long productId) {
        ProductEntity productEntity = productService.getProductById(productId)
                .orElseThrow(() -> new RuntimeException("Product Not Found"));
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Product fetched successfully!");
        response.put("data", productEntity);
        response.put("timestamp", LocalDateTime.now());
        return ResponseEntity.ok(response);
    }


    // Get a list of all products including active or non-deleted
    @GetMapping("/getAllProducts")
    public ResponseEntity<Map<String, Object>> getAllProducts() {
        List<ProductEntity> products = productService.getAllProducts();
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Products fetched successfully!");
        response.put("data", products);
        response.put("timestamp", LocalDateTime.now());
        return ResponseEntity.ok(response);
    }
    // get paginated active and non-delete product
    @GetMapping("/getAllProductsWithPagination")
    public ResponseEntity<Map<String, Object>> getAllProductsWithPagination(Pageable pageable) {
        Page<ProductEntity> productPage = productService.getAllProductsWithPagination(pageable);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Products fetched successfully!");
        response.put("data", productPage.getContent());
        response.put("currentPage", productPage.getNumber());
        response.put("totalPages", productPage.getTotalPages());
        response.put("totalItems", productPage.getTotalElements());
        response.put("timestamp", LocalDateTime.now());
        return ResponseEntity.ok(response);
    }


}
