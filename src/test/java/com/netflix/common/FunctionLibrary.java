package com.netflix.common;


import java.lang.reflect.Method;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.netflix.objectrepository.objectRepository;
import com.netflix.configmanager.FileLocSetter;
import com.netflix.driver.TestDriver;
import com.netflix.reporter.Reporter;

public class FunctionLibrary {
	
	public static WebDriver driver=null;
	public static Properties prop=null;
	public static int mainStep; // keep track of main step for each testcases
	public static int subStep; // keep track of subStep for each mainStep
	public static boolean testCaseStatus; // keep track of the test flow
	public static Reporter obj=new Reporter();
	
	public void fnSetBrowserCapabilities(){
		String sBrowser = TestDriver.mEnvSheetData.get(TestDriver.iMasterRowId).get("Browser").toString().trim();
		if(sBrowser.equalsIgnoreCase("Chrome")){
			System.setProperty("webdriver.chrome.driver", FileLocSetter.sProjPath+"chromedriver.exe");
			System.out.println(FileLocSetter.sProjPath+"chromedriver.exe");  
			driver = new ChromeDriver();
			try {
				fnLoadingPageWait(10);
			} catch (Exception e) {
				e.printStackTrace();
			}
			driver.manage().window().maximize();
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes"})
	public void fnExecuteFunction(String ClassName, String sFunctionName)
	{
		try {
			Class[] cArg = new Class[1];
			cArg[0] = obj.getClass();
			
			// load the ClassName at runtime
			Class clsObj = Class.forName(ClassName);
			Object obj1 = clsObj.newInstance();

			//String RowNum = Integer.toString(DriverScript.i);
			Method method = clsObj.getMethod(sFunctionName);
		//	Reporter.iTotalExecuted++;
			method.invoke(obj1);

		} catch (Exception e) {
			TestDriver.log.info("Test case failed for "+sFunctionName+". Check if the function is missing...");
		}

	}
	
	//Sign out of the Application 
	 public void fnSignOut(boolean bQuitDriver) throws Exception
	 {
		try {
				if(driver!=null)
				{
					if(driver.getPageSource().contains("Logout"))
					{
						obj.repAddData( "Sign out from the application","", "", "");
						WebElement ele;
						ele = driver.findElement(By.xpath("//*[contains(text(),'Logout')]"));
						HighlightElement(ele);				
												
						if (ele.isDisplayed()) 
						{
							ele.click();
						}
						obj.repAddData( "Logout of the application","Logout should be successful","Logged out successfully", "Pass");
						//TestDriver.bLoginFlag=false;
					}
					if(bQuitDriver==true)
					{
						TestDriver.bLoginFlag=false;  //Moved here to handle signout scenarios of security roles
						driver.manage().deleteAllCookies();
						driver.quit();
						driver=null;
					}
					
				}

		} catch (Exception e) {
			System.out.println("fnSignOut-----failed");
			obj.repAddData( "Logout of the application", "Logout should be successful","Logout failed", "Fail");
			driver.manage().deleteAllCookies();
			driver.quit();
			driver=null;
			throw(e);
		}	
	}
	
	public void HighlightElementByName(String value) throws InterruptedException 
	{
		WebElement ele =  driver.findElement(By.name(value));
		
		String originalStyle = ele.getAttribute("style");
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		for(int i=0;i<2;i++)
		{
			js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red; width: 35%');", ele);
			Thread.sleep(500);
			js.executeScript("arguments[0].setAttribute('style', '" + originalStyle + "');", ele);
			Thread.sleep(500);
		}
	}
	
		
	public void HighlightElementById(String IDvalue) throws InterruptedException 
	{
		WebElement ele =  driver.findElement(By.id(IDvalue));
		
		String originalStyle = ele.getAttribute("style");
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		
		
		for(int i=0;i<2;i++)
		{
			js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red; width: 35%');", ele);
			Thread.sleep(500);
			js.executeScript("arguments[0].setAttribute('style', '" + originalStyle + "');", ele);
			Thread.sleep(500);
		}
		
	}
	
	
	public void HighlightElementByXpath(String xPath) throws InterruptedException 
	{
		WebElement ele =  driver.findElement(By.xpath(xPath));
		
		String originalStyle = ele.getAttribute("style");
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		for(int i=0;i<2;i++)
		{
			js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red; width: 35%');", ele);
			Thread.sleep(150);
			js.executeScript("arguments[0].setAttribute('style', '" + originalStyle + "');", ele);
			Thread.sleep(150);
		}
	}
	
		
	public void HighlightElement(WebElement ele) throws InterruptedException 
	{	
		String originalStyle = ele.getAttribute("style");
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		
		for(int i=0;i<2;i++)
		{
			js.executeScript("arguments[0].setAttribute('style', 'background: gold; border: 1px solid blue; width: 35%');", ele);
			Thread.sleep(150);
			js.executeScript("arguments[0].setAttribute('style', '" + originalStyle + "');", ele);
			Thread.sleep(150);
		}
	}
	
	public void SendKeyById(String IDvalue, String key, String sFieldName) throws Exception
	{
		try {
			WebElement ele = driver.findElement(By.id(IDvalue));
			HighlightElement(ele);
			ele.clear();
			ele.sendKeys(key);
			Thread.sleep(500);
			if(sFieldName.equalsIgnoreCase("Password") || sFieldName.contains("Password"))
			{
				obj.repAddData( "Enter data to "+sFieldName+" field", "<b>"+"*******"+"</b> should be entered", ""+"*******"+"</b> entered", "Pass");
			}
			else
			{	
				obj.repAddData( "Enter data to "+sFieldName+" field", "<b>"+key+"</b> should be entered", ""+key+"</b> entered", "Pass");
			}
		} catch (Exception e) {
			System.out.println("--No Such Element Found--");
			obj.repAddData( "Enter data to "+sFieldName+" field", "<b>"+key+"<b/> should be entered", "<b>"+key+"</b> not entered", "Fail");
			//throw(e);
		}
	}
	
	
	public void SendKeyByXpath(String Xpath, String key, String sFieldName) throws Exception
	{
		try {
			WebElement ele = driver.findElement(By.xpath(Xpath));
			HighlightElement(ele);
			ele.clear();
			ele.sendKeys(key);
			Thread.sleep(500);
			obj.repAddData( "Enter data to "+sFieldName+" field", "'"+key+"' should be entered", "'"+key+"' entered", "Pass");
		} catch (Exception e) {
			System.out.println("--No Such Element Found--");
			obj.repAddData("Enter data to "+sFieldName+" field", "'"+key+"' should be entered", "'"+key+"' not entered", "Fail");
			//throw(e);
		}
	}
	
	public void SendKeyByName(String value, String key, String sFieldName) throws Exception
	{
		try {
			WebElement ele = driver.findElement(By.name(value));
			HighlightElement(ele);
			ele.clear();
			ele.sendKeys(key);
			Thread.sleep(500);
			obj.repAddData( "Enter data to "+sFieldName+" field", "<b>'"+key+"'</b> should be entered", "<b>'"+key+"'</b> entered", "Pass");
		} catch (Exception e) {
			System.out.println("--No Such Element Found--");
			obj.repAddData( "Enter data to "+sFieldName+" field", "<b>'"+key+"'</b> should be entered", "<b>'"+key+"'</b> not entered", "Fail");
			//throw(e);
		}
	}
	
	
	public void SendKeyByElement(WebElement ele, String key, String sFieldName) throws Exception
	{
		try {
			HighlightElement(ele);
			ele.clear();
			ele.sendKeys(key);
			Thread.sleep(500);
			obj.repAddData("Enter data to "+sFieldName+" field",
					"'" + key + "' should be entered", "'" + key + "' entered",
					"Pass");
		} catch (Exception e) {
			System.out.println("--No Such Element Found--");
			obj.repAddData( "Enter data to "+sFieldName+" field",
					"'" + key + "' should be entered", "'" + key
							+ "' not entered", "Fail");
			throw (e);
		}
	}
	
	public void ClickById(String IDvalue, String faceValue, boolean bReportFlag) throws Exception
	{
		try {
			WebElement ele;
			ele = driver.findElement(By.id(IDvalue));
			HighlightElement(ele);
			ele.click();
			Thread.sleep(1000);
			if(bReportFlag==true)
			{
				obj.repAddData( "Click on '"+faceValue+"'", "'"+faceValue+"' should be clicked", "'"+faceValue+"' clicked", "Pass");
			}
		} catch (Exception e) {
			System.out.println("--No Such Element Found--");
			if(bReportFlag==true)
			{
			obj.repAddData( "Click on '"+faceValue+"'", "'"+faceValue+"' should be clicked", "'"+faceValue+"' not clicked", "Fail");
			}
			throw(e);
		}
	}
	
	public void ClickByName(String value, String faceValue, boolean bReportFlag) throws Exception
	{
		try {
			WebElement ele;
			ele = driver.findElement(By.name(value));
			HighlightElement(ele);
			ele.click();
			Thread.sleep(1000);
			if(bReportFlag==true)
			{
			obj.repAddData( "Click on '"+faceValue+"'", "'"+faceValue+"' should be clicked", "'"+faceValue+"' clicked", "Pass");
			}
		} catch (Exception e) {
			System.out.println("--No Such Element Found--");
			if(bReportFlag==true)
			{
			obj.repAddData( "Click on '"+faceValue+"'", "'"+faceValue+"' should be clicked", "'"+faceValue+"' not clicked", "Fail");
			}
			throw(e);
		}
	}
	
	public void ClickByLinkText(String value, String faceValue, boolean bReportFlag) throws Exception
	{
		try {
			WebElement ele;
			ele = driver.findElement(By.linkText(value));
			HighlightElement(ele);
			ele.click();
			Thread.sleep(1000);
			if(bReportFlag==true)
			{
			obj.repAddData( "Click on '"+faceValue+"'", "'"+faceValue+"' should be clicked", "'"+faceValue+"' clicked", "Pass");
			}
		} catch (Exception e) {
			System.out.println("--No Such Element Found--");
			if(bReportFlag==true)
			{
			obj.repAddData( "Click on '"+faceValue+"'", "'"+faceValue+"' should be clicked", "'"+faceValue+"' not clicked", "Fail");
			}
			throw(e);
		}
	}
	
	public void ClickByPartialLinkText(String value, String faceValue, boolean bReportFlag) throws Exception
	{
		if(testCaseStatus)
		{
			try {
				WebElement ele;
				ele = driver.findElement(By.partialLinkText(value));
				HighlightElement(ele);
				ele.click();
				Thread.sleep(1000);
				if(bReportFlag==true)
				{
				obj.repAddData( "Click on '"+faceValue+"'", "'"+faceValue+"' should be clicked", "'"+faceValue+"' clicked", "Pass");
				}
			} catch (Exception e) {
				System.out.println("--No Such Element Found--");
				if(bReportFlag==true)
				{
				obj.repAddData( "Click on '"+faceValue+"'", "'"+faceValue+"' should be clicked", "'"+faceValue+"' not clicked", "Fail");
				}
			}
		} else return;
	}
	
	public void ClickByTagName(String value, String faceValue, boolean bReportFlag) throws Exception
	{
		try {
			WebElement ele;
			ele = driver.findElement(By.tagName(value));
			HighlightElement(ele);
			ele.click();
			Thread.sleep(1000);
			if(bReportFlag==true)
			{
			obj.repAddData( "Click on '"+faceValue+"'", "'"+faceValue+"' should be clicked", "'"+faceValue+"' clicked", "Pass");
			}
		} catch (Exception e) {
			System.out.println("--No Such Element Found--");
			if(bReportFlag==true)
			{
			obj.repAddData( "Click on '"+faceValue+"'", "'"+faceValue+"' should be clicked", "'"+faceValue+"' not clicked", "Fail");
			}
			throw(e);
		}
	}
	
	
	public void ClickByXpath(String value, String faceValue, boolean bReportFlag) throws Exception
	{
		try {
			WebElement ele;
			ele = driver.findElement(By.xpath(value));
			HighlightElement(ele);
			ele.click();
			Thread.sleep(1000);
			if(bReportFlag==true)
			{
			obj.repAddData( "Click on '"+faceValue+"'", "'"+faceValue+"' should be clicked", "'"+faceValue+"' is clicked", "Pass");
			}
		} catch (Exception e) {
			System.out.println("--No Such Element Found--");
			if(bReportFlag==true)
			{
			obj.repAddData( "Click on '"+faceValue+"'", "'"+faceValue+"' should be clicked", "'"+faceValue+"' not clicked", "Fail");
			}
			throw(e);
		}
	}
	
	
	
	public void ClickByElement(WebElement ele, String faceValue, boolean bReportFlag) throws Exception
	{
		try {
			Actions action = new Actions(driver);
			action.click(ele).perform();
			if(bReportFlag==true)
			{
			obj.repAddData( "Click on '"	+ faceValue + "'", "'" + faceValue+ "' should be clicked", "'" + faceValue + "' clicked","Pass");
			}
		} catch (Exception e) {
			System.out.println("Click by Element on line 373 failed");
			if(bReportFlag==true)
			{
			obj.repAddData( "Click on '"	+ faceValue + "'", "'" + faceValue+ "' should be clicked", "'" + faceValue+ "' not clicked", "Fail");
			}
			throw(e);
		}
	}
	
	public int CheckId(String sLocator, String sReportText, boolean bReportFlag) {
		int result = 0;
		String sText ="";
		try {
			List<WebElement> eleList = driver.findElements(By.id(sLocator));
			result = eleList.size();
			if(bReportFlag==true)
			{
				if(result==1)
				{
					sText = eleList.get(0).getText().trim();
					HighlightElement(eleList.get(0));
					obj.repAddData( "Verify "+sReportText, sReportText+" should be shown", sReportText+" shown successfully with value '"+sText+"'", "Pass");
				}
				else
				{
					obj.repAddData( "Verify "+sReportText, sReportText+" should be shown", sReportText+" not shown", "Fail");
				}
			}
			
		} catch (Exception e) {
			System.out.println("Id>>"+sLocator+" not found.");
			TestDriver.log.info("Id>>"+sLocator+" not found.");
		}
		return result;
	}
	
	 public void fnLoadingPageWait(int time) throws Exception

	 {
		try
		{
			driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
			
			//WebDriverWait wait = new WebDriverWait(driver,120);
			//CheckId(RenowalkModules.Property_GenProp_ImagePageLoading_id,"'Loading...' icon",true);
			//wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id(RenowalkModules.Property_GenProp_MsgPageLoading_id)));	 //Wait for loading message to disappear

		} catch (Exception e) {
	    System.out.println("fnLoadingPageWait --------------Failed");    
	    TestDriver.log.error("fnLoadingPageWait failed",e);
	    }
	 }
	
}
