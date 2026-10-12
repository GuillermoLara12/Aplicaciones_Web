package com.example.producto.controller;

import java.util.ArrayList;
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

import com.example.producto.dto.ProductRequest;
import com.example.producto.dto.ProductResponse;
import com.example.producto.model.Product;
import com.example.producto.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private ProductService service;

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
        Optional<Product> product = service.findById(id); //product ees una entity y en este punto no es igual al ProductResponse
        if(product.isPresent()){
            return ResponseEntity.ok(toResponse(product.get()));
        }else{
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping
    public ResponseEntity<ProductResponse> save(@Valid @RequestBody ProductRequest product){
        if (product.name().isEmpty()){
             ResponseEntity.badRequest().body("no puede ser asi");
        }
        Product productSaved=service.save(toEntity(product));
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(productSaved));

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
    public ResponseEntity<ProductResponse> update(@Valid @PathVariable Long id,@Valid @RequestBody ProductRequest product){
        Optional<Product> optional = service.update(id,toEntity(product));
        if(optional.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(toResponse( optional.get()));
        }
        return ResponseEntity.notFound().build();

    }

    private ProductResponse toResponse(Product product){
        return new ProductResponse(product.getId(),product.getName(),product.getPrice(),product.getStock());
    }

    private Product toEntity(ProductRequest request){
        Product product = new Product();
        product.setName(request.name());
        product.setPrice(request.price());
        product.setStock(request.stock());
        return product;
    }
}

