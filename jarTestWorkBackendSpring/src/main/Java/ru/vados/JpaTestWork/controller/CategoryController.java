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

    ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/AddCategory")
    public ResponseEntity<String> addCategory(@RequestBody CategoryDto.CategoryCreate newCategoryData)  {
        return ResponseEntity.status(HttpStatus.OK).
                body(categoryService.addCategory(newCategoryData));
    }

    @PostMapping("/GetCategories")
    public ResponseEntity<String> getCategories() throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.OK).
                body(objectMapper.writeValueAsString(categoryService.findAllCategory()));
    }

    @PostMapping("/DeleteCategory")
    public ResponseEntity<String> deleteCategory(@RequestBody CategoryDto.CategoryUpdate categoryData) throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.OK).
                body(categoryService.deleteCategory(categoryData));
    }

    @PostMapping("/UpdateCategory")
    public ResponseEntity<String> updateCategory(@RequestBody CategoryDto.CategoryUpdate categoryData)  {
       return  ResponseEntity.status(HttpStatus.OK).
              body(categoryService.updateCategory(categoryData));
    }
}
