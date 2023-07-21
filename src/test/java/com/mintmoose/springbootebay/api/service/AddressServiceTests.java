package com.mintmoose.springbootebay.api.service;

import com.mintmoose.springbootebay.Model.Address;
import com.mintmoose.springbootebay.Model.NewAddressRequest;
import com.mintmoose.springbootebay.Repos.AddressRepository;
import com.mintmoose.springbootebay.Service.AddressService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressServiceTests {

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private AddressService addressService;

    @Test
    void testGetAllAddresses() {
        // Arrange
        List<Address> mockAddresses = List.of(
                new Address(1L, "1", "Street 1", "City 1", "Country 1", "12345"),
                new Address(2L, "2", "Street 2", "City 2", "Country 2", "67890")
        );

        when(addressRepository.findAll()).thenReturn(mockAddresses);

        // Act
        List<Address> addresses = addressService.getAllAddresses();

        // Assert
        assertEquals(mockAddresses.size(), addresses.size());
        assertEquals(mockAddresses, addresses);
    }

    @Test
    public void testGetAddressById_ExistingId() {
        // Given
        Long expectedId = 1L;
        Address expectedAddress = new Address(); // Assuming you create an instance of Address with the expected data.

        // When
        when(addressRepository.findById(anyLong())).thenReturn(Optional.of(expectedAddress));
        Address result = addressService.getAddressById(expectedId);

        // Then
        // Use ArgumentCaptor to capture the argument passed to the addressRepository.findById method
        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(addressRepository).findById(argumentCaptor.capture());

        // Assert that the captured value matches the expectedId
        Long actualId = argumentCaptor.getValue();
        assertEquals(expectedId, actualId);

        // Assert that the result returned by the service method is as expected
        assertEquals(expectedAddress, result);
    }

    @Test
    void testGetAddressById_NonExistingId() {
        // Arrange
        Long addressId = 1L;

        when(addressRepository.findById(addressId)).thenReturn(Optional.empty());

        // Act
        Address address = addressService.getAddressById(addressId);

        // Assert
        assertNull(address);
    }

    @Test
    void testGetAddressByCustomerId_ExistingCustomerId() {
        // Arrange
        Long customerId = 1L;
        Address mockAddress = new Address(customerId, "1", "Street 1", "City 1", "Country 1", "12345");

        when(addressRepository.findByCustomerId(customerId)).thenReturn(Optional.of(mockAddress));

        // Act
        Address address = addressService.getAddressByCustomerId(customerId);

        // Assert
        assertNotNull(address);
        assertEquals(customerId, address.getCustomerId());
        assertEquals(mockAddress, address);
    }

    @Test
    void testGetAddressByCustomerId_NonExistingCustomerId() {
        // Arrange
        Long customerId = 1L;

        when(addressRepository.findByCustomerId(customerId)).thenReturn(Optional.empty());

        // Act
        Address address = addressService.getAddressByCustomerId(customerId);

        // Assert
        assertNull(address);
    }

    @Test
    void testCreateAddress_ValidRequest() {
        // Arrange
        Long customerId = 1L;
        NewAddressRequest newAddressRequest = new NewAddressRequest("1", "Street 1", "City 1", "Country 1", "12345");
        Address savedAddress = new Address(customerId, "1", "Street 1", "City 1", "Country 1", "12345");

        when(addressRepository.findByCustomerId(customerId)).thenReturn(Optional.empty());
        when(addressRepository.save(any())).thenReturn(savedAddress);

        // Act
        Address createdAddress = addressService.createAddress(newAddressRequest, customerId);

        // Assert
        assertNotNull(createdAddress);
        assertEquals(customerId, createdAddress.getCustomerId());
        assertEquals(newAddressRequest.buildingNumber(), createdAddress.getBuildingNumber());
        assertEquals(newAddressRequest.street(), createdAddress.getStreet());
        assertEquals(newAddressRequest.city(), createdAddress.getCity());
        assertEquals(newAddressRequest.country(), createdAddress.getCountry());
        assertEquals(newAddressRequest.postcode(), createdAddress.getPostcode());

        // Verify that save method was called with the correct Address object
        ArgumentCaptor<Address> addressCaptor = ArgumentCaptor.forClass(Address.class);
        verify(addressRepository).save(addressCaptor.capture());
        Address capturedAddress = addressCaptor.getValue();
        assertEquals(customerId, capturedAddress.getCustomerId());
        assertEquals(newAddressRequest.buildingNumber(), capturedAddress.getBuildingNumber());
        assertEquals(newAddressRequest.street(), capturedAddress.getStreet());
        assertEquals(newAddressRequest.city(), capturedAddress.getCity());
        assertEquals(newAddressRequest.country(), capturedAddress.getCountry());
        assertEquals(newAddressRequest.postcode(), capturedAddress.getPostcode());
    }

    @Test
    void testCreateAddress_AddressAlreadyExists() {
        // Arrange
        Long customerId = 1L;
        NewAddressRequest newAddressRequest = new NewAddressRequest("1", "Street 1", "City 1", "Country 1", "12345");
        Address existingAddress = new Address(customerId, "1", "Street 1", "City 1", "Country 1", "12345");

        when(addressRepository.findByCustomerId(customerId)).thenReturn(Optional.of(existingAddress));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> addressService.createAddress(newAddressRequest, customerId));
    }

    @Test
    void testCreateAddress_MissingParameters() {
        // Arrange
        Long customerId = 1L;
        NewAddressRequest newAddressRequest = new NewAddressRequest(null, null, null, null, null);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> addressService.createAddress(newAddressRequest, customerId));
    }
}
