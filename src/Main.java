import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Instant start = Instant.now();

        System.setProperty("webdriver.chrome.driver", "C:\\Users\\majd1\\Downloads\\chromedriver_win32\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        driver.get("https://www.cars.com/");


        WebElement newUsed = driver.findElement(By.id("make-model-search-stocktype")); // for new used tap
        Select newused1 = new Select(newUsed);
        Assert.assertEquals(newused1.getFirstSelectedOption().getText(), "New & used cars");

        WebElement make = driver.findElement(By.id("makes")); //for make tap
        Select make1 = new Select(make);
        Assert.assertEquals(make1.getFirstSelectedOption().getText(), "All makes");

        WebElement model = driver.findElement(By.id("models")); // for model tap
        Select model1 = new Select(model);
        Assert.assertEquals(model1.getFirstSelectedOption().getText(), "All models");

        WebElement price = driver.findElement(By.id("make-model-max-price")); // for price tap
        Select price1 = new Select(price);
        Assert.assertEquals(price1.getFirstSelectedOption().getText(), "No max price");

        WebElement distance = driver.findElement(By.id("make-model-maximum-distance")); // for distance tap
        Select distance1 = new Select(distance);
        Assert.assertEquals(distance1.getFirstSelectedOption().getText(), "20 miles");

        String[] compare = {"New & used cars", "New & certified cars", "New cars", "Used cars", "Certified cars"};
        List<WebElement> option = newused1.getOptions(); // list that have all the options already

        for (int i = 0; i < option.size(); i++) {
            Assert.assertEquals(option.get(i).getText(), compare[i]); // comparing the actual text

        }
        newused1.selectByIndex(3);
        make1.selectByValue("tesla");

        String[] teslacom = {"All models", "Model 3", "Model S", "Model X", "Model Y", "Roadster"};
        List<WebElement> optionT = model1.getOptions();

        for (int i = 0; i < optionT.size(); i++) {
            Assert.assertEquals(optionT.get(i).getText(), teslacom[i]);
        }

        model1.selectByIndex(2);
        price1.selectByValue("100000");
        distance1.selectByValue("50");
        driver.findElement(By.id("make-model-zip")).clear();
        driver.findElement(By.id("make-model-zip")).sendKeys("22182", Keys.ENTER);

        Thread.sleep(2000);
        List<WebElement> element = driver.findElements(By.xpath("//h2[@class='title']")); // to find common xpath for all text

        if (element.isEmpty()) {
            throw new RuntimeException();
        }
        System.out.println("list size is " + element.size()); // to check how many element I have

        for (WebElement count : element) {
            Assert.assertTrue(count.getText().contains("Tesla")); // to check if all title had tesla
        }

        WebElement element1 = driver.findElement(By.id("sort-dropdown")); // to select low price
        Select sortby = new Select(element1);
        sortby.selectByValue("list_price");

        Thread.sleep(3000);
        List<WebElement> elements = driver.findElements(By.xpath("//span[@class='primary-price']"));
        if (elements.isEmpty()) {
            throw new RuntimeException();
        }
        double num = 0.0;
         for (WebElement count : elements) { // to check if all prices sorted right
            Assert.assertTrue(Double.parseDouble(count.getText().replaceAll("[$,]", "")) >= num);
            num = Double.parseDouble(count.getText().replaceAll("[$,]", ""));
            System.out.println(num);
        }


        sortby.selectByValue("mileage_desc");//sorting by miles


        Thread.sleep(3000);
        List<WebElement> elements1 = driver.findElements(By.xpath("//div[@class='mileage'][contains(text(),' mi.')]"));
        if (elements1.isEmpty()) {
            throw new RuntimeException();
        }
        long hello1 = 1000000000;
        for (WebElement webElement : elements1) {
             Assert.assertTrue(Long.parseLong(webElement.getText().replaceAll("[$,mi. ]", "")) < hello1);
            hello1 = (Long.parseLong(webElement.getText().replaceAll("[$,mi. ]", "")));
            System.out.println(hello1);
        }


        sortby.selectByValue("distance");

        Thread.sleep(3000);    //sorting by distance from zipcode
        List<WebElement> far = driver.findElements(By.xpath("//div[@data-qa='miles-from-user']"));
        if (far.isEmpty()) {
            throw new RuntimeException();
        }

        int hello12 = 0;
        for (WebElement webElement : far) { //check if the distance miles from your zipcode sorted correct
            Assert.assertTrue(Integer.parseInt(webElement.getText().substring(0, 4).replaceAll("[$,mi. ]", "")) >= hello12);
            hello12 = (Integer.parseInt(webElement.getText().substring(0, 4).replaceAll("[$,mi. ]", "")));
            System.out.println(hello12);
        }


        sortby.selectByValue("year");
//
        Thread.sleep(3000);    //sorting by years
        List<WebElement> year = driver.findElements(By.xpath("//h2[@class='title'] "));
        if (far.isEmpty()) {
            throw new RuntimeException();
        }

        int hello13 = 0;
        for (WebElement webElement : year) { //check if the years sorted correct

            Assert.assertTrue(Integer.parseInt(webElement.getText().substring(0, 5).replaceAll("[$,mi. ]", "")) >= hello13);
            hello13 = (Integer.parseInt(webElement.getText().substring(0, 5).replaceAll("[$,mi. ]", "")));
            System.out.println(hello13);

    }driver.quit();
        Instant end = Instant.now();
        Duration timeElapsed = Duration.between(start, end);
        System.out.println("Time taken: "+ timeElapsed.toMillis() +" milliseconds");

    }}