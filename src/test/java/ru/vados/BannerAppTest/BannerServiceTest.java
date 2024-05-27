package ru.vados.BannerAppTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import ru.vados.BannerApp.dto.BannerDto;
import ru.vados.BannerApp.exception.NotFoundException;
import ru.vados.BannerApp.repository.BannerRepository;
import ru.vados.BannerApp.service.BannerService;

@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {
                "/sql/ddl-before-test.sql",
                "/sql/prepare-test-set1.sql"
        })
})
public class BannerServiceTest extends AbstractTest {

    @Autowired
    private BannerService bannerService;

    @Autowired
    private BannerRepository bannerRepository;

    private static final String EXIST_CATEGORY_NAME = "category1";
    private static final String NOT_EXIST_CATEGORY_NAME = "notExistCategory";
    private static final Long EXIST_BANNER_ID = 2L;
    private static final Long NOT_EXIST_BANNER_ID = 222L;
    private static final String NEW_BANNER_NAME = "newBanner";
    private static final String UPDATED_BANNER_NAME = "updatedBanner";


    @Test
    public void test__add_banner() {
        bannerService.addBanner(new BannerDto.BannerCreate(
                EXIST_CATEGORY_NAME,
                NEW_BANNER_NAME,
                "bannerText",
                111L
        ));

        bannerRepository.findByName(NEW_BANNER_NAME)
                .ifPresentOrElse((a) -> {
                            Assertions.assertEquals(EXIST_CATEGORY_NAME, a.getCategory().getName());
                            Assertions.assertEquals("bannerText", a.getContent());
                        }, () -> {
                            Assertions.fail("not exist new banner");
                        }
                );
    }

    @Test
    public void test__delete_category() {
        bannerService.deleteBanner(new BannerDto.BannerDelete(EXIST_BANNER_ID));

        bannerRepository.findByIdAndDeletedIsFalse(EXIST_BANNER_ID)
                .ifPresentOrElse((a) -> {
                            Assertions.fail("banner still exist");
                        }, () -> {
                        }
                );
    }

    @Test
    public void test__update_category() {
        bannerService.updateBanner(new BannerDto.BannerUpdate(
                EXIST_CATEGORY_NAME,
                UPDATED_BANNER_NAME,
                "updatedBannerText",
                222L,
                EXIST_BANNER_ID
        ));

        bannerRepository.findByIdAndDeletedIsFalse(EXIST_BANNER_ID)
                .ifPresentOrElse((a) -> {
                            Assertions.assertEquals(UPDATED_BANNER_NAME, a.getName());
                            Assertions.assertEquals("updatedBannerText", a.getContent());
                            Assertions.assertEquals(EXIST_CATEGORY_NAME, a.getCategory().getName());
                        }, () -> {
                            Assertions.fail("not exist updated banner");
                        }
                );
    }

    @Test
    public void test__add_banner_not_found_category_exception() {
        Assertions.assertThrows(NotFoundException.class, () -> {
            bannerService.addBanner(new BannerDto.BannerCreate(
                    NOT_EXIST_CATEGORY_NAME,
                    NEW_BANNER_NAME,
                    "bannerText",
                    111L
            ));
        });
    }

    @Test
    public void test__update_banner_not_found_banner_exception() {
        Assertions.assertThrows(NotFoundException.class, () -> {
            bannerService.updateBanner(new BannerDto.BannerUpdate(
                    EXIST_CATEGORY_NAME,
                    UPDATED_BANNER_NAME,
                    "updatedBannerText",
                    222L,
                    NOT_EXIST_BANNER_ID
            ));
        });
    }

    @Test
    public void test__update_banner_not_found_category_exception() {
        Assertions.assertThrows(NotFoundException.class, () -> {
            bannerService.updateBanner(new BannerDto.BannerUpdate(
                    NOT_EXIST_CATEGORY_NAME,
                    UPDATED_BANNER_NAME,
                    "updatedBannerText",
                    222L,
                    EXIST_BANNER_ID
            ));
        });
    }

    @Test
    public void test__delete_banner_not_found_banner_exception() {
        Assertions.assertThrows(NotFoundException.class, () -> {
            bannerService.deleteBanner(new BannerDto.BannerDelete(
                    NOT_EXIST_BANNER_ID
            ));
        });
    }

}
