package com.example.producto.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.producto.dto.ProductRequest;
import com.example.producto.dto.ProductResponse;
import com.example.producto.dto.StockRequest;
import com.example.producto.exception.ResourceNotFoundException;
import com.example.producto.model.Product;
import com.example.producto.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }
    @GetMapping
    public ResponseEntity <List<ProductResponse>>  findAll(){
        List<ProductResponse> listResponse = new ArrayList<>();
        for(Product p : service.getAll()){
            listResponse.add(toResponse(p));
        }
        return ResponseEntity.ok(listResponse);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getById(@PathVariable Long id){
        Product product = service.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("Product not found with id:" + id)
        );
        return ResponseEntity.ok(toResponse(product));
    }

    @PostMapping
    public ResponseEntity<ProductResponse> save(@Valid @RequestBody ProductRequest product){
        Product newProduct=service.save(toEntity(product), product.categoryId());
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(newProduct));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        boolean deleted = service.delete(id);
        if(deleted){
           return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update(@PathVariable Long id,@Valid @RequestBody ProductRequest productUpdate){
        Product product = service.update(id,toEntity(productUpdate), productUpdate.categoryId());
        return ResponseEntity.ok(toResponse(product));
    }

    @PatchMapping("/{id}/stock")
    public ResponseEntity<ProductResponse> updateStock(@PathVariable Long id,@Valid @RequestBody StockRequest request) {
        Product product = service.updateStock(id, request.cantidad(), request.palabraClave());
        return ResponseEntity.ok(toResponse(product));
    }

    private ProductResponse toResponse(Product product){
        return new ProductResponse(product.getId(),product.getName(),product.getPrice(),product.getStock(), product.getCategory().getId(), product.getCategory().getName());
    }

    private Product toEntity(ProductRequest request){
        Product product = new Product();
        product.setName(request.name());
        product.setPrice(request.price());
        product.setStock(request.stock());
        return product;
    }
}

