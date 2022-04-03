package com.getir.assessment.readingisgood.service.impl;

import com.getir.assessment.readingisgood.exception.NotFoundException;
import com.getir.assessment.readingisgood.exception.ValidationException;
import com.getir.assessment.readingisgood.model.Book;
import com.getir.assessment.readingisgood.model.Order;
import com.getir.assessment.readingisgood.payload.request.CreateOrderRequest;
import com.getir.assessment.readingisgood.payload.response.CreateOrderResponse;
import com.getir.assessment.readingisgood.repository.OrderRepository;
import com.getir.assessment.readingisgood.service.BookService;
import com.getir.assessment.readingisgood.service.CustomerService;
import com.getir.assessment.readingisgood.service.OrderService;
import com.getir.assessment.readingisgood.util.enums.OrderStatusEnum;
import com.getir.assessment.readingisgood.util.enums.PaymentStatusEnum;
import com.getir.assessment.readingisgood.util.enums.ResponseInfoEnum;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final Logger LOGGER = LogManager.getLogger(OrderServiceImpl.class);

    private final OrderRepository orderRepository;
    private final BookService bookService;
    private final CustomerService customerService;


    @Override
    public CreateOrderResponse createOrder(CreateOrderRequest request) {
        CreateOrderResponse response = new CreateOrderResponse();
        try {
            customerService.getCustomerById(request.getCustomerId());
            Order order = new Order();
            AtomicReference<Double> amount = new AtomicReference<>(0.0);
            List<Book> bookList = new ArrayList<>();
            bookList.addAll(request.getBooks());

            bookList.stream().forEach(e ->{
                Book book = bookService.getBookInfo(e.getBookName());
                bookService.addQuantity(e.getBookName(), e.getSoldQuantity());
                e.setPrice(book.getPrice());
                amount.updateAndGet(v -> v + (book.getPrice()*e.getSoldQuantity()));

            });
            order.setBooks(bookList);
            order.setAmount(amount.get());
            order.setCustomerId(request.getCustomerId());
            order.setOrderDate(new Date());
            order.setOrderStatusEnum(OrderStatusEnum.COMPLETED);
            order.setPaymentStatusEnum(PaymentStatusEnum.PAID);
            orderRepository.save(order);
            bookService.reduceBookStock(order.getBooks());
            response.setId(order.getId());
            response.setResponseStatus(ResponseInfoEnum.SUCCESS.getStatus());
            response.setResponseMessage("Thanks, you has been ordered");
            LOGGER.info("Order has been registered for Customer - "+request.getCustomerId());
        }catch (NotFoundException | ValidationException e){
            response.setResponseStatus(ResponseInfoEnum.FAIL.getStatus());
            response.setResponseMessage(e.getMessage());
            LOGGER.error("Order hasnot been registered for Customer - "+request.getCustomerId()+" "+e.getMessage());
        }catch (Exception e){
            response.setResponseStatus(ResponseInfoEnum.FAIL.getStatus());
            response.setResponseMessage(ResponseInfoEnum.FAIL.getMessage());
            LOGGER.error("Order hasnot been registered for Customer - "+request.getCustomerId()+" "+e.getMessage());
        }

        return response;
    }

    @Override
    public List<Order> getOrderListByDate(Date startDate, Date endDate) {
        return orderRepository.findAllByOrderDateBetween(startDate, endDate);
    }

    @Override
    public Page<Order> getOrderListByCustomerId(String customerId, Pageable paging) {
        return orderRepository.findAllByCustomerId(customerId, paging);
    }
}
