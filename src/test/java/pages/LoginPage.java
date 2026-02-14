package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import utils.WaitUtil;

import java.util.logging.Level;
import java.util.logging.Logger;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class LoginPage {
    private static final Logger logger = Logger.getLogger(LoginPage.class.getName());
    private final Page page;

    public LoginPage(Page page) {
        this.page = page;
    }

    public void demo(String searchLocation, String selector, String expected) {
        page.mouse().click(10, 10);
        try {
            Locator fromCityLabel = page.locator("//label[@for='fromCity']");
            fromCityLabel.click();
            WaitUtil.waitForPageLoad(page);

            handlingFromCity(searchLocation, selector, expected);

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Demo test failed", e);
            throw new RuntimeException("Test execution failed", e);
        }
    }

    public void handlingFromCity(String input, String selector, String expectedSuggestion) {

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
