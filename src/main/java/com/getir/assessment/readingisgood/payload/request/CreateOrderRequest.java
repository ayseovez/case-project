package com.getir.assessment.readingisgood.payload.request;

import com.getir.assessment.readingisgood.model.Book;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateOrderRequest {
    private String customerId;
    private String password;
    private List<Book> books;
}
