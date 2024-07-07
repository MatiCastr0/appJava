package com.example.providers.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "providers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Provider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "This field cannot be empty")
    private String name;

    @NotBlank(message = "This field cannot be empty")
    private String dni;

    @NotBlank(message = "This field cannot be empty")
    private String address;

    @NotBlank(message = "This field cannot be empty")
    private String phone;

    @NotBlank(message = "This field cannot be empty")
    private String email;

    @NotNull(message = "This field cannot be empty")
    private Long productId;

    @NotNull(message = "This field cannot be empty")
    private LocalDate date;
}