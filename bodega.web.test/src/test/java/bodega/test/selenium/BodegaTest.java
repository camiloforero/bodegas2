/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package bodega.test.selenium;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.AfterClass;
import org.junit.Assert;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;


/**
 *
 * @author admin
 */
public class BodegaTest {
    
    private static WebDriver webDriver;
    
    private static String baseUrl;
    
    private static URI baseUri;
    
    private static boolean nextAlerts;
    
    private static StringBuffer checkFails;
    
    @BeforeClass
    public static void setUp() throws URISyntaxException {
        webDriver = new FirefoxDriver();
        baseUrl = "http://localhost:8080";
        baseUri = new URI("http://localhost:8080");
        
        webDriver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
    }
    
    @Before
    public void setUpUrl() {
        webDriver.get(baseUrl + "/bodega.web/");
    }
    
    @Test
    public void testCrearBodega() throws Exception {
        webDriver.findElement(By.xpath("//button[contains(@id, 'createButton')]")).click();
        Thread.sleep(2000);
        
        webDriver.findElement(By.id("name")).clear();
        webDriver.findElement(By.id("name")).sendKeys("NuevaBodega");
        
        webDriver.findElement(By.xpath("//button[contains(@id, 'saveButton')]")).click();
        Thread.sleep(2000);
        List<WebElement> table = webDriver.findElements(By.xpath("//table[contains(@class,'table striped')]/tbody/tr"));
        boolean good = false;
        for (WebElement webElement : table) {
            List<WebElement> elements = webElement.findElements(By.xpath("td"));
            for(WebElement elemento : elements)
            {
                if(elemento.getText().equals("NuevaBodega"))
                    good = true;
            }
        }
        
        assertTrue(good);
    }
    
    @AfterClass
    public static void tearDown()
    {
        webDriver.quit();
    }
}




