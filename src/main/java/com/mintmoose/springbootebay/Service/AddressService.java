package com.mintmoose.springbootebay.Service;

import com.mintmoose.springbootebay.Model.Address;
import com.mintmoose.springbootebay.Model.NewAddressRequest;
import com.mintmoose.springbootebay.Model.Product;
import com.mintmoose.springbootebay.Repos.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    private final AddressRepository addressRepository;


    @Autowired
    AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    public Address getAddressById(Long id) {
        return addressRepository.findById(id).orElse(null);
    }

    public Address getAddressByCustomerId(Long customerId) {
        return addressRepository.findByCustomerId(customerId).orElse(null);
    }

    public Address createAddress(NewAddressRequest request) {

        if (request.customerId() == null ||
            request.buildingNumber() == null ||
            request.street() == null || request.city() == null ||
            request.country() == null ||
            request.postcode() == null) {
            throw new IllegalArgumentException("Missing required parameters for creating a product.");
        }
        else {
            Address address = new Address();
            address.setCustomerId(request.customerId());
            address.setBuildingNumber(request.buildingNumber());
            address.setStreet(request.street());
            address.setCity(request.city());
            address.setCountry(request.country());
            address.setPostcode(request.postcode());

            return addressRepository.save(address);
        }

    }
}
