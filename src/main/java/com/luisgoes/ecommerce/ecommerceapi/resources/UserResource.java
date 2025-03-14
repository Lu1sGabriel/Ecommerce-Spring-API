package com.luisgoes.ecommerce.ecommerceapi.resources;

import com.luisgoes.ecommerce.ecommerceapi.entities.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/users")

public class UserResource {

    @GetMapping
    public ResponseEntity<User> getAll() {
        var user = new User(UUID.randomUUID(), "Luis Gabriel", "luisGoes@gmail.com", "(71) 9 9856-000", UUID.randomUUID().toString().substring(0, 12));
        return ResponseEntity.ok().body(user);
    }


}