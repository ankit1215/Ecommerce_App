package com.ems.CategoryManagement;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {

    private Long categoryId; // Used only in responses

    @NotBlank(message = "Category name is required")
    private String categoryName;
}
