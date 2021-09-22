package ru.vados.JpaTestWork;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.vados.JpaTestWork.model.Category;
import ru.vados.JpaTestWork.repository.CategoryRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@SpringBootTest(classes = {AppConfig.class, CategoryRepository.class})
//@ContextConfiguration(classes = {AppConfig.class,AppInit.class, WebConfig.class})
///@DataJpaTest
///@ContextConfiguration(classes = {H2TestProfileJPAConfig.class,CategoryRepository.class})
@SpringBootTest()
@ActiveProfiles("test")
public class CategoryRepositoryInMemoryTestJunit5 {

    @Autowired
    CategoryRepository categoryRepository;

    @Test
    void addCategoryTest() {
        Category category = new Category();
        category.setName("testName");
        category.setReqName("reqtestname");
        category.setDeleted(false);

        categoryRepository.save(category);
       /// ArrayList<Category> categories = new ArrayList<>();
        // categoryRepository.findAll().forEach(categories::add);
        //System.out.println(categories);
        assertEquals(category,categoryRepository.findByName(category.getName()));

    }

}
