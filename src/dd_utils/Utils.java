package dd_utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import dd_core.Core;

public class Utils extends Core {
	/*
	 * Utility for screenshot
	 * Utility for XLS File Reader
	 * Utility for sending emails
	 * Utility for zipping the reports before sending the email
	 * Utility for generating logs 	
	 * 
	 */

	public static String screenShotPath;

	public static void CaptureScreenshot() throws IOException{

		Calendar cal = new GregorianCalendar();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DATE);
		int hour = cal.get(Calendar.HOUR);
		int min = cal.get(Calendar.MINUTE);
		int sec = cal.get(Calendar.SECOND);
		int mills = cal.get(Calendar.MILLISECOND);

		screenShotPath = System.getProperty("usr.dir") +  (year+month+day+hour+min+sec+mills);

		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

		FileUtils.copyFile(scrFile, new File (screenShotPath));	
	}

	public static void zip(String filepath){
		try
		{
			File inFolder=new File(filepath);
			File outFolder=new File("Reports.zip");
			ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(outFolder)));
			BufferedInputStream in = null;
			byte[] data  = new byte[1000];
			String files[] = inFolder.list();
			for (int i=0; i<files.length; i++)
			{
				in = new BufferedInputStream(new FileInputStream
						(inFolder.getPath() + "/" + files[i]), 1000);  
				out.putNextEntry(new ZipEntry(files[i])); 
				int count;
				while((count = in.read(data,0,1000)) != -1)
				{
					out.write(data, 0, count);
				}
				out.closeEntry();
			}
			out.flush();
			out.close();

		}
		catch(Exception e)
		{
			e.printStackTrace();
		} 
	}
	
	
	
}
