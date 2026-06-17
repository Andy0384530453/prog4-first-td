package com.example.demo.service;

import com.example.demo.entity.Book;
import com.example.demo.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookCreationService {

    private final BookRepository bookRepository;

    public Book createBook(Book book){
        if ((book.getId() == null) || (book.getTitle() == null)) {
            throw new RuntimeException("Field required");
        }
        return bookRepository.save(book);
    }
}
