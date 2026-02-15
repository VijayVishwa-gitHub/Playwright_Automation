package utils;
import org.testng.annotations.DataProvider;

public class TestDataProvider {


        @DataProvider(name = "fromCityData")
        public static Object[][] getFromCityData() {
            return new Object[][]{
                    {"bali", "Denpasar Bali, Indonesia"},
                    {"cali", "Cali, Colombia"}
            };
        }

        @DataProvider(name = "toCityData")
        public static Object[][] getToCityData() {
            return new Object[][]{
                    {"Delhi", "New Delhi, India"},
                    {"cali", "Cali, Colombia"}
            };
        }
}
