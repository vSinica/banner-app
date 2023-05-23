package ru.vados.BannerAppTest;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import ru.vados.BannerAppTest.config.TestAppConfig;
@SpringBootTest(classes = {TestAppConfig.class})
@Profile("test")
public abstract class AbstractTest {
}
