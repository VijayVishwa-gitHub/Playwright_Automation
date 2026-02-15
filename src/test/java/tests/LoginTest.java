package tests;

import Base.BaseTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.TestDataProvider;


public class LoginTest extends BaseTest {


    @Test(dataProvider = "fromCityData", dataProviderClass = TestDataProvider.class)
    public void verifyUserCanSelectFromCity(String searchLocation, String expectedCity) {
        LoginPage loginPage = new LoginPage(page);
        loginPage.selectFromCity(searchLocation, expectedCity, expectedCity);
    }

    @Test(dataProvider = "toCityData", dataProviderClass = TestDataProvider.class)
    public void verifyUserCanSelectToCity(String searchLocation, String expectedCity) {
        LoginPage loginPage = new LoginPage(page);
        loginPage.selectToCity(searchLocation, expectedCity, expectedCity);
    }

    @Test
    public void verifyHeaderMenuTabs() {

        LoginPage loginPage = new LoginPage(page);
        loginPage.validateHeaderMenuTabs();

    }

}

