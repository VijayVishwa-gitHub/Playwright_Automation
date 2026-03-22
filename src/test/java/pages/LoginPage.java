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

    // Locators (instance-based)
    private final Locator fromInputBox;
    private final Locator toInputBox;
    private final Locator fromCityLabel;
    private final Locator toCityLabel;
    private final Locator tabs;

    public LoginPage(Page page) {
        this.page = page;
        /* HOmePage Locators */
        this.fromInputBox = page.locator("input[placeholder='From']");
        this.toInputBox = page.locator("input[placeholder='To']");
        this.fromCityLabel = page.locator("//label[@for='fromCity']");
        this.toCityLabel = page.locator("#toCity");
        this.tabs = page.locator("//ul[contains(@class,'headerIconsGap')]/li");
    }


    /* HOmePage Related Methods */
    public void selectFromCity(String searchLocation, String expected) {
        page.mouse().click(10, 10);
        try {
            fromCityLabel.click();
            WaitUtil.waitForPageLoad(page);
            handlingCity(searchLocation, fromInputBox, expected);

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Demo test failed", e);
            throw new RuntimeException("Test execution failed", e);
        }
    }
    public void selectToCity(String searchLocation, String expected){
        page.mouse().click(10, 10);
        try {

            toCityLabel.click();
            WaitUtil.waitForPageLoad(page);

            handlingCity(searchLocation, toInputBox, expected);

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Demo test failed", e);
            throw new RuntimeException("Test execution failed", e);
        }
    }
    public void validateHeaderMenuTabs(){

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


    /* Helper Methods */
    public void handlingCity(String input, Locator selector, String expectedSuggestion) {

        fillInputFieldWithRetry(selector, input, "Entering City" );

        Locator suggestionText = page.locator("(//div[@class='revampedSuggestionHeader'])[1]//span");
        suggestionText.waitFor();

        assertThat(
                page.getByText(expectedSuggestion,
                        new Page.GetByTextOptions().setExact(true)
                )
        ).isVisible();

        System.out.println("Suggestion: " + suggestionText.textContent());

    }
    public void fillInputFieldWithRetry(Locator selector, String value, String actionName) {
        boolean success = WaitUtil.retryAction(
                actionName + " '" + value + "'",
                () -> selector.fill(value),
                3
        );

        if (!success) {
            throw new RuntimeException("Failed to fill input field after retries: " + selector);
        }
    }

}
