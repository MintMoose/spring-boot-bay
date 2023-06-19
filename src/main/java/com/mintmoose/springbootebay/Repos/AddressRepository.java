package com.mintmoose.springbootebay.Repos;

import com.mintmoose.springbootebay.Model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {

    Optional<Address> findByCustomerId(Long customerId);
}
