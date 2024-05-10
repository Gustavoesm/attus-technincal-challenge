package com.attus.attusbackendchallenge.model;

import com.attus.attusbackendchallenge.model.exceptions.AddressNotFoundException;
import com.attus.attusbackendchallenge.model.exceptions.MainAddressNotFoundException;
import com.attus.attusbackendchallenge.model.exceptions.MainAddressRemovalException;

import java.util.List;
import java.util.Objects;

public class PersonAddresses {
    private final List<Address> addressList;
    private AddressIdentifier mainAddressId;

    public PersonAddresses(List<Address> addressList, AddressIdentifier mainAddressId) {
        this.addressList = addressList;
        this.mainAddressId = mainAddressId;
    }

    public List<Address> listAll() {
        return addressList;
    }

    public Address getMainAddress() {
        return this.addressList.stream()
                .filter(it -> mainAddressId.value().equals(it.identifier().value()))
                .findFirst()
                .orElseThrow(() -> new MainAddressNotFoundException("Unable to find main address for person"));
    }

    public void addAddress(Address address) {
        addressList.add(address);
    }

    public void changeMainAddress(AddressIdentifier identifier) {
        Address found = this.getAddressFor(identifier);
        this.mainAddressId = found.identifier();
    }

    private Address getAddressFor(AddressIdentifier identifier) {
        return this.addressList.stream()
                .filter(it -> it.identifier().value().equals(identifier.value()))
                .findFirst()
                .orElseThrow(() -> new AddressNotFoundException(
                        "Unable to find address for with id (%s)".formatted(identifier.value())
                ));
    }

    public void removeAddress(AddressIdentifier identifier) {
        if (identifier.value().equals(mainAddressId.value())) {
            throw new MainAddressRemovalException("Cannot remove a main address, " +
                    "please select a new main address first.");
        }

        Address removed = getAddressFor(identifier);
        this.addressList.remove(removed);
    }

    public void replaceAddress(AddressIdentifier identifier, Address newValues) {
        Address address = this.getAddressFor(identifier);
        int foundIndex = this.addressList.indexOf(address);

        addressList.set(foundIndex, newValues);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersonAddresses addresses)) return false;
        return mainAddressId == addresses.mainAddressId && Objects.equals(addressList, addresses.addressList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(addressList, mainAddressId);
    }
}
