package com.attus.attusbackendchallenge.model.repositories;

import com.attus.attusbackendchallenge.model.Address;
import com.attus.attusbackendchallenge.model.PersonAddresses;
import com.attus.attusbackendchallenge.model.PersonIdentifier;

public interface AddressRepository {
    boolean add(PersonIdentifier identifier, Address address, boolean isMainAddress);

    PersonAddresses personAddresses(PersonIdentifier identifier);

    long findIndexOf(PersonIdentifier identifier, Address address);

    boolean replaceAddress(long replacedIndex, Address newAddress);

    boolean remove(long index);
}
