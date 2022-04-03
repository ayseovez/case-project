package com.getir.assessment.readingisgood.repository;

import com.getir.assessment.readingisgood.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Date;
import java.util.List;

public interface OrderStatisticsRepository extends MongoRepository<Order, String> {
    @Query("{'customerId': ?0, 'orderDate': {$gte: ?1, $lte:?2 }}")
    List<Order> findAllByCustomerIdAndOrderDateBetween(String customerId, Date startDate, Date endDate);
}
