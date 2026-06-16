package com.example.demo.service;

import com.example.demo.entity.BookCopy;
import com.example.demo.repository.BookCopyRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookCopyService {

  private final BookCopyRepository bookCopyRepository;

  @Transactional(readOnly = true)
  public List<BookCopy> getAllBookCopies() {
    return bookCopyRepository.findAll();
  }

  public Optional<BookCopy> findById(UUID id) {
    return bookCopyRepository.findById(id);
  }
}
