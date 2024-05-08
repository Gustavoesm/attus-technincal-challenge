package com.attus.attusbackendchallenge.service;

import com.attus.attusbackendchallenge.infra.exceptions.PersonNotFoundException;
import com.attus.attusbackendchallenge.model.Address;
import com.attus.attusbackendchallenge.model.AddressIdentifier;
import com.attus.attusbackendchallenge.model.PersonAddresses;
import com.attus.attusbackendchallenge.model.PersonIdentifier;
import com.attus.attusbackendchallenge.model.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AddressService {

    @Autowired
    private AddressRepository repository;

    public Address addAddressTo(PersonIdentifier identifier, Address address) {
        boolean isMainAddress = isFirstAddressOf(identifier);
        return repository.add(identifier, address, isMainAddress);
    }

    private boolean isFirstAddressOf(PersonIdentifier identifier) {
        try {
            repository.personAddresses(identifier);
            return false;
        } catch (PersonNotFoundException e) {
            return true;
        }
    }

    public PersonAddresses listAddressesFor(PersonIdentifier identifier) {
        return repository.personAddresses(identifier);
    }

    public Address replaceAddress(PersonIdentifier personIdentifier, Address oldAddress, Address newAddress) {
        AddressIdentifier oldAddressId = repository.findIndexOf(personIdentifier, oldAddress);
        newAddress.setIdentifier(oldAddressId);
        repository.replaceAddress(oldAddressId, newAddress);
        return newAddress;
    }

    public Address removeAddress(PersonIdentifier personIdentifier, Address address) {
        AddressIdentifier id = repository.findIndexOf(personIdentifier, address);
        address.setIdentifier(id);
        repository.remove(id);
        return address;
    }

    public PersonAddresses changeMainAddress(PersonIdentifier personIdentifier, Address address) {
        PersonAddresses addresses = repository.personAddresses(personIdentifier);
        addresses.changeMainAddress(address);
        addresses.listAll().forEach(replace -> repository.replaceAddress(replace.identifier(), replace));
        return addresses;
    }

    public PersonAddresses clearAddressesFromPerson(PersonIdentifier personId) {
        PersonAddresses addresses = repository.personAddresses(personId);
        repository.clearAddressesFrom(personId);
        return addresses;
    }
}
