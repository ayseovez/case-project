package com.getir.assessment.readingisgood.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.getir.assessment.readingisgood.util.enums.OrderStatusEnum;
import com.getir.assessment.readingisgood.util.enums.PaymentStatusEnum;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;

@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Order {
    @Id
    private String id;
    private String customerId;
    private String password;
    private OrderStatusEnum orderStatusEnum;
    private PaymentStatusEnum paymentStatusEnum;
    private Double amount;
    private Date orderDate = new Date();

    private List<Book> books;
}
