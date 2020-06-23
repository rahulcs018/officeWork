package Runner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import AmazonProducts.AmazonProductPriceMonitoring;
import FlipkartProducts.FlipkartProductPriceMonitoring;

public class PriceMonitoringRunner {

	String PriceFromFlipkart;
	String ProductNameFromFlipkart;

	String PriceFromAmazon;
	String ProductNameFromAmazon;
	WebDriver driver;

	public void driverSetup() {

		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\rahul\\Downloads\\chromedriver_win32\\chromedriver.exe");
		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
	}

	@Test(priority = 1, dataProvider = "inputProdctName")

	public void searchProductOnFlipkartPageCalling(String productNameForSearch) throws InterruptedException {
		driverSetup();
		FlipkartProductPriceMonitoring flip = PageFactory.initElements(driver, FlipkartProductPriceMonitoring.class);
		flip.searchProductOnFlipkart(productNameForSearch);
		PriceFromFlipkart = flip.productPrice;
		ProductNameFromFlipkart = flip.produtName;

	}

	@Test(priority = 2,enabled =true)

	public void searchProductOnAmazonPageCalling() throws InterruptedException {

		AmazonProductPriceMonitoring amazon = PageFactory.initElements(driver, AmazonProductPriceMonitoring.class);
		amazon.searchProductOnAmazon(ProductNameFromFlipkart);
		PriceFromAmazon = amazon.productPriceOnAmazon;
		ProductNameFromAmazon = amazon.produtNameOnAmazon;

	}

	@Test(priority = 3,enabled =true)

	public void cheapestPriceAvailable() {
		Integer priceOnflipkart = new Integer(PriceFromFlipkart);
		try {
			Integer priceOnAmazon = new Integer(PriceFromAmazon);

			System.out.println("Product Name on Flipkart = " + ProductNameFromFlipkart);
			Reporter.log("Product Name on Flipkart = " + ProductNameFromFlipkart);
			System.out.println(ProductNameFromFlipkart + " Price on Flipkart = INR " + priceOnflipkart);
			Reporter.log(" Price on Flipkart = INR " + priceOnflipkart);

			System.out.println("Product Name on Amazon = " + ProductNameFromAmazon);
			Reporter.log("Product Name on Amazon = " + ProductNameFromAmazon);
			System.out.println("Price on Amazon = INR " + priceOnAmazon);
			Reporter.log(" Price on Amazon = INR " + priceOnAmazon);
			Reporter.log("");
			if (priceOnflipkart < priceOnAmazon) {
				System.out.println("Go ahead and order with flipKart " + (priceOnAmazon - priceOnflipkart)
						+ " INR cheaper than Amazon");
				Reporter.log("Go ahead and order with flipKart " + (priceOnAmazon - priceOnflipkart)
						+ " INR cheaper than Amazon");
			} else {
				System.out.println("Go ahead and order with Amazon " + (priceOnflipkart - priceOnAmazon)
						+ " INR cheaper than Flipkart");
				Reporter.log("Go ahead and order with Amazon " + (priceOnflipkart - priceOnAmazon)
						+ " INR cheaper than Flipkart");
			}
		} catch (Exception e) {
			Reporter.log("Flipkart item (" + ProductNameFromFlipkart + ")is not available on amazon ");
			Reporter.log(" Price on Flipkart = INR " + priceOnflipkart);
			
		}
		
	}

	@DataProvider(name = "inputProdctName") // supplying data for a test method.
	public Object[][] getData() throws IOException {
		XSSFWorkbook excelWorkbook = null;
		XSSFSheet excelSheet = null;
		// XSSFRow row = null;

		System.out.println("Start Reading excel......");
		FileInputStream fis = new FileInputStream("C:\\Users\\rahul\\Desktop\\DataProvider.xlsx"); // Your .xlsx file
																									// name along with
																									// path
		excelWorkbook = new XSSFWorkbook(fis);
		// Read sheet inside the workbook by its index
		excelSheet = excelWorkbook.getSheetAt(0);
		// Find number of rows in excel file
		System.out.println("First Row Number/index:" + excelSheet.getFirstRowNum() + " *** Last Row Number/index:"
				+ excelSheet.getLastRowNum());
		int rowCount = excelSheet.getLastRowNum() - excelSheet.getFirstRowNum() + 1;
		int colCount = excelSheet.getRow(0).getLastCellNum();
		System.out.println("Row Count is: " + rowCount + " *** Column count is: " + colCount);
		Object data[][] = new Object[rowCount][colCount];
		for (int rNum = 0; rNum < rowCount; rNum++) {
			Row row = excelSheet.getRow(rNum);

			for (int cNum = 0; cNum < colCount; cNum++) {
				data[rNum][cNum] = row.getCell(cNum).getStringCellValue();

				System.out.println(data[rNum][cNum]);
			}
			System.out.println();
		}

		return data;
	}

}
