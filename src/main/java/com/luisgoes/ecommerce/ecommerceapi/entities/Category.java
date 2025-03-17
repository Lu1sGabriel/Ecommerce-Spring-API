package com.luisgoes.ecommerce.ecommerceapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tb_category")
@NoArgsConstructor
@Getter
@Setter
public class Category implements Serializable {

    @Serial
    private static final long serialVersionUID = 113946159820703800L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "categories")
    @Setter(AccessLevel.NONE)
    private Set<Product> products = new HashSet<>();


    public Category(String name) {
        this.name = name;
    }

}