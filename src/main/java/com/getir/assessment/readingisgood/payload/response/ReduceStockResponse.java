package com.getir.assessment.readingisgood.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReduceStockResponse extends BaseResponse{
    private List<String> bookNames;

}
