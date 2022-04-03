package com.getir.assessment.readingisgood.service.impl;

import com.getir.assessment.readingisgood.exception.NotFoundException;
import com.getir.assessment.readingisgood.exception.ValidationException;
import com.getir.assessment.readingisgood.model.Customer;
import com.getir.assessment.readingisgood.payload.response.CreateCustomerResponse;
import com.getir.assessment.readingisgood.repository.CustomerRepository;
import com.getir.assessment.readingisgood.service.CustomerService;
import com.getir.assessment.readingisgood.util.enums.ResponseInfoEnum;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final Logger LOGGER = LogManager.getLogger(CustomerServiceImpl.class);


    private final CustomerRepository customerRepository;
    private final PasswordEncoder encoder;

    @Override
    public CreateCustomerResponse createCustomer(Customer customer) {
        CreateCustomerResponse response = new CreateCustomerResponse();
        try{
            validate(customer);
            customer.setPassword(encoder.encode(customer.getPassword()));
            customer.setFirstName(customer.getFirstName().toUpperCase());
            customer.setLastName(customer.getLastName().toUpperCase());
            customerRepository.insert(customer);

            LOGGER.info(customer.getFirstName()+" "+customer.getLastName()+" customer registered");
            response.setFirstName(customer.getFirstName());
            response.setResponseStatus(ResponseInfoEnum.SUCCESS.getStatus());
            response.setResponseMessage(ResponseInfoEnum.SUCCESS.getMessage());
        }catch (ValidationException e){
            response.setResponseStatus(ResponseInfoEnum.FAIL.getStatus());
            response.setResponseMessage(e.getMessage());
        }catch (DuplicateKeyException e){
            response.setResponseStatus(ResponseInfoEnum.FAIL.getStatus());
            response.setResponseMessage("Email has already been registered.");
            LOGGER.error(e.getMessage());
        }catch (Exception e){
            response.setResponseStatus(ResponseInfoEnum.FAIL.getStatus());
            response.setResponseMessage(ResponseInfoEnum.FAIL.getMessage());
            LOGGER.error(e.getMessage());
        }

        return response;
    }

    @Override
    public Optional<Customer> getCustomerById(String id) {

        return Optional.of(customerRepository.findById(id)).orElseThrow(() -> new NotFoundException(id, " Cannot find, Email can wrong."));

    }


    private void validate(Customer customer) {
        if (customer.getFirstName().isEmpty() || customer.getLastName().isEmpty() || customer.getPassword().isEmpty() )
            throw new ValidationException("Please enter all fields of User completely.");
        else if (customer.getAddress().getCountry().isEmpty() ||
                customer.getAddress().getCity().isEmpty() || customer.getAddress().getTown().isEmpty() || customer.getAddress().getAddressDetail().isEmpty())
            throw new ValidationException("Please enter fields of Address completely.");

    }
}
