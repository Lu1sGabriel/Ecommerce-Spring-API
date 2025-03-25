package com.luisgoes.ecommerce.ecommerceapi.entities.primaryKeys;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.luisgoes.ecommerce.ecommerceapi.entities.Order;
import com.luisgoes.ecommerce.ecommerceapi.entities.Product;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

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
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        OrderItemPK that = (OrderItemPK) o;
        return getOrder() != null && Objects.equals(getOrder(), that.getOrder())
                && getProduct() != null && Objects.equals(getProduct(), that.getProduct());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(order, product);
    }
}