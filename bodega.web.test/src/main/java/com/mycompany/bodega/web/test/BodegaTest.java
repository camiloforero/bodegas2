/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.bodega.web.test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;
import org.junit.AfterClass;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;
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
    
    
    @AfterClass
    public static void tearDown() {
        webDriver.quit();
        
        String fails = checkFails.toString();
        if(!"".equals(fails)) {
            fail(fails);
        }
    }
    
    
    
}
