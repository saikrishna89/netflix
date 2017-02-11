package com.netflix.driver;

import java.util.HashMap;
import com.netflix.reporter.*;
import com.netflix.objectrepository.objectRepository;

public class LoginAndVerifyLinks extends TestDriver{
	
	objectRepository OR = new objectRepository();
	
	@SuppressWarnings("static-access")
	public String TC01() throws Exception
	{
		//Boolean bLoginFlag = true;	
		log.info("Execution of Function TC01 Started..");

		try {
			ClickByXpath(OR.signIn, "signIn",true);
			fnLoadingPageWait(5);
			SendKeyByXpath(OR.emailField, mTestPhaseData.get(iDataRowId).get("Username"), "Email");
			SendKeyByXpath(OR.passwordField, mTestPhaseData.get(iDataRowId).get("Password"), "Password");
			ClickByXpath(OR.rememberMeButton, "Remember Me Button", true);
			ClickByXpath(OR.signInButton, "Sign In BUtton", true);
			fnLoadingPageWait(5);
			fnSignOut(true);
		}
		catch (Exception e) {
			e.printStackTrace();
			testCaseStatus = false;
			//bLoginFlag=false;
	//		obj.repAddData( "Error Handler ", "There should not be any error/exception in the test", "Exception found in current test.", "Fail");
			log.error( "Function TC01 Failed!", e );
		}
		finally {
			if(!testCaseStatus)
			{
				Reporter.iTotalFail++;	
			}
			else
			{
				Reporter.iTotalPass++;
			}
			/*obj.repGenerateResult(Test_Case_Name, header);
			obj.repGenerateIndexReport(indexHeader);
			header = null;    //Enable after testing
*/				log.info("Execution of Function TC27057 Completed");
		}
		return "a";
	//	return obj;
	} //End of function TC27057	
}
