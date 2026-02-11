import com.microsoft.playwright.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

/**
 * Test class demonstrating Playwright automation with TestNG
 */
public class PlaywrightTest {

    private static final Logger logger = LoggerFactory.getLogger(PlaywrightTest.class);
    private PlaywrightBasics automation;

    @BeforeClass
    public void setUp() {
        logger.info("========== Starting Test Setup ==========");
        automation = new PlaywrightBasics();
        automation.setup();
    }

    @AfterClass
    public void tearDown() {
        logger.info("========== Starting Test Teardown ==========");
        automation.teardown();
    }

    @Test(description = "Test navigation to example.com")
    public void testNavigateToExample() {
        logger.info("Test: testNavigateToExample");
        automation.navigateToUrl("https://example.com");

        String title = automation.getPageTitle();
        String url = automation.getPageUrl();

        Assert.assertNotNull(title, "Page title should not be null");
        Assert.assertTrue(url.contains("example.com"), "URL should contain example.com");

        logger.info("Test passed: Navigation successful");
    }

    @Test(description = "Test page title retrieval")
    public void testPageTitle() {
        logger.info("Test: testPageTitle");
        automation.navigateToUrl("https://example.com");

        String title = automation.getPageTitle();
        Assert.assertEquals(title, "Example Domain", "Page title should match 'Example Domain'");

        logger.info("Test passed: Page title verified");
    }

    @Test(description = "Test element visibility")
    public void testElementVisibility() {
        logger.info("Test: testElementVisibility");
        automation.navigateToUrl("https://example.com");

        // Wait for h1 element to be visible
        automation.waitForElement("h1");

        boolean isVisible = automation.isElementVisible("h1");
        Assert.assertTrue(isVisible, "H1 element should be visible");

        logger.info("Test passed: Element visibility verified");
    }

    @Test(description = "Test getting element text")
    public void testGetElementText() {
        logger.info("Test: testGetElementText");
        automation.navigateToUrl("https://example.com");

        automation.waitForElement("h1");
        String text = automation.getElementText("h1");

        Assert.assertNotNull(text, "Element text should not be null");
        Assert.assertTrue(text.contains("Example"), "Text should contain 'Example'");

        logger.info("Test passed: Element text retrieved successfully");
    }
}

