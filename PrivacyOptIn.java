package DNS;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PrivacyOptIn {
	private static String url = "https://www.verizon.com/digital/nsa/nos/ui/acct/privacy/your-privacy-choices";
	private static WebDriver driver;
	private static WebDriverWait wait;
	// on page loading / redirect will wait for this element to be visible before
	// doing any operation. Just to avoid thread.sleep
	private String elemntOnLoading = "gnav20-Shop-L1";

	// These are two radio buttons opt in / opt out

	private static String optInId = "doNotOptOut";

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

	// Accessories id
	private static String accessoriesId = "gnav20-Shop-L2-4";

	// Gaming accessories
	private static String gamingAccessories = "gnav20-Shop-L3-52";

	private static String popupXPath = "//div[@class='fgifts-m8s17p']";

	private static String chatPopUp = "//*[@id=\"lpChat\"]/div[2]/div[1]/div/div[4]/div[3]";

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\velma3p.USWIN\\.m2\\chromedriver-win64\\chromedriver.exe");

		/*
		 * ChromeOptions options = new ChromeOptions(); //
		 * options.setExperimentalOption("w3c", false);
		 * options.addArguments("--remote-allow-origins=*");
		 * 
		 * options.setCapability("goog:loggingPrefs", "{\"browser\": \"ALL\"}");
		 */
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		Actions action = new Actions(driver);

		driver.get(url);
		WebElement optIn = driver.findElement(By.id(optInId));
//		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",
//				driver.findElement(By.id(optInId)));
		wait.until(ExpectedConditions.elementToBeClickable(optIn));

		if (!optIn.isSelected()) {
//			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",
//					driver.findElement(By.id(optInId)));
			wait.until(ExpectedConditions.elementToBeClickable(optIn));
			optIn.click();
			handleDialog();
		}

		// ----------------This flow is for Devices page

		// Clicking Shop button
		driver.findElement(By.id(shopButtonId)).click();

		// Waiting for Devices button to be clickable then clicking it
		wait.until(ExpectedConditions.elementToBeClickable(By.id(devicesId)));
		driver.findElement(By.id(devicesId)).click();

		// Waiting for Smartphone button to be clickable then clicking it
		wait.until(ExpectedConditions.elementToBeClickable(By.id(smartPhoneId)));
		driver.findElement(By.id(smartPhoneId)).click();
		
		try
		{
			// Scrolling and selecting first phone option available
			// Scrolling and selecting first phone option available
			try {
				driver.findElement(By.cssSelector("#MTQP3LL\\/A-null > #productDetails img")).click();
			} catch (Exception e) {
				driver.findElement(By.cssSelector("#MTQP3LL\\/A-null > #productDetails img")).click();
			}
			// Selecting phone color
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".swatch:nth-child(2) > .h-9")));
			handleChatPopUp();
			driver.findElement(By.cssSelector(".swatch:nth-child(2) > .h-9")).click();

			// Selecting storage
			// jsExecutor.executeScript("window.scrollTo(0,563)");
			// checking if the chat is open

			String xpath = "//*[@title='End conversation' and @class='lp_close lpc_maximized-header__close-button lpc_desktop']";
			// Find the elements matching the XPath
			WebElement element;
			try {
				element = driver.findElement(By.xpath(xpath));
				if (element != null) {
					element.click();
				} else {

				}

			} catch (NoSuchElementException e) {
				// *[@id="128"]/div/label
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(
						By.xpath("//input[@aria-label='128 GB' and @class='StyledInput-VDS__sc-n9jxr1-1 exZZih']")));
				WebElement storage = driver.findElement(By.xpath("//*[@id=\"128\"]/div/label"));
				// ((JavascriptExecutor)
				// driver).executeScript("arguments[0].scrollIntoView(true);", storage);
				storage.click();

				handleSidePopUp();

				String newCustomer = "(//*[contains(text(),'New customer')])[2]";

//				WebElement newCustomerRadioButton = driver
//						.findElement(By.xpath("//input[@aria-label='New customer' and @name='newOrExisting']"));
				WebElement newCustomerRadioButton = driver.findElement(By.xpath(newCustomer));

				try {
					newCustomerRadioButton.click();
				} catch (ElementClickInterceptedException e1) {
					try {
						driver.switchTo().defaultContent();
						// action.moveToElement(newCustomerRadioButton).build().perform();
						// action.click(newCustomerRadioButton).build().perform();
						// wait.until(ExpectedConditions.elementToBeClickable(newCustomerRadioButton));
						newCustomerRadioButton.click();
					} catch (ElementClickInterceptedException e2) {
						newCustomerRadioButton.click();
					}
				}
			}

			// Clicking payment type
			String paymentOptionXpath = "(//*[@class='LabelBase-VDS__sc-6gb90f-1 StackedLabel-VDS__sc-6gb90f-3 bSIEdH cRXKyB'])[position()=1]";
			WebElement paymentOption = driver.findElement(By.xpath(paymentOptionXpath));
			paymentOption.click();

			// clicking continue
			String nextStepXpath ="//*[@id=\"cta-btn\"]/div[1]/button/span[1]";
			////*[@id="cta-btn"]/div[1]/button/span[1]
			WebElement nextStep = driver.findElement(By.xpath(nextStepXpath));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(nextStepXpath)));
			driver.findElement(By.xpath(nextStepXpath)).click();

			// XPath for the dialog box and confirming location
			//String dialogBoxXPath = "//div[@class='ModalDialog-VDS__sc-1m9nirl-0 dJipA-d']";
			String dialogBoxXPath = "//*[@id=\"zipCode-overlay\"]/div[3]/div/div/div"; 
			WebElement dialogBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(dialogBoxXPath)));
			// Clear the zip code field and add value '30004'
			WebElement zipCodeField = driver.findElement(By.xpath("//input[@data-testid='zipInput']"));
			zipCodeField.clear();
			zipCodeField.sendKeys("30004");

			// Click the 'Confirm Location' button
			WebElement confirmLocationButton = driver.findElement(By.xpath("//button[@data-testid='zipConfirm']"));
			confirmLocationButton.click();

			handleSidePopUp();

			// Clicking on continue button
			String goToCartId = "goToCartCTA";
			driver.findElement(By.id(goToCartId)).click();

			// Selecting plan
			// *[@id="select_for_tile1"]/span[1]
			String selectPlanXpath = "//*[@id=\"select_for_tile1\"]/span[1]";
//			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",
//					driver.findElement(By.xpath(selectPlanXpath)));
//			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(selectPlanXpath)));
			// wait.until(ExpectedConditions.elementToBeClickable(By.xpath(selectPlanCss)));

			handleChatPopUp();
			handleSidePopUp();
			// *[@id="lpChat"]/div[2]/div[1]/div/div[4]/div[3]
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",
					driver.findElement(By.xpath(selectPlanXpath)));
			handleSidePopUp();
			driver.switchTo().defaultContent();
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",
					driver.findElement(By.xpath(selectPlanXpath)));
			// wait.until(ExpectedConditions.elementToBeClickable(By.xpath(selectPlanXpath))).click();

			try {
				driver.findElement(By.xpath(
						"/html/body/div[8]/div[1]/div[2]/div/div[2]/div/section/div[2]/div[2]/div[1]/div[1]/div/div[2]/div/div[3]/div[2]/div[2]/div[2]/div/div/div[1]/button/span[1]"))
						.click();

				// Execute the airgap.getConsent() command and store the result
				Object result = jsExecutor.executeScript("return airgap.getConsent();");

				// Converting output received by executing airgap.getConsent(); in console to
				// string

				String consoleResult = result.toString();
				System.out.println("Consent Result: " + result);

				// saleOfInfo method takes result og airgap.getConsent as string and check if it
				// has SaleofInfo & its value and return true / false
				boolean SaleOfInfo = saleOfInfo(consoleResult);

				// Depending upon our selection of opt in / out throwing exception
				if (SaleOfInfo == false) {
					throw new Exception("SaleOfInfo is false even when we have opted in");
				} else {
					System.out.println("SaleofInfo=" + SaleOfInfo + " , for Devices page");
				}
			} catch (Exception e) {
				Thread.sleep(3000);

				// Execute the airgap.getConsent() command and store the result
				Object result = jsExecutor.executeScript("return airgap.getConsent();");

				// Converting output received by executing airgap.getConsent(); in console to
				// string

				String consoleResult = result.toString();
				System.out.println("Consent Result: " + result);

				// saleOfInfo method takes result og airgap.getConsent as string and check if it
				// has SaleofInfo & its value and return true / false
				boolean SaleOfInfo = saleOfInfo(consoleResult);

				// Depending upon our selection of opt in / out throwing exception
				if (SaleOfInfo == false) {
					throw new Exception("SaleOfInfo is false even when we have opted in");
				} else {
					System.out.println("SaleofInfo=" + SaleOfInfo + " , for Devices page");
				}
			}
//			String noDeviceProtectionId = "protection_decline_btn_lbl";
//			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",
//					driver.findElement(By.id(noDeviceProtectionId)));
	//
//			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(noDeviceProtectionId)));
	//
//			driver.findElement(By.id(noDeviceProtectionId)).click();

			// --------------- This flow is for Accessories page

			// ((JavascriptExecutor) driver).executeScript("window.scrollTo(0,
			// -document.body.scrollHeight)");
			driver.get(url);
			WebElement shopButton = driver.findElement(By.id(shopButtonId));

			// Scrolling back to shop page
//			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",
//					driver.findElement(By.id(shopButtonId)));
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
//			wait.until(
//					ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".glVjuF > .HitArea-VDS__sc-bc3yhn-0")));
//			wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".glVjuF > .HitArea-VDS__sc-bc3yhn-0")));
			// driver.findElement(By.cssSelector(".glVjuF >
			// .HitArea-VDS__sc-bc3yhn-0")).click();
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",
					driver.findElement(By.xpath("//*[@id=\"cta-btn\"]/div[1]/button/span[1]")));
			// *[@id="cta-btn"]/div[1]/button/span[1]

			try {
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",
						driver.findElement(By.xpath("//*[@id=\"cta-btn\"]/div[1]/button/span[1]")));
				driver.findElement(By.xpath("//*[@id=\"cta-btn\"]/div[1]/button/span[1]")).click();
				driver.findElement(By.id("continueButtonId")).click();
				String dialogClassName = "ModalDialog-VDS__sc-1m9nirl-0";
				WebElement popup = wait.until(ExpectedConditions.presenceOfElementLocated(By.className(dialogClassName)));
				// Find the "New Customer" button and click it
				WebElement newCustomerButton = popup.findElement(By.xpath("//button[@data-testid='newCustomerCta']"));
				newCustomerButton.click();
				Object result = jsExecutor.executeScript("return airgap.getConsent();");
				result = jsExecutor.executeScript("return airgap.getConsent();");
				boolean resultForAccessoreisPage = saleOfInfo(result.toString());

				if (resultForAccessoreisPage == true) {
					throw new Exception("SaleOfInfo is false even when we have opted in");
				} else {
					System.out.println(
							"SaleofInfo=" + resultForAccessoreisPage + " , for Accessories page when we've opted out");
				}

				if (driver != null) {
					driver.quit();
				}
			} catch (Exception e) {
				Object result = jsExecutor.executeScript("return airgap.getConsent();");
				result = jsExecutor.executeScript("return airgap.getConsent();");
				boolean resultForAccessoreisPage = saleOfInfo(result.toString());
				if (resultForAccessoreisPage == false) {
					throw new Exception("SaleOfInfo is false even when we have opted in");
				} else {
					System.out.println("SaleofInfo=" + resultForAccessoreisPage + " , for Devices page");
				}

				if (driver != null) {
					driver.quit();
				}

			}
		}
		catch(Exception e)
		{
			Object result = jsExecutor.executeScript("return airgap.getConsent();");

			// Converting output received by executing airgap.getConsent(); in console to
			// string

			String consoleResult = result.toString();
			System.out.println("Consent Result: " + result);

			// saleOfInfo method takes result og airgap.getConsent as string and check if it
			// has SaleofInfo & its value and return true / false
			boolean SaleOfInfo = saleOfInfo(consoleResult);

			// Depending upon our selection of opt in / out throwing exception
			if (SaleOfInfo == false) {
				throw new Exception("SaleOfInfo is false even when we have opted in");
			} else {
				System.out.println("SaleofInfo=" + SaleOfInfo + " , for Devices page");
			}
			// --------------- This flow is for Accessories page

						// ((JavascriptExecutor) driver).executeScript("window.scrollTo(0,
						// -document.body.scrollHeight)");
						driver.get(url);
						WebElement shopButton = driver.findElement(By.id(shopButtonId));

						// Scrolling back to shop page
//						((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",
//								driver.findElement(By.id(shopButtonId)));
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
								.visibilityOfElementLocated(By.xpath("//*[@id=\"1000037770-Save $80\"]")));
						wait.until(ExpectedConditions
								.elementToBeClickable(By.cssSelector("//*[@id=\"1000037770-Save $80\"]")));
						driver.findElement(By.cssSelector("//*[@id=\"1000037770-Save $80\"]")).click();

						// Clicking on the continue button
//						wait.until(
//								ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".glVjuF > .HitArea-VDS__sc-bc3yhn-0")));
//						wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".glVjuF > .HitArea-VDS__sc-bc3yhn-0")));
						// driver.findElement(By.cssSelector(".glVjuF >
						// .HitArea-VDS__sc-bc3yhn-0")).click();
						((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",
								driver.findElement(By.xpath("//*[@id=\"cta-btn\"]/div[1]/button/span[1]")));
						// *[@id="cta-btn"]/div[1]/button/span[1]

						try {
							((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);",
									driver.findElement(By.xpath("//*[@id=\"cta-btn\"]/div[1]/button/span[1]")));
							driver.findElement(By.xpath("//*[@id=\"cta-btn\"]/div[1]/button/span[1]")).click();
							driver.findElement(By.id("continueButtonId")).click();
							String dialogClassName = "ModalDialog-VDS__sc-1m9nirl-0";
							WebElement popup = wait.until(ExpectedConditions.presenceOfElementLocated(By.className(dialogClassName)));
							// Find the "New Customer" button and click it
							WebElement newCustomerButton = popup.findElement(By.xpath("//button[@data-testid='newCustomerCta']"));
							newCustomerButton.click();
							 result = jsExecutor.executeScript("return airgap.getConsent();");
							result = jsExecutor.executeScript("return airgap.getConsent();");
							boolean resultForAccessoreisPage = saleOfInfo(result.toString());

							if (resultForAccessoreisPage == true) {
								throw new Exception("SaleOfInfo is false even when we have opted in");
							} else {
								System.out.println(
										"SaleofInfo=" + resultForAccessoreisPage + " , for Accessories page when we've opted out");
							}

							if (driver != null) {
								driver.quit();
							}
						} catch (Exception e1) {
							 result = jsExecutor.executeScript("return airgap.getConsent();");
							result = jsExecutor.executeScript("return airgap.getConsent();");
							boolean resultForAccessoreisPage = saleOfInfo(result.toString());
							if (resultForAccessoreisPage == false) {
								throw new Exception("SaleOfInfo is false even when we have opted in");
							} else {
								System.out.println("SaleofInfo=" + resultForAccessoreisPage + " , for Accessories page");
							}
			
						}
			
		}

		

	}

	private static void handleSidePopUp() {
		// Check if the pop-up is present
		if (isElementPresent(driver, By.xpath(popupXPath))) {
			// Click the "Not now" button
			WebElement notNowButton = driver
					.findElement(By.xpath("//button[@data-testid='_15gifts-engagement-bubble-button-secondary']"));
			notNowButton.click();
		}
	}

	private static void handleChatPopUp() {
		if (isElementPresent(driver, By.xpath(chatPopUp))) {
			driver.findElement(By.xpath(chatPopUp)).click();
		}
	}

	// Function to check if an element is present
	private static boolean isElementPresent(WebDriver driver, By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (org.openqa.selenium.NoSuchElementException e) {
			return false;
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

	private static boolean saleOfInfo(String consoleResult) {
		if (consoleResult.contains("SaleOfInfo=true") || consoleResult.contains("SaleOfInfo=Auto")) {
			return true;
		} else {
			return false;
		}
	}
}
