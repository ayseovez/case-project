package com.getir.assessment.readingisgood.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateBookResponse extends BaseResponse{
    private String id;
    private String bookName;
}
