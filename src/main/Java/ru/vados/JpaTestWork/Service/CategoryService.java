package ru.vados.JpaTestWork.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import ru.vados.JpaTestWork.Dto.CategoryDto;
import ru.vados.JpaTestWork.Entity.CategoryEntity;

import javax.validation.Valid;
import java.util.List;

public interface CategoryService {
    Boolean existsCategoryByName(String categoryName);
    ResponseEntity<Iterable<CategoryDto.Categoryitem>> findAllCategory();
    Boolean existsCategoryById (Long idCategory);

    ResponseEntity<Void> addCategory(@Valid CategoryDto.CategoryCreate newCategoryData);
    ResponseEntity<Void> deleteCategory(@Valid CategoryDto.CategoryUpdate categoryData) throws JsonProcessingException;
    ResponseEntity<Void> updateCategory(@Valid CategoryDto.CategoryUpdate categoryData);
}
