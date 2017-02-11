package com.netflix.reporter;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
//import org.apache.commons.io.FileUtils;
import org.apache.commons.io.*;
import com.netflix.driver.TestDriver;
//import com.homedepot.renowalk.Common.IpadFunctionLibrary;

public class ScreenShotGetter {
	

	public static String screenShotCapture(String path) throws Exception {
		Robot robot = new Robot();
		String filePath = path;
		File file = new File(filePath);
		file.mkdir();
		String imgfileName = TestDriver.sTest_Case_ID+"_" + Reporter.getCurrentTimeStamp("yyyy_MM_dd_hh_mm_ss_a") + ".png"; //"yyyy_MM_dd:hh:mm:ss a"
		String osName = System.getProperty("os.name").toLowerCase();
		if (osName.startsWith("windows")){
			BufferedImage screenShot = robot.createScreenCapture(new Rectangle(
					Toolkit.getDefaultToolkit().getScreenSize()));
			ImageIO.write(screenShot, "PNG", new File(filePath + "\\" + imgfileName));
		}else {
		//	IpadFunctionLibrary IpadLib = new IpadFunctionLibrary();
		//	File scrFile = IpadLib.IOSTakeScreenShot();
		    try {

			    File ScreenFilePath = new File(filePath + "/" + imgfileName);
			    //Copy the file to screenshot folder
			//    FileUtils.copyFile(scrFile, ScreenFilePath);
		    }
		    catch (Exception e){
		    	e.printStackTrace();
		    }
		}
		return imgfileName;
	}
}
