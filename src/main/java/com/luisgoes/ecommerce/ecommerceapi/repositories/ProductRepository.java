package com.luisgoes.ecommerce.ecommerceapi.repositories;

import com.luisgoes.ecommerce.ecommerceapi.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    Product id(UUID id);
}