package edu.agh.mobile.contacts.lab.test.gui;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import edu.agh.mobile.contacts.lab.test.Configuration;

public class PositiveGuiTest {

    private WebDriver driver;

    @Before
    public void setUp() {
        driver = new FirefoxDriver();
    }

    @Test
    public void forNewContactCreationShouldBeAccepted() {

        // given
        driver.get(Configuration.CONTACTS_SERVER + "/catalog");

        WebElement id = driver.findElement(By.name("id"));
        id.sendKeys("7");
        
        driver.findElement(By.name("name"))
                .sendKeys("Jasiek K. Próbny");

        driver.findElement(By.name("phone"))
                .sendKeys("003 300 300");

        driver.findElement(By.name("email"))
                .sendKeys("jasiekk@proba.pl");

        // when
        id.submit();

        // then
        Assert.assertTrue(driver.getPageSource().contains("Contact successfully created"));
    }

    @After
    public void tearDown() {
        driver.close();
    }

}
