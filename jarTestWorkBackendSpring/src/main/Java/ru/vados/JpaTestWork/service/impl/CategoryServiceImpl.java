package ru.vados.JpaTestWork.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import ru.vados.JpaTestWork.DTO.CategoryDto;
import ru.vados.JpaTestWork.model.Banner;
import ru.vados.JpaTestWork.model.Category;
import ru.vados.JpaTestWork.repository.CategoryRepository;
import ru.vados.JpaTestWork.service.CategoryService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    ObjectMapper objectMapper = new ObjectMapper();
    
    CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public String addCategory(CategoryDto newCategoryData){
        String categoryReqId = null;
        String categoryName = null;

        if(newCategoryData.getCategory_name()!= null && !newCategoryData.getCategory_name().equals(""))
            categoryName = newCategoryData.getCategory_name();
        else return  "Имя категории некорректно";

        if(existsCategoryByName(categoryName))
            return "Категория с таким именем уже есть ";

        if(newCategoryData.getCategoryReqId()!=null && !newCategoryData.getCategoryReqId().equals(""))
            categoryReqId = newCategoryData.getCategoryReqId();
        else return  "Request id категории некорректно ";

        Category category = new Category();
        category.setName(categoryName);
        category.setReqName(categoryReqId);
        category.setDeleted(false);
        categoryRepository.save(category);

        return null;
    }

    @Override
    public String deleteCategory(CategoryDto categoryData) throws JsonProcessingException {
        Optional<Category> category;

        Long idCategory = categoryData.getIdCategory();
        if(idCategory == null)
            return "id кактегории равно null ";

        if(!existsCategoryById(idCategory)){
            return "категории с таким id нет";
        }

        category = Optional.ofNullable(findCategoryById(idCategory));

        if(category.get().hasBanner())
        {
            List<Banner> banners = category.get().getBanners();
            String messageToClient = "Нельзя удалить категорию так как в ней есть банеры. Вот их список: ";
            for (Banner baner:banners) {
                messageToClient+= "  ";
                messageToClient+= baner.getName();
            }

            return objectMapper.writeValueAsString(messageToClient);
        }

        deleteById(idCategory);
        return null;
    }

    @Override
    public String updateCategory(CategoryDto categoryData){
        String categoryReqId = null;
        String categoryName = null;

        if(categoryData.getCategory_name()!=null && !categoryData.getCategory_name().equals(""))
            categoryName = categoryData.getCategory_name();
        else return  "Имя категории некорректно ";


        if(categoryData.getCategoryReqId()!=null && !Objects.equals(categoryData.getCategoryReqId(), ""))
            categoryReqId = categoryData.getCategoryReqId();
        else return "Request id категории некорректно ";

        Category category = findCategoryById(categoryData.getIdCategory());

        category.setName(categoryName);
        category.setReqName(categoryReqId);
        saveCategory(category);

        return null;
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
