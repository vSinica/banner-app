package ru.vados.JpaTestWork;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.vados.JpaTestWork.model.Category;
import ru.vados.JpaTestWork.repository.CategoryRepository;
import ru.vados.JpaTestWork.service.CategoryService;
import ru.vados.JpaTestWork.service.impl.CategoryServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;


@SpringBootTest(classes = {CategoryServiceImpl.class})
class CategoryServiceTestJunit5 {

    @Autowired
    CategoryService categoryService;

    @MockBean
    CategoryRepository categoryRepository;

    Category category;

    @BeforeEach
    void initCategory(){
        category = new Category();
        category.setName("testName");
        category.setReqName("reqtestname");
        category.setDeleted(false);
    }

    @Test
    void findByNameCategoryTest() {

        doReturn(category).when(categoryRepository).findByName("testName");

        Category returnedCategory = categoryService.findByCategoryName("testName");

        assertNotNull(returnedCategory);
        assertEquals(category,returnedCategory);
    }

    @Test
    void findByIdCategoryTest(){
        Long categotyId = category.getId();

        doReturn(Optional.of(category)).when(categoryRepository).findById(categotyId);

        Category returnedCategory = categoryService.findCategoryById(categotyId);

        assertNotNull(returnedCategory);
        assertEquals(category,returnedCategory);
    }
}