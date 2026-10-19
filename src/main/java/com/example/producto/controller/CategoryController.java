package com.example.producto.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.producto.repository.CategoryRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import com.example.producto.model.Category;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryRepository repoCategory;

    public CategoryController(CategoryRepository repoCategory) {
        this.repoCategory = repoCategory;
    }

    @PostMapping
    public ResponseEntity<Category> save(@RequestBody Category category) {

        return ResponseEntity.ok(repoCategory.save(category));
    }
    
    @GetMapping
    public ResponseEntity<List<Category>> getAll() {
        return ResponseEntity.ok(repoCategory.findAll());
    }
    
}
