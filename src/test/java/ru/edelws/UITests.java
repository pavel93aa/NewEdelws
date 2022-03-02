package ru.edelws;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

/**
 * Класс содержит простые UI-тесты, которые демонстрируют возможности работы с объектами типа WebDriver и WebElement
 */
public class UITests {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\tools\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        //Установка неявного ожидания для всех элементов
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        //Установка явного ожидания
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test(description = "Основной тест")
    public void mainTest() {
        System.out.println("Это основной тест");
        try {
            driver.get("https://edelws.ru/");
            Thread.sleep(2000);

            //Переход в конструктор
            driver.findElement(By.xpath("//a[@href='/constructor/']")).click();

            //В разделе "Процессор" выбираем "Ryzen 5"
            driver.findElement(By.xpath("//option[text()='Ryzen 5']")).click();

            //Выбираем модель номер 1
            WebElement model1 = driver.findElement(By.xpath("(//div[@data-id='RPOCESSOR']//div[@class='configurator__equipment-list-box'])[1]"));
            Assert.assertEquals(model1.getAttribute("innerText"), model1.getText(), "Ошибка!");
            Assert.assertEquals(model1.getAttribute("outerText"), model1.getText(), "Ошибка!");
            model1.click();

            //В разделе "Охлаждение" выбираем "Воздушное охлаждение"
            driver.findElement(By.xpath("//option[text()='Воздушное охлаждение']")).click();

            //Выбираем модель номер 2
            WebElement model2 = driver.findElement(By.xpath("(//div[@data-id='COOLER']//div[@class='configurator__equipment-list-box'])[2]"));
            Assert.assertEquals(model2.getAttribute("innerText"), model2.getText(), "Ошибка!");
            Assert.assertEquals(model2.getAttribute("outerText"), model2.getText(), "Ошибка!");
            model2.click();

            //В разделе "Материнская плата" выбираем "MSI"
            driver.findElement(By.xpath("//option[text()='MSI']")).click();

            //Выбираем модель номер 3 используя метод findElements (для примера)
            List<WebElement> webElementList = driver.findElements(By.xpath("//div[@data-id='MOTHERBOARD']//div[@class='configurator__equipment-list-box']"));
            Assert.assertEquals(webElementList.get(2).getAttribute("innerText"), webElementList.get(2).getText(), "Ошибка!");
            Assert.assertEquals(webElementList.get(2).getAttribute("outerText"), webElementList.get(2).getText(), "Ошибка!");
            webElementList.get(2).click();

            //Ввод текста (для примера)
            driver.findElement(By.xpath("//input[@class='configurator__search-input']")).sendKeys("test", Keys.ENTER);

            //Закрытие окна
            WebElement windowCloseButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#failsearch > div > span")));
            Assert.assertEquals(windowCloseButton.getText(), "ЗАКРЫТЬ ОКНО", "Ошибка!");
            windowCloseButton.click();

            //Нажатие на кнопку "КУПИТЬ"
            WebElement buyButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("a.configurator__result-btn.configurator__result-btn--1.btn.btn--type-3")));
            Assert.assertEquals(buyButton.getText(), "КУПИТЬ", "Ошибка!");
            buyButton.click();

            //Открытие новой вкладки
            driver.switchTo().newWindow(WindowType.TAB);

            driver.get("https://edelws.ru/contacts/");
            Thread.sleep(2000);

            Assert.assertEquals(driver.findElement(By.xpath("//div[@class='contacts__box']//a[@href='tel:88002349919']")).getText(), "8 (800) 234 99 19", "Ошибка!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test(description = "DragAndDrop тест")
    public void dragAndDropTest() {
        System.out.println("Это DragAndDrop тест");
        try {
            driver.get("https://crossbrowsertesting.github.io/drag-and-drop");
            Thread.sleep(2000);

            WebElement draggable = driver.findElement(By.id("draggable"));
            WebElement droppable = driver.findElement(By.id("droppable"));

            //Проверка свойств найденных элементов
            Assert.assertEquals(draggable.getAttribute("innerText"), draggable.getText(), "Ошибка!");
            Assert.assertEquals(draggable.getAttribute("outerText"), draggable.getText(), "Ошибка!");
            Assert.assertEquals(droppable.getAttribute("innerText"), droppable.getText(), "Ошибка!");
            Assert.assertEquals(droppable.getAttribute("outerText"), droppable.getText(), "Ошибка!");

            //Проверка и получение значения атрибута box-sizing элемента draggable (для примера)
            Assert.assertEquals(draggable.getCssValue("box-sizing"), "content-box", "Ошибка!");

            //Проверка и получение свойств элемента droppable до действия
            Assert.assertEquals(droppable.getAttribute("innerText"), "Drop here", "Ошибка!");
            Assert.assertEquals(droppable.getAttribute("outerText"), "Drop here", "Ошибка!");

            //Создание цепочки действий dragAndDrop
            Actions actions = new Actions(driver);
            actions.moveToElement(draggable).clickAndHold().moveToElement(droppable).release().build().perform();

            //Проверка и получение свойств элемента droppable после действия
            Assert.assertEquals(droppable.getAttribute("innerText"), "Dropped!", "Ошибка!");
            Assert.assertEquals(droppable.getAttribute("outerText"), "Dropped!", "Ошибка!");

            //Обновление страницы
            driver.navigate().refresh();

            //Ожидание исчезновения элемента droppable (после обновления страницы всегда проходит успешно)
            wait.until(ExpectedConditions.stalenessOf(droppable));

            draggable = driver.findElement(By.id("draggable"));
            droppable = driver.findElement(By.id("droppable"));

            //Проверка и получение свойств элемента droppable до действия
            Assert.assertEquals(droppable.getAttribute("innerText"), "Drop here", "Ошибка!");
            Assert.assertEquals(droppable.getAttribute("outerText"), "Drop here", "Ошибка!");

            //Создание действия dragAndDrop при помощи соответствующего метода
            actions.dragAndDrop(draggable, droppable).build().perform();

            //Проверка и получение свойств элемента droppable после действия
            Assert.assertEquals(droppable.getAttribute("innerText"), "Dropped!", "Ошибка!");
            Assert.assertEquals(droppable.getAttribute("outerText"), "Dropped!", "Ошибка!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}