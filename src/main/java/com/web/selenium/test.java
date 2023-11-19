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
	// on page loading / redirect will wait for this element to be visible before
	// doing any operation. Just to avoid thread.sleep
	private static String elemntOnLoading = "gnav20-Shop-L1";
	private static String privacyLink = "//*[contains(text(), 'Your Privacy Choices')][1]";
//		private String onLoadingPrivacyPage = "gnav20-Shop-L1";

	// These are two radio buttons opt in / opt out
	private static String optOutId = "optOut";
	private static String optInId = "doNotOptOut";

	// This is the dialog box on clicking yes button
	private static String privacyDialogBox = ".ModalDialog-sc-1m9nirl-0[role='dialog']";

	// This is the yes button option for dialog
	private static String privacyDialogBoxYes = "button[aria-label='Yes']";

	public static void main(String[] args) {

		// Here configuring the driver

		System.setProperty("webdriver.chrome.driver",
				System.getProperty("user.dir") + "\\src\\main\\resources\\chromedriver.exe");

		// getting 403 sometimes while accing the url so added chrome options
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("w3c", false);
		options.addArguments("--remote-allow-origins=*");

		// options.setCapability("goog:loggingPrefs", "{\"browser\": \"ALL\"}");

		driver = new ChromeDriver(options);

		// maximazing the window
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();

		// Adding wait to look elements to load instead of adding thread.sleep
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		
		//------------------
		// This flow is for opt in button
		driver.get(url);

		// Waiting for element to load when passing the url to driver
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(elemntOnLoading)));

		// Scrolling to bottom of the page to fing privacy setting option at footer and
		// clicking it
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",
				driver.findElement(By.xpath(privacyLink)));
		driver.findElement(By.xpath(privacyLink)).click();

		// On your privacy settings page locating the radio button "Opt in"

		WebElement optIn = driver.findElement(By.id(optInId));

		// Checking if the "Opt In" button is selected or not, if not selecting it and
		// then calling the handleDialog function to acccept it
		if (!optIn.isSelected()) {
			optIn.click();
			handleDialog();
		}

		// After clicking yes in dialog box, executing the command
		// Create an instance of JavascriptExecutor
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

		// Execute the airgap.getConsent() command and store the result
		Object result = jsExecutor.executeScript("return airgap.getConsent();");
		
		boolean saleOfInfoValue = (boolean) ((org.openqa.selenium.JavascriptExecutor) jsExecutor)
				.executeScript("return arguments[0].purposes.SaleOfInfo;", result);

		System.out.println("SaleOfInfo status: " + saleOfInfoValue);
		// Print the result
		System.out.println("Consent Result: " + result);
		
		//-------------------
		// This flow is for opt out button
		
		driver.get(url);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(elemntOnLoading)));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",
				driver.findElement(By.xpath(privacyLink)));
		driver.findElement(By.xpath(privacyLink)).click();
		WebElement optOut = driver.findElement(By.id(optOutId));
		optOut.click();
		result = jsExecutor.executeScript("return airgap.getConsent();");
		saleOfInfoValue = (boolean) ((org.openqa.selenium.JavascriptExecutor) jsExecutor)
				.executeScript("return arguments[0].purposes.SaleOfInfo;", result);
		System.out.println("SaleOfInfo status: " + saleOfInfoValue);
		// Print the result
		System.out.println("Consent Result: " + result);
		
		// After the flow terminating the driver instance if its available

		if (driver != null) {
			driver.quit();
		}

	}

	private static void handleDialog() {
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

}
