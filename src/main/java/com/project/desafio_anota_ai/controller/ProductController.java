package com.project.desafio_anota_ai.controller;

import com.project.desafio_anota_ai.domain.product.Product;
import com.project.desafio_anota_ai.domain.product.ProductRequestDTO;
import com.project.desafio_anota_ai.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Product> insertProduct(@RequestBody ProductRequestDTO productData) {
        Product newCategory = this.productService.insert(productData);
        return ResponseEntity.ok().body(newCategory);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = this.productService.getAll();
        return ResponseEntity.ok().body(products);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable String id, @RequestBody ProductRequestDTO productData) {
        Product updatedProduct = this.productService.update(id, productData);
        return ResponseEntity.ok().body(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable String id) {
        this.productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
