package com.example.books.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerIT {
    
    private MockMvc mockMvc;

    @Test
    public void testThatBookIsCreated(){
        
    }
}
