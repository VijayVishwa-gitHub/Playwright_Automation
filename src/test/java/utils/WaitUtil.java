package utils;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

public class WaitUtil {
    private static final long DEFAULT_TIMEOUT = 10000;
    private static final long SHORT_TIMEOUT = 5000;
    private static final int MAX_RETRIES = 3;

    public static void waitForElementVisible(Locator locator) {
        waitForElementVisible(locator, DEFAULT_TIMEOUT);
    }

    public static void waitForElementVisible(Locator locator, long timeout) {
        locator.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(timeout));
    }

    public static void waitForPageLoad(Page page) {
        page.waitForLoadState();
    }

    public static boolean retryAction(String actionName, ActionRetry action, int maxRetries) {
        for (int attempt = 1; attempt <= maxRetries; attempt++) {
            try {
                action.execute();
                return true;
            } catch (Exception e) {
                if (attempt < maxRetries) {
                    long waitTime = 1000 * attempt;
                    System.out.println(actionName + " failed. Retry attempt " + attempt + "/" + maxRetries +
                                     ". Waiting " + waitTime + "ms before retry...");
                    try {
                        Thread.sleep(waitTime);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                    }
                } else {
                    System.out.println(actionName + " failed after " + maxRetries + " attempts. Error: " + e.getMessage());
                    return false;
                }
            }
        }
        return false;
    }

    public static boolean retryAction(String actionName, ActionRetry action) {
        return retryAction(actionName, action, MAX_RETRIES);
    }

    @FunctionalInterface
    public interface ActionRetry {
        void execute() throws Exception;
    }
}

