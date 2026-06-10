package com.example.demo.repository;

import com.example.demo.entity.BookCopy;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookCopyRepository extends JpaRepository<BookCopy, String> {
  Optional<BookCopy> findByIsbn(String isbn);
}
