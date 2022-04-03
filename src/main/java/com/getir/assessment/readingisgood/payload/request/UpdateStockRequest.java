package com.getir.assessment.readingisgood.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateStockRequest {
    private String id;
    private int stock;
}
