package ru.vados.JpaTestWork.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import ru.vados.JpaTestWork.DTO.CategoryDto;
import ru.vados.JpaTestWork.model.Category;

import java.util.List;

public interface CategoryService {
    Boolean existsCategoryByName(String categoryName);
    void saveCategory (Category category);
    Iterable<Category> findAllCategory();
    Boolean existsCategoryById (Long idCategory);
    Category findCategoryById(Long idCategory);
    void deleteById (Long idCategory);
    Category findByCategoryName(String name);
    List<String> getAllCategoryNames();
    public String addCategory(CategoryDto newCategoryData);
    String deleteCategory(CategoryDto categoryData) throws JsonProcessingException;
    String updateCategory(CategoryDto categoryData);
}
