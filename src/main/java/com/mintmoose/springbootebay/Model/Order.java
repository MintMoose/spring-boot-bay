package com.mintmoose.springbootebay.Model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@Table(name = "customer_orders", uniqueConstraints = @UniqueConstraint(columnNames = {"customerId", "products"}))
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long orderId;
    private Long customerId;
    private LocalDateTime orderDate;

    @OneToMany
    @ToString.Exclude
    private List<Product> products;
    private double totalPrice;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    private Long sellerId;

    public Order(Long customerId, List<Product> products, double totalAmount, Long sellerId) {
        this.customerId = customerId;
        this.sellerId = sellerId;
        this.products = products;
        this.totalPrice = totalAmount;
        this.paymentStatus = PaymentStatus.PENDING;
    }

    @PrePersist
    protected void onCreate() {
        orderDate = LocalDateTime.now();
    }

    public boolean isProductAlreadyTaken(Product product) {
        return products.stream().anyMatch(p -> p.getId().equals(product.getId()) || p.getSold());
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Order order = (Order) o;
        return orderId != null && Objects.equals(orderId, order.orderId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
