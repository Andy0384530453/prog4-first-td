package com.example.demo.controller;

import com.example.demo.entity.BookCopy;
import com.example.demo.service.BookCopyService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book-copies")
@RequiredArgsConstructor
public class BookCopyController {

  private final BookCopyService bookCopyService;

  @GetMapping
  public ResponseEntity<List<BookCopy>> getAllBookCopies() {
    return ResponseEntity.ok(bookCopyService.getAllBookCopies());
  }

  @GetMapping("/{id}")
  public ResponseEntity<BookCopy> getBookCopyById(@PathVariable UUID id) {
    return bookCopyService
        .findById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }
}
