package com.getir.assessment.readingisgood.service.impl.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.getir.assessment.readingisgood.controller.CustomerController;
import com.getir.assessment.readingisgood.model.Address;
import com.getir.assessment.readingisgood.model.Customer;
import com.getir.assessment.readingisgood.payload.response.CreateCustomerResponse;
import com.getir.assessment.readingisgood.service.CustomerService;
import com.getir.assessment.readingisgood.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testcontainers.shaded.com.github.dockerjava.core.dockerfile.DockerfileStatement;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerServiceImplIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    public CustomerController customerController;

    @MockBean
    public CustomerServiceImpl customerService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp(){
        mockMvc= MockMvcBuilders.standaloneSetup(customerController).build();
    }

    void createCustomerTest() throws Exception {
        Address address = Address.builder()
                .country("Turkey")
                .city("İstanbul")
                .town("Kartal")
                .addressDetail("Yeni mah. Getir cad. No:5")
                .build();

        Customer customer = Customer.builder()
                .id("ayse.ovez@gmail.com")
                .firstName("Ayşe")
                .lastName("Övez")
                .password("1q2w34r")
                .address(address)
                .build();

//        when(customerService.createCustomer(any()))
//                .thenReturn(CreateCustomerResponse.builder()
//                        .firstName("Ayşe")
//                        .lastName("Övez")
////                        .build());

        mockMvc.perform(post("/api/readingIsGood/createCustomer")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsBytes(customer))
                ).andExpect(status().is(201));

    }


}
