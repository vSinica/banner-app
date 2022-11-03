package ru.vados.JpaTestWork.Service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vados.JpaTestWork.Dto.CategoryDto;
import ru.vados.JpaTestWork.Entity.Banner;
import ru.vados.JpaTestWork.Entity.Category;
import ru.vados.JpaTestWork.Repository.CategoryRepository;
import ru.vados.JpaTestWork.Service.CategoryService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public String addCategory(@Valid CategoryDto.CategoryCreate newCategoryData){

        if(existsCategoryByName(newCategoryData.getCategoryName()))
            return "Category with this name exist ";

        Category category = new Category();

        if(newCategoryData.getCategoryName()== null || newCategoryData.getCategoryName().isBlank()) {
            return "Category name field is blank";
        }
        else category.setName(newCategoryData.getCategoryName());

        if(newCategoryData.getCategoryReqId()==null || newCategoryData.getCategoryReqId().isBlank()) {
            return "Request id category is blank ";
        }
        else category.setReqName(newCategoryData.getCategoryReqId());

        category.setDeleted(false);
        categoryRepository.save(category);

        return null;
    }

    @Override
    @Transactional
    public String deleteCategory(CategoryDto.CategoryUpdate categoryData) throws JsonProcessingException {

        Long idCategory = categoryData.getIdCategory();
        if(idCategory == null)
            return "id category is empty ";

        if(!existsCategoryById(idCategory)){
            return "category with this id not exist: "+ idCategory;
        }

        List<Banner> banners = categoryRepository.findById(idCategory)
                .map(Category::getBanners)
                .orElse(null);

        if(banners!=null && !banners.isEmpty())
        {
            StringBuilder messageToClient = new StringBuilder("You can't delete a category because it has banners. Here is their list: ");
            for (Banner baner:banners) {
                messageToClient.append("  ");
                messageToClient.append(baner.getName());
            }
            return objectMapper.writeValueAsString(messageToClient.toString());
        }

        categoryRepository.deleteById(idCategory);
        return null;
    }

    @Override
    @Transactional
    public String updateCategory(CategoryDto.CategoryUpdate categoryData){
        Optional<Category> optCategory = categoryRepository.findById(categoryData.getIdCategory());
        Category category = null;
        if(optCategory.isEmpty()){
            return "not exist catogory id";
        } else {
            category = optCategory.get();
        }

        if(categoryData.getCategoryName()==null || categoryData.getCategoryName().isBlank()) {
            return "name category is blank";
        }
        else category.setName(categoryData.getCategoryName());


        if(categoryData.getCategoryReqId()==null || categoryData.getCategoryReqId().isBlank()) {
            return "Request id category blank";
        }
        else category.setReqName(categoryData.getCategoryReqId());

        categoryRepository.save(category);

        return null;
    }
    
    @Override
    @Transactional(readOnly = true)
    public Boolean existsCategoryByName(String categoryName) {
        return categoryRepository.existsCategoryByName(categoryName);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> findAllCategory() {
        return categoryRepository.findAllByDeletedFalse();
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean existsCategoryById(Long idCategory) {
        return categoryRepository.existsCategoryById(idCategory);
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getAllCategoryNames(){
        return categoryRepository.getAllCategoryNames();
    }

}
