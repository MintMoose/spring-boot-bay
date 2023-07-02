package com.mintmoose.springbootebay.Service;

import com.mintmoose.springbootebay.Model.Address;
import com.mintmoose.springbootebay.Model.NewAddressRequest;
import com.mintmoose.springbootebay.Model.Product;
import com.mintmoose.springbootebay.Repos.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Address createAddress(NewAddressRequest request, Long customerId) {
        System.out.println(request);
        if (request.buildingNumber() == null || request.buildingNumber().isEmpty() ||
                request.street() == null || request.street().isEmpty() ||
                request.city() == null || request.city().isEmpty() ||
                request.country() == null || request.country().isEmpty() ||
                request.postcode() == null || request.postcode().isEmpty()) {
            throw new IllegalArgumentException("Missing required parameters for creating a product.");
        }

        else {
            Address address = new Address();
            address.setCustomerId(customerId);
            address.setBuildingNumber(request.buildingNumber());
            address.setStreet(request.street());
            address.setCity(request.city());
            address.setCountry(request.country());
            address.setPostcode(request.postcode());

            return addressRepository.save(address);
        }

    }

    public void deleteAddress(Long CustomerId) {
        addressRepository.deleteByCustomerId(CustomerId);
    }

    public Address updateAddress(Long customerId, NewAddressRequest request) {
        Optional<Address> optionalAddress = addressRepository.findByCustomerId(customerId);

        boolean noUpdate = true;

        if (optionalAddress.isPresent()) {
            Address address = optionalAddress.get();

            if (request.buildingNumber() != null && !request.buildingNumber().isEmpty()) {
                address.setBuildingNumber(request.buildingNumber());
                noUpdate = false;
            }
            if (request.street() != null && !request.street().isEmpty()) {
                address.setStreet(request.street());
                noUpdate = false;
            }
            if (request.city() != null && !request.city().isEmpty()) {
                address.setCity(request.city());
                noUpdate = false;
            }
            if (request.country() != null && !request.country().isEmpty()) {
                address.setCountry(request.country());
                noUpdate = false;
            }
            if (request.postcode() != null && !request.postcode().isEmpty()) {
                address.setPostcode(request.postcode());
                noUpdate = false;
            }

            if (noUpdate) {
                throw new IllegalArgumentException("Nothing to update (no user input)");
            }


            return addressRepository.save(address);
        } else {
            throw new IllegalArgumentException("Address not found with Customer ID: " + customerId);
        }
    }



}
