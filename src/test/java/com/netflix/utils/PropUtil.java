package com.netflix.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class PropUtil {

	public static Properties objRepProp=null; // object repository property
	
	public Properties fnLoadObjects(String sFilePath, String objPropFileName)
	{		
		//File file = new File(FileLoc.sProjPath + FileLoc.objRepPath + objPropFileName);
		File file = new File(sFilePath + objPropFileName);
		  
		FileInputStream fileInput = null;
		try {
			fileInput = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		objRepProp = new Properties();
		
		//load objects' properties
		try {
			objRepProp.load(fileInput);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return objRepProp;
	}
	
	public static void fnWriteToTextFile(String sFilePath, String sContent)
	{
		BufferedWriter  writer = null;
		String sWebServiceDataPath = sFilePath;
		File fWebServiceDataPath = new File(sWebServiceDataPath);
		try {
			writer = new BufferedWriter(new FileWriter(fWebServiceDataPath));
			writer.write(sContent);
			writer.close();
	}
		catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}


}
