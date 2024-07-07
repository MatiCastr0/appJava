package com.example.orders.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Product ID cannot be null")
    private Long productId;

    @NotNull(message = "Unit price cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Unit price must be greater than 0")
    private double unitPrice;

    @NotNull(message = "Quantity cannot be null")
    @Min(value = 1, message = "Quantity must be greater than zero")
    private Long quantity;

    @NotNull(message = "Total cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Total must be greater than 0")
    private double total;

    @NotNull(message = "Date cannot be null")
    private LocalDate date;

    @NotBlank(message = "Notes cannot be empty")
    private String notes;
}