package com.example.products.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "This field cannot be empty")
    private String sku;

    @NotBlank(message = "This field cannot be empty")
    private String name;

    @NotNull(message = "This field cannot be null")
    @DecimalMin(value = "0.0", message = "Must be greater than or equal to zero")
    private double price;

    private Boolean status;

    @NotBlank(message = "This field cannot be empty")
    private String description;

    @NotNull(message = "This field cannot be null")
    @DecimalMin(value = "0.0", message = "Must be greater than or equal to zero")
    private double weight;

    @NotBlank(message = "This field cannot be empty")
    private String category;

    @NotBlank(message = "This field cannot be empty")
    private String color;
}