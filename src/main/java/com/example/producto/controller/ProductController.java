package com.example.producto.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.producto.model.Product;
import com.example.producto.service.ProductService;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private ProductService service;

    public ProductController(ProductService service){
        this.service = service;
    }

    @GetMapping()
    public ResponseEntity<List<Product>> findAll(){
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id) {
        Optional<Product> product = service.findbyId(id);
        if(!product.isEmpty()){
            return ResponseEntity.ok(product.get());
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping()
    public ResponseEntity<Product> save(@RequestBody Product product){
        service.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> delete(@PathVariable Long id){
        boolean deleted = service.delete(id);
        if(deleted){
            return ResponseEntity.noContent().build();
        }
            return ResponseEntity.notFound().build();  
    }

    @PutMapping ("/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Product product){
        Optional<Product> optional = service.update(id, product);
        if(optional.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(optional.get());
        }
        return ResponseEntity.notFound().build();
    }
}

