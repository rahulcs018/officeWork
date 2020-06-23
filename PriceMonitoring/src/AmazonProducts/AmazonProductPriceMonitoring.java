package AmazonProducts;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.annotations.Test;

import Utility.UtilityClass;
public class AmazonProductPriceMonitoring {
	private WebDriver driver;
	public String productPriceOnAmazon;
	public String produtNameOnAmazon;

	public AmazonProductPriceMonitoring(WebDriver driver)
	{
		this.driver =driver;
	}
	
	@FindBy(xpath = "//*[@id='twotabsearchtextbox']")
	WebElement SearchBox;
	
	@FindBy(xpath = "//*[@type='submit']")
	WebElement SearchButton;
	
	@FindBy(xpath = "//span[@class='a-size-medium a-color-base a-text-normal']")
	WebElement ProdutNameOnAmazon;
	
	@FindBy(xpath = "//*[@class='a-price-whole']")
	WebElement ProductPriceOnAmazon;
	
	
	
	
@Test
	public void searchProductOnAmazon(String productName) throws InterruptedException {
	driver.get("https://www.amazon.in/");
	Thread.sleep(3000);
	SearchBox.sendKeys(productName);
	SearchButton.click();
	Thread.sleep(5000);
	UtilityClass.SaveScreenshot("productOnAmazon", driver);
	produtNameOnAmazon = ProdutNameOnAmazon.getText();
	String productNameVerify=productName.substring(0, 20);
	boolean flag=produtNameOnAmazon.contains(productNameVerify);
	if(flag) {
		System.out.println("Flipkart item ("+productName+")is available on amazon "+flag);
		productPriceOnAmazon=ProductPriceOnAmazon.getText();
		productPriceOnAmazon=productPriceOnAmazon.replaceAll(",", "");
	}
	else{
		System.out.println("Flipkart item ("+productName+")is not available on amazon ");
		UtilityClass.SaveScreenshot("notAvailableOnAmazon", driver);
	}
	
	Thread.sleep(3000);
	driver.close();
	
	
	}

}
