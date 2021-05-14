package com.example.braiveassignment.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class FlightAPIControllerTestIT {

    MockMvc mockMvc;
    @Autowired
    WebApplicationContext wac;


    private MultiValueMap<String, String> params(){
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("name", "SAS");
        params.add("number", "143");
        params.add("scheduledTime", "2021-05-16T13:30");
        params.add("arrivalTime", "2021-05-16T15:30");
        params.add("destination","Athens");
        params.add("departure","Paris");
        params.add("fare","234.5");
        return params;
    }
    private MultiValueMap<String, String> paramsInit(){
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("name", "SAS");
        params.add("number", "143");
        params.add("scheduledTime", "2021-05-16T13:30");
        params.add("arrivalTime", "2021-05-16T15:30");
        params.add("destination","Athens");
        params.add("departure","Paris");
        params.add("fare","234.5");
        return params;
    }


    @BeforeEach
    void setup() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).apply(springSecurity()).build();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/add").params(paramsInit()).with(httpBasic("Admin", "adminPass")));
    }
    @Test
    void addFlightForNoRegisteredUserShouldFail() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/add").params(params()).with(httpBasic("Unknown","Unknown"))).andExpect(status().isUnauthorized());
    }
    @Test
    void addFlightRegisteredUserShouldBeOK() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/add").params(params()).with(httpBasic("Admin","adminPass"))).andExpect(status().isOk());
    }
    @Test
    void getFlightsShouldReturnUnauthorizedForWrongUser() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/showAll")).andExpect(status().isUnauthorized());
    }

    @Test
    void searchFlightShouldReturnUnauthorizedForWrongUser() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/search")).andExpect(status().isUnauthorized());
    }

    @Test
    void searchFlightShouldReturnOkForRegisteredUser() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/search").with(httpBasic("Admin","adminPass"))).andExpect(status().isOk());
    }

    @Test
    void deleteFlightShouldReturnUnauthorizedForWrongUser() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/delete?id=1")).andExpect(status().isUnauthorized());

    }

    @Test
    void deleteFlightShouldReturnOKForRegisteredUser() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/delete?id=1").with(httpBasic("Admin","adminPass"))).andExpect(status().isOk());
    }

    @Test
    void updateFlightShouldReturnUnauthorizedForWrongUser() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/update?id=1")).andExpect(status().isUnauthorized());
    }

    @Test
    void updateFlightShouldReturnOKForRegisteredUser() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/update?id=1").with(httpBasic("User","userPass"))).andExpect(status().isOk());
    }

}
