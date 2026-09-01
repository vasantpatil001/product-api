package com.zestindia.productapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ProductRequest(

        @NotBlank(message = "Product name is required")
        @Size(max = 255, message = "Product name must be at most 255 characters")
        String productName

) {
}