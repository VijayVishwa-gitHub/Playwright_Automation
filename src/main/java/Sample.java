import com.microsoft.playwright.*;

import java.nio.file.Paths;
import java.util.logging.Level;

public class Sample {

    public void demo(){
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
            try (browser; BrowserContext context = browser.newContext(new Browser.NewContextOptions()
                    .setViewportSize(1920, 1080))) {
                   // behaves like "maximize"

                Page page = context.newPage();
                try (page) {
                    page.navigate("https://www.amazon.in/");

                    // Locate the search box and send text
                    Locator searchBox = page.locator("id=twotabsearchtextbox");
                    searchBox.fill("Laptop");
                    searchBox.press("Enter");

                    // Wait for results to load
                    page.waitForLoadState();

                    // Take screenshot of search results
                    page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("screenshot.png")));
                    System.out.println("Page Title: " + page.title());
                    System.out.println("Screenshot saved as screenshot.png");


                    page.close();       // Close the tab
                    browser.close();    // Close the browser
                    playwright.close(); // Shut down Playwright engine


                }
            }
        }
        catch (Exception e) {
            java.util.logging.Logger.getLogger(Sample.class.getName()).log(Level.SEVERE, "Demo failed", e);
        }
    }
    public static void main(String[] args){
        Sample s = new Sample();
        s.demo();
    }
    }



