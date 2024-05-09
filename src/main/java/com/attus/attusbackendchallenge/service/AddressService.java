package com.attus.attusbackendchallenge.service;

import com.attus.attusbackendchallenge.infra.exceptions.PersonNotFoundException;
import com.attus.attusbackendchallenge.model.Address;
import com.attus.attusbackendchallenge.model.AddressIdentifier;
import com.attus.attusbackendchallenge.model.PersonAddresses;
import com.attus.attusbackendchallenge.model.PersonIdentifier;
import com.attus.attusbackendchallenge.model.exceptions.AddressNotFoundException;
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

    public Address replaceAddress(AddressIdentifier addressId, Address newValues) {
        repository.replaceAddress(addressId, newValues);
        newValues.setIdentifier(addressId);
        return newValues;
    }

    public Address removeAddress(AddressIdentifier id) {
        Address address = repository.find(id);
        if (!repository.remove(id)) {
            throw new AddressNotFoundException("Unable to remove address");
        }
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
