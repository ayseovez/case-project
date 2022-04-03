package com.getir.assessment.readingisgood.repository;

import com.getir.assessment.readingisgood.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String> {
    @Query("{'orderDate': {$gte: ?0, $lte:?1 }}")
    List<Order> findAllByOrderDateBetween(Date startDate, Date endDate);

    @Query("{'customerId': ?0}")
    Page<Order> findAllByCustomerId(String customerId, Pageable paging);
}
