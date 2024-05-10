package com.attus.attusbackendchallenge.controller;

import com.attus.attusbackendchallenge.controller.dto.AddressDto;
import com.attus.attusbackendchallenge.controller.dto.PersonAddressesDto;
import com.attus.attusbackendchallenge.model.DatabaseIdentifier;
import com.attus.attusbackendchallenge.service.AddressService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.attus.attusbackendchallenge.fixtures.AddressFixture.anAddress;
import static com.attus.attusbackendchallenge.fixtures.IntegrationTestsFixture.*;
import static com.attus.attusbackendchallenge.fixtures.PersonAddressFixture.aPersonAddresses;
import static com.attus.attusbackendchallenge.service.helpers.AddressServiceHelper.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class AddressControllerTest {

    @Autowired
    private AddressController controller;

    @MockBean
    private AddressService service;

    @Test
    void shouldDelegateGetToService() {
        when(service.listAddressesFor(any())).thenReturn(aPersonAddresses());
        ResponseEntity<PersonAddressesDto> response = controller.listAddresses(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(toDtoGroup(aPersonAddresses()), response.getBody());
    }

    @Test
    void shouldDelegateInsertToService() {
        AddressDto dto = aRandomDto();
        when(service.addAddressTo(DatabaseIdentifier.of(1L), toAddress(dto))).thenReturn(anAddress());
        ResponseEntity<AddressDto> response = controller.insertAddress(1L, dto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(toDto(anAddress()), response.getBody());
    }

    @Test
    void shouldDelegateUpdateToService() {
        AddressDto dto = aRandomDto();
        when(service.replaceAddress(DatabaseIdentifier.of(1L), toAddress(dto))).thenReturn(anAddress());
        ResponseEntity<AddressDto> response = controller.updateAddress(1L, dto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(toDto(anAddress()), response.getBody());
    }

    @Test
    void shouldDelegateMainAddressChangeToService() {
        when(service.changeMainAddress(aPersonWithAnAddress(), anAddressId())).thenReturn(aPersonAddresses());
        ResponseEntity<PersonAddressesDto> response = controller.changeMainAddress(100L, 99L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(toDtoGroup(aPersonAddresses()), response.getBody());
    }

    @Test
    void shouldDelegateRemoveToService() {
        when(service.removeAddress(DatabaseIdentifier.of(1L))).thenReturn(anAddress());
        ResponseEntity<AddressDto> response = controller.deleteAddress(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(toDto(anAddress()), response.getBody());
    }
}