package com.project.desafio_anota_ai.domain.product;

import com.project.desafio_anota_ai.domain.category.Category;

public record ProductRequestDTO(String title, String description, String ownerId, Integer price, String categoryId) {
}
