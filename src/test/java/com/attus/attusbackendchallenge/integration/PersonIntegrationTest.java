package com.attus.attusbackendchallenge.integration;

import com.attus.attusbackendchallenge.controller.dto.PersonDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = {"classpath:personIntegrationSetup.sql"})
@Transactional
public class PersonIntegrationTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldListAllPersons() throws Exception {
        String expectedResponse = "[{\"id\":100,\"firstName\":\"Pierre-Emerick\",\"lastName\":\"Aubameyang\",\"birthDate\":\"18-06-1989\"},{\"id\":101,\"firstName\":\"Roman\",\"lastName\":\"Weidenfeller\",\"birthDate\":\"06-08-1980\"},{\"id\":102,\"firstName\":\"Nuri\",\"lastName\":\"Sahin\",\"birthDate\":\"05-09-1988\"},{\"id\":103,\"firstName\":\"Sven\",\"lastName\":\"Bender\",\"birthDate\":\"27-04-1989\"},{\"id\":104,\"firstName\":\"Jakub\",\"lastName\":\"Blaszczykowski\",\"birthDate\":\"14-12-1985\"}]";

        mockMvc.perform(get("/person/all")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse));
    }

    @Test
    void shouldGetAPerson() throws Exception {
        String expectedResponse = "{\"id\":103,\"firstName\":\"Sven\",\"lastName\":\"Bender\",\"birthDate\":\"27-04-1989\"}";

        mockMvc.perform(get("/person/103")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse));
    }

    @Test
    void shouldGetNoPerson() throws Exception {
        String expectedResponse = "{\"id\":103,\"firstName\":\"Sven\",\"lastName\":\"Bender\",\"birthDate\":\"27-04-1989\"}";

        mockMvc.perform(get("/person/-1")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(status().reason("Unable to find Person"))
                .andExpect(content().bytes(new byte[0]));
    }

    @Test
    void shouldCreateAPerson() throws Exception {
        PersonDto personDto = new PersonDto(null, "Manuel", "Neuer", new Date(512276400000L), null);
        String json = objectMapper.writeValueAsString(personDto);
        String expectedResponse = "{\"id\":1,\"firstName\":\"Manuel\",\"lastName\":\"Neuer\",\"birthDate\":\"27-03-1986\"}";

        mockMvc.perform(post("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse));
    }

    @Test
    void shouldUpdateAPerson() throws Exception {
        PersonDto dto = new PersonDto(null, "Mats", "Hummels", new Date(598240800000L), null);
        String json = objectMapper.writeValueAsString(dto);
        String expectedResponse = "{\"id\":102,\"firstName\":\"Mats\",\"lastName\":\"Hummels\",\"birthDate\":\"16-12-1988\"}";

        mockMvc.perform(put("/person/102")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse));
    }

    @Test
    void shouldUpdateAPersonWithPartialUpdates() throws Exception {
        String expectedResponse = "{\"id\":104,\"firstName\":\"Ilkay\",\"lastName\":\"Blaszczykowski\",\"birthDate\":\"14-12-1985\"}";
        mockMvc.perform(put("/person/104")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\": \"Ilkay\"}")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse));

        expectedResponse = "{\"id\":104,\"firstName\":\"Ilkay\",\"lastName\":\"Gundogan\",\"birthDate\":\"14-12-1985\"}";
        mockMvc.perform(put("/person/104")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"lastName\": \"Gundogan\"}")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse));

        expectedResponse = "{\"id\":104,\"firstName\":\"Ilkay\",\"lastName\":\"Gundogan\",\"birthDate\":\"24-10-1990\"}";
        mockMvc.perform(put("/person/104")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"birthDate\":\"24-10-1990\"}")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse));
    }

    @Test
    void shouldUpdateNobody() throws Exception {
        PersonDto dto = new PersonDto(null, "Mats", "Hummels", new Date(598240800000L), null);
        String json = objectMapper.writeValueAsString(dto);

        mockMvc.perform(put("/person/-1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(status().reason("Unable to find Person"))
                .andExpect(content().bytes(new byte[0]));
    }

    @Test
    void shouldRemoveAPersonWithAddresses() throws Exception {
        String expectedResponse = "{\"id\":100,\"firstName\":\"Pierre-Emerick\",\"lastName\":\"Aubameyang\",\"birthDate\":\"18-06-1989\",\"addresses\":{\"all\":[{\"id\":1000,\"postalCode\":\"03034-070\",\"state\":\"São Paulo\",\"city\":\"São Paulo\",\"street\":\"R. Comendador Nestor Pereira\",\"number\":\"33\"},{\"id\":1001,\"postalCode\":\"21870-102\",\"state\":\"Rio de Janeiro\",\"city\":\"Rio de Janeiro\",\"street\":\"R. Sul América\",\"number\":\"950\"},{\"id\":1002,\"postalCode\":\"89801-561\",\"state\":\"Santa Catarina\",\"city\":\"Chapecó\",\"street\":\"R. Clevelândia\",\"number\":\"656e\"}],\"mainAddress\":{\"id\":1001,\"postalCode\":\"21870-102\",\"state\":\"Rio de Janeiro\",\"city\":\"Rio de Janeiro\",\"street\":\"R. Sul América\",\"number\":\"950\"}}}";

        mockMvc.perform(delete("/person/100")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse));
    }

    @Test
    void shouldRemoveAPersonWithoutAddresses() throws Exception {
        String expectedResponse = "{\"id\":101,\"firstName\":\"Roman\",\"lastName\":\"Weidenfeller\",\"birthDate\":\"06-08-1980\"}";

        mockMvc.perform(delete("/person/101")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse));
    }

    @Test
    void shouldRemoveNobody() throws Exception {
        mockMvc.perform(delete("/person/-1")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(status().reason("Unable to find Person"))
                .andExpect(content().bytes(new byte[0]));
    }
}