package com.getir.assessment.readingisgood.service.impl;

import com.getir.assessment.readingisgood.model.Address;
import com.getir.assessment.readingisgood.model.Customer;
import com.getir.assessment.readingisgood.payload.response.CreateCustomerResponse;
import com.getir.assessment.readingisgood.repository.CustomerRepository;
import com.getir.assessment.readingisgood.service.CustomerService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class CustomerServiceImplTest {

    @InjectMocks
    CustomerServiceImpl customerService;

    @Mock
    CustomerRepository customerRepository;

    @Mock
    PasswordEncoder encoder;


    @Test
    void createCustomer() {
        Address address = null;
        address.setCountry("Turkey");
        address.setCity("İstanbul");
        address.setTown("Kartal");
        address.setAddressDetail("Yeni mah. Getir cad. No:5");

        Customer customer = null;
        customer.setId("ayseeove@gmail.com");
        customer.setPassword("qawsed");
        customer.setFirstName("Ayşe");
        customer.setLastName("Övez");
        customer.setAddress(address);

        Mockito.when(customerRepository.insert(customer)).thenReturn(customer);

        CreateCustomerResponse result = customerService.createCustomer(customer);

        Assert.assertEquals(result.getFirstName(), customer.getFirstName());
    }

    @Test
    void getCustomerByIdAndPassword() {
        String id = "ayseovez@gmail.com";

        Customer customer = null;
        customer.setId("ayseovez@gmail.com");
        customer.setPassword("q1w2e3r4");
        customer.setFirstName("Ayşe");
        customer.setLastName("Övez");

        Mockito.when(customerService.getCustomerById(id)).thenReturn(Optional.of(customer));

        Optional<Customer> result = customerService.getCustomerById(id);

        Assert.assertEquals(result, Optional.of(customer));

    }
}