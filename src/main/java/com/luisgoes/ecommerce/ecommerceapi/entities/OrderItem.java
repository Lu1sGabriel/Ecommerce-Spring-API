package com.luisgoes.ecommerce.ecommerceapi.entities;

import com.luisgoes.ecommerce.ecommerceapi.entities.primaryKeys.OrderItemPK;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "tb_order_item")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class OrderItem implements Serializable {

    @Serial
    private static final long serialVersionUID = -3779640313301681361L;

    @EmbeddedId
    @Setter(AccessLevel.NONE)
    private OrderItemPK id;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "price")
    private BigDecimal price;

    public OrderItem(Order order, Product product, Integer quantity, BigDecimal price) {
        id.setOrder(order);
        id.setProduct(product);
        this.quantity = quantity;
        this.price = price;
    }

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

}