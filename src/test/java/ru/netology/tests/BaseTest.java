package ru.netology.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.chrome.ChromeOptions;

public abstract class BaseTest {

    @BeforeAll
    public static void setup() {
        Configuration.baseUrl = "http://localhost:7777";
        Configuration.headless = Boolean.parseBoolean(System.getProperty("selenide.headless"));
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--start-fullscreen", "--start-incognito");
        Configuration.browser = "chrome";
        Configuration.browserCapabilities = options;
        Configuration.browserSize = "1920x1080";
    }

    @AfterEach
    public void tearDown() {
        Selenide.closeWebDriver();
    }
}
