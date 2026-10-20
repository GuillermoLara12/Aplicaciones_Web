package com.example.producto.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.producto.exception.InsufficientStockException;
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

    public Product update(Long id, Product newProduct, Long categoryId){

        Product productDB = repo.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("Product not found with id" + id)
        );

        Category category = repoCategory.findById(categoryId).orElseThrow(
            () -> new ResourceNotFoundException("Category not found with id: " + categoryId)
        );

        productDB.setName(newProduct.getName());
        productDB.setPrice(newProduct.getPrice());
        productDB.setStock(newProduct.getStock());
        productDB.setCategory(category);
        return repo.save(productDB);
    }

    public Product updateStock(Long id, Integer cantidad, String palabraClave){
        Product productDB = repo.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("Product not found with id" + id)
        );
        String accion = palabraClave.trim().toLowerCase();
    int stockActual = productDB.getStock();

    if (cantidad == null || cantidad <= 0) {
        throw new IllegalArgumentException(
            "La cantidad debe ser mayor que cero"
        );
    }

    switch (accion) {
        case "agregar":
            productDB.setStock(stockActual + cantidad);
            break;

        case "remover":
            if (stockActual < cantidad) {
                throw new InsufficientStockException(
                    "Stock insuficiente. Stock disponible: " + stockActual
                );
            }
            productDB.setStock(stockActual - cantidad);
            break;

        default:
            throw new IllegalArgumentException(
                "La palabra clave debe ser 'agregar' o 'remover'"
            );
    }
    
        return repo.save(productDB);
    }
    public boolean delete(long id){
        if(!repo.existsById(id)){
            return false;
        }
        repo.deleteById(id);
        return true;
    }
}