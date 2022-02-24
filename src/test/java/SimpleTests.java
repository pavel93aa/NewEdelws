import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * В данном классе расположены все простые тесты (для примера)
 */
public class SimpleTests {
    @DataProvider(name = "dptest")
    public static Object[][] dataProvider() {
        return new Object[][]{
                {"Значение 1", 1},
                {"Значение 2", 2}
        };
    }

    @Test(description = "Тест с использованием DataProvider", dataProvider = "dptest")
    public void testWithDataProvider(String stringValue, int intValue) {
        System.out.println("Это тест с использованием DataProvider " + stringValue + " " + intValue);
    }

    @Test(description = "Простой тест")
    public void simpleTest() {
        System.out.println("Это самый простой тест");
    }
}