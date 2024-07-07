package com.example.providers.repositories;

import com.example.providers.model.Provider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProviderRepository extends JpaRepository<Provider, Long>{}
