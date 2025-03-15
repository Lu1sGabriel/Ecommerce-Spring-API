package com.luisgoes.ecommerce.ecommerceapi.repositories;

import com.luisgoes.ecommerce.ecommerceapi.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
}