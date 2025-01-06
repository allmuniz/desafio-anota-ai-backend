package com.project.desafio_anota_ai.service;

import com.project.desafio_anota_ai.domain.category.Category;
import com.project.desafio_anota_ai.domain.category.exceptions.CategoryNotFoundException;
import com.project.desafio_anota_ai.domain.product.Product;
import com.project.desafio_anota_ai.domain.product.ProductRequestDTO;
import com.project.desafio_anota_ai.domain.product.exceptions.ProductNotFoundException;
import com.project.desafio_anota_ai.repository.ProductRepository;
import com.project.desafio_anota_ai.service.aws.AwsSnsService;
import com.project.desafio_anota_ai.service.aws.MessageDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productrepository;
    private final CategoryService categoryService;
    private final AwsSnsService snsService;

    public ProductService(ProductRepository productrepository, CategoryService categoryService, AwsSnsService snsService) {
        this.productrepository = productrepository;
        this.categoryService = categoryService;
        this.snsService = snsService;
    }

    public Product insert(ProductRequestDTO productData) {
        Category category = this.categoryService.getById(productData.categoryId()).orElseThrow(CategoryNotFoundException::new);

        Product newProduct = new Product(productData);
        newProduct.setCategory(category);

        this.productrepository.save(newProduct);
        this.snsService.publish(new MessageDTO(newProduct.getOwnerId()));

        return newProduct;
    }

    public List<Product> getAll() {
        return this.productrepository.findAll();
    }

    public Product update(String id, ProductRequestDTO productData) {
        Product product = this.productrepository.findById(id).orElseThrow(ProductNotFoundException::new);

        if (!productData.title().isEmpty())product.setTitle(productData.title());
        if (!productData.description().isEmpty())product.setDescription(productData.description());
        if (!(productData.price() == null))product.setPrice(productData.price());
        this.categoryService.getById(productData.categoryId()).ifPresent(product::setCategory);

        this.productrepository.save(product);
        this.snsService.publish(new MessageDTO(product.getOwnerId()));

        return product;
    }

    public void delete(String id) {
        Product product = this.productrepository.findById(id).orElseThrow(ProductNotFoundException::new);
        this.productrepository.delete(product);
    }
}
