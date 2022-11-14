package ru.vados.BannerApp.test;

import org.springframework.boot.test.context.SpringBootTest;
import ru.vados.BannerApp.test.config.TestAppConfig;

@SpringBootTest(classes = {TestAppConfig.class})
public abstract class AbstractTest {
}
