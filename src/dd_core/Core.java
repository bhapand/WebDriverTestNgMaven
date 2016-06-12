package dd_core;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import dd_utils.TestConfig;
import dd_utils.Xls_Reader;
import dd_utils.monitoringMail;

public class Core {

	public static WebDriver driver;
	public static Properties Config = new Properties();
	public static Properties Object = new Properties();
	public static FileInputStream fis;
	public static Xls_Reader excel = new Xls_Reader(System.getProperty("user.dir")+"/src/dd_core/TestCases.xlsx");
	public static Logger logs = Logger.getLogger("devpinoyLogger");




	@BeforeSuite
	public void init() throws IOException{
		//Initializing the property files
		if(driver==null){

			fis = new FileInputStream(System.getProperty("user.dir")+"/src/dd_properties/Config.properties");
			Config.load(fis);
			logs.debug("Loading the Configuration File");

			fis = new FileInputStream(System.getProperty("user.dir")+"/src/dd_properties/Object.properties");
			Object.load(fis);
			logs.debug("Loading the Objects File-(Containing the XPATHs)");
			
			if(Config.getProperty("browser").equals("firefox")){
				driver = new FirefoxDriver();
			}else if(Config.getProperty("browser").equals("ie")){
				System.setProperty("webdriver.ie, driver","InternetExplorerDriver");
				driver = new InternetExplorerDriver();
			}else if(Config.getProperty("browser").equals("chrome")){
				System.setProperty("webdriver.chrome.driver", "ChromeDriver");
				driver = new ChromeDriver();
			}else if(Config.getProperty("browser").equals("safari")){
				System.setProperty("webdriver.safari.driver", "SafariDriver");
			}
		}
		driver.get(Object.getProperty("site"));
		driver.manage().window().maximize();
	}




	//Checking if the test case is executable

	public boolean toRun(String testCaseName){

		logs.debug("Checking if the test case is executable");
		for(int rownum=2;rownum<=excel.getRowCount("testSuite"); rownum++){
			if(excel.getCellData("testSuite", "Test Case", rownum).equals(testCaseName)){
				if(excel.getCellData("testSuite", "Run Mode", rownum).equals("Y")){
					return true;
				}
			}

		}return false;
	}


	//Reading values from the object file

	public WebElement findElement(String key){
		return driver.findElement(By.xpath(Object.getProperty(key)));
	}

	//Global Wait

	public void waitTime(){
		driver.manage().timeouts().implicitlyWait(20L, TimeUnit.SECONDS);
	}


	//Closing browser after the end of test
	@AfterSuite
	public void quitBrowser() throws AddressException, MessagingException{
		logs.debug("Closing the Browser now");
		driver.quit();


		//Sending email after the end of test

		monitoringMail mail = new monitoringMail();
		mail.sendMail(TestConfig.server, TestConfig.from, TestConfig.to, TestConfig.subject, TestConfig.messageBody, TestConfig.attachmentPath, TestConfig.attachmentName);
	}

}
