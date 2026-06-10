package com.example.demo.repository;

import com.example.demo.entity.BookCopy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BookCopyRepository extends JpaRepository<BookCopy, String> {
}
