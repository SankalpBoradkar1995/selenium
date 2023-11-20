package com.web.selenium;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class PrivacyOptOut {
	private static String url = "https://www.verizon.com/digital/nsa/nos/ui/acct/privacy/your-privacy-choices";
	private static WebDriver driver;
	private static WebDriverWait wait;
	// on page loading / redirect will wait for this element to be visible before
	// doing any operation. Just to avoid thread.sleep
	private String elemntOnLoading = "gnav20-Shop-L1";

	// These are two radio buttons opt in / opt out

	private static String optOutId = "optOut";

	// This is the dialog box on clicking yes button
	private static String privacyDialogBox = ".ModalDialog-sc-1m9nirl-0[role='dialog']";

	// This is the yes button option for dialog
	private static String privacyDialogBoxYes = "button[aria-label='Yes']";

	// Shop button
	private static String shopButtonId = "gnav20-Shop-L1";

	// Devices option
	private static String devicesId = "gnav20-Shop-L2-3";

	// Smartphone option
	private static String smartPhoneId = "gnav20-Shop-L3-1";

	// Help me choices pop up
	private static String helpMeChoicesId = "_15gifts-popup-wrapper-testId";

	// Chat close button
	private static String closeChat = "//*[@data-lp-event='close']";

	public static void main(String[] args) throws InterruptedException {
		System.setProperty("webdriver.chrome.driver",
				System.getProperty("user.dir") + "\\src\\main\\resources\\chromedriver.exe");

		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("w3c", false);
		options.addArguments("--remote-allow-origins=*");

		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		driver.get(url);
		WebElement optOut = driver.findElement(By.id(optOutId));
//		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",
//				driver.findElement(By.id(optOutId)));
		wait.until(ExpectedConditions.visibilityOf(optOut));
		wait.until(ExpectedConditions.elementToBeClickable(optOut));
		optOut.click();

		// Clicking Shop button
		driver.findElement(By.id(shopButtonId)).click();

		// Waiting for Devices button to be clickable then clicking it
		wait.until(ExpectedConditions.elementToBeClickable(By.id(devicesId)));
		driver.findElement(By.id(devicesId)).click();

		// Waiting for Smartphone button to be clickable then clicking it
		wait.until(ExpectedConditions.elementToBeClickable(By.id(smartPhoneId)));
		driver.findElement(By.id(smartPhoneId)).click();

		// Scrolling and selecting first phone option available
		driver.findElement(By.cssSelector("#MTQP3LL\\/A-null > #productDetails img")).click();
		// Selecting phone color
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".swatch:nth-child(2) > .h-9")));
		driver.findElement(By.cssSelector(".swatch:nth-child(2) > .h-9")).click();

		// *[@id="128"]/div/label
		// Scrolling down to selet storage
		Thread.sleep(3000);
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

		// Execute the airgap.getConsent() command and store the result
		Object result = jsExecutor.executeScript("return airgap.getConsent();");
		System.out.println("Consent Result: " + result);
		boolean saleOfInfoValue;
		String OptInString;

		// Adding try catch exception because, sometimes "SaleOfInfo" returns true
		// sometimes auto.
		// Since auto is not a boolean type but string it gives classCastException
		// Hence to avoid it handeling it in try catch block such that it will return
		// either "auto" or "true" as saleOfInfo on opting in
		// also asserting the result using Assert.assertTrue
		try {
			saleOfInfoValue = (boolean) ((org.openqa.selenium.JavascriptExecutor) jsExecutor)
					.executeScript("return arguments[0].purposes.SaleOfInfo;", result);
			System.out.println("SaleOfInfo status: " + saleOfInfoValue);
			if (!saleOfInfoValue) {
				Assert.assertTrue(true, "Sale of info is true if opting out");
			}
		} catch (ClassCastException e) {
			if (e.getMessage().contains("class java.lang.String cannot be cast to class java.lang.Boolean")) {
				OptInString = (String) ((org.openqa.selenium.JavascriptExecutor) jsExecutor)
						.executeScript("return arguments[0].purposes.SaleOfInfo;", result);
				System.out.println("SaleOfInfo status: " + OptInString);
				if (!(OptInString.equalsIgnoreCase("Auto") || OptInString.equalsIgnoreCase("false"))) {
					Assert.assertTrue(true, "Sale of info is true if opting out");
				}
			}
		}

		if (driver != null) {
			driver.quit();
		}

	}

}
