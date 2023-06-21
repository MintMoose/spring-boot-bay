package com.mintmoose.springbootebay.Model;

public record NewAddressRequest(Long customerId, String buildingNumber, String street, String city, String country, String postcode) {
}
