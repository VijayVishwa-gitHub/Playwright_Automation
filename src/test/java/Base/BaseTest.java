package Base;

import com.microsoft.playwright.*;
import config.ConfigReader;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import utils.WaitUtil;

public class BaseTest {
    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;

    @BeforeClass
    public void setUp() {
        playwright = Playwright.create();
        String browserType = ConfigReader.getProperty("browser");
        boolean headless = Boolean.parseBoolean(ConfigReader.getProperty("headless"));
        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions().setHeadless(headless);
        if (ConfigReader.getProperty("browser").equalsIgnoreCase("chromium")) {
            options.setArgs(java.util.Arrays.asList("--start-maximized"));
            browser = playwright.chromium().launch(options);
        } else if (browserType.equalsIgnoreCase("firefox")) {
            browser = playwright.firefox().launch(options);
        } else if (browserType.equalsIgnoreCase("edge")) {
            options.setArgs(java.util.Arrays.asList("--start-maximized"));
            browser = playwright.chromium().launch(options);
        } else {
            browser = playwright.webkit().launch(options);
        }

        context = browser.newContext(new Browser.NewContextOptions().setViewportSize(1366, 768));
        page = context.newPage();
        page.navigate(ConfigReader.getProperty("baseUrl"));
        page.evaluate("document.body.style.zoom = '75%'");
        page.setViewportSize(1366, 768);
        page.locator("//img[@alt='minimize']").click();
        WaitUtil.waitForPageLoad(page);
        page.locator("//span[@class='commonModal__close']").click();
        WaitUtil.waitForPageLoad(page);
        page.mouse().click(10, 10);
        WaitUtil.waitForPageLoad(page);

    }


    public void tearDown() {
        if (context != null) context.close();
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }
}
