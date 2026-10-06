package com.example.producto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.producto.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
