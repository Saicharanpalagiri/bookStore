package com.booking.booking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.booking.booking.entities.Book;
import com.booking.booking.entities.User;
import com.booking.booking.repositories.BookRepository;
import com.booking.booking.repositories.UserRepository;
import com.booking.booking.services.BookService;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class BookingTest {
    @Mock
    private BookRepository bookRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private BookService bookService;

    @Test
    public void testRentABookUserNotFound(){
        when(userRepository.findByUsername("testUser"))
        .thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, ()->{
            bookService.rentABook("testUser", 1L);
        });
    }

    @Test
    public void testReturnABookUserNotFound() {
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            bookService.returnABook("testUser", 1L);
        });
    }

    @Test
    public void testRentABookSuccess() {
        User user = new User();
        user.setUsername("testUser");

        Book book = new Book();
        book.setId(1L);
        book.setAvailable(true);

        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Object result = bookService.rentABook("testUser", 1L);

        assertFalse(book.isAvailable());
        assertEquals(user, book.getUser());
        verify(bookRepository, times(1)).save(book);
        assertEquals(book, result);
    }

    @Test
    public void testReturnABookSuccess() {
        User user = new User();
        user.setUsername("testUser");

        Book book = new Book();
        book.setId(1L);
        book.setAvailable(false);
        book.setUser(user);

        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Object result = bookService.returnABook("testUser", 1L);

        assertTrue(book.isAvailable());
        assertNull(book.getUser());
        verify(bookRepository, times(1)).save(book);
        assertEquals(book, result);
    }

}
