package com.gahbree0x4c.saas.mappers;

import com.gahbree0x4c.saas.entities.Category;
import com.gahbree0x4c.saas.requests.CategoryRequest;
import com.gahbree0x4c.saas.responses.CategoryResponse;
import org.springframework.stereotype.Service;

@Service
public class CategoryMapper {
    public Category toEntity(final CategoryRequest request) {
        return Category.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();
    }

    public CategoryResponse toResponse(final Category entity) {
        return CategoryResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .build();
    }
}
