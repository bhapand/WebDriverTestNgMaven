package dd_test;



import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import dd_core.Core;

public class AllLinks extends Core {
	@Test 
	public void allLinks() throws InterruptedException {

		System.out.println("Printing the links names of the top row link!");
		for(int i=1;i<=7;i++){
			System.out.println(driver.findElement(By.xpath("html/body/div[1]/header/div/ul/li["+i+"]/a")).getText());
		}
		System.out.println("Printing the bank name link");
		System.out.println(driver.findElement(By.xpath("//*[@id='main']/div[2]/a")));
		System.out.println("Printing the links names of the second row link!");
		for(int i=3;i<=7;i++){
			System.out.println(driver.findElement(By.xpath("//*[@id='main']/div[2]/ul/li["+i+"]/a")).getText());
		}
//		System.out.println("Printing the links names of the last row link!");
//		for(int i=3;i<=7;i++){
//			System.out.println(driver.findElement(By.xpath("//*[@id='main']/div[6]/ul/li["+i+"]/a/span")).getText());
//		}
				

	}

}
