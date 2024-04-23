package com.booking.booking.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.booking.booking.entities.Book;
import com.booking.booking.entities.User;
import com.booking.booking.repositories.BookRepository;
import com.booking.booking.repositories.UserRepository;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;

    public Book createBook(String username,Book book){
        User user=userRepository.findByUsername(username).get();
        Boolean findRole=user.getRoles().stream().anyMatch(i->i.getName().equals("ADMIN"));   
        if(findRole){
         Optional<Book> existingBook=bookRepository.findByTitleAndAuthor(book.getTitle(),book.getAuthor());
         if(!existingBook.isPresent()){
            book.setAvailable(true);
            Book savedBook=bookRepository.save(book);
            return savedBook;
         }
    }
    return null;
}

public List<Book> listBooks(){
    return bookRepository.findAll();
}

public Object rentABook(String username, Long bookId) {
    // TODO Auto-generated method stub
    User user=userRepository.findByUsername(username)
    .orElseThrow(()->new RuntimeException("User not found"));
    Book book=bookRepository.findById(bookId)
    .orElseThrow(()->new RuntimeException("Book not found with Request Id"));
    if(book.isAvailable()){
        book.setAvailable(false);
        book.setUser(user);
        bookRepository.save(book);
        return book;
    }
    System.out.println(book.isAvailable()+" is book available");
    return new String("Book is not Available");
}

public Object returnABook(String username, Long bookId) {
    // TODO Auto-generated method stub
    User user=userRepository.findByUsername(username)
    .orElseThrow(()->new RuntimeException("User not found"));
    Book book=bookRepository.findById(bookId)
    .orElseThrow(()->new RuntimeException("Book not found with Request Id"));
    if(!book.isAvailable()){
        if(book.getUser().equals(user)){
        book.setAvailable(true);
        book.setUser(null);
        bookRepository.save(book);
        return book;
        }else{
            return new String("Not a Valid User");
        }
    }
    return new String("Book is not rented yet!!");
}
}
