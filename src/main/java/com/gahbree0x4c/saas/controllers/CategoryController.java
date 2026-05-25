package com.gahbree0x4c.saas.controllers;

import com.gahbree0x4c.saas.requests.CategoryRequest;
import com.gahbree0x4c.saas.responses.CategoryResponse;
import com.gahbree0x4c.saas.services.CategoryService;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@Slf4j
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/{category-id}")
    public ResponseEntity<CategoryResponse> getCategoryById(
            @PathVariable("category-id") final String id) {
        return ResponseEntity.ok(this.categoryService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Void> createCategory(
            @Valid @RequestBody final CategoryRequest categoryRequest) {
        final String id = this.categoryService.create(categoryRequest);
        final URI location = URI.create("/api/v1/categories/" + id);

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{category-id}")
    public ResponseEntity<Void> updateCategory(
            @Valid @RequestBody final CategoryRequest categoryRequest,
            @PathVariable("category-id") final String id) {
        this.categoryService.update(id, categoryRequest);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{category-id}")
    public ResponseEntity<Void> deleteCategoryById(@PathVariable("category-id") final String id) {
        this.categoryService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
