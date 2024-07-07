package com.example.products.services;

import com.example.products.model.Product;
import com.example.products.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    /* Retrieve all products */
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    /* Create a new product */
    public ResponseEntity<Object> newProduct(Product product) {
        Product savedProduct = productRepository.save(product);
        // Log the ID of the newly created product
        return new ResponseEntity<>("Product with ID " + savedProduct.getId() + " created successfully", HttpStatus.CREATED);
    }

    /* Delete a product */
    public ResponseEntity<Object> deleteProduct(Long id) {
        Optional<Product> existingProductOptional = productRepository.findById(id);
        if (existingProductOptional.isPresent()) {
            productRepository.deleteById(id);
            return new ResponseEntity<>("Product deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }
    }

    /* Update an existing product */
    public ResponseEntity<Object> updateProduct(Long id, Product updatedProduct) {
        Optional<Product> existingProductOptional = productRepository.findById(id);
        if (existingProductOptional.isPresent()) {
            Product existingProduct = existingProductOptional.get();
            existingProduct.setSku(updatedProduct.getSku());
            existingProduct.setName(updatedProduct.getName());
            existingProduct.setPrice(updatedProduct.getPrice());
            existingProduct.setStatus(updatedProduct.getStatus());
            existingProduct.setDescription(updatedProduct.getDescription());
            existingProduct.setWeight(updatedProduct.getWeight());
            existingProduct.setCategory(updatedProduct.getCategory());
            existingProduct.setColor(updatedProduct.getColor());

            productRepository.save(existingProduct);

            return new ResponseEntity<>("Product updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }
    }

    /* Find a product by ID */
    public ResponseEntity<Object> findByIdProduct(Long id) {
        Optional<Product> existingProductOptional = productRepository.findById(id);
        if (existingProductOptional.isPresent()) {
            Product existingProduct = existingProductOptional.get();
            return new ResponseEntity<>(existingProduct, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }
    }
}