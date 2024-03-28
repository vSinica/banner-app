package ru.vados.BannerApp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.vados.BannerApp.dto.CategoryDto;
import ru.vados.BannerApp.service.impl.CategoryServiceImpl;

@RestController
@AllArgsConstructor
public class CategoryController {

    private final CategoryServiceImpl categoryService;

    @PostMapping("/AddCategory")
    public ResponseEntity<Void> addCategory(@RequestBody CategoryDto.CategoryCreate newCategoryData)  {
        return categoryService.addCategory(newCategoryData);
    }

    @PostMapping("/GetCategories")
    public ResponseEntity<Iterable<CategoryDto.Categoryitem>> getCategories() throws JsonProcessingException {
        return categoryService.findAllCategory();
    }

    @PostMapping("/DeleteCategory")
    public ResponseEntity<Void> deleteCategory(@RequestBody CategoryDto.CategoryDelete categoryData) throws JsonProcessingException {
        return categoryService.deleteCategory(categoryData);

    }

    @PostMapping("/UpdateCategory")
    public ResponseEntity<Void> updateCategory(@RequestBody CategoryDto.CategoryUpdate categoryData)  {
       return  categoryService.updateCategory(categoryData);
    }
}
