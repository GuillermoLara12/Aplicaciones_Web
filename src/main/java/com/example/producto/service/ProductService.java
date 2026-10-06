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

    public Optional<Product> findbyId(Long id){
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
        Product productdb = optional.get();

        productdb.setName(product.getName());
        productdb.setPrice(product.getPrice());
        productdb.setStock(product.getStock());

        return Optional.of(repo.save(productdb));
    }

    public boolean delete(Long id){
        if(repo.existsById(id)){
            return false;
        }
        repo.deleteById(id);
        return true;
    }
}