package com.luisgoes.ecommerce.ecommerceapi.services;

import com.luisgoes.ecommerce.ecommerceapi.entities.Order;
import com.luisgoes.ecommerce.ecommerceapi.repositories.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Transactional(readOnly = true)
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Order findById(final UUID id) {
        return orderRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

}