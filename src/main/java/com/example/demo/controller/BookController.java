package com.example.demo.controller;

import com.example.demo.entity.Book;
import com.example.demo.service.BookService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

  private final BookService bookService;

  @GetMapping("/sold-today")
  public ResponseEntity<List<Book>> getBooksSoldToday() {
    return ResponseEntity.ok(bookService.getBooksSoldToday());
  }
}
