package ru.vados.JpaTestWork.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.vados.JpaTestWork.Dto.CategoryDto;
import ru.vados.JpaTestWork.Service.impl.CategoryServiceImpl;

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
    public ResponseEntity<Void> deleteCategory(@RequestBody CategoryDto.CategoryUpdate categoryData) throws JsonProcessingException {
        return categoryService.deleteCategory(categoryData);

    }

    @PostMapping("/UpdateCategory")
    public ResponseEntity<Void> updateCategory(@RequestBody CategoryDto.CategoryUpdate categoryData)  {
       return  categoryService.updateCategory(categoryData);
    }
}
