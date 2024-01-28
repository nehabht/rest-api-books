package com.example.books.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.books.domain.Book;
import com.example.books.domain.BookEntity;
import com.example.books.repositories.BookRepository;


/**
 * Unit test for the BookServiceImpl class.
 *
 * This test ensures that the BookServiceImpl's create method properly saves a BookEntity
 * using the BookRepository, and that the returned Book matches the expected result.
 */
@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl underTest;

    /**
     * Tests that a Book is saved correctly using the create method of BookServiceImpl.
     */
    @Test
    public void testThatBookIsSaved(){
        // Create a sample Book
        final Book book = Book.builder()
        .isbn("0099572958")
        .author("Frances Hodgson Burnett")
        .title("The Secret Garden")
        .build();

        // Create the corresponding BookEntity for comparison
        final BookEntity bookEntity = BookEntity.builder()
        .isbn("0099572958")
        .author("Frances Hodgson Burnett")
        .title("The Secret Garden")
        .build();

        // Configure the mock behavior for the save method
        when(bookRepository.save(eq(bookEntity))).thenReturn(bookEntity);

        // Invoke the create method of BookServiceImpl
        final Book result = underTest.create(book);

        // Verify that the result matches the expected Book
        // assertEquals(book,null); //expected error
        assertEquals(book,result);


    }

    
}
