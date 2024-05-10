package com.attus.attusbackendchallenge.controller;

import com.attus.attusbackendchallenge.controller.dto.AddressDto;
import com.attus.attusbackendchallenge.controller.dto.PersonAddressesDto;
import com.attus.attusbackendchallenge.model.Address;
import com.attus.attusbackendchallenge.model.DatabaseIdentifier;
import com.attus.attusbackendchallenge.model.PersonAddresses;
import com.attus.attusbackendchallenge.service.AddressService;
import com.attus.attusbackendchallenge.service.helpers.AddressServiceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.attus.attusbackendchallenge.service.helpers.AddressServiceHelper.*;

@RestController
@CrossOrigin(origins = {"http://localhost:8080"})
public class AddressController {

    @Autowired
    private AddressService service;

    @GetMapping("/person/{personId}/address")
    public ResponseEntity<PersonAddressesDto> listAddresses(@PathVariable Long personId) {
        PersonAddresses addresses = service.listAddressesFor(DatabaseIdentifier.of(personId));
        return ResponseEntity.ok(AddressServiceHelper.toDtoGroup(addresses));
    }

    @PostMapping("/person/{personId}/address")
    public ResponseEntity<AddressDto> insertAddress(@PathVariable Long personId, @RequestBody AddressDto request) {
        Address address = toAddress(request);
        Address insertedAddress = service.addAddressTo(DatabaseIdentifier.of(personId), address);
        return ResponseEntity.ok(toDto(insertedAddress));
    }

    @PutMapping("/address/{addressId}")
    public ResponseEntity<AddressDto> updateAddress(@PathVariable Long addressId, @RequestBody AddressDto request) {
        Address address = toAddress(request);
        Address updatedAddress = service.replaceAddress(DatabaseIdentifier.of(addressId), address);
        return ResponseEntity.ok(toDto(updatedAddress));
    }

    @PostMapping("/{personId}/address/{addressId}")
    public ResponseEntity<PersonAddressesDto> changeMainAddress(@PathVariable Long personId, @PathVariable Long addressId) {
        PersonAddresses addresses = service.changeMainAddress(DatabaseIdentifier.of(personId), DatabaseIdentifier.of(addressId));
        return ResponseEntity.ok(toDtoGroup(addresses));
    }

    @DeleteMapping("/address/{addressId}")
    public ResponseEntity<AddressDto> deleteAddress(@PathVariable Long addressId) {
        Address removedAddress = service.removeAddress(DatabaseIdentifier.of(addressId));
        return ResponseEntity.ok(toDto(removedAddress));
    }
}
