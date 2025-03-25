package com.luisgoes.ecommerce.ecommerceapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.luisgoes.ecommerce.ecommerceapi.entities.primaryKeys.OrderItemPK;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "tb_order_item")
@NoArgsConstructor
@Getter
@Setter
public class OrderItem implements Serializable {

    @Serial
    private static final long serialVersionUID = 1776564555121607930L;

    @EmbeddedId
    @Setter(AccessLevel.NONE)
    @JsonIgnore
    private OrderItemPK id = new OrderItemPK();

    private Integer quantity;

    private BigDecimal price;

    public OrderItem(Order order, Product product, Integer quantity, BigDecimal price) {
        id.setOrder(order);
        id.setProduct(product);
        this.quantity = quantity;
        this.price = price;
    }

    @JsonIgnore
    public Order getOrder() {
        return id.getOrder();
    }

    public void setOrder(Order order) {
        id.setOrder(order);
    }


    public Product getProduct() {
        return id.getProduct();
    }

    public void setProduct(Product product) {
        id.setProduct(product);
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return Objects.equals(id, orderItem.id);
    }

    @Override
    public final int hashCode() {
        return Objects.hash(id);
    }

}