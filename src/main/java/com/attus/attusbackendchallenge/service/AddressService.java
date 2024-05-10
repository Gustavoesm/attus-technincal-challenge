package com.attus.attusbackendchallenge.service;

import com.attus.attusbackendchallenge.model.Address;
import com.attus.attusbackendchallenge.model.AddressIdentifier;
import com.attus.attusbackendchallenge.model.PersonAddresses;
import com.attus.attusbackendchallenge.model.PersonIdentifier;
import com.attus.attusbackendchallenge.model.exceptions.AddressNotFoundException;
import com.attus.attusbackendchallenge.model.exceptions.MainAddressRemovalException;
import com.attus.attusbackendchallenge.model.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.attus.attusbackendchallenge.service.helpers.AddressServiceHelper.updateWithNonNullAttributes;
import static java.util.Objects.isNull;

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
        PersonAddresses addresses = repository.personAddresses(identifier);
        return isNull(addresses);
    }

    public PersonAddresses listAddressesFor(PersonIdentifier identifier) {
        return repository.personAddresses(identifier);
    }

    public Address replaceAddress(AddressIdentifier addressId, Address newValues) {
        Address addressToUpdate = repository.find(addressId);
        updateWithNonNullAttributes(addressToUpdate, newValues);
        repository.replaceAddress(addressId, addressToUpdate);
        return addressToUpdate;
    }

    public Address removeAddress(AddressIdentifier id) {
        Address address = repository.find(id);
        PersonIdentifier owner = repository.findOwner(id);
        PersonAddresses addresses = repository.personAddresses(owner);

        if (addresses.getMainAddress().equals(address)) {
            throw new MainAddressRemovalException("Unable to remove main address");
        }

        if (!repository.remove(id)) {
            throw new AddressNotFoundException("Unable to remove address");
        }

        return address;
    }

    public PersonAddresses changeMainAddress(PersonIdentifier personIdentifier, AddressIdentifier addressIdentifier) {
        PersonAddresses addresses = repository.personAddresses(personIdentifier);
        addresses.changeMainAddress(addressIdentifier);
        addresses.listAll().forEach(replace -> repository.replaceAddress(replace.identifier(), replace));
        return addresses;
    }

    public PersonAddresses clearAddressesFromPerson(PersonIdentifier personId) {
        PersonAddresses addresses = repository.personAddresses(personId);
        repository.clearAddressesFrom(personId);
        return addresses;
    }
}
