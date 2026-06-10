package com.example.demo.service;

import com.example.demo.entity.BookCopy;
import com.example.demo.repository.BookCopyRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookCopyService {

  private final BookCopyRepository bookCopyRepository;

  public Optional<BookCopy> findById(UUID id) {
    return bookCopyRepository.findById(id);
  }
}
