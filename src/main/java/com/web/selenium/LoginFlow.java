package com.web.selenium;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginFlow {

	private static String loginId = "gnav20-sign-id";
	private static String signInToMyAccountId = "gnav20-sign-id-list-item-1";
	private static String idFieldid = "IDToken1";
	private static String passwordFieldId = "IDToken2";
	private static String rememberMeButton = "emailReset";
	private static String continutButtonId = "continueBtn";
	private static String signOutButtonId = "gnav20-account-link-id";
	private static String url = "https://www.verizon.com/digital/nsa/nos/ui/acct/privacy/your-privacy-choices";
	private static WebDriver driver;
	private static WebDriverWait wait;

	public static void main(String[] args) throws IOException, InterruptedException {
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
		Actions action = new Actions(driver);

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(loginId)));
		driver.findElement(By.id(loginId)).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(signInToMyAccountId)));
		driver.findElement(By.id(signInToMyAccountId)).click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(idFieldid)));
		WebElement id = driver.findElement(By.id(idFieldid));
		id.click();
		id.clear();
		id.sendKeys("boradkarsankalp6@gmail.com");
		action.sendKeys(Keys.ENTER).build().perform();
		
		
			

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(passwordFieldId)));
		WebElement password = driver.findElement(By.id(passwordFieldId));
		password.click();
		password.clear();
		password.sendKeys("@Sankalp1995");
		action.sendKeys(Keys.ENTER).build().perform();
		
		if(driver.findElement(By.id(continutButtonId)).isDisplayed())
		{
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(continutButtonId)));
			wait.until(ExpectedConditions.elementToBeClickable(By.id(continutButtonId)));
			driver.findElement(By.id(continutButtonId)).click();
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(passwordFieldId)));
			wait.until(ExpectedConditions.elementToBeClickable(By.id(passwordFieldId)));
			password = driver.findElement(By.id(passwordFieldId));
			password.click();
			password.clear();
			password.sendKeys("Goa");
			action.sendKeys(Keys.ENTER).build().perform();
			
			//Thread.sleep(10000);
			
			try {
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@aria-label='Link services']")));
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@aria-label='Link services']")));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				takeScreenShot(driver);
			}
			
//			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(signOutButtonId)));
//			((JavascriptExecutor) driver)
//		    .executeScript("window.scrollTo(0, -document.body.scrollHeight)");
			//Link services
			//takeScreenShot(driver);
		}
		else
		{
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(rememberMeButton)));
			WebElement rememberMe = driver.findElement(By.id(rememberMeButton));
			rememberMe.click();

			driver.findElement(By.id(continutButtonId)).click();

			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(passwordFieldId)));
			password.click();
			password.clear();
			password.sendKeys("Goa");
			action.sendKeys(Keys.ENTER).build().perform();

			Thread.sleep(10000);
			
			
			
//			((JavascriptExecutor) driver)
//		    .executeScript("window.scrollTo(0, -document.body.scrollHeight)");
//			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(signOutButtonId)));
			
			takeScreenShot(driver);
		}

	}
	
	private static void takeScreenShot(WebDriver driver) throws IOException
	{
		// Convert web driver object to TakeScreenshot
		TakesScreenshot scrShot = ((TakesScreenshot) driver);

		// Set source path
		File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);

		// Create timestamp for every new screenshot
		Date dt = new Date();
		String dateStamp = dt.toString().replaceAll(":", "_").replaceAll("\\s", "_");
		System.out.println();

		File DestFile = new File(System.getProperty("user.dir")+"\\src\\main\\resources\\" + dateStamp + ".png");

		// Copy file at destination

		FileUtils.copyFile(SrcFile, DestFile);

		System.out.println("Login success, check src/main/resources for screenshot...");
	}

}
