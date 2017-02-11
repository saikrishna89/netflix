package com.netflix.driver;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Properties;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.annotations.*;

import com.netflix.common.FunctionLibrary;
import com.netflix.configmanager.FileLocSetter;
import com.netflix.utils.ExcelUtil;
import com.netflix.utils.PropUtil;
import com.netflix.reporter.*;

import jxl.read.biff.BiffException;

public class TestDriver extends FunctionLibrary{
	
	String header = null;
	static String sIndexHeader = null;
	public static boolean bLoginFlag = false;
	public static int iRowId;
	public static int iDataRowId;
	public static int iTotalTestSetRows;
	public static int iMasterRowId;
	public static String iTCID;
	public static String sMachine = null;
	public static String sSkillSet = null;
	public static String sAppLink = null; 
	public static String sCorporation = null;
	public static String sOffice = null;
	public static String sUsername = null;
	public static String sPassword = null;
	public static boolean bIpadLoginFlag = false;
	
	public static String sWorkingDirPath;

	public static String sHtmlLink = null; // sHtmlLink to index file
	public static String sFolderPath = null; // local report folder

	public static String sEnvironment; // IDT5, etc
	public static String sPhase; // Smoke, Regression, SIT
	public static String sModule; // Groups, Items, Flows, Property <-- to be implemented later
	public static String sRelease; // sRelease build: 5.2, 5.3, etc.s
	public static String sApplication; // GPS Europe
	public static String sReportName; //Test Suite Name

	public static int iTC_ID; // keep track of the testID
	public static String sTest_Case_ID; // keep track of the testID
	public static String sTest_Case_Name; // keep track of the tcName
	public static String sTest_Case_Description; // keep track of the test desc
	public static String sAPI_Test; //Keep track of API Test Flag

	public static String Production; // Pre_Production or Production
	
	public static  HashMap<Integer, HashMap<String, String>> mEnvSheetData=null;
	public static  HashMap<Integer, HashMap<String, String>> mTestSetSheetData=null;
	public static  HashMap<Integer, HashMap<String, String>> mTestPhaseData=null;
	
	public static String sSoapActionUrl;
	public static String sIndexHTMLFileName="";
	
	public static Logger log = Logger.getLogger(TestDriver.class.getName());

	PropUtil objPropertiesUtility = new PropUtil();
	ExcelUtil objExcelUtility = new ExcelUtil();

	@BeforeTest
	public void beforeTest(){
		prop = objPropertiesUtility.fnLoadObjects(FileLocSetter.sConfigPath, "global.properties");
	}
/*	
	@AfterTest
	public void afterTest(){
		System.out.println("After Test");
		if(driver != null)
		driver.quit();
	}
*/	
	@BeforeMethod
	public void beforeMethod(){
		System.out.println("Before Method");
	}
	
	@AfterMethod
	public void afterMethod(){
		System.out.println("After Method");
	}
	
	@Test
	public void run() throws BiffException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		String sPackageClass = "";
		sPackageClass = this.getClass().getPackage().getName();
		System.out.println(sPackageClass);
		mEnvSheetData = objExcelUtility.fnGetExcelData(FileLocSetter.sTestDataPath+ FileLocSetter.sFileName,"Environment", "");
		for (int masterRow = 1; masterRow <= mEnvSheetData.size(); masterRow++) 
		{	
			iMasterRowId = masterRow;

			if(mEnvSheetData.get(iMasterRowId).get("Execution_Flag").equalsIgnoreCase("Y")) 
			{
				fnSetBrowserCapabilities();
				String URL = mEnvSheetData.get(iMasterRowId).get("URL");
				driver.get(URL);
				mTestSetSheetData = objExcelUtility.fnGetExcelData(FileLocSetter.sTestDataPath+ FileLocSetter.sFileName,"TestSet",""); 
				mTestPhaseData = objExcelUtility.fnGetExcelData(FileLocSetter.sTestDataPath+ FileLocSetter.sFileName,"TestData", "Test_Case_ID");
				
	//			System.out.println(mEnvSheetData.get(iMasterRowId).get("URL"));
	//			System.out.println(mTestSetSheetData.get(iMasterRowId).get("Test_Case_Description"));
	//			System.out.println(mTestPhaseData.get(iMasterRowId).get("Username"));
			}
			
			for(int row = 1; row <= mTestSetSheetData.size(); row++)
			{
				iRowId = row;
				
				if(mTestSetSheetData.get(iRowId).get("Automation Status").equalsIgnoreCase("Y"))
				{
					iTCID = mTestSetSheetData.get(iRowId).get("Test_Case_ID");
					sModule = mTestSetSheetData.get(iRowId).get("Module");
					
					for(int dataRow = 1; iTCID.equals(mTestPhaseData.get(dataRow).get("Test_Case_ID")); dataRow++)
					{
						iDataRowId = dataRow;
				//		System.out.println(iTCID);
						sUsername = mTestPhaseData.get(iDataRowId).get("Username");
						sPassword = mTestPhaseData.get(iDataRowId).get("Password");
						
						String ClassName = sPackageClass+"."+sModule;
						
						fnExecuteFunction(ClassName, iTCID);
					}
				}
			}		
	}
	
	}
	
}
