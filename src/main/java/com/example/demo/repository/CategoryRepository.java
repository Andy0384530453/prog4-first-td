package com.example.demo.repository;

import com.example.demo.entity.Category;
import com.example.demo.entity.CategoryEnum;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
  Optional<Category> findByCategoryEnum(CategoryEnum categoryEnum);
}
