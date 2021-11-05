package ru.vados.JpaTestWork.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@CrossOrigin
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

    @Transactional
    @PostMapping("/AddCategory")
    public ResponseEntity<String> addCategory(@RequestBody CategoryDto newCategoryData) throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.OK).
                body(categoryService.addCategory(newCategoryData));
    }

    @Transactional
    @PostMapping("/GetCategories")
    public ResponseEntity<String> getCategories() throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.OK).
                body(objectMapper.writeValueAsString(categoryService.findAllCategory()));
    }

    @Transactional
    @PostMapping("/DeleteCategory")
    public ResponseEntity<String> deleteCategory(@RequestBody CategoryDto categoryData) throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.OK).
                body(categoryService.deleteCategory(categoryData));
    }

    @Transactional
    @PostMapping("/UpdateCategory")
    public ResponseEntity<String> updateCategory(@RequestBody CategoryDto categoryData) throws JsonProcessingException {
       return  ResponseEntity.status(HttpStatus.OK).
              body(categoryService.updateCategory(categoryData));
    }



}
