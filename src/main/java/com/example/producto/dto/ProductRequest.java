package com.example.producto.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record ProductRequest(
        @NotBlank(message = "Name is mandatory")
        String name,
        @Positive
        Double price,
        @PositiveOrZero
        Integer stock
) {


}
