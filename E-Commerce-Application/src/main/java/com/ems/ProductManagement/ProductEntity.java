package com.ems.ProductManagement;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.ems.CategoryManagement.CategoryDTO;
import com.ems.CategoryManagement.CategoryEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product")
@AllArgsConstructor
@Data
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productId")
    private Long productId;

    @NotBlank(message = "Product name is required")
    @Column(name = "productName", nullable = false)
    private String productName;

    @NotBlank(message = "Product description is required")
    @Column(name = "productDescription", nullable = false)
    private String productDescription;

    @Positive(message = "Price must be positive")
    @Column(name = "productPrice", nullable = false)
    private double productPrice;

    @Min(value = 0, message = "Stock cannot be negative")
    @Column(name = "productStock", nullable = false)
    private int productStock;

    //@NotBlank(message = "Image URL is required")
    @Column(name = "productImageURL", nullable = false)
    private String productImageURL;

    @NotNull(message = "Category is required")
    @ManyToOne
    @JoinColumn(name = "categoryId", nullable = false)
    private CategoryEntity category;  // Many Products belong to one category

    @CreatedBy
    @JsonIgnore
    private String createdBy;

    @CreatedDate
    @JsonIgnore
    private LocalDateTime createdDate;

    @LastModifiedBy
    @JsonIgnore
    private String lastModifiedBy;

    @LastModifiedDate
    @JsonIgnore
    private LocalDateTime lastModifiedDate;

    @JsonIgnore
    private boolean isActive = true;

    @JsonIgnore
    private boolean isDeleted = false;

}
