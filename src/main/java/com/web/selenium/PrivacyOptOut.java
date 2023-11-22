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

	// Accessories id
	private static String accessoriesId = "gnav20-Shop-L2-4";

	// Gaming accessories
	private static String gamingAccessories = "gnav20-Shop-L3-52";

	public static void main(String[] args) throws Exception {
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

		wait.until(ExpectedConditions.visibilityOf(optOut));
		wait.until(ExpectedConditions.elementToBeClickable(optOut));
		optOut.click();

		// ----------------------Checking sale of info for mobile devices page

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
		// Thread.sleep(3000);
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

		// Execute the airgap.getConsent() command and store the result
		Object result = jsExecutor.executeScript("return airgap.getConsent();");
		System.out.println("Consent Result: " + result);

		// Converting output received by executing airgap.getConsent(); in console to
		// string

		String consoleResult = result.toString();
		System.out.println("Consent Result: " + result);

		// Checking if string contains "SaleOfInfo=false" since this flow is for opt out
		// If SaleOfInfo=true even if we have opted out then throwing exception to fail
		// the test

		boolean SaleOfInfo = saleOfInfo(consoleResult);

		if (SaleOfInfo == true) {
			throw new Exception("SaleOfInfo is true even when we have opted out");
		} else {
			System.out.println("SaleofInfo=" + SaleOfInfo+" , for Devices page when we've opted out");
		}

		// ------------------------ Checking flow for Accessories page
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0, -document.body.scrollHeight)");
		WebElement shopButton = driver.findElement(By.id(shopButtonId));

		// Scrolling back to shop page
//		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",
//				driver.findElement(By.id(shopButtonId)));
		// Clicking on shop page
		wait.until(ExpectedConditions.visibilityOf(shopButton));
		wait.until(ExpectedConditions.elementToBeClickable(shopButton));
		shopButton.click();

		// Clicking on accessories option
		wait.until(ExpectedConditions.elementToBeClickable(By.id(accessoriesId)));
		driver.findElement(By.id(accessoriesId)).click();

		// Clicking on Gaming option
		wait.until(ExpectedConditions.elementToBeClickable(By.id(gamingAccessories)));
		driver.findElement(By.id(gamingAccessories)).click();

		// Selecting an element with css selectore, its href is:
		// href="/products/playstation/"
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.cssSelector(".categoryTilelets:nth-child(1) .tilelet-wrapper:nth-child(2) img")));
		wait.until(ExpectedConditions.elementToBeClickable(
				By.cssSelector(".categoryTilelets:nth-child(1) .tilelet-wrapper:nth-child(2) img")));
		driver.findElement(By.cssSelector(".categoryTilelets:nth-child(1) .tilelet-wrapper:nth-child(2) img")).click();

		// Clicking on the console
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.cssSelector("#\\31 000039815-Save\\ \\$60 #gridwallProductName > h2")));
		wait.until(ExpectedConditions
				.elementToBeClickable(By.cssSelector("#\\31 000039815-Save\\ \\$60 #gridwallProductName > h2")));
		driver.findElement(By.cssSelector("#\\31 000039815-Save\\ \\$60 #gridwallProductName > h2")).click();

		// Clicking on the continue button
		wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".glVjuF > .HitArea-VDS__sc-bc3yhn-0")));
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".glVjuF > .HitArea-VDS__sc-bc3yhn-0")));
		driver.findElement(By.cssSelector(".glVjuF > .HitArea-VDS__sc-bc3yhn-0")).click();

		result = jsExecutor.executeScript("return airgap.getConsent();");
		boolean resultForAccessoreisPage = saleOfInfo(result.toString());

		if (resultForAccessoreisPage == true) {
			throw new Exception("SaleOfInfo is false even when we have opted in");
		} else {
			System.out.println("SaleofInfo=" + resultForAccessoreisPage + " , for Accessories page when we've opted out");
		}

		if (driver != null) {
			driver.quit();
		}

	}

	private static boolean saleOfInfo(String consoleResult) {
		if (consoleResult.contains("SaleOfInfo=true") || consoleResult.contains("SaleOfInfo=auto")) {
			return true;
		} else {
			return false;
		}
	}

}
