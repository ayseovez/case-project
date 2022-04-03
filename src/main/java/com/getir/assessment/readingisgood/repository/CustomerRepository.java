package com.getir.assessment.readingisgood.repository;

import com.getir.assessment.readingisgood.model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface CustomerRepository extends MongoRepository<Customer, String> {
    @Query("{'_id': ?0}")
    Optional<Customer> findById (String id);
}
