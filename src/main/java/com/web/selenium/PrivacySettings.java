package com.web.selenium;

import java.time.Duration;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class PrivacySettings {

	//private String url = "https://www.verizon.com/";
	private String url = "https://www.verizon.com/digital/nsa/nos/ui/acct/privacy/your-privacy-choices";
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
	
	// Shop button
	private String shopButtonId = "gnav20-Shop-L1";
	
	// Devices option
	private String devicesId = "gnav20-Shop-L2-3";
	
	// Smartphone option
	private String smartPhoneId = "gnav20-Shop-L3-1";
	
	// Help me choices pop up
	private String helpMeChoicesId = "_15gifts-popup-wrapper-testId";
	
	// Chat close button
	private String closeChat = "//*[@data-lp-event='close']";

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

	@Test(enabled=false)
	public void clickOptIn() throws InterruptedException {
		driver.get(url);
		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(elemntOnLoading)));

//		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",
//				driver.findElement(By.xpath(privacyLink)));
//		driver.findElement(By.xpath(privacyLink)).click();

		WebElement optIn = driver.findElement(By.id(optInId));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",
				driver.findElement(By.id(optInId)));
		wait.until(ExpectedConditions.elementToBeClickable(optIn));

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
		System.out.println("Consent Result: " + result);

		boolean saleOfInfoValue;
		String OptInString;
		try {
			saleOfInfoValue = (boolean) ((org.openqa.selenium.JavascriptExecutor) jsExecutor)
					.executeScript("return arguments[0].purposes.SaleOfInfo;", result);
			System.out.println("SaleOfInfo status: " + saleOfInfoValue);
		} catch (ClassCastException e) {
			if(e.getMessage().contains("class java.lang.String cannot be cast to class java.lang.Boolean"))
			{
				OptInString = (String) ((org.openqa.selenium.JavascriptExecutor) jsExecutor)
						.executeScript("return arguments[0].purposes.SaleOfInfo;", result);
				System.out.println("SaleOfInfo status: "+OptInString);
				if(!(OptInString.equalsIgnoreCase("Auto")||OptInString.equalsIgnoreCase("true")))
				{
					Assert.assertTrue(true,"Sale of info is false if opting in");
				}
			}
		}

		
		// Print the result
		
	}

	@Test(enabled=true)
	public void clickOptOut() throws InterruptedException {
		driver.get(url);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(elemntOnLoading)));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",
				driver.findElement(By.id(optOutId)));
		wait.until(ExpectedConditions.elementToBeClickable(By.id(optOutId)));
		//driver.findElement(By.xpath(privacyLink)).click();
		WebElement optOut = driver.findElement(By.id(optOutId));
		optOut.click();
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		Object result = jsExecutor.executeScript("return airgap.getConsent();");
		boolean saleOfInfoValue = (boolean) ((org.openqa.selenium.JavascriptExecutor) jsExecutor)
				.executeScript("return arguments[0].purposes.SaleOfInfo;", result);
		System.out.println("SaleOfInfo status: " + saleOfInfoValue);
		// Print the result
		System.out.println("Consent Result: " + result);
		
		// Clicking Shop button
		driver.findElement(By.id(shopButtonId)).click();
		
		// Waiting for Devices button to be clickable then clicking it
		wait.until(ExpectedConditions.elementToBeClickable(By.id(devicesId)));
		driver.findElement(By.id(devicesId)).click();
		
		// Waiting for Smartphone button to be clickable then clicking it
		wait.until(ExpectedConditions.elementToBeClickable(By.id(smartPhoneId)));
		driver.findElement(By.id(smartPhoneId)).click();
		
		// Closing chat card
//		WebElement chatOption = driver.findElement(By.xpath(closeChat));
//		wait.until(ExpectedConditions.visibilityOf(chatOption));
//		chatOption.click();
		
		// Find all product options using the XPath
//        List<WebElement> productOptions = driver.findElements(By.xpath("//div[contains(@class, 'grid') and contains(@class, 'gap-3') and contains(@class, 'w-full') and contains(@class, 'grid-cols-2')]/div"));
//        
//     // Scroll to a random product from the list above and click it
//        if(!productOptions.isEmpty())
//        {
//        	 // Get a random index
//            int randomIndex = new Random().nextInt(productOptions.size());
//         // Scroll to the random product
//            WebElement randomProduct = productOptions.get(randomIndex);
//            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", randomProduct);
//            wait.until(ExpectedConditions.visibilityOf(randomProduct));
//            wait.until(ExpectedConditions.elementToBeClickable(randomProduct));
//            // Click the random product
//            Actions action = new Actions(driver);
//            action.moveToElement(randomProduct).build().perform();
//            action.click(randomProduct).build().perform();
//            randomProduct.click();
//           // randomProduct.click();
//            Thread.sleep(5000);
//        }
//        else
//        {
//        	 System.out.println("No product options found.");
//        }
		
		// Scrolling and selecting first phone option available 
		driver.findElement(By.cssSelector("#MTQP3LL\\/A-null > #productDetails img")).click();
		// Selecting phone color
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".swatch:nth-child(2) > .h-9")));
		driver.findElement(By.cssSelector(".swatch:nth-child(2) > .h-9")).click();
		
		//*[@id="128"]/div/label
		// Scrolling down to selet storage
		Thread.sleep(3000);
		jsExecutor.executeScript("window.scrollTo(0,12)");
		// selecting storage
		driver.findElement(By.id("label-clp7391t3000g358jb7vgbjq3")).click();
		//driver.findElement(By.id("//*[@id='128']/div/label")).click();
		// Selecting New Customer button
		driver.findElement(By.cssSelector("#label-clp7393vu000o358jsc63nrme > .ChildWrapper-VDS__sc-n9jxr1-2 > div > div > .flex")).click();
		// Scrolling down
		jsExecutor.executeScript("window.scrollTo(0,478)");
		
		WebElement element = driver.findElement(By.cssSelector(".glVjuF > .HitArea-VDS__sc-bc3yhn-0"));
	    Actions builder = new Actions(driver);
	    builder.moveToElement(element).perform();
		  
		Thread.sleep(5000);

		
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
//		if (driver != null) {
//			driver.quit();
//		}
	}
}
