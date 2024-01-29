package com.example.books;

import com.example.books.domain.Book;
import com.example.books.domain.BookEntity;

/**
 * Utility class for providing test data related to books.
 * This class contains static methods to generate instances of Book and BookEntity for testing purposes.
 */
public final class TestData {

    // Private constructor to prevent instantiation of the utility class.
    private TestData(){

    }

    /**
     * Generates and returns a test instance of the Book class.
     *
     * @return A test Book instance.
     */
    public static Book testBook(){

        return Book.builder()
        .isbn("0099572958")
        .author("Frances Hodgson Burnett")
        .title("The Secret Garden")
        .build();

    }


    /**
     * Generates and returns a test instance of the BookEntity class.
     *
     * @return A test BookEntity instance.
     */
    public static BookEntity testBookEntity(){
        return BookEntity.builder()
        .isbn("0099572958")
        .author("Frances Hodgson Burnett")
        .title("The Secret Garden")
        .build();
    }
}
