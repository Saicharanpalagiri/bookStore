package com.booking.booking.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.booking.booking.Payload.BookRequest;
import com.booking.booking.Payload.UserRequest;
import com.booking.booking.entities.Book;
import com.booking.booking.services.BookService;

@RestController
@RequestMapping("api/private/books/")
public class BookController {
    @Autowired
    private BookService bookService;

    @PostMapping("createBook")
    private ResponseEntity<?> addBook(@RequestBody BookRequest bookRequest){
        String username=bookRequest.getUsername();
        Book book=new Book(bookRequest.getTitle(),bookRequest.getAuthor(),
        bookRequest.getGenre());
        Book createdBook=bookService.createBook(username,book);
        return new ResponseEntity(createdBook,HttpStatus.CREATED);
    }

    @GetMapping
    private ResponseEntity<?> listBooks(){
        List<Book> books=bookService.listBooks();
        return new ResponseEntity(books,HttpStatus.OK);
    }



    @PostMapping("{bookId}/rent")
    private ResponseEntity<?> rentABook(@PathVariable("bookId") Long bookId,@RequestBody UserRequest userRequest){
        String username=userRequest.getUsername();
        Object obj=bookService.rentABook(username,bookId);
        return new ResponseEntity(obj,HttpStatus.OK);
    }

    @PostMapping("{bookId}/return")
    private ResponseEntity<?> returnABook(@PathVariable("bookId") Long bookId,@RequestBody UserRequest userRequest){
        String username=userRequest.getUsername();
        Object obj=bookService.returnABook(username,bookId);
        return new ResponseEntity(obj,HttpStatus.OK);
    }
}
