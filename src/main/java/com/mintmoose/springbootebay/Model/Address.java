package com.mintmoose.springbootebay.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotBlank
    private String buildingNumber;
    @NotBlank
    private String street;
    @NotBlank
    private String city;
    @NotBlank
    private String country;
    @NotBlank
    private String postcode;


    public Address(Long customerId, String buildingNumber, String street, String city, String country, String postcode) {
        this.customerId = customerId;
        this.buildingNumber = buildingNumber;
        this.street = street;
        this.city = city;
        this.country = country;
        this.postcode = postcode;
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
