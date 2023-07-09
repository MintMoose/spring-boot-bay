package com.mintmoose.springbootebay.Repos;

import com.mintmoose.springbootebay.Model.Address;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

@Transactional
public interface AddressRepository extends JpaRepository<Address, Long> {

    Optional<Address> findByCustomerId(Long customerId);



    @Modifying
    @Query("DELETE FROM Address a WHERE a.customerId = :customerId")
    void deleteByCustomerId(@Param("customerId") Long customerId);
}
