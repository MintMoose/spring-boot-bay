package com.mintmoose.springbootebay.api.controller;

import com.mintmoose.springbootebay.Controller.AddressController;
import com.mintmoose.springbootebay.Model.Address;
import com.mintmoose.springbootebay.Model.Customer;
import com.mintmoose.springbootebay.Model.NewAddressRequest;
import com.mintmoose.springbootebay.Service.AddressService;
import com.mintmoose.springbootebay.Service.CustomerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AddressControllerTest {

    @InjectMocks
    private AddressController addressController;

    @Mock
    private AddressService addressService;

    @Mock
    private CustomerService customerService;

    @Test
    public void testGetAddressById() {
        // Mock Authentication
        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getName()).thenReturn("test_user");

        // Mock AddressService behavior
        Long addressId = 1L;
        Address address = new Address();
        address.setCustomerId(1L);
        when(addressService.getAddressById(addressId)).thenReturn(address); // Return a valid Address object

        // Mock CustomerService behavior
        Customer requestCustomer = new Customer();
        requestCustomer.setCustomerId(1L);
        requestCustomer.setUsername("test_user");
        when(customerService.getCustomerByUsername("test_user")).thenReturn(Optional.of(requestCustomer));
        when(customerService.getCustomerById(1L)).thenReturn(Optional.of(requestCustomer));

        // Perform the test
        ResponseEntity<?> response = addressController.getAddressById(addressId, authentication);

        // Assert the response
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        // You may add more assertions based on your specific use case
    }

    @Test
    public void testGetAddressByIdUnauthorized() {
        // Mock Authentication
        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(false);

        // Mock AddressService behavior
        Long addressId = 1L;
        Address address = new Address();
        address.setCustomerId(1L);
        when(addressService.getAddressById(addressId)).thenReturn(address); // Return a valid Address object

        // Perform the test with unauthorized access
        ResponseEntity<?> response = addressController.getAddressById(1L, authentication);

        // Assert the response
        assertNotNull(response);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        // You may add more assertions based on your specific use case
    }


@Test
    public void testCreateAddress() {
        // Mock Authentication
        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getName()).thenReturn("test_user");

        // Mock CustomerService behavior
        Customer requestCustomer = new Customer();
        requestCustomer.setCustomerId(1L);
        requestCustomer.setUsername("test_user");
        when(customerService.getCustomerByUsername("test_user")).thenReturn(Optional.of(requestCustomer));

        // Perform the test
        NewAddressRequest request = new NewAddressRequest("145", "bond", "london", "uk", "WK134"); // Add necessary fields
        ResponseEntity<?> response = addressController.createAddress(request, authentication);

        // Assert the response
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        // You may add more assertions based on your specific use case
    }

    @Test
    public void testUpdateAddress_Success() {
        // Mock Authentication
        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getName()).thenReturn("test_user");

        // Mock CustomerService behavior
        Customer requestCustomer = new Customer();
        requestCustomer.setCustomerId(1L);
        requestCustomer.setUsername("test_user");
        when(customerService.getCustomerByUsername("test_user")).thenReturn(Optional.of(requestCustomer));

        // Mock AddressService behavior
        Address existingAddress = new Address(1L, "145", "old_street", "old_city", "old_country", "old_postal_code");
        existingAddress.setId(1L);
        when(addressService.getAddressByCustomerId(requestCustomer.getCustomerId())).thenReturn(existingAddress);

        // New Address details for update
        NewAddressRequest updatedAddressRequest = new NewAddressRequest("123", "new_street", "new_city", "new_country", "new_postal_code");
        Address updatedAddressReturn = new Address(1L, "123", "new_street", "new_city", "new_country", "new_postal_code");
        when(addressService.updateAddress(requestCustomer.getCustomerId(), updatedAddressRequest)).thenReturn(updatedAddressReturn);

        // Perform the test
        ResponseEntity<?> response = addressController.updateAddress("test_user", updatedAddressRequest, authentication);

        // Assert the response
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Address updatedAddress = (Address) response.getBody();
        System.out.println(response);
        System.out.println(response.getBody());
        System.out.println(updatedAddress);
        assertNotNull(updatedAddress);
        assertEquals(updatedAddressRequest.street(), updatedAddress.getStreet());
        assertEquals(updatedAddressRequest.city(), updatedAddress.getCity());
        assertEquals(updatedAddressRequest.country(), updatedAddress.getCountry());
        assertEquals(updatedAddressRequest.postcode(), updatedAddress.getPostcode());
    }

    @Test
    public void testUpdateAddress_UnauthorizedAccess() {
        // Mock Authentication
        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getName()).thenReturn("test_user");

        // Mock CustomerService behavior
        Customer requestCustomer = new Customer();
        requestCustomer.setCustomerId(1L);
        requestCustomer.setUsername("test_user");
        when(customerService.getCustomerByUsername("test_user")).thenReturn(Optional.of(requestCustomer));

        // Mock AddressService behavior
        when(addressService.getAddressByCustomerId(requestCustomer.getCustomerId())).thenReturn(null); // No address associated with this user

        // New Address details for update
        NewAddressRequest updatedAddressRequest = new NewAddressRequest("123", "new_street", "new_city", "new_country", "new_postal_code");

        // Perform the test
        ResponseEntity<?> response = addressController.updateAddress("test_user", updatedAddressRequest, authentication);

        // Assert the response
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Address not found.", response.getBody());
    }

    @Test
    public void testDeleteAddress_Success() {
        // Mock Authentication
        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getName()).thenReturn("test_user");

        // Mock CustomerService behavior
        Customer requestCustomer = new Customer();
        requestCustomer.setCustomerId(1L);
        requestCustomer.setUsername("test_user");
        when(customerService.getCustomerByUsername("test_user")).thenReturn(Optional.of(requestCustomer));

        // Mock AddressService behavior
        Address existingAddress = new Address(1L, "145", "old_street", "old_city", "old_country", "old_postal_code");
        existingAddress.setId(1L);
        when(addressService.getAddressByCustomerId(requestCustomer.getCustomerId())).thenReturn(existingAddress);

        // Perform the test
        ResponseEntity<?> response = addressController.deleteAddress(requestCustomer.getCustomerId(), authentication);

        // Assert the response
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeleteAddress_UnauthorizedAccess() {
        // Mock Authentication
        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getName()).thenReturn("test_user");

        // Mock CustomerService behavior
        Customer requestCustomer = new Customer();
        requestCustomer.setCustomerId(1L);
        requestCustomer.setUsername("test_user");

        // Mock AddressService behavior
        when(addressService.getAddressByCustomerId(requestCustomer.getCustomerId())).thenReturn(null); // No address associated with this user

        // Perform the test
        ResponseEntity<?> response = addressController.deleteAddress(requestCustomer.getCustomerId(), authentication);

        // Assert the response
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Address not found.", response.getBody());
    }

    @Test
    public void testGetAddressByCustomerId_Success() {
        // Mock Authentication
        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getName()).thenReturn("test_user");

        // Mock CustomerService behavior
        Long customerId = 1L;
        Customer requestCustomer = new Customer( "test_user", "Test User", "email", "password");
        when(customerService.getCustomerById(customerId)).thenReturn(Optional.of(requestCustomer));

        // Mock AddressService behavior
        Address address1 = new Address(1L, "145", "street1", "city1", "country1", "postcode1");
        when(addressService.getAddressByCustomerId(customerId)).thenReturn(address1);

        // Perform the test
        ResponseEntity<?> response = addressController.getAddressByCustomerId(customerId, authentication);

        // Assert the response
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Address responseAddresses = (Address) response.getBody();
        assertNotNull(responseAddresses);
        assertEquals(address1.getId(), responseAddresses.getId());

    }

    @Test
    public void testGetAddressByCustomerId_UnauthorizedAccess() {
        // Mock Authentication
        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(false);

        // Mock CustomerService behavior
        Long customerId = 1L;
        Customer requestCustomer = new Customer( "test_user", "Test User", "email", "password");


        // Perform the test with unauthorized access
        ResponseEntity<?> response = addressController.getAddressByCustomerId(customerId, authentication);

        // Assert the response
        assertNotNull(response);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void testGetAddressByCustomerUsername_Success() {
        // Mock Authentication
        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getName()).thenReturn("test_user");

        // Mock CustomerService behavior
        String customerUsername = "test_user";
        Customer requestCustomer = new Customer( "test_user", "Test User", "email", "password");
        when(customerService.getCustomerByUsername(customerUsername)).thenReturn(Optional.of(requestCustomer));

        // Mock AddressService behavior
        Address address1 = new Address(1L, "145", "street1", "city1", "country1", "postcode1");
        when(addressService.getAddressByCustomerId(requestCustomer.getCustomerId())).thenReturn(address1);

        // Perform the test
        ResponseEntity<?> response = addressController.getAddressByCustomerId(customerUsername, authentication);

        // Assert the response
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Address responseAddresses = (Address) response.getBody();
        assertNotNull(responseAddresses);
        assertEquals(address1.getId(), responseAddresses.getId());
    }

    @Test
    public void testGetAddressByCustomerUsername_UnauthorizedAccess() {
        // Mock Authentication
        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(false);

        // Mock CustomerService behavior
        String customerUsername = "another_user";


        // Perform the test with unauthorized access
        ResponseEntity<?> response = addressController.getAddressByCustomerId(customerUsername, authentication);

        // Assert the response
        assertNotNull(response);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

}
