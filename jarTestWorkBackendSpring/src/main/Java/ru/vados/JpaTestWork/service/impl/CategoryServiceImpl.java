package ru.vados.JpaTestWork.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vados.JpaTestWork.model.Category;
import ru.vados.JpaTestWork.repository.CategoryRepository;
import ru.vados.JpaTestWork.service.CategoryService;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Boolean existsCategoryByName(String categoryName) {
        return categoryRepository.existsCategoryByName(categoryName);
    }

    @Override
    public void saveCategory(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public Iterable<Category> findAllCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public Boolean existsCategoryById(Long idCategory) {
        return categoryRepository.existsCategoryById(idCategory);
    }

    @Override
    public Category findCategoryById(Long idCategory) {
        Optional<Category> category = categoryRepository.findById(idCategory);
        if(category.isPresent()){
            return category.get();
        }
        else{
            return null;
        }
    }

    @Override
    public Category findByCategoryName(String name){
        return categoryRepository.findByName(name);
    }


    @Override
    public void deleteById(Long idCategory) {
        categoryRepository.deleteById(idCategory);
    }

    @Override
    public List<String> getAllCategoryNames(){
        return categoryRepository.getAllCategoryNames();
    }

}
