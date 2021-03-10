package ru.vados.JpaTestWork.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.vados.JpaTestWork.model.Banner;
import ru.vados.JpaTestWork.model.Category;
import ru.vados.JpaTestWork.repository.BannerRepository;
import ru.vados.JpaTestWork.repository.CategoryRepository;
import ru.vados.JpaTestWork.repository.RequestRepository;
import java.util.*;

@RestController
public class CategoryController {

    CategoryRepository categoryRepository;
    BannerRepository bannerRepository;
    RequestRepository requestRepository;

    @Autowired
    public CategoryController(CategoryRepository categoryRepository,
                              BannerRepository bannerRepository,
                              RequestRepository requestRepository) {
        this.categoryRepository = categoryRepository;
        this.bannerRepository = bannerRepository;
        this.requestRepository = requestRepository;
    }

    ObjectMapper objectMapper = new ObjectMapper();

    @CrossOrigin
    @Transactional
    @PostMapping("/AddCategory")
    public String addCategory(@RequestBody(required = false)HashMap<String,String> newCategoryData) throws JsonProcessingException {

        String categoryReqId = null;
        String categoryName = null;

        if(newCategoryData.get("category_name")!= null || !newCategoryData.get("category_name").equals(""))
            categoryName = newCategoryData.get("category_name");
        else objectMapper.writeValueAsString("Имя категории некорректно ");

        if(categoryRepository.existsCategoryByName(categoryName))
            return objectMapper.writeValueAsString("Категория с таким именем уже есть ");

        if(newCategoryData.get("categoryReqId")!=null || (String) newCategoryData.get("categoryReqId")!="")
            categoryReqId = newCategoryData.get("categoryReqId");
        else objectMapper.writeValueAsString("Request id категории некорректно ");

        Category category = new Category();
        category.setName(categoryName);
        category.setReqName(categoryReqId);
        category.setDeleted(false);
        categoryRepository.save(category);

        return null;
    }

    @CrossOrigin
    @Transactional
    @PostMapping("/GetCategories")
    public String getCategories() throws JsonProcessingException {

        return objectMapper.writeValueAsString(categoryRepository.findAll());
    }

    @CrossOrigin
    @Transactional
    @PostMapping("/DeleteCategory")
    public String deleteCategory(@RequestBody(required = false)HashMap<String,Long> categoryData) throws JsonProcessingException {

        Optional<Category> category;

        Long idCategory = categoryData.get("idCategory");
        if(idCategory == null)
            return objectMapper.writeValueAsString("id кактегории равно null ");

        if(!categoryRepository.existsCategoryById(idCategory)){
            return objectMapper.writeValueAsString("категории с таким id нет");
        }

        category = categoryRepository.findById(idCategory);

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

        categoryRepository.deleteById(idCategory);
        return null;
    }

    @CrossOrigin
    @Transactional
    @PostMapping("/UpdateCategory")
    public String updateCategory(@RequestBody(required = false) HashMap<String,String> categoryData) throws JsonProcessingException {

        int categoryId = Integer.parseInt(categoryData.get("idCategory"));

        String categoryReqId = null;
        String categoryName = null;

        if(categoryData.get("category_name")!=null || !categoryData.get("category_name").equals(""))
            categoryName = categoryData.get("category_name");
        else objectMapper.writeValueAsString("Имя категории некорректно ");


        if(categoryData.get("categoryReqId")!=null || !categoryData.get("categoryReqId").equals(""))
            categoryReqId = categoryData.get("categoryReqId");
        else objectMapper.writeValueAsString("Request id категории некорректно ");

        Optional<Category> category = categoryRepository.findById((long) categoryId);

        if(category.isPresent()) {
            category.get().setName(categoryName);
            category.get().setReqName(categoryReqId);
            categoryRepository.save(category.get());
        }

        return null;
    }



}
