package com.getir.assessment.readingisgood.service.impl;

import com.getir.assessment.readingisgood.model.Book;
import com.getir.assessment.readingisgood.model.Customer;
import com.getir.assessment.readingisgood.model.Order;
import com.getir.assessment.readingisgood.payload.request.CreateOrderRequest;
import com.getir.assessment.readingisgood.payload.response.CreateOrderResponse;
import com.getir.assessment.readingisgood.repository.BookRepository;
import com.getir.assessment.readingisgood.repository.CustomerRepository;
import com.getir.assessment.readingisgood.repository.OrderRepository;
import com.getir.assessment.readingisgood.util.enums.BookCategoriesEnum;
import com.getir.assessment.readingisgood.util.enums.OrderStatusEnum;
import com.getir.assessment.readingisgood.util.enums.PaymentStatusEnum;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class OrderServiceImplTest {

    @InjectMocks
    OrderServiceImpl orderService;

    @Mock
    OrderRepository orderRepository;

    @Mock
    CustomerRepository customerRepository;

    @Mock
    BookRepository bookRepository;


    @Test
    void createOrder() {
        CreateOrderRequest request = new CreateOrderRequest();
        List<Book> books= new ArrayList<>();

        Book book2 = new Book();
        book2.setBookName("Rezonans Kanunu");
        book2.setSoldQuantity(2);
        books.addAll(books);

        request.setCustomerId("ayse.ovez@gmail.com");
        request.setPassword("q1w2e3r4");
        request.setBooks(books);

        Customer customer = null;
        customer.setId("ayse.ovez@gmail.com");
        customer.setPassword("q1w2e3r4");
        customer.setFirstName("Ayşe");
        customer.setLastName("Övez");

        Mockito.when(customerRepository.findById("ayse.ovez@gmail.com")).thenReturn(Optional.of(customer));

        Order order = new Order();
        order.setId("624982e31444d655219ce964");
        order.setBooks(books);
        order.setAmount(50.00);
        order.setCustomerId(request.getCustomerId());
        order.setPassword(request.getPassword());
        order.setOrderDate(new Date());
        order.setOrderStatusEnum(OrderStatusEnum.COMPLETED);
        order.setPaymentStatusEnum(PaymentStatusEnum.PAID);

        Mockito.when(orderRepository.save(order)).thenReturn(order);

        CreateOrderResponse result = orderService.createOrder(request);

        Assert.assertEquals(result.getId(), order.getId());


    }

    @Test
    void getOrderListByDate() throws ParseException {
        SimpleDateFormat DateFor = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = DateFor.parse("01-04-2022");
        Date endDate = new Date();

        List<Book> books1= new ArrayList<>();

        Book book = new Book();
        book.setBookName("Angels and Demons");
        book.setSoldQuantity(2);
        books1.add(book);


        Order order = new Order();
        order.setId("624982e31444d655219ce964");
        order.setBooks(books1);
        order.setAmount(50.00);
        order.setCustomerId("ayse.ovez@gmail.com");
        order.setPassword("q1w2e3r4");
        order.setOrderDate(new Date());
        order.setOrderStatusEnum(OrderStatusEnum.COMPLETED);
        order.setPaymentStatusEnum(PaymentStatusEnum.PAID);


        List<Book> books2= new ArrayList<>();
        Book book2 = new Book();
        book2.setBookName("Rezonans Kanunu");
        book2.setSoldQuantity(2);
        books2.add(book2);

        Order order2 = new Order();
        order2.setId("624982e31444d655219ce964");
        order2.setBooks(books2);
        order2.setAmount(50.00);
        order2.setCustomerId("ayse.ovez@gmail.com");
        order2.setPassword("q1w2e3r4");
        order2.setOrderDate(new Date());
        order2.setOrderStatusEnum(OrderStatusEnum.COMPLETED);
        order2.setPaymentStatusEnum(PaymentStatusEnum.PAID);

        List<Order> orders = new ArrayList<>();
        orders.add(order);
        orders.add(order2);


        Mockito.when(orderRepository.findAllByOrderDateBetween(startDate, endDate)).thenReturn(orders);

        List<Order> result = orderService.getOrderListByDate(startDate, endDate);

        Assert.assertEquals(result, orders);

    }

    @Test
    void getOrderListByCustomerId() {
        String customerId = "ayse.ovez@gmail.com";
        Pageable paging = PageRequest.of(0, 3);

        List<Book> books1= new ArrayList<>();

        Book book = new Book();
        book.setBookName("Angels and Demons");
        book.setSoldQuantity(2);
        books1.add(book);


        Order order = new Order();
        order.setId("624982e31444d655219ce964");
        order.setBooks(books1);
        order.setAmount(50.00);
        order.setCustomerId("ayse.ovez@gmail.com");
        order.setPassword("q1w2e3r4");
        order.setOrderDate(new Date());
        order.setOrderStatusEnum(OrderStatusEnum.COMPLETED);
        order.setPaymentStatusEnum(PaymentStatusEnum.PAID);


        List<Book> books2= new ArrayList<>();
        Book book2 = new Book();
        book2.setBookName("Rezonans Kanunu");
        book2.setSoldQuantity(2);
        books2.add(book2);

        Order order2 = new Order();
        order2.setId("624982e31444d655219ce964");
        order2.setBooks(books2);
        order2.setAmount(50.00);
        order2.setCustomerId("ayse.ovez@gmail.com");
        order2.setPassword("q1w2e3r4");
        order2.setOrderDate(new Date());
        order2.setOrderStatusEnum(OrderStatusEnum.COMPLETED);
        order2.setPaymentStatusEnum(PaymentStatusEnum.PAID);

        List<Order> orders = new ArrayList<>();
        orders.add(order);
        orders.add(order2);

        long start = paging.getOffset();
        long end = (start + paging.getPageSize()) > orders.size() ? orders.size() : (start + paging.getPageSize());
        int totalRows = orders.size();
        Page<Order> pageToReturnOrder = new PageImpl<Order>(orders.subList((int) start, (int) end), paging, totalRows);

        Mockito.when(orderRepository.findAllByCustomerId(customerId, paging)).thenReturn(pageToReturnOrder);

        Page<Order> result = orderService.getOrderListByCustomerId(customerId, paging);

        Assert.assertEquals(result, pageToReturnOrder);

    }
}