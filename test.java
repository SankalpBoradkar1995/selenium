package com.web.selenium;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class test {
	  private static String url = "https://www.verizon.com/";
	    private static WebDriver driver;
	    private static WebDriverWait wait;
	    private static String elemntOnLoading = "gnav20-Shop-L1";
	    private static String privacyLink = "//*[contains(text(), 'Your Privacy Choices')][1]";
	    private static String onLoadingPrivacyPage = "gnav20-Shop-L1";
	    private static String optOutId = "optOut";
	    private static String optInId = "doNotOptOut";
	    private static String privacyDialogBox = ".ModalDialog-sc-1m9nirl-0[role='dialog']";
	    private static String privacyDialogBoxYes = "button[aria-label='Yes']";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.setProperty("webdriver.chrome.driver",
                System.getProperty("user.dir") + "\\src\\main\\resources\\chromedriver.exe");
        
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("w3c", false);
        options.addArguments("--remote-allow-origins=*");
        
        options.setCapability("goog:loggingPrefs", "{\"browser\": \"ALL\"}");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get(url);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(elemntOnLoading)));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",
                driver.findElement(By.xpath(privacyLink)));
        driver.findElement(By.xpath(privacyLink)).click();

        WebElement optIn = driver.findElement(By.id(optInId));

        if (!optIn.isSelected()) {
            optIn.click();
            handleDialog();
        }
        LogEntries logs = driver.manage().logs().get("browser");
        for (LogEntry entry : logs) {
            System.out.println(entry.getMessage());
        }
        
        driver.get(url);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(elemntOnLoading)));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",
                driver.findElement(By.xpath(privacyLink)));
        driver.findElement(By.xpath(privacyLink)).click();
        WebElement optOut = driver.findElement(By.id(optOutId));
        optOut.click();
        
        if(driver!=null)
        {
        	driver.quit();
        }
		

	}

	private static void handleDialog() {
		 try {
             WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
             WebElement dialogBox = wait.until(
                     ExpectedConditions.visibilityOfElementLocated(By.cssSelector(privacyDialogBox)));
             WebElement okButton = dialogBox.findElement(By.cssSelector(privacyDialogBoxYes));
             okButton.click();
             driver.switchTo().defaultContent();
             wait.until(ExpectedConditions
                     .invisibilityOfElementLocated(By.cssSelector(privacyDialogBox)));
         } catch (Exception e) {
             e.printStackTrace();
         }
		
	}

}
