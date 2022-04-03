package com.getir.assessment.readingisgood.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReduceStockRequest {
    private String id;
    private String bookName;
    private int count;
}
