package com.getir.assessment.readingisgood.service.impl;

import com.getir.assessment.readingisgood.model.Book;
import com.getir.assessment.readingisgood.payload.request.UpdateStockRequest;
import com.getir.assessment.readingisgood.payload.response.CreateBookResponse;
import com.getir.assessment.readingisgood.payload.response.ReduceStockResponse;
import com.getir.assessment.readingisgood.payload.response.UpdateStockResponse;
import com.getir.assessment.readingisgood.repository.BookRepository;
import com.getir.assessment.readingisgood.util.enums.BookCategoriesEnum;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class BookServiceImplTest {

    @InjectMocks
    BookServiceImpl bookService;

    @Mock
    BookRepository bookRepository;

    @Mock
    MongoOperations mongoOperations;

    @Test
    void createBookInfo() {
        Book book = new Book();
        book.setBookName("Angels and Demons");
        book.setPrice(37.00);
        book.setStock(100);
        book.setSoldQuantity(2);
        book.setAuthor("Dan Brown");
        book.setImage("image/angel.jpg");
        book.setDescription("Book Description");
        book.setCategory(BookCategoriesEnum.THRILLER);

        Mockito.when(bookRepository.save(book)).thenReturn(book);

        CreateBookResponse result = bookService.createBookInfo(book);

        Assert.assertEquals(result.getBookName(), book.getBookName());
    }

    @Test
    void updateBookStock() {
        UpdateStockRequest request = new UpdateStockRequest();
        request.setId("62483be36683f07ee245c006");
        request.setStock(101);

        Book book = new Book();
        book.setBookName("Angels and Demons");
        book.setPrice(37.00);
        book.setStock(100);
        book.setSoldQuantity(2);
        book.setAuthor("Dan Brown");
        book.setImage("image/angel.jpg");
        book.setDescription("Book Description");
        book.setCategory(BookCategoriesEnum.THRILLER);

        Mockito.when(bookRepository.findById(request.getId())).thenReturn(Optional.of(book));
        book.setStock(102);
        Mockito.when(bookRepository.save(book)).thenReturn(book);

        UpdateStockResponse result = bookService.updateBookStock(request);

        Assert.assertEquals(result.getBookName(), book.getBookName());
    }

    @Test
    void reduceBookStock() {
        List<Book> bookList = new ArrayList<>();

        Book book = new Book();
        book.setBookName("Angels and Demons");
        book.setPrice(37.00);
        book.setStock(100);
        book.setSoldQuantity(2);
        book.setAuthor("Dan Brown");
        book.setImage("image/angel.jpg");
        book.setDescription("Book Description");
        book.setCategory(BookCategoriesEnum.THRILLER);

        Mockito.when(bookRepository.findByBookName(book.getBookName())).thenReturn(Optional.of(book));

        bookList.addAll((Collection<? extends Book>) book);

        int quantity = book.getSoldQuantity();
        Query query = new Query();
        query.addCriteria(Criteria.where("bookName").is(book.getBookName()));
        Update update = new Update();
        int newStock = book.getStock() - quantity;
        update.set("stock", newStock);
        Mockito.when(mongoOperations.findAndModify(query, update, new FindAndModifyOptions().returnNew(true), Book.class)).thenReturn(book);

        ReduceStockResponse result = bookService.reduceBookStock(bookList);

        Assert.assertEquals(result.getBookNames(), book.getBookName());


    }

    @Test
    void getBookInfo() {
        String bookName = "Rezonans Kanunu";

        Book book2 = new Book();
        book2.setBookName("Rezonans Kanunu");
        book2.setPrice(25.00);
        book2.setStock(100);
        book2.setSoldQuantity(2);
        book2.setAuthor("Pierre Franckh");
        book2.setImage("image/flash.jpg");
        book2.setDescription("Book Description");
        book2.setCategory(BookCategoriesEnum.EDUCATION);

        Mockito.when(bookRepository.findByBookName(bookName)).thenReturn(Optional.of(book2));

        Book result = bookService.getBookInfo(bookName);

        Assert.assertEquals(result.getBookName(), book2.getBookName());
    }

    @Test
    void addQuantity() {
        String bookName = "Rezonans Kanunu";
        int quantity = 1;

        Book book2 = new Book();
        book2.setBookName("Rezonans Kanunu");
        book2.setPrice(25.00);
        book2.setStock(100);
        book2.setSoldQuantity(2);
        book2.setAuthor("Pierre Franckh");
        book2.setImage("image/flash.jpg");
        book2.setDescription("Book Description");
        book2.setCategory(BookCategoriesEnum.EDUCATION);
        Mockito.when(bookRepository.findByBookName(bookName)).thenReturn(Optional.of(book2));

        book2.setSoldQuantity(book2.getSoldQuantity()+quantity);
        Mockito.when(bookRepository.save(book2)).thenReturn(book2);

        int result = bookService.addQuantity(bookName, quantity);
        Assert.assertEquals(result, book2.getSoldQuantity());
    }
}