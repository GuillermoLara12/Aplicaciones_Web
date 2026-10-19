package com.example.producto.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.producto.exception.ResourceNotFoundException;
import com.example.producto.model.Category;
import com.example.producto.model.Product;
import com.example.producto.repository.CategoryRepository;
import com.example.producto.repository.ProductRepository;

@Service 
public class ProductService {
    private final ProductRepository repo;
    private final CategoryRepository repoCategory;

    public ProductService(ProductRepository repo, CategoryRepository repoCategory) {
        this.repo = repo;
        this.repoCategory = repoCategory;
    }

    public List<Product> getAll(){
        return repo.findAll();
    }

    public Optional<Product> findById(Long id){
        return repo.findById(id);
    }

    public Product save(Product product, Long categoryId){
        Category category = repoCategory.findById(categoryId).orElseThrow(
            () -> new RuntimeException("Category not found with id: " + categoryId)
        );
        product.setCategory(category);
        return repo.save(product);
    }

    public Optional<Product> update(Long id, Product newProduct, Long categoryId){

        Product productDB = repo.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("Product not found with id" + id)
        );

        Category category = repoCategory.findById(categoryId).orElseThrow(
            () -> new ResourceNotFoundException("Category not found with id: " + categoryId)
        );
        //Product productDB = optional.get();

        productDB.setName(newProduct.getName());
        productDB.setPrice(newProduct.getPrice());
        productDB.setStock(newProduct.getStock());
        productDB.setCategory(category);
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