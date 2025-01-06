package com.project.desafio_anota_ai.service;

import com.project.desafio_anota_ai.domain.category.Category;
import com.project.desafio_anota_ai.domain.category.CategoryRequestDTO;
import com.project.desafio_anota_ai.domain.category.exceptions.CategoryNotFoundException;
import com.project.desafio_anota_ai.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public Category insert(CategoryRequestDTO categoryData) {
        Category newCategory = new Category(categoryData);
        this.repository.save(newCategory);
        return newCategory;
    }

    public List<Category> getAll() {
        return this.repository.findAll();
    }

    public Category update(String id, CategoryRequestDTO categoryData) {
        Category category = this.repository.findById(id).orElseThrow(CategoryNotFoundException::new);

        if (!categoryData.title().isEmpty())category.setTitle(categoryData.title());
        if (!categoryData.description().isEmpty())category.setDescription(categoryData.description());

        return this.repository.save(category);
    }

    public void delete(String id) {
        Category category = this.repository.findById(id).orElseThrow(CategoryNotFoundException::new);
        this.repository.delete(category);
    }

    public Optional<Category> getById(String id) {
        return this.repository.findById(id);
    }
}
