package com.example.braiveassignment.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class UserAPIControllerTestIT {

    MockMvc mockMvc;
    @Autowired
    WebApplicationContext wac;


    @BeforeEach
    void setup() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).apply(springSecurity()).build();

    }

    @Test
    void registerUserMustReturnUnauthorizedWhenUserIsNotAdmin() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/register").param("username","Someone").param("password","password")).andExpect(status().isUnauthorized());
    }

    @Test
    void registerUserMustReturnOkWhenUserIsAdmin() throws Exception
    {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/register").param("username","Someone").param("password","password").with(httpBasic("Admin","adminPass"))).andExpect(status().isOk());
    }
}