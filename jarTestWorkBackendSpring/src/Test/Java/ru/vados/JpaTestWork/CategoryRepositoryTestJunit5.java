package ru.vados.JpaTestWork;

import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import ru.vados.JpaTestWork.config.AppConfig;
import ru.vados.JpaTestWork.model.Banner;
import ru.vados.JpaTestWork.model.Category;
import ru.vados.JpaTestWork.repository.BannerRepository;
import ru.vados.JpaTestWork.repository.CategoryRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {AppConfig.class,CategoryRepository.class, BannerRepository.class})
class CategoryRepositoryTestJunit5 {

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