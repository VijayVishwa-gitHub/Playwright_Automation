package utils;

import com.microsoft.playwright.Page;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScreenshotUtil {
    public static void takeScreenshot(Page page, String screenshotName) {
        try {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            Path path = Paths.get("screenshots", screenshotName + "_" + timestamp + ".png");
            Files.createDirectories(path.getParent());
            page.screenshot(new Page.ScreenshotOptions().setPath(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

