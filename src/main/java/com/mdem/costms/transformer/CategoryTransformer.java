package com.mdem.costms.transformer;

import com.mdem.costms.DTO.CategoryDto;
import com.mdem.costms.model.Category;
import com.mdem.costms.model.User;

public class CategoryTransformer {
    public static CategoryDto toCategoryDto(Category category) {
        return new CategoryDto(
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.getUser().getId()
        );
    }

    public static Category toCategoryEntity(CategoryDto categoryDto, User user) {
        Category category = new Category();

        if (categoryDto.getId() != null) {
            category.setId(categoryDto.getId());
        }
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        category.setUser(user);

        return category;
    }
}
