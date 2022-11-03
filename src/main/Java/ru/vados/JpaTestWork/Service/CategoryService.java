package ru.vados.JpaTestWork.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import ru.vados.JpaTestWork.Dto.CategoryDto;
import ru.vados.JpaTestWork.Entity.Category;

import javax.validation.Valid;
import java.util.List;

public interface CategoryService {
    Boolean existsCategoryByName(String categoryName);
    Iterable<Category> findAllCategory();
    Boolean existsCategoryById (Long idCategory);
    List<String> getAllCategoryNames();
    String addCategory(@Valid CategoryDto.CategoryCreate newCategoryData);
    String deleteCategory(@Valid CategoryDto.CategoryUpdate categoryData) throws JsonProcessingException;
    String updateCategory(@Valid CategoryDto.CategoryUpdate categoryData);
}
