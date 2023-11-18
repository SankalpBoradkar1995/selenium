package com.web.selenium;

import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class PrivacySettings {

	private String url = "https://www.verizon.com/";
	private WebDriver driver;
	private WebDriverWait wait;
	private String elemntOnLoading = "gnav20-Shop-L1";
	private String privacyLink = "//*[contains(text(), 'Your Privacy Choices')][1]";
	private String onLoadingPrivacyPage = "gnav20-Shop-L1";
	private String optOutId = "optOut";
	private String optInId = "doNotOptOut";
	private String privacyDialogBox = ".ModalDialog-sc-1m9nirl-0[role='dialog']";
	private String privacyDialogBoxYes = "button[aria-label='Yes']";

	@BeforeTest
	public void init() {
		System.setProperty("webdriver.chrome.driver",
				System.getProperty("user.dir") + "\\src\\main\\resources\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--remote-allow-origins=*");
		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	@Test
	public void clickOptIn() throws InterruptedException {
		
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
	}
	
	@Test
	public void clickOptOut()
	{
		driver.get(url);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(elemntOnLoading)));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",
				driver.findElement(By.xpath(privacyLink)));
		driver.findElement(By.xpath(privacyLink)).click();
		WebElement optOut = driver.findElement(By.id(optOutId));
		optOut.click();
	}

	private void handleDialog() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			WebElement dialogBox = wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.cssSelector(privacyDialogBox)));
			WebElement okButton = dialogBox.findElement(By.cssSelector(privacyDialogBoxYes));
			okButton.click();
			driver.switchTo().defaultContent();
			wait.until(ExpectedConditions
					.invisibilityOfElementLocated(By.cssSelector(privacyDialogBox)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@AfterTest
	public void shutDown() {
		if (driver != null) {
			driver.quit();
		}
	}
}
