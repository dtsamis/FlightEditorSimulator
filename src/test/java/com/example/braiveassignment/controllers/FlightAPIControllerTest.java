package com.example.braiveassignment.controllers;

import com.example.braiveassignment.Model.FlightsEntity;
import com.example.braiveassignment.TestUtility;
import com.example.braiveassignment.repositories.FlightRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.hamcrest.Matchers.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;


import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
class FlightAPIControllerTest {
    MockMvc mockMvc;
    @Autowired
    WebApplicationContext wac;
    @Autowired
    FlightRepository flightRepository;
    Pageable pageable;

    private MultiValueMap<String, String> params(){
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("name", "SAS");
        params.add("number", "143");
        params.add("scheduledTime", "2021-05-16T13:30");
        params.add("arrivalTime", "2021-05-16T15:30");
        params.add("destination","Athens");
        params.add("departure","Berlin");
        params.add("fare","234.5");
        return params;
    }
    private MultiValueMap<String, String> paramsInit(){
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("name", "Aegean");
        params.add("number", "140");
        params.add("scheduledTime", "2021-05-16T15:30");
        params.add("arrivalTime", "2021-05-16T18:30");
        params.add("destination","Stockholm");
        params.add("departure","Paris");
        params.add("fare","214.5");
        return params;
    }

    @BeforeEach
    void setup() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).apply(springSecurity()).build();
        pageable= PageRequest.of(0,2);
    }

    @Test
    @Transactional
    void WhenAFlightIsAddedNumberOfFlightsIncreaseByOneAndCorrectValuesAreEntered() throws Exception
    {
        long initialCount= flightRepository.count();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/add").params(params()).with(httpBasic("Admin", "adminPass")));
        assertEquals(flightRepository.count(),initialCount+1);
        assertEquals(flightRepository.findByName("SAS",pageable).getContent().get(0).getDeparture(),"Paris");

    }
    @Test
    @Transactional
    void WhenAFlightIsDeletedNumberOfFlightsDecreaseByOne() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/add").params(params()).with(httpBasic("Admin", "adminPass")));
        long initialCount= flightRepository.count();
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/delete").param("id","1").with(httpBasic("Admin","adminPass")));
        assertEquals(flightRepository.count(),initialCount-1);
        assertEquals(flightRepository.findById(1).isPresent(),false);
    }

    @Test
    @Transactional
    void SearchShouldReturnCorrectNumberOfFlights() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/add").params(params()).with(httpBasic("Admin", "adminPass")));
        mockMvc.perform(MockMvcRequestBuilders.post("/api/add").params(paramsInit()).with(httpBasic("Admin", "adminPass")));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/search").param("departure","Berlin").with(httpBasic("Admin", "adminPass")))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.totalItems",is(1)));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/search").param("departure","Berlin").param("destination","Stockholm").with(httpBasic("Admin", "adminPass")))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.totalItems",is(2)));
    }

    @Test
    @Transactional
    void UpdateShouldUpdateRequestedValue() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/add").params(params()).with(httpBasic("Admin", "adminPass")));
        mockMvc.perform(MockMvcRequestBuilders.post("/api/update").param("id","1").param("name","Norwegian").with(httpBasic("Admin", "adminPass")));
        assertEquals(flightRepository.findById(1).get().getName(),"Norwegian");
    }
}