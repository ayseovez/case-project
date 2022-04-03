package com.getir.assessment.readingisgood.controller;

import com.getir.assessment.readingisgood.model.Customer;
import com.getir.assessment.readingisgood.payload.response.*;
import com.getir.assessment.readingisgood.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/readingIsGood")
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping("/createCustomer")
    public CreateCustomerResponse createCustomer(@RequestBody Customer request){
        CreateCustomerResponse createCustomerResponse = customerService.createCustomer(request);
        return createCustomerResponse;
    }
}
