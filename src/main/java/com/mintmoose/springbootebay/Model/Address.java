package com.mintmoose.springbootebay.Model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@Table(name = "customer_address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    private Long customerId;
    private String buildingNumber;
    private String street;
    private String city;
    private String country;
    private String zipcode;


    public Address(Long customerId, String street, String city, String country) {
        this.customerId = customerId;
        this.street = street;
        this.city = city;
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Address address = (Address) o;
        return Id != null && Objects.equals(Id, address.Id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
