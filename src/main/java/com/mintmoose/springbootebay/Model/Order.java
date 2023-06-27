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
@Table(name = "customer_orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long orderId;
    private Long customerId;
    private LocalDateTime orderDate;

    @OneToOne
    @ToString.Exclude
    private Product product;
    private double totalPrice;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    private Long sellerId;

    public Order(Long customerId, Product product, double totalAmount, Long sellerId) {
        this.customerId = customerId;
        this.sellerId = sellerId;
        this.product = product;
        this.totalPrice = totalAmount;
        this.paymentStatus = PaymentStatus.PENDING;
    }

    @PrePersist
    protected void onCreate() {
        orderDate = LocalDateTime.now();
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
