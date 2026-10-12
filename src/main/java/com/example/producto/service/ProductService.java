package com.example.producto.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.producto.model.Product;
import com.example.producto.repository.ProductRepository;

@Service 
public class ProductService {
    private ProductRepository repo;
    public ProductService(ProductRepository repo){
        this.repo = repo;
    }

    public List<Product> getAll(){
        return repo.findAll();
    }

    public Optional<Product> findById(Long id){
        return repo.findById(id);
    }

    public Product save(Product product){
        return repo.save(product);
    }

    public Optional<Product> update(long id, Product product){
        Optional<Product> optional = repo.findById(id);
        if(optional.isEmpty()){
            return optional.empty();
        }
        Product productDB = optional.get();
        productDB.setName(product.getName());
        productDB.setPrice(product.getPrice());
        productDB.setStock(product.getStock());
        return Optional.of(repo.save(productDB));
    }

    public boolean delete(long id){
        if(!repo.existsById(id)){
            return false;
        }
        repo.deleteById(id);
        return true;
    }
}