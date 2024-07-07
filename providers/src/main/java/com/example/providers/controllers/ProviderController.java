package com.example.providers.controllers;

import com.example.providers.model.Provider;
import com.example.providers.services.ProviderService;
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
@RequestMapping(path = "api/providers")
@RequiredArgsConstructor
public class ProviderController {

    private final ProviderService providerService;
    private static final Logger logger = LoggerFactory.getLogger(ProviderController.class);

    /* Retrieve providers */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Provider> getProviders() {
        return providerService.getProviders();
    }

    /* Create a new provider */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> newProvider(@Valid @RequestBody Provider provider, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Error handling
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        ResponseEntity<Object> response = providerService.newProvider(provider);
        if (response.getStatusCode() == HttpStatus.CREATED) {
            logger.info("Provider created with ID: {}", provider.getId());
        }
        return response;
    }

    /* Update an existing provider */
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> updateProvider(@PathVariable("id") Long id, @RequestBody Provider updatedProvider) {
        ResponseEntity<Object> response = providerService.updateProvider(id, updatedProvider);
        if (response.getStatusCode() == HttpStatus.OK) {
            logger.info("Provider updated with ID: {}", id);
        }
        return response;
    }

    /* Delete a provider */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Object> deleteProvider(@PathVariable("id") Long id) {
        ResponseEntity<Object> response = providerService.deleteProvider(id);
        if (response.getStatusCode() == HttpStatus.OK) {
            logger.info("Provider deleted with ID: {}", id);
        }
        return response;
    }
}
