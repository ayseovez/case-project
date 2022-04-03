package com.getir.assessment.readingisgood.service;

import com.getir.assessment.readingisgood.payload.response.StatisticsResponse;

import java.util.Date;
import java.util.List;

public interface OrderStatisticsService {
    List<StatisticsResponse> getOrderStatistics(String customerId, Date startDate, Date endDate);
}
