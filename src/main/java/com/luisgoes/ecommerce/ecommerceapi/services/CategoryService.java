package com.luisgoes.ecommerce.ecommerceapi.services;

import com.luisgoes.ecommerce.ecommerceapi.entities.Category;
import com.luisgoes.ecommerce.ecommerceapi.repositories.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;


    @Transactional(readOnly = true)
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Category findById(final Long id) {
        return categoryRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

}
