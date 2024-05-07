package com.attus.attusbackendchallenge.model;

import com.attus.attusbackendchallenge.model.exceptions.AddressNotFoundException;
import com.attus.attusbackendchallenge.model.exceptions.MainAddressRemovalException;

import java.util.List;
import java.util.Objects;

public class PersonAddresses {
    private final List<Address> addressList;
    private int mainAddressIndex;

    public PersonAddresses(List<Address> addressList, int mainAddressIndex) {
        this.addressList = addressList;
        this.mainAddressIndex = mainAddressIndex;
    }

    public List<Address> listAll() {
        return addressList;
    }

    public Address getMainAddress() {
        return addressList.get(mainAddressIndex);
    }

    public void addAddress(Address address) {
        addressList.add(address);
    }

    public void changeMainAddress(Address newMainAddress) {
        this.mainAddressIndex = getAddressIndex(newMainAddress);
    }

    private int getAddressIndex(Address address) {
        int foundIndex = addressList.indexOf(address);
        if (foundIndex >= 0) {
            return foundIndex;
        }

        throw new AddressNotFoundException("Address %s does not exists.".formatted(address));
    }

    public void removeAddress(Address address) {
        int foundIndex = getAddressIndex(address);

        if (foundIndex == mainAddressIndex) {
            throw new MainAddressRemovalException("Cannot remove a main address, " +
                    "please select a new main address first.");
        }

        removeAndUpdateMainAddressIndex(foundIndex);
    }

    private void removeAndUpdateMainAddressIndex(int removalIndex) {
        addressList.remove(removalIndex);
        if (removalIndex < this.mainAddressIndex) {
            this.mainAddressIndex--;
        }
    }

    public void replaceAddress(Address oldAddress, Address newAddress) {
        int foundIndex = getAddressIndex(oldAddress);

        addressList.set(foundIndex, newAddress);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersonAddresses addresses)) return false;
        return mainAddressIndex == addresses.mainAddressIndex && Objects.equals(addressList, addresses.addressList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(addressList, mainAddressIndex);
    }
}
