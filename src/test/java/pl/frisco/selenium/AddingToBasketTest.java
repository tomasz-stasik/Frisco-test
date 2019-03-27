package pl.frisco.selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;
import java.util.concurrent.TimeUnit;
import static org.assertj.core.api.Assertions.assertThat;

public class AddingToBasketTest {
    ChromeDriver chromeDriver;
    @Before
    public void browserPreparation() {
        System.setProperty("webdriver.chrome.driver", "C:/DRIVERS/chromedriver.exe");
        chromeDriver = new ChromeDriver();
        chromeDriver.manage().window().maximize();
    }

    @After
    public void cleaning() {
        chromeDriver.quit();
    }

    //Test case 1 (Przypadek testowy 1)
    @Test
    public void valueMinus1(){
        inPutValue("-1");
        checkingValueFalse();
    }

    //Test case 2 (Przypadek testowy 2)
    @Test
    public void value0(){
        inPutValue("0");
        checkingValueFalse();
    }

    //Test case 3 (Przypadek testowy 3)
    @Test
    public void value1(){
        inPutValue("1");
        checkingValueTrue("1");
    }

    //Test case 4 (Przypadek testowy 4)
    @Test
    public void value500(){
        inPutValue("500");
        checkingValueTrue("500");
    }

    //Test case 5 (Przypadek testowy 5)
    @Test
    public void value998(){
        inPutValue("998");
        checkingValueTrue("998");
    }

    //Test case 6 (Przypadek testowy 6)
    @Test
    public void value999(){
        inPutValue("999");
        checkingValueTrue("999");
    }

    //Test case 7 (Przypadek testowy 7)
    @Test
    public void value1000(){
        inPutValue("1000");
        checkingValueTrue("1");
    }

    //Test case 8 (Przypadek testowy 8)
    @Test
    public void valueFloaringPoint(){
        inPutValue("2,6");
        checkingValueFalse();
    }

    //Test case 9 (Przypadek testowy 9)
    @Test
    public void valueAsText(){
        inPutValue("abc");
        checkingValueFalse();
    }

    //Test case 10 (Przypadek testowy 10)
    @Test
    public void valueAsWhiteMark(){
        inPutValue(" ");
        checkingValueFalse();
    }

    private void loadingWeb(String cssSelector) {
        WebDriverWait wait = new WebDriverWait(chromeDriver, 20);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(cssSelector)));
    }

    private void initialActivities(){
        chromeDriver.get("https://www.frisco.pl/pid,3617/n,frisco-fresh-natka-pietruszki---peczek/stn,product");
        loadingWeb(".product-box_cart-button a.cart-button_add");
        chromeDriver.findElement(By.cssSelector(".product-box_cart-button a.cart-button_add")).click();
        loadingWeb(".button.secondary.post-code-popup_location.alone.cta");
        chromeDriver.findElement(By.cssSelector(".button.secondary.post-code-popup_location.alone.cta")).click();
        loadingWeb(".post-code-popup_bottom .button.cta");
        chromeDriver.findElement(By.cssSelector(".post-code-popup_bottom .button.cta")).click();
        loadingWeb(".cart-button_inputs .cart-button_quantity");

    }

    private void inPutValue(String value){
        initialActivities();
        WebElement inPut = chromeDriver.findElement(By.cssSelector(".cart-button_inputs .cart-button_quantity"));
        inPut.clear();
        inPut.sendKeys(value);
        loadingWeb(".header_bar.seach-state .header_cart-box.with-chevron");
    }

    private void checkingValueTrue(String verifiedValue) {
        chromeDriver.findElement(By.cssSelector(".header_bar.seach-state .header_cart-box.with-chevron")).click();
        loadingWeb(".mini-product-box .cart-button_inputs .cart-button_quantity");
        WebElement date = chromeDriver.findElement(By.cssSelector(".mini-product-box .cart-button_inputs .cart-button_quantity"));
        assertThat(date.getText().contains(verifiedValue));
    }

    private void checkingValueFalse() {
        chromeDriver.findElement(By.cssSelector(".header_bar.seach-state .header_cart-box.with-chevron")).click();
        loadingWeb(".price.digits-1 .price_num");
        WebElement date1 = chromeDriver.findElement(By.cssSelector("a.button.primary.large.alone .price.digits-1 .price_num"));
        assertThat(date1.getText().contains("0"));
        WebElement date2 = chromeDriver.findElement(By.cssSelector("a.button.primary.large.alone .price.digits-1 .comma-hidden"));
        assertThat(date2.getText().contains(","));
        WebElement date3 = chromeDriver.findElement(By.cssSelector("a.button.primary.large.alone .price.digits-1 .price_decimals"));
        assertThat(date3.getText().contains("00"));
    }

}
