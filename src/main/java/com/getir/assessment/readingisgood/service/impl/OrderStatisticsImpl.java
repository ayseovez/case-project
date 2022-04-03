package com.getir.assessment.readingisgood.service.impl;

import com.getir.assessment.readingisgood.model.Order;
import com.getir.assessment.readingisgood.payload.response.StatisticsResponse;
import com.getir.assessment.readingisgood.repository.OrderStatisticsRepository;
import com.getir.assessment.readingisgood.service.OrderStatisticsService;
import com.getir.assessment.readingisgood.util.enums.ResponseInfoEnum;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderStatisticsImpl implements OrderStatisticsService {
    private final Logger LOGGER = LogManager.getLogger(OrderStatisticsImpl.class);

    private final OrderStatisticsRepository orderStatisticsRepository;

    @Override
    public List<StatisticsResponse> getOrderStatistics(String customerId, Date startDate, Date endDate) {
        List<StatisticsResponse> responseList = new ArrayList<>();
        try {
            List<Order> orders = orderStatisticsRepository.findAllByCustomerIdAndOrderDateBetween(customerId, startDate, endDate);

            StatisticsResponse orderInfo = new StatisticsResponse();

            orderInfo.setTotalOrder(orders.size());
            orders.stream().forEachOrdered(e -> {
                e.getBooks().forEach(s -> {
                    orderInfo.setTotalPurchasedBook(orderInfo.getTotalPurchasedBook()+s.getSoldQuantity());
                });
                orderInfo.setTotalPurchasedAmount((e.getAmount()));
                orderInfo.setMonth(e.getOrderDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getMonth());
            });
            responseList.add(orderInfo);
            LOGGER.info(customerId+" Customer’s monthly order statistics served");

        }catch (Exception e){
            responseList.get(0).setResponseStatus(ResponseInfoEnum.FAIL.getStatus());
            responseList.get(0).setResponseMessage(ResponseInfoEnum.FAIL.getMessage());
            LOGGER.error(e.getMessage());
        }

        return responseList;
    }
}
