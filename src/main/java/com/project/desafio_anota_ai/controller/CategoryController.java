package com.project.desafio_anota_ai.controller;

import com.project.desafio_anota_ai.domain.category.Category;
import com.project.desafio_anota_ai.domain.category.CategoryRequestDTO;
import com.project.desafio_anota_ai.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<Category> insertCategory(@RequestBody CategoryRequestDTO categoryData) {
        Category newCategory = this.categoryService.insert(categoryData);
        return ResponseEntity.ok().body(newCategory);
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = this.categoryService.getAll();
        return ResponseEntity.ok().body(categories);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable String id, @RequestBody CategoryRequestDTO categoryData) {
        Category updatedCategory = this.categoryService.update(id, categoryData);
        return ResponseEntity.ok().body(updatedCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Category> deleteCategory(@PathVariable String id) {
        this.categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
