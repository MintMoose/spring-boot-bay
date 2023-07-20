package com.mintmoose.springbootebay.api.repository;

import com.mintmoose.springbootebay.Model.Address;
import com.mintmoose.springbootebay.Repos.AddressRepository;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class AddressRepositoryTests {

    @Autowired
    private AddressRepository addressRepository;

    private Long customerId;

    @BeforeEach
    public void setUp() {
        customerId = 12345L;
        Address address = new Address(customerId, "123", "Main Street", "New York", "USA", "10001");
        addressRepository.save(address);
    }

    @Test
    public void addressRepository_FindByCustomerId_ReturnsAddress() {
        Optional<Address> addressReturn = addressRepository.findByCustomerId(customerId);

        Assertions.assertThat(addressReturn).isNotEmpty();
        Assertions.assertThat(addressReturn.get().getCustomerId()).isEqualTo(customerId);
    }

    @Test
    public void addressRepository_DeleteByCustomerId_ReturnsEmptyAddressAfterCheckingExistence() {
        Optional<Address> addressReturn = addressRepository.findByCustomerId(customerId);
        Assertions.assertThat(addressReturn).isNotEmpty();

        addressRepository.deleteByCustomerId(customerId);

        addressReturn = addressRepository.findByCustomerId(customerId);
        Assertions.assertThat(addressReturn).isEmpty();
    }

    @Test
    public void addressRepository_DeleteByNonExistentCustomerId_ReturnsNoException() {
        long nonExistentCustomerId = 999999L;

        Assertions.assertThatCode(() -> addressRepository.deleteByCustomerId(nonExistentCustomerId))
                .doesNotThrowAnyException();
    }
}
