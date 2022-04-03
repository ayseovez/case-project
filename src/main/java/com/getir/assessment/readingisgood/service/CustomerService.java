package com.getir.assessment.readingisgood.service;

import com.getir.assessment.readingisgood.model.Customer;
import com.getir.assessment.readingisgood.payload.response.CreateCustomerResponse;

import java.util.Optional;

public interface CustomerService {
    CreateCustomerResponse createCustomer(Customer customer);
    Optional<Customer> getCustomerById(String id);

}
