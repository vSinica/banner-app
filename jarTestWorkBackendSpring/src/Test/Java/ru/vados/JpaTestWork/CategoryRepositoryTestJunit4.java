package ru.vados.JpaTestWork;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;
import ru.vados.JpaTestWork.config.AppConfig;
import ru.vados.JpaTestWork.config.AppInit;
import ru.vados.JpaTestWork.config.WebConfig;
import ru.vados.JpaTestWork.model.Banner;
import ru.vados.JpaTestWork.model.Category;
import ru.vados.JpaTestWork.repository.BannerRepository;
import ru.vados.JpaTestWork.repository.CategoryRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@ContextConfiguration(classes = {AppConfig.class,CategoryRepository.class,BannerRepository.class})
//@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {AppConfig.class,CategoryRepository.class,BannerRepository.class})
class CategoryRepositoryTestJunit4 {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    BannerRepository bannerRepository;

    @Test
    @Transactional
    @Rollback(true)
    void addCategoryTest() {
        Category category = new Category();
        category.setName("testName");
        category.setReqName("reqtestname");
        category.setDeleted(false);

        categoryRepository.save(category);
        assertEquals(category,categoryRepository.findByName(category.getName()));

    }

    @Test
    @Transactional
    @Rollback
    void addBannerTest(){
        Category category = new Category();
        category.setName("testName");
        category.setReqName("reqtestname");
        category.setDeleted(false);

        Banner banner = new Banner();
        banner.setPrice(999);
        banner.setDeleted(false);
        banner.setName("testName");
        banner.setContent("testContent");

        category.addBanner(banner);
        banner.setCategoryId(category);
        categoryRepository.save(category);
        bannerRepository.save(banner);

        assertEquals(banner,bannerRepository.findById(banner.getId()).get());

    }

}