package ru.vados.BannerAppTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import ru.vados.BannerApp.Dto.CategoryDto;
import ru.vados.BannerApp.Exception.HaveBannerInCategoryWhenDelete;
import ru.vados.BannerApp.Exception.NotFoundException;
import ru.vados.BannerApp.Repository.CategoryRepository;
import ru.vados.BannerApp.Service.CategoryService;

@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {
                "/sql/ddl-before-test.sql",
                "/sql/prepare-test-set1.sql"
        })
})
public class CategoryTest extends AbstractTest {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    private static final String NEW_CATEGORY_NAME = "newCategoryName";
    private static final Long EXIST_CATEGORY_ID = 1L;
    private static final String EXIST_CATEGORY_NAME = "category1";
    private static final Long EXIST_CATEGORY_ID_WITHOUT_BANNERS = 2L;
    private static final Long NOT_EXIST_CATEGORY_ID = 222l;


    @Test
    public void test__add_category() {
        categoryService.addCategory(new CategoryDto.CategoryCreate(
                NEW_CATEGORY_NAME,
                "newCategoryReqId"
        ));

        categoryRepository.findByName(NEW_CATEGORY_NAME)
                .ifPresentOrElse((a) -> {
                            Assertions.assertEquals(NEW_CATEGORY_NAME, a.getName());
                            Assertions.assertEquals("newCategoryReqId", a.getReqName());
                        }, () -> {
                            Assertions.fail("not exist new category");
                        }
                );
    }

    @Test
    public void test__delete_category() throws JsonProcessingException {
        categoryService.deleteCategory(new CategoryDto.CategoryDelete(EXIST_CATEGORY_ID_WITHOUT_BANNERS));

        categoryRepository.findById(EXIST_CATEGORY_ID_WITHOUT_BANNERS)
                .ifPresentOrElse((a) -> {
                            Assertions.fail("category still exist");
                        }, () -> {
                        }
                );
    }

    @Test
    public void test__update_category() {
        categoryService.updateCategory(new CategoryDto.CategoryUpdate(
                NEW_CATEGORY_NAME,
                "newCategoryReqId",
                EXIST_CATEGORY_ID
        ));

        categoryRepository.findByName(NEW_CATEGORY_NAME)
                .ifPresentOrElse((a) -> {
                            Assertions.assertEquals(NEW_CATEGORY_NAME, a.getName());
                            Assertions.assertEquals("newCategoryReqId", a.getReqName());
                        }, () -> {
                            Assertions.fail("not exist updated category");
                        }
                );
    }

    @Test
    public void test__add_category_already_exist_exception() {
        Assertions.assertThrows(NotFoundException.class, () -> {
            categoryService.addCategory(new CategoryDto.CategoryCreate(
                    EXIST_CATEGORY_NAME,
                    "categoryReqId"
            ));
        });
    }

    @Test
    public void test__delete_category_not_exist_exception() {
        Assertions.assertThrows(NotFoundException.class, () -> {
            categoryService.deleteCategory(new CategoryDto.CategoryDelete(
                    NOT_EXIST_CATEGORY_ID
            ));
        });
    }

    @Test
    public void test__delete_category_have_banner_exception() {
        Assertions.assertThrows(HaveBannerInCategoryWhenDelete.class, () -> {
            categoryService.deleteCategory(new CategoryDto.CategoryDelete(
                    EXIST_CATEGORY_ID
            ));
        });
    }

    @Test
    public void test__update_category_not_exist_category_exception() {
        Assertions.assertThrows(NotFoundException.class, () -> {
            categoryService.updateCategory(new CategoryDto.CategoryUpdate(
                    NEW_CATEGORY_NAME,
                    "newCategoryReqId",
                    NOT_EXIST_CATEGORY_ID
            ));
        });
    }

}
