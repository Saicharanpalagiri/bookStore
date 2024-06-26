package com.booking.booking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.booking.booking.entities.Book;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book,Long>{
    Optional<Book> findByTitleAndAuthor(String title, String author);
}
