package com.example.books.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.books.domain.BookEntity;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, String> {
    
}
