package com.luisgoes.ecommerce.ecommerceapi.repositories;

import com.luisgoes.ecommerce.ecommerceapi.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}