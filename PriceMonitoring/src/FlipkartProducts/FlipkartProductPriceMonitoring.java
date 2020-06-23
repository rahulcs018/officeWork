package FlipkartProducts;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Utility.UtilityClass;


public class FlipkartProductPriceMonitoring {

	private WebDriver driver;
	public String productPrice;
	public String produtName;

	public FlipkartProductPriceMonitoring(WebDriver driver)
	{
		this.driver =driver;
	}
	
	
	@FindBy(xpath = "//*[@class='_2AkmmA _29YdH8']")
	WebElement SkipLogin;
	
	@FindBy(xpath = "//input[@class='LM6RPg']")
	WebElement SearchBox;
	
	@FindBy(xpath = "//*[@class='_2BhAHa']")
	WebElement SearchButton;
	
	WebElement firstproductPrice;
	
	@FindBy(xpath = "//*[@class='_3wU53n']")
	WebElement firstproductName;
	
	@FindBy(xpath = "//*[@class='_2cLu-l']")
	WebElement firstproductNameAlternative;

	public void searchProductOnFlipkart(String productNameForSearch) throws InterruptedException {

		driver.get("https://www.flipkart.com/");
		Thread.sleep(3000);
		SkipLogin.click();
		SearchBox.sendKeys(productNameForSearch);
		Thread.sleep(3000);
		SearchButton.click();
		
		WebDriverWait wait = new WebDriverWait(driver, 20);
		firstproductPrice = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(@class, \"_1vC4OE\")]")));
		UtilityClass.SaveScreenshot("productOnFlipKart", driver);
		productPrice = firstproductPrice.getText();
		productPrice = productPrice.substring(1);
		productPrice=productPrice.replaceAll(",", "");
		try {
			produtName =firstproductName.getText();

		} catch (Exception e) {
		
			produtName = firstproductNameAlternative.getText();
		}
		Thread.sleep(3000);

	}

}
