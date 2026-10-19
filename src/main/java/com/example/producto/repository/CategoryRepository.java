package com.example.producto.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.producto.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
