package com.getir.assessment.readingisgood.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.Month;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatisticsResponse extends BaseResponse{
    private Month month;
    private int totalOrder;
    private int totalPurchasedBook;
    private Double totalPurchasedAmount;
}
