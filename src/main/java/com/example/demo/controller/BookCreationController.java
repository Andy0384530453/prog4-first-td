package com.example.demo.controller;


import com.example.demo.entity.Book;
import com.example.demo.service.BookCreationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/book")
public class BookCreationController {

    private final BookCreationService bookCreationService;

    @PostMapping
    public Book createBook(@RequestParam Book book){
        return bookCreationService.createBook(book);
    }

}
