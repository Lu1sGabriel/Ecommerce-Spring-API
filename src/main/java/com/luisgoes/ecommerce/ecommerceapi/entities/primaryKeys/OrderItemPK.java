package com.luisgoes.ecommerce.ecommerceapi.entities.primaryKeys;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.luisgoes.ecommerce.ecommerceapi.entities.Order;
import com.luisgoes.ecommerce.ecommerceapi.entities.Product;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
public class OrderItemPK implements Serializable {

    @Serial
    private static final long serialVersionUID = 5301603157419283241L;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private Product product;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItemPK that = (OrderItemPK) o;
        return Objects.equals(order, that.order) && Objects.equals(product, that.product);
    }

    @Override
    public final int hashCode() {
        return Objects.hash(order, product);
    }

}