package ru.vados.JpaTestWork;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.vados.JpaTestWork.controller.CategoryController;
import ru.vados.JpaTestWork.config.AppConfig;
import ru.vados.JpaTestWork.model.Category;
import ru.vados.JpaTestWork.repository.CategoryRepository;
import ru.vados.JpaTestWork.service.CategoryService;

import java.util.Collections;
import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CategoryController.class)
@ContextConfiguration(classes = {AppConfig.class})
class CategoryControllerTestJunit5 {

    @MockBean
    CategoryService categoryService;

    @MockBean
    CategoryRepository categoryRepository;

    @Autowired
    MockMvc mockMwc;

    Category category;

    @BeforeEach
    void initCategory(){
        category = new Category();
        category.setName("testName");
        category.setReqName("reqtestname");
        category.setDeleted(false);
    }

    @Test
    void getCategoriesTest() throws Exception {
        List<Category> categories = Collections.singletonList(category);

        Mockito.when(categoryService.findAllCategory()).thenReturn(categories);

        mockMwc.perform(post("/GetCategories"))
                .andExpect(status().isOk());
                ///.andExpect((ResultMatcher) jsonPath("$.name",is("testName")));
    }
}