package com.mintmoose.springbootebay.Service;

import com.mintmoose.springbootebay.Model.Address;
import com.mintmoose.springbootebay.Repos.AddressRepository;
import com.mintmoose.springbootebay.Repos.CustomerRepository;
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
}
