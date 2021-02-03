package ru.vados.JpaTestWork.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
    @PostMapping("/AddCategory")
    @ResponseBody
    private String addCategory(@RequestBody(required = false)HashMap<String,String> newCategoryData) throws JsonProcessingException {
        System.out.println(newCategoryData.get("category_name")+"    "+newCategoryData.get("categoryReqId"));

        String categoryReqId = null;
        String categoryName = null;

        if(newCategoryData.get("category_name")!=null || (String)newCategoryData.get("category_name")!="")
            categoryName = newCategoryData.get("category_name");
        else objectMapper.writeValueAsString("Имя категории некорректно ");

        if(categoryRepository.existsCategoryByName(categoryName))
            return objectMapper.writeValueAsString("Категория с таким именем уже есть ");

        if(newCategoryData.get("categoryReqId")!=null || (String) newCategoryData.get("categoryReqId")!="")
            categoryReqId = newCategoryData.get("categoryReqId");
        else objectMapper.writeValueAsString("Request id категории некорректно ");

        Category category = new Category();
        category.setName(categoryName);
        category.setReq_name(categoryReqId);
        category.setDeleted(false);
        categoryRepository.save(category);

        return null;
    }

    @CrossOrigin
    @PostMapping("/GetCategories")
    private String getCategories() throws JsonProcessingException {

        return objectMapper.writeValueAsString(categoryRepository.findAll());
    }

    @CrossOrigin
    @PostMapping("/DeleteCategory")
    @ResponseBody
    private String deleteCategory(@RequestBody(required = false)HashMap<String,Long> categoryData) throws JsonProcessingException {
        System.out.println(categoryData.get("idCategory")+"   idCategory to delete");

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
    @PostMapping("/UpdateCategory")
    @ResponseBody
    private String updateCategory(@RequestBody(required = false) HashMap<String,String> categoryData) throws JsonProcessingException {
        System.out.println(categoryData.get("idCategory")+"   idCategory to update");
        System.out.println(categoryData.get("category_name")+"   name category to update");
        System.out.println(categoryData.get("categoryReqId")+"   req_name category to update");

        int categoryId = Integer.parseInt(categoryData.get("idCategory"));

        String categoryReqId = null;
        String categoryName = null;


        if(categoryData.get("category_name")!=null || (String)categoryData.get("category_name")!="")
            categoryName = categoryData.get("category_name");
        else objectMapper.writeValueAsString("Имя категории некорректно ");


        if(categoryData.get("categoryReqId")!=null || (String) categoryData.get("categoryReqId")!="")
            categoryReqId = categoryData.get("categoryReqId");
        else objectMapper.writeValueAsString("Request id категории некорректно ");

        Optional<Category> category = categoryRepository.findById((long) categoryId);

        category.get().setName(categoryName);
        category.get().setReq_name(categoryReqId);

        categoryRepository.save(category.get());

        return null;
    }

    @CrossOrigin
    @PostMapping("/GetBanners")
    @ResponseBody
    private String getBanners() throws JsonProcessingException {
        return objectMapper.writeValueAsString(bannerRepository.findAll());
    }

}
