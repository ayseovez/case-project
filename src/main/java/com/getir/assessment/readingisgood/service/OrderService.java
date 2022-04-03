package com.getir.assessment.readingisgood.service;

import com.getir.assessment.readingisgood.model.Order;
import com.getir.assessment.readingisgood.payload.request.CreateOrderRequest;
import com.getir.assessment.readingisgood.payload.response.CreateOrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface OrderService {
    CreateOrderResponse createOrder(CreateOrderRequest request);
    List<Order> getOrderListByDate(Date startDate, Date endDate);
    Page<Order> getOrderListByCustomerId(String customerId, Pageable paging);
}
