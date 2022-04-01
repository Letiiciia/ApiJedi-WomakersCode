package com.bootcampjava.starwars.controller;

import com.bootcampjava.starwars.model.Jedi;
import com.bootcampjava.starwars.service.JediService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class JediControllerTest {

    @MockBean
    private JediService jediService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET/jedi/1 - SUCCESS")
    public void testGetJediByIdWithSuccess() throws Exception {

        //cenario
        Jedi mockJedi = new Jedi(1,"HanSolo",10, 1);

        Mockito.doReturn(Optional.of(mockJedi)).when(jediService).findById(1);

        // execucao
        mockMvc.perform(get("/jedi/{id}", 1))

                // asserts
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))

                .andExpect(header().string(HttpHeaders.ETAG, "\"1\""))
                .andExpect(header().string(HttpHeaders.LOCATION, "/jedi/1"))

                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("HanSolo")))
                .andExpect(jsonPath("$.strength", is(10)))
                .andExpect(jsonPath("$.version", is(1)));


    }

    @Test
    @DisplayName("GET/jedi/1 - Not Found")
    public void testGetJediByIdNotFound() throws Exception {

        Mockito.doReturn(Optional.empty()).when(jediService).findById(1);
        mockMvc.perform(get("/jedi/{1}", 1))
                .andExpect(status().isNotFound());

    }

    @Test
    @DisplayName("POST/jedi - Success")
    public void testSaveWithSuccess() throws Exception {

        Jedi jediMock = new Jedi(1, "HanSolo", 10, 1);

        Mockito.doReturn(jediMock).when(jediService).save(jediMock);

        mockMvc.perform(post("/jedi")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":\"1\",\"name\":\"HanSolo\",\"strength\":\"10\",\"version\":\"1\"}"))
                //.content(asJsonString(jediMock)))
                .andExpect(status().isCreated());

    }

    @Test
    @DisplayName("PUT/update - Success")
    public void testUpdateWithSuccess() throws Exception {

        Jedi jediMock = new Jedi(1, "HanSolo", 10,1);

        Mockito.doReturn(jediMock).when(jediService).update(jediMock);

        mockMvc.perform(put("/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":\"1\",\"name\":\"HanSolo\",\"strength\":\"10\",\"version\":\"1\"}"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("PUT/update - Not Found")
    public void testUpdateNotFound() throws Exception {
        Jedi jedi = new Jedi(1, "HanSolo", 10, 1);
        Mockito.doReturn(null).when(jediService).update(jedi);
        mockMvc.perform(put("/update"))
                .andExpect(status().isNotFound());

    }


    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
    

