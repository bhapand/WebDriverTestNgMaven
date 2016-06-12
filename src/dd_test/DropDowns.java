package dd_test;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import dd_core.Core;

public class DropDowns extends Core{
	@Test
	public void dropDowns() {
		
		driver.get("http://www.icicibank.com/");
		driver.manage().window().maximize();
		
		List<WebElement> drop_downs = driver.findElements(By.tagName("option"));
		for(int i=0;i<drop_downs.size();i++){
		System.out.println(drop_downs.get(i).getAttribute("value"));
		
		}
	}
}
