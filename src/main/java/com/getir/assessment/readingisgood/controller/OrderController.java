package com.getir.assessment.readingisgood.controller;

import com.getir.assessment.readingisgood.model.Order;
import com.getir.assessment.readingisgood.payload.request.CreateOrderRequest;
import com.getir.assessment.readingisgood.payload.response.CreateOrderResponse;
import com.getir.assessment.readingisgood.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/readingIsGood")
public class OrderController {
    private final OrderService orderService;

     @PostMapping("/createOrder")
     public CreateOrderResponse createOrder(@RequestBody CreateOrderRequest request){
         CreateOrderResponse response = orderService.createOrder(request);
         return response;
     }

     @GetMapping("/getOrderListByDate/{startDate}/{endDate}")
     public List<Order> getOrderByDate(@PathVariable("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd")  Date startDate,
                                       @PathVariable("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd")  Date endDate){
         return orderService.getOrderListByDate(startDate, endDate);
     }

     @GetMapping("/getOrderListByCustomer/{customerId}")
     public Page<Order> getOrderListByCustomerId(@PathVariable("customerId") String customerId,
                                                 @RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "3") int size){
         Pageable paging = PageRequest.of(page, size);
         return orderService.getOrderListByCustomerId(customerId, paging);
     }
}
