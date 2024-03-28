package ru.vados.BannerApp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import ru.vados.BannerApp.dto.CategoryDto;

import javax.validation.Valid;

public interface CategoryService {
    Boolean existsCategoryByName(String categoryName);
    ResponseEntity<Iterable<CategoryDto.Categoryitem>> findAllCategory();
    Boolean existsCategoryById (Long idCategory);

    Boolean existsCategoryByReqName(String categoryName);

    ResponseEntity<Void> addCategory(@Valid CategoryDto.CategoryCreate newCategoryData);
    ResponseEntity<Void> deleteCategory(@Valid CategoryDto.CategoryDelete categoryData) throws JsonProcessingException;
    ResponseEntity<Void> updateCategory(@Valid CategoryDto.CategoryUpdate categoryData);
}
