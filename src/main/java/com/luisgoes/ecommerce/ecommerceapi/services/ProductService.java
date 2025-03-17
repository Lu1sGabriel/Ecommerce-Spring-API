package com.luisgoes.ecommerce.ecommerceapi.services;

import com.luisgoes.ecommerce.ecommerceapi.entities.Product;
import com.luisgoes.ecommerce.ecommerceapi.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Product findById(final UUID id) {
        return repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

}