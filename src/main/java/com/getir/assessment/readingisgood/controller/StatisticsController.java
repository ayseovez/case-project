package com.getir.assessment.readingisgood.controller;

import com.getir.assessment.readingisgood.payload.response.StatisticsResponse;
import com.getir.assessment.readingisgood.service.OrderStatisticsService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/readingIsGood")
public class StatisticsController {
    private final OrderStatisticsService orderStatisticsService;

    @GetMapping("/getTotalOrderInfo/{customerId}/{startDate}/{endDate}")
    public List<StatisticsResponse> getTotalOrderInfo(@PathVariable("customerId") String customerId,
                                                      @PathVariable("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                                      @PathVariable("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd")  Date endDate){
        return orderStatisticsService.getOrderStatistics(customerId, startDate, endDate);
    }
}
