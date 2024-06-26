package com.attus.attusbackendchallenge.model.repositories;

import com.attus.attusbackendchallenge.model.Address;
import com.attus.attusbackendchallenge.model.AddressIdentifier;
import com.attus.attusbackendchallenge.model.PersonAddresses;
import com.attus.attusbackendchallenge.model.PersonIdentifier;

public interface AddressRepository {
    Address add(PersonIdentifier identifier, Address address, boolean isMainAddress);

    PersonAddresses personAddresses(PersonIdentifier identifier);

    Address find(AddressIdentifier identifier);

    boolean replaceAddress(AddressIdentifier replacedIndex, Address newAddress);

    boolean remove(AddressIdentifier index);

    void clearAddressesFrom(PersonIdentifier personId);

    PersonIdentifier findOwner(AddressIdentifier id);
}
