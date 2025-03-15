package com.luisgoes.ecommerce.ecommerceapi.entities;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "tb_order")
@Getter
@Setter
@EqualsAndHashCode
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Order implements Serializable {

    @Serial
    private static final long serialVersionUID = -2433046400464961007L;

    public Order() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(AccessLevel.NONE)
    private UUID id;
    @Column(name = "moment")
    private Instant moment;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private User client;

    public Order(Instant moment, User client) {
        this.moment = moment;
        this.client = client;
    }

}