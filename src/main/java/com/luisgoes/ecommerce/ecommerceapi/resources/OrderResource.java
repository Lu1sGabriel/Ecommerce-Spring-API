package com.luisgoes.ecommerce.ecommerceapi.resources;

import com.luisgoes.ecommerce.ecommerceapi.entities.Order;
import com.luisgoes.ecommerce.ecommerceapi.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/orders")
public class OrderResource {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<List<Order>> findAll() {
        var oderList = orderService.findAll();
        return ResponseEntity.ok().body(oderList);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<Order> findById(@PathVariable UUID id) {
        var order = orderService.findById(id);
        return ResponseEntity.ok().body(order);
    }

}