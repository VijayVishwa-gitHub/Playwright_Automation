package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import utils.WaitUtil;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class LoginPage {
    private static final Logger logger = Logger.getLogger(LoginPage.class.getName());
    private final Page page;
    public LoginPage(Page page) {
        this.page = page;
    }


    public void selectFromCity(String searchLocation, String selector, String expected) {
        page.mouse().click(10, 10);
        try {
            Locator fromCityLabel = page.locator("//label[@for='fromCity']");
            fromCityLabel.click();
            WaitUtil.waitForPageLoad(page);

            handlingCity(searchLocation, selector, expected);

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Demo test failed", e);
            throw new RuntimeException("Test execution failed", e);
        }
    }
    public void selectToCity(String searchLocation, String selector, String expected){
        page.mouse().click(10, 10);
        try {
            Locator fromCityLabel = page.locator("//input[@id='toCity']");
            fromCityLabel.click();
            WaitUtil.waitForPageLoad(page);

            handlingCity(searchLocation, selector, expected);

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Demo test failed", e);
            throw new RuntimeException("Test execution failed", e);
        }
    }
    public void validateHeaderMenuTabs(){

        Locator tabs = page.locator("//ul[@class='makeFlex font12 headerIconsGap']/li");

        int count = tabs.count();
        for (int i = 0; i < count; i++) {
            page.mouse().click(10, 10);
            Locator currentTab = tabs.nth(i).locator("a");
            System.out.println(currentTab.textContent());
            currentTab.click();


            // Assert current tab is active
            assertThat(currentTab).hasClass(Pattern.compile(".*active.*"));

            // Validate all other tabs are NOT active
            for (int j = 0; j < count; j++) {
                if (i == j) continue;
                Locator otherTab = tabs.nth(j).locator("a");
                assertThat(otherTab).not().hasClass(Pattern.compile(".*active.*"));
            }
        }

    }


    public void handlingCity(String input, String selector, String expectedSuggestion) {

        Locator inputField = page.locator(selector);
        inputField.fill(input);

        Locator suggestionText = page.locator("(//div[@class='revampedSuggestionHeader'])[1]//span");
        suggestionText.waitFor();

        assertThat(
                page.getByText(expectedSuggestion,
                        new Page.GetByTextOptions().setExact(true)
                )
        ).isVisible();

        System.out.println("Suggestion: " + suggestionText.textContent());
        inputField.fill("");
    }


    private void fillInputFieldWithRetry(String selector, String value, String actionName) {
        boolean success = WaitUtil.retryAction(
            actionName + " '" + value + "'",
            () -> page.locator(selector).fill(value),
            3
        );

        if (!success) {
            throw new RuntimeException("Failed to fill input field after retries: " + selector);
        }
    }
}
