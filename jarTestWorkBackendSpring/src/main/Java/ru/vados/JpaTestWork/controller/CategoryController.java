package ru.vados.JpaTestWork.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.vados.JpaTestWork.DTO.CategoryDto;
import ru.vados.JpaTestWork.model.Banner;
import ru.vados.JpaTestWork.model.Category;
import ru.vados.JpaTestWork.repository.BannerRepository;
import ru.vados.JpaTestWork.repository.RequestRepository;
import ru.vados.JpaTestWork.service.impl.CategoryServiceImpl;

import java.util.*;

@RestController
public class CategoryController {

    BannerRepository bannerRepository;
    RequestRepository requestRepository;

    CategoryServiceImpl categoryService;

    @Autowired
    public CategoryController(BannerRepository bannerRepository, RequestRepository requestRepository, CategoryServiceImpl categoryService) {
        this.bannerRepository = bannerRepository;
        this.requestRepository = requestRepository;
        this.categoryService = categoryService;
    }

    ObjectMapper objectMapper = new ObjectMapper();

    @CrossOrigin
    @Transactional
    @PostMapping("/AddCategory")
    public String addCategory(@RequestBody CategoryDto newCategoryData) throws JsonProcessingException {
        return categoryService.addCategory(newCategoryData);
    }

    @CrossOrigin
    @Transactional
    @PostMapping("/GetCategories")
    public String getCategories() throws JsonProcessingException {
        return objectMapper.writeValueAsString(categoryService.findAllCategory());
    }

    @CrossOrigin
    @Transactional
    @PostMapping("/DeleteCategory")
    public String deleteCategory(@RequestBody CategoryDto categoryData) throws JsonProcessingException {
        return categoryService.deleteCategory(categoryData);
    }

    @CrossOrigin
    @Transactional
    @PostMapping("/UpdateCategory")
    public String updateCategory(@RequestBody CategoryDto categoryData) throws JsonProcessingException {
       return categoryService.updateCategory(categoryData);
    }



}
