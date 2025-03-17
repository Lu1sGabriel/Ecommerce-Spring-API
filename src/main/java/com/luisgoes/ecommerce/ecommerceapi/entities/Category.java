package com.luisgoes.ecommerce.ecommerceapi.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;


@Entity
@Table(name = "tb_category")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Category implements Serializable {

    @Serial
    private static final long serialVersionUID = 9025668089659576468L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long Id;

    @Column(name = "name")
    private String name;

    public Category(String name) {
        this.name = name;
    }

}