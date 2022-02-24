package ru.edelws;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Данный класс показывает возможности работы с объектами типа WebDriver и WebElement
 */
public class Main {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "C:\\tools\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        //Устанавливаем неявное ожидание для всех элементов
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        //Устанавливаем явное ожидание (для примера)
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            driver.get("https://edelws.ru/");
            Thread.sleep(2000);

            //Переходим в конструктор
            WebElement constructor = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@href='/constructor/']")));
            constructor.click();

            //В разделе "Процессор" выбираем "Ryzen 7"
            driver.findElement(By.xpath("//option[text()='Ryzen 7']")).click();

            //Выбираем модель номер 1
            driver.findElement(By.xpath("(//div[@data-id='RPOCESSOR']//div[@class='configurator__equipment-list-box'])[1]")).click();

            //В разделе "Охлаждение" выбираем "Воздушное охлаждение"
            driver.findElement(By.xpath("//option[text()='Воздушное охлаждение']")).click();

            //Выбираем модель номер 2
            driver.findElement(By.xpath("(//div[@data-id='COOLER']//div[@class='configurator__equipment-list-box'])[2]")).click();

            //В разделе "Материнская плата" выбираем "MSI"
            driver.findElement(By.xpath("//option[text()='MSI']")).click();

            //Выбираем модель номер 3 используя метод findElements (для примера)
            List<WebElement> webElementList = driver.findElements(By.xpath("//div[@data-id='MOTHERBOARD']//div[@class='configurator__equipment-list-box']"));
            webElementList.get(2).click();

            //Ввод текста (для примера)
            driver.findElement(By.xpath("//input[@class='configurator__search-input']")).sendKeys("test", Keys.ENTER);

            //Закрытие окна
            driver.findElement(By.cssSelector("#failsearch > div > span")).click();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            //Открываем новую вкладку
            driver.switchTo().newWindow(WindowType.TAB);

            driver.get("https://crossbrowsertesting.github.io/drag-and-drop");
            Thread.sleep(2000);

            WebElement draggable = driver.findElement(By.id("draggable"));
            WebElement droppable = driver.findElement(By.id("droppable"));

            //Получение текста конкретного элемента (для примера)
            System.out.println(draggable.getText());

            //Получение значения атрибута конкретного элемента (для примера)
            System.out.println(draggable.getCssValue("box-sizing"));

            //Получение свойства конкретного элемента до действия (для примера)
            System.out.println(droppable.getAttribute("innerText"));

            //Создаем цепочку действий dragAndDrop
            Actions actions = new Actions(driver);
            actions.moveToElement(draggable).clickAndHold().moveToElement(droppable).release().build().perform();

            //Получение свойства конкретного элемента после действия (для примера)
            System.out.println(droppable.getAttribute("innerText"));

            //Обновляем страницу
            driver.navigate().refresh();

            //Ожидаем исчезновения элемента (после обновления страницы всегда будет проходить успешно)
            wait.until(ExpectedConditions.stalenessOf(droppable));

            draggable = driver.findElement(By.id("draggable"));
            droppable = driver.findElement(By.id("droppable"));

            //Создаем действие dragAndDrop
            actions.dragAndDrop(draggable, droppable).build().perform();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}