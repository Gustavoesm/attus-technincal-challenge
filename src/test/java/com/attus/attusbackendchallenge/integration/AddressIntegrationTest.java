package com.attus.attusbackendchallenge.integration;

import com.attus.attusbackendchallenge.controller.dto.AddressDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = {"classpath:addressIntegrationSetup.sql"})
@Transactional
public class AddressIntegrationTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldGetMultipleAddresses() throws Exception {
        String expectedResponse = "{\"all\":[{\"id\":1000,\"postalCode\":\"31275-000\",\"state\":\"Minas Gerais\",\"city\":\"Belo Horizonte\",\"street\":\"Av. Antônio Abrahão Caram\",\"number\":\"1001\"},{\"id\":1001,\"postalCode\":\"40050-565\",\"state\":\"Bahia\",\"city\":\"Salvador\",\"street\":\"Ladeira da Fonte das Pedras\",\"number\":\"s/n\"},{\"id\":1002,\"postalCode\":\"90810-240\",\"state\":\"Rio Grande do Sul\",\"city\":\"Porto Alegre\",\"street\":\"Av. Padre Cacique\",\"number\":\"891\"}],\"mainAddress\":{\"id\":1002,\"postalCode\":\"90810-240\",\"state\":\"Rio Grande do Sul\",\"city\":\"Porto Alegre\",\"street\":\"Av. Padre Cacique\",\"number\":\"891\"}}";

        mockMvc.perform(get("/person/100/address")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse));
    }

    @Test
    void shouldGetOneAddress() throws Exception {
        String expectedResponse = "{\"all\":[{\"id\":1003,\"postalCode\":\"20271-150\",\"state\":\"Rio de Janeiro\",\"city\":\"Rio de Janeiro\",\"street\":\"R. Prof. Eurico Rabelo\",\"number\":\"s/N\"}],\"mainAddress\":{\"id\":1003,\"postalCode\":\"20271-150\",\"state\":\"Rio de Janeiro\",\"city\":\"Rio de Janeiro\",\"street\":\"R. Prof. Eurico Rabelo\",\"number\":\"s/N\"}}";

        mockMvc.perform(get("/person/101/address")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse));
    }

    @Test
    void shouldGetNoAddress() throws Exception {
        mockMvc.perform(get("/person/102/address")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().bytes(new byte[0]));
    }

    @Test
    void shouldCreateAddress() throws Exception {
        String expectedResponse = "{\"id\":1,\"postalCode\":\"80250-070\",\"state\":\"Paraná\",\"city\":\"Curitiba\",\"street\":\"R. Buenos Aires\",\"number\":\"1260\"}";
        AddressDto dto = new AddressDto(null, "80250-070", "PR", "Curitiba", "R. Buenos Aires", "1260 ");
        String request = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post("/person/101/address")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse));
    }

    @Test
    void shouldCreateFirstAddress() throws Exception {
        String expectedResponse = "{\"id\":2,\"postalCode\":\"80250-070\",\"state\":\"Paraná\",\"city\":\"Curitiba\",\"street\":\"R. Buenos Aires\",\"number\":\"1260\"}";
        AddressDto dto = new AddressDto(null, "80250-070", "PR", "Curitiba", "R. Buenos Aires", "1260 ");
        String request = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post("/person/102/address")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse));
    }

    @Test
    void shouldUpdateAddress() throws Exception {
        String expectedResponse = "{\"id\":1003,\"postalCode\":\"80250-070\",\"state\":\"Paraná\",\"city\":\"Curitiba\",\"street\":\"R. Buenos Aires\",\"number\":\"1260\"}";
        AddressDto dto = new AddressDto(null, "80250-070", "PR", "Curitiba", "R. Buenos Aires", "1260 ");
        String request = objectMapper.writeValueAsString(dto);

        mockMvc.perform(put("/address/1003")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse));
    }

    @Test
    void shouldNotUpdateAddress() throws Exception {
        AddressDto dto = new AddressDto(null, "80250-070", "PR", "Curitiba", "R. Buenos Aires", "1260 ");
        String request = objectMapper.writeValueAsString(dto);

        mockMvc.perform(put("/address/-1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(status().reason("Unable to find Address"))
                .andExpect(content().bytes(new byte[0]));
    }

    @Test
    void shouldRemoveAddress() throws Exception {
        String expectedResponse = "{\"id\":1000,\"postalCode\":\"31275-000\",\"state\":\"Minas Gerais\",\"city\":\"Belo Horizonte\",\"street\":\"Av. Antônio Abrahão Caram\",\"number\":\"1001\"}";

        mockMvc.perform(delete("/address/1000")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse));
    }

    @Test
    void shouldNotRemoveMainAddress() throws Exception {
        mockMvc.perform(delete("/address/1002")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(status().reason("You cannot remove a person's main address"))
                .andExpect(content().bytes(new byte[0]));
    }

    @Test
    void shouldNotRemoveOnlyAddress() throws Exception {
        mockMvc.perform(delete("/address/1003")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(status().reason("You cannot remove a person's main address"))
                .andExpect(content().bytes(new byte[0]));
    }

    @Test
    void shouldChangeMainAddress() throws Exception {
        String expectedResponse = "{\"all\":[{\"id\":1000,\"postalCode\":\"31275-000\",\"state\":\"Minas Gerais\",\"city\":\"Belo Horizonte\",\"street\":\"Av. Antônio Abrahão Caram\",\"number\":\"1001\"},{\"id\":1001,\"postalCode\":\"40050-565\",\"state\":\"Bahia\",\"city\":\"Salvador\",\"street\":\"Ladeira da Fonte das Pedras\",\"number\":\"s/n\"},{\"id\":1002,\"postalCode\":\"90810-240\",\"state\":\"Rio Grande do Sul\",\"city\":\"Porto Alegre\",\"street\":\"Av. Padre Cacique\",\"number\":\"891\"}],\"mainAddress\":{\"id\":1000,\"postalCode\":\"31275-000\",\"state\":\"Minas Gerais\",\"city\":\"Belo Horizonte\",\"street\":\"Av. Antônio Abrahão Caram\",\"number\":\"1001\"}}";

        mockMvc.perform(post("/100/address/1000")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse));
    }

    @Test
    void shouldNotChangeMainAddress() throws Exception {
        mockMvc.perform(post("/100/address/1003")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(status().reason("Unable to find Address"))
                .andExpect(content().bytes(new byte[0]));
    }
}
