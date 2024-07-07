package com.example.providers.services;

import com.example.providers.model.Provider;
import com.example.providers.repositories.ProviderRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProviderService {

    private static final Logger logger = LoggerFactory.getLogger(ProviderService.class);
    private final RestTemplate restTemplate = new RestTemplate();
    private final ProviderRepository providerRepository;

    /* Retrieve all providers */
    public List<Provider> getProviders() {
        return providerRepository.findAll();
    }

    /* Create a new provider */
    public ResponseEntity<Object> newProvider(Provider provider) {
        Long productId = provider.getProductId();
        String url = "http://localhost:8083/api/products/find/" + productId;

        try {
            ResponseEntity<Object> response = restTemplate.getForEntity(url, Object.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                // Product found
                Provider savedProvider = providerRepository.save(provider);
                return new ResponseEntity<>("Provider created with ID " + savedProvider.getId(), HttpStatus.CREATED);
            } else {
                // Product not found
                return new ResponseEntity<>("Product not found, provider not created", HttpStatus.NOT_FOUND);
            }
        } catch (HttpClientErrorException.NotFound ex) {
            return new ResponseEntity<>("Product not found, provider not created", HttpStatus.NOT_FOUND);
        }
    }

    /* Delete a provider */
    public ResponseEntity<Object> deleteProvider(Long id) {
        Optional<Provider> existingProviderOptional = providerRepository.findById(id);
        if (existingProviderOptional.isPresent()) {
            providerRepository.deleteById(id);
            return new ResponseEntity<>("Provider deleted with ID " + id, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Provider not found", HttpStatus.NOT_FOUND);
        }
    }

    /* Update an existing provider */
    public ResponseEntity<Object> updateProvider(Long id, Provider updatedProvider) {
        Optional<Provider> existingProviderOptional = providerRepository.findById(id);
        if (existingProviderOptional.isPresent()) {
            Provider existingProvider = existingProviderOptional.get();
            existingProvider.setName(updatedProvider.getName());
            existingProvider.setDni(updatedProvider.getDni());
            existingProvider.setAddress(updatedProvider.getAddress());
            existingProvider.setPhone(updatedProvider.getPhone());
            existingProvider.setEmail(updatedProvider.getEmail());
            existingProvider.setProductId(updatedProvider.getProductId());
            existingProvider.setDate(updatedProvider.getDate());

            providerRepository.save(existingProvider);
            return new ResponseEntity<>("Provider updated with ID " + id, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Provider not found", HttpStatus.NOT_FOUND);
        }
    }
}