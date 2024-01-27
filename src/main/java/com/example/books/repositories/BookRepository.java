package com.example.books.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.books.domain.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {
    
}
