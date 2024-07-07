package com.example.products.controllers;

import com.example.products.model.Product;
import com.example.products.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    /* Endpoint to retrieve products */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Product> getProducts() {
        return productService.getProducts();
    }

    /* Endpoint to create a new product */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> newProduct(@Valid @RequestBody Product product, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Error handling
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        ResponseEntity<Object> response = productService.newProduct(product);
        if (response.getStatusCode() == HttpStatus.CREATED) {
            logger.info("Product created with ID: {}", product.getId());
        }
        return response;
    }

    /* Endpoint to update an existing product */
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> updateProduct(@PathVariable("id") Long id, @RequestBody Product updatedProduct) {
        ResponseEntity<Object> response = productService.updateProduct(id, updatedProduct);
        if (response.getStatusCode() == HttpStatus.OK) {
            logger.info("Product updated with ID: {}", id);
        }
        return response;
    }

    /* Endpoint to delete a product */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Object> deleteProduct(@PathVariable("id") Long id) {
        ResponseEntity<Object> response = productService.deleteProduct(id);
        if (response.getStatusCode() == HttpStatus.OK) {
            logger.info("Product deleted with ID: {}", id);
        }
        return response;
    }

    /* Endpoint to find a product by ID */
    @GetMapping("/find/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> findByIdProduct(@PathVariable("id") Long id) {
        ResponseEntity<Object> response = productService.findByIdProduct(id);
        if (response.getStatusCode() == HttpStatus.OK) {
            logger.info("Product found with ID: {}", id);
        }
        return response;
    }
}
