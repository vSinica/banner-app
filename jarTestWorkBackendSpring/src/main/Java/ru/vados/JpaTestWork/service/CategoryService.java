package ru.vados.JpaTestWork.service;

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
}
