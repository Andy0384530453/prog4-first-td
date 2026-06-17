package com.example.demo.controller;

import com.example.demo.entity.Book;
import com.example.demo.service.BookService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

  @GetMapping("/{id}")
  public ResponseEntity<?> getBooksById(@PathVariable UUID id){
    Book book = bookService.getBookById(id);

    if(book == null){
    return ResponseEntity.badRequest().body("Book not found");
  }
    else return ResponseEntity.ok(book);
  }

}

