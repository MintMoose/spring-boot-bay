package com.mintmoose.springbootebay.Model;

public record NewAddressRequest(String buildingNumber, String street, String city, String country, String postcode) {
}
