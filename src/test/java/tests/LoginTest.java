package tests;

import Base.BaseTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.LoginPage;


public class LoginTest extends BaseTest {

    @Test(dataProvider = "searchData")
    public void testLoginPageDemo(String searchLocation, String selector, String expected) {
        LoginPage loginPage = new LoginPage(page);
        loginPage.demo(searchLocation, selector, expected);
    }

    @DataProvider(name = "searchData")
    public Object[][] getData() {
        return new Object[][]{
                {"bali", "//input[@placeholder='From']", "Denpasar Bali, Indonesia"},
                {"cali", "//input[@placeholder='From']", "Cali, Colombia"}
        };
    }
}

