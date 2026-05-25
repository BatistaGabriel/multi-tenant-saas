package com.gahbree0x4c.saas.services.impl;

import com.gahbree0x4c.saas.entities.Category;
import com.gahbree0x4c.saas.mappers.CategoryMapper;
import com.gahbree0x4c.saas.repositories.CategoryRepository;
import com.gahbree0x4c.saas.requests.CategoryRequest;
import com.gahbree0x4c.saas.responses.CategoryResponse;
import com.gahbree0x4c.saas.services.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public String create(CategoryRequest request) {
        executeCommonValidations(request);

        final Category category = categoryMapper.toEntity(request);
        return this.categoryRepository.save(category).getId();
    }

    @Override
    public void update(String id, CategoryRequest request) {
        executeUpdateValidations(id, request);

        final Category updatedCategory = categoryMapper.toEntity(request);
        updatedCategory.setId(id);

        this.categoryRepository.save(updatedCategory);
    }

    @Override
    public CategoryResponse findById(String id) {
        return this.categoryRepository
                .findById(id)
                .map(categoryMapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
    }

    @Override
    public void deleteById(String id) {
        final Category category =
                this.categoryRepository
                        .findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Category not found"));
        category.setDeleted(true);

        this.categoryRepository.save(category);
    }

    private void executeCommonValidations(CategoryRequest request) {
        categoryNameValidator(request.getName());
    }

    private void categoryNameValidator(String name) {
        final Optional<Category> category = this.categoryRepository.findByNameIgnoreCase(name);
        if (category.isPresent()) {
            log.debug("Category already exists!");
            throw new RuntimeException("Category already exists!");
        }
    }

    private void executeUpdateValidations(String id, CategoryRequest request) {
        final Optional<Category> existingCategory = this.categoryRepository.findById(id);
        if (existingCategory.isEmpty()) {
            log.debug("Category not found!");
            throw new EntityNotFoundException("Category not found!");
        }

        final Category category = existingCategory.get();
        if (!category.getName().equalsIgnoreCase(request.getName())) {
            executeCommonValidations(request);
        }
    }
}
