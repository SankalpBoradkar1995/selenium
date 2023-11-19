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
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class PrivacySettings {

	private String url = "https://www.verizon.com/";
	private WebDriver driver;
	private WebDriverWait wait;
	//on page loading / redirect will wait for this element to be visible before doing any operation. Just to avoid thread.sleep
	private String elemntOnLoading = "gnav20-Shop-L1";
	private String privacyLink = "//*[contains(text(), 'Your Privacy Choices')][1]";
//	private String onLoadingPrivacyPage = "gnav20-Shop-L1";
	
	// These are two radio buttons opt in / opt out
	private String optOutId = "optOut";
	private String optInId = "doNotOptOut";
	
	// This is the dialog box on clicking yes button
	private String privacyDialogBox = ".ModalDialog-sc-1m9nirl-0[role='dialog']";
	
	// This is the yes button option for dialog
	private String privacyDialogBoxYes = "button[aria-label='Yes']";

	@BeforeTest
	public void init() {

		
		// Setting up the driver
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
		LogEntries logs = driver.manage().logs().get("browser");
//		for (LogEntry entry : logs) {
//			System.out.println(entry.getMessage());
//		}
		// Create an instance of JavascriptExecutor
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

		// Execute the airgap.getConsent() command and store the result
		Object result = jsExecutor.executeScript("return airgap.getConsent();");

		boolean saleOfInfoValue = (boolean) ((org.openqa.selenium.JavascriptExecutor) jsExecutor)
				.executeScript("return arguments[0].purposes.SaleOfInfo;", result);

		System.out.println("SaleOfInfo status: " + saleOfInfoValue);
		// Print the result
		System.out.println("Consent Result: " + result);
	}

	@Test
	public void clickOptOut() {
		driver.get(url);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(elemntOnLoading)));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",
				driver.findElement(By.xpath(privacyLink)));
		driver.findElement(By.xpath(privacyLink)).click();
		WebElement optOut = driver.findElement(By.id(optOutId));
		optOut.click();
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		Object result = jsExecutor.executeScript("return airgap.getConsent();");
		boolean saleOfInfoValue = (boolean) ((org.openqa.selenium.JavascriptExecutor) jsExecutor)
				.executeScript("return arguments[0].purposes.SaleOfInfo;", result);
		System.out.println("SaleOfInfo status: " + saleOfInfoValue);
		// Print the result
		System.out.println("Consent Result: " + result);
	}

	private void handleDialog() {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			WebElement dialogBox = wait
					.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(privacyDialogBox)));
			WebElement okButton = dialogBox.findElement(By.cssSelector(privacyDialogBoxYes));
			okButton.click();
			driver.switchTo().defaultContent();
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(privacyDialogBox)));
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
