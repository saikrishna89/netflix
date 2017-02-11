package com.netflix.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import jxl.read.biff.BiffException;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.*;

import com.netflix.driver.TestDriver;
public class ExcelUtil 
{
	
	 public FileInputStream fileInput = null;
	 public FileOutputStream fileOutput =null;
	 public HSSFWorkbook wb = null;
	 public HSSFSheet ws = null;        
	 
	 public static  HashMap<Integer, HashMap<String, String>> mExcelData=null;


		public HSSFWorkbook fnInitializeWorkbook(String sFilePath)
		{
			try {
				fileInput = new FileInputStream(sFilePath);
				wb = new HSSFWorkbook(fileInput);
			} catch (IOException e) {
				System.out.println("fnInitializeWorkbook--------------Failed");
				e.printStackTrace();
			}
			
			return wb;
		}
		public void fnCloseReadExcel()
		{
			try {
				fileInput.close();
				wb.close();
				fileInput=null;
				wb=null;
				ws=null;
			} catch (IOException e) {
				System.out.println("fnCloseReadExcel--------------Failed");
				e.printStackTrace();
			}
			
		}
		
		public void fnCloseWriteExcel()
		{
			try {
				wb.close();
				wb=null;
				fileOutput.flush();
				fileOutput.close();
			} catch (IOException e) {
				System.out.println("fnCloseWriteExcel--------------Failed");
				e.printStackTrace();
			}
			
		}
		
		public static String fnCellToString(HSSFCell cell)
	    {
	        Object result = null;
			try {
				int type;
				type = cell.getCellType();                                            
				switch (type){
				       case 0 :
			                      result = cell.getNumericCellValue();
			                      break;
				                     
				       case 1 :
			                      result = cell.getStringCellValue();
			                      break;
				                     
				       default :
			                      //throw new RuntimeException("Unsupportd cell."); //Coomented this line to accommodate blank cell
				    	   			result ="";
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("cellToString--------------Failed");
				e.printStackTrace();
			}
	        return result.toString();
	    }
	  //To retrieve No Of Rows from .xls file's sheets.
    public int fnRetrieveNoOfRows(String sSheetName)
    {                     
	       int sheetIndex=wb.getSheetIndex(sSheetName);
	       if(sheetIndex==-1)
	       {
	           return 0;
	       }
	       else
	       {
	          ws = wb.getSheetAt(sheetIndex);
	          //int rowCount=ws.getPhysicalNumberOfRows();   
	          int LastRowCount=ws.getLastRowNum(); 
	          //return rowCount;                           
	          return LastRowCount;  //LastRowCount would be 9 if there are 10 rows, so while retrieving data start with 0 till <=LastRowCount
	       }
    }
   
    //To retrieve No Of Columns from .cls file's sheets.
    public int fnRetrieveNoOfCols(String sSheetName)
    {
           int sheetIndex=wb.getSheetIndex(sSheetName);
           if(sheetIndex==-1)
           {
              return 0;
           }
           else
           {
              ws = wb.getSheetAt(sheetIndex);
              int colCount=ws.getRow(0).getLastCellNum();                                   
              return colCount;
           }
    }
    
    
    //public HashMap<Integer, HashMap<String, String>> fnreadxls(String sFilePath, String wsName) throws BiffException, IOException
 /*   public HashMap<Integer, HashMap<String, String>> fnReadExcel(String sSheetName) throws BiffException, IOException
	{
		HashMap<Integer, HashMap<String, String>> mFinalExcelData = new HashMap<Integer, HashMap<String, String>>();
		//Map<String,String> mExcelData = new HashMap<String,String>();
		try {
			//fileInput = new FileInputStream(sFilePath);
			//wb = new HSSFWorkbook(fileInput);
			//////////////////////////////////////////////
			  int sheetIndex=wb.getSheetIndex(sSheetName);
			  if(sheetIndex==-1)
			  {
			     return null;
			  }
			  else
			  {
			      int rowNum = fnRetrieveNoOfRows(sSheetName);
			      int colNum = fnRetrieveNoOfCols(sSheetName);

			      HSSFRow rowH = ws.getRow(0);
			      
			      for (int i=1; i<rowNum; i++)
			      {
			    	  if (!mFinalExcelData.containsKey(i)) {
			  			mFinalExcelData.put(i, new HashMap<String, String>());
			  			}
  
			    	  HSSFRow row = ws.getRow(i);
			           for(int j=0; j< colNum; j++)
			           {                                                                  
			                if(row==null)
			                {
			                  
			                }
			                else
			                {
			                	  HSSFCell cellH = rowH.getCell(j);
			                      HSSFCell cell = row.getCell(j);      
			                      if(cell==null)
			                      {
			                    	  //mExcelData.put(HeaderRow.getCell(j), row.getCell(j));                                                                                              
			                      }
			                      else
			                      {
			                    	 cellH.setCellType(Cell.CELL_TYPE_STRING);
			                         cell.setCellType(Cell.CELL_TYPE_STRING);
			                         String valueH = fnCellToString(cellH);
			                         String value = fnCellToString(cell);
			                         //mExcelData.put(valueH, value);
			                         mFinalExcelData.get(i).put(valueH, value);                    
			                               
			                      }
			                }
			           }                                                          
			    }                                           
			  }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("fnreadxls--------------Failed");
			e.printStackTrace();
		} 
		*/
          /*
		finally
		{
			fileInput.close();
			wb.close();
		}
        */  
          //return mExcelData;     
/*          return mFinalExcelData;
	}
    
    */
    
    public HashMap<Integer, HashMap<String, String>> fnReadExcel(String sSheetName, String sPrimaryKey) throws BiffException, IOException
  	{
  		HashMap<Integer, HashMap<String, String>> mFinalExcelData = new HashMap<Integer, HashMap<String, String>>();
  		//Map<String,String> mExcelData = new HashMap<String,String>();
  		try {
  			//fileInput = new FileInputStream(sFilePath);
  			//wb = new HSSFWorkbook(fileInput);
  			//////////////////////////////////////////////
  			  int sheetIndex=wb.getSheetIndex(sSheetName);
  			  if(sheetIndex==-1)
  			  {
  			     return null;
  			  }
  			  else
  			  {
  			      int rowNum = fnRetrieveNoOfRows(sSheetName);
  			      if(sSheetName.equalsIgnoreCase("TestSet"))
  			      {
  			    	  TestDriver.iTotalTestSetRows=rowNum; //resetting last row value in global iTotalTestSetRows for test set loop in driver script
  			      }
  			      int colNum = fnRetrieveNoOfCols(sSheetName);
  			   
  			      int iPrimarykeyCol = 0;
  			      String sPK="";

  			      HSSFRow rowH = ws.getRow(0);
	  			  for(int iCol=0; iCol< colNum; iCol++)
	  			  {
	  			   	if(rowH.getCell(iCol).toString().equalsIgnoreCase(sPrimaryKey))
	  			   	{
	  			   		iPrimarykeyCol = iCol;
	  			   		break;
	  			   	}	
	  			  }
  			      
  			      //for (int i=1; i<rowNum; i++)
	  			for (int i=1; i<=rowNum; i++)  //Changed to accommodate blank rows also
  			      {
	  				HSSFRow row = ws.getRow(i);  
	  				String valueCell="";
	  				if (!mFinalExcelData.containsKey(i)) 
  			    	  {
  			    		  if(iPrimarykeyCol==0)
  			    		  {
	  			  			if(row!=null)
	  			  			{
	  			  			  HSSFCell cellTest = row.getCell(1); 
	  			  			  
	  			  			  if(cellTest!=null)
	  			  			  {
	  			  				  valueCell = fnCellToString(cellTest);
	  			  				if(!valueCell.equalsIgnoreCase(""))
	  			  				{
	  			  				  mFinalExcelData.put(i, new HashMap<String, String>());
	  			  				}
	  			  			  }
	  			  			}
  			    		  }
  			    		  else
  			    		  {
	  			    		HSSFCell cellPK = ws.getRow(i).getCell(iPrimarykeyCol); 
	  			    		if(cellPK!=null)
	  			    		{
	  			    			cellPK.setCellType(Cell.CELL_TYPE_STRING);
			                    sPK = fnCellToString(cellPK);
			                    valueCell = sPK;
			                    if(!valueCell.equalsIgnoreCase(""))
	  			  				{
			                    	mFinalExcelData.put(Integer.parseInt(sPK), new HashMap<String, String>());
	  			  				}
	  			    		}
			                  
	  			    		//mFinalExcelData.put(Integer.parseInt(sPK), new HashMap<String, String>());
  			    		  }
  			    	  }
    
  			    	  //HSSFRow row = ws.getRow(i);
  			           for(int j=0; j< colNum; j++)
  			           {                                                                  
  			                if(row==null || valueCell.equalsIgnoreCase(""))
  			                {
  			                	//System.out.println("No data found for row = "+i+" in sheet '"+sSheetName+"'");
  			                	//DriverScript.log.info("No data found for row = "+i+" in sheet '"+sSheetName+"'");
  			                	// System.out.println("Primary key found for row = "+i+" in sheet '"+sSheetName+"'");
	  			    		   //System.out.println("Primary key missing for row = "+i+" in sheet '"+sSheetName+"'");
	  			    	
  			                }
  			                else
  			                {
  			                	  //System.out.println("Data found for row = "+i+" in sheet '"+sSheetName+"'");
  			                	  HSSFCell cellH = rowH.getCell(j);
  			                      HSSFCell cell = row.getCell(j);      
  			                      if(cell==null)
  			                      {
  			                    	  //mExcelData.put(HeaderRow.getCell(j), row.getCell(j));                                                                                              
  			                      }
  			                      else
  			                      {
  			                    	 cellH.setCellType(Cell.CELL_TYPE_STRING);
  			                         cell.setCellType(Cell.CELL_TYPE_STRING);
  			                         String valueH = fnCellToString(cellH);
  			                         String value = fnCellToString(cell);
  			                         //mExcelData.put(valueH, value);
  			                       if(iPrimarykeyCol==0)
  		  			    		  	{
  			                    	 mFinalExcelData.get(i).put(valueH, value);
  		  			    		  	}
  			                       else
  			                       {
  			                         mFinalExcelData.get(Integer.parseInt(sPK)).put(valueH, value);
  			                       }
  			                               
  			                      }
  			                }
  			           }                                                          
  			    }                                           
  			  }
  		} catch (Exception e) {
  			// TODO Auto-generated catch block
  			System.out.println("fnreadxls--------------Failed");
  			TestDriver.log.info("fnreadxls-------Failed");
  			e.printStackTrace();
  		} 
            /*
  		finally
  		{
  			fileInput.close();
  			wb.close();
  		}
          */  
            //return mExcelData;     
            return mFinalExcelData;
  	}
      
    
    public HashMap<Integer, HashMap<String, String>> fnGetExcelData(String sFilePath, String sSheetName, String sPrimaryKey) throws BiffException
    {
    	try {
    		fnInitializeWorkbook(sFilePath);
			mExcelData = fnReadExcel(sSheetName, sPrimaryKey);
			fnCloseReadExcel();
		} catch (IOException e) {
			System.out.println("fnGetExcelData--------------Failed");
			e.printStackTrace();
		}
		
    	return mExcelData;
    }
	 
	 public void fnWriteHeadersToExcel(String sResultFilePath, String[] arrHeaders, String sSheetName ) throws Exception 
		{
			try {
				
				//HSSFWorkbook wb = null;
				//HSSFSheet ws = null;
				//FileOutputStream fileOut = null;
				int index=0;
				//File fResultFilePath = new File(prop.getProperty("ResultExcelReportPath"));
				File fResultFilePath = new File(sResultFilePath);
				
				
				if(fResultFilePath.exists())   //If workbook exists
				{
					wb = (HSSFWorkbook) WorkbookFactory.create(fResultFilePath);
					index = wb.getSheetIndex(sSheetName);
					if(index==-1)  //If sheet doesn't exist
					{
					ws = wb.createSheet(sSheetName);
					}
					else  //do nothing
					{	
						//worksheet = workbook.getSheet(sSheetName);
					}
				}
				
				else    //If workbook doesn't exist
				{
					wb = new HSSFWorkbook();
					ws = wb.createSheet(sSheetName);
					index=-1;
				}
			if(index==-1)
			{
				HSSFRow row1 = ws.createRow(0);

				for(int iCol =0;iCol<arrHeaders.length;iCol++)
				{
								
					HSSFCell cellCol = row1.createCell(iCol);
					cellCol.setCellValue(arrHeaders[iCol].toString());
					HSSFCellStyle cellStyle = wb.createCellStyle();
					
					cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
					cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
					cellCol.setCellStyle(cellStyle);
				}
				
				//fileOut = new FileOutputStream(prop.getProperty("ResultExcelReportPath"));
				fileOutput = new FileOutputStream(sResultFilePath);
				wb.write(fileOutput);
				wb.close();
				fileOutput.flush();
				fileOutput.close();
			}
				
				wb.close();
				
				
			} catch (Exception e) {
				System.out.println("fnWriteHeadersToExcel--------------Failed");
				throw(e);
			}	
		}
	 
	 
	 public void fnWriteContentsToExcelRows(String sResultFilePath, ArrayList<String> arrContents, String sSheetName, int iCol) throws Exception
		{
			try {
				
				fileInput = new FileInputStream(sResultFilePath);				
				wb = new HSSFWorkbook(fileInput);
				ws = wb.getSheet(sSheetName);

				HSSFRow row = null;
				
				//@SuppressWarnings("deprecation")
				for(int iRow =0;iRow<arrContents.size();iRow++)
				{
					int iRowCheck = ws.getPhysicalNumberOfRows();
					if(iRowCheck==iRow+1)
					{
						row = ws.createRow(iRow+1);
					}
					else
					{
						row = ws.getRow(iRow+1);
					}
					HSSFCell cellA1 = row.createCell(iCol);
					//cellA1.setCellValue("Hello"+iRow);
					cellA1.setCellValue(arrContents.get(iRow));
					HSSFCellStyle cellStyle = wb.createCellStyle();
					if((iRow%2)==0)
					{
						//cellStyle.setFillForegroundColor(HSSFColor.LAVENDER.index);
						cellStyle.setFillForegroundColor(HSSFColor.LIGHT_CORNFLOWER_BLUE.index);
					}
					else
					{
						//cellStyle.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);
						cellStyle.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
					}
						
					cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
					cellA1.setCellStyle(cellStyle);
					
					
				}
				
				fileOutput = new FileOutputStream(sResultFilePath);
				wb.write(fileOutput);
				wb.close();
				fileOutput.flush();
				fileOutput.close();
				
			} catch (Exception e) {
				System.out.println("fnWriteContentsToExcelRows--------------Failed");
				throw(e);
			}	
		}
	 
	 public void fnWriteContentsToExcelColumns(String sResultFilePath, ArrayList<String> arrContents, String sSheetName, int iRow) throws Exception
		{
			try {
				
				fileInput = new FileInputStream(sResultFilePath);
				
				wb = new HSSFWorkbook(fileInput);
				ws = wb.getSheet(sSheetName);
				
				iRow = ws.getPhysicalNumberOfRows();
				
				HSSFRow row = null;
				
				//@SuppressWarnings("deprecation")
				for(int iCol =0;iCol<arrContents.size();iCol++)
				{
					//HSSFRow row1 = worksheet.createRow(iRow+1);
					
					int iRowCheck = ws.getPhysicalNumberOfRows();
					if(iRowCheck==iRow)
					{
						row = ws.createRow(iRow);
					}
					else
					{
						row = ws.getRow(iRow);
					}
					HSSFCell cellA1 = row.createCell(iCol);
					//cellA1.setCellValue("Hello"+iRow);
					cellA1.setCellValue(arrContents.get(iCol));
					HSSFCellStyle cellStyle = wb.createCellStyle();
					if((iRow%2)==0)
					{
						//cellStyle.setFillForegroundColor(HSSFColor.LAVENDER.index);
						cellStyle.setFillForegroundColor(HSSFColor.LIGHT_CORNFLOWER_BLUE.index);
					}
					else
					{
						//cellStyle.setFillForegroundColor(HSSFColor.LIGHT_TURQUOISE.index);
						cellStyle.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
					}
						
					cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
					cellA1.setCellStyle(cellStyle);
					
					
				}
			
				fileOutput = new FileOutputStream(sResultFilePath);
				wb.write(fileOutput);
				//wb.close();
				//fileOutput.flush();
				//fileOutput.close();
				
			} catch (Exception e) {
				System.out.println("fnWriteContentsToExcelColumns--------------Failed");
				throw(e);
			}	
		}
	 
	 public void fnCompareExcelSheets(String sResultFilePath, String sExpSheetName, String sActSheetName, String sSummarySheet, int iSearchCol) throws Exception
		{
			try {
				
			
				fileInput = new FileInputStream(sResultFilePath);
				
				wb = new HSSFWorkbook(fileInput);
				HSSFSheet expWorksheet = wb.getSheet(sExpSheetName);
				HSSFSheet actWorksheet = wb.getSheet(sActSheetName);
				HSSFSheet summaryWorksheet = wb.getSheet(sSummarySheet);
				
				int iExpMaxRow = expWorksheet.getPhysicalNumberOfRows();
				int iActMaxRow = actWorksheet.getPhysicalNumberOfRows();
				
				
				HSSFRow expRow = null;
				HSSFRow actRow = null;
				HSSFRow summaryRow = null;
				int iActTargetRow=0;
				
				//@SuppressWarnings("deprecation")
				for(int iExpRow =1;iExpRow<iExpMaxRow;iExpRow++)
				{
					//HSSFRow row1 = worksheet.createRow(iExpRow+1);
					expRow = expWorksheet.getRow(iExpRow);
					//actRow = actWorksheet.getRow(iExpRow);
					
					
					///////////////////////////////////////////////
					 HSSFCell CellA  = expRow.getCell((int) 0);//looking for VIn in expected sheet
					 String sExpVIN = CellA.getStringCellValue();
					 
					 HSSFCell CellB  = expRow.getCell((int) 1);//looking for Offering in expected sheet
					 String sExpOffering = CellB.getStringCellValue();
					///////////////////////////////////////////////
					 
					 
					 //String[] expArray = getExcelRowData(expRow,7);   //7 is column size
					 String[] expArray = new String[7];   //7 is column size
					 String[] actArray = new String[7];
					 expArray = fnGetExcelRowData(expRow,7);   //7 is column size
					 //////////////////////////////////////////////////////////////////////
					 for(int iActRow=1;iActRow<iActMaxRow;iActRow++)
					 {
						 actRow = actWorksheet.getRow(iActRow);
						 HSSFCell actCellA  = actRow.getCell((int) 0);//looking for VIn in actual sheet
						 String sActVIN = actCellA.getStringCellValue();
						 
						 HSSFCell actCellB  = actRow.getCell((int) 1);//looking for Offering in actual sheet
						 String sActoffering = actCellB.getStringCellValue();
						 
						 if(sExpVIN.equalsIgnoreCase(sActVIN) && sExpOffering.equalsIgnoreCase(sActoffering))
						 {
							 iActTargetRow = iActRow;
							 break;
						 }
					 }
					 
					 if(iActTargetRow!=0)
					 {
						 actRow = actWorksheet.getRow(iActTargetRow);
						 actArray = fnGetExcelRowData(actRow,7);
					 }
					 else
					 {
						 for(int i=0; i<actArray.length;i++)
						 {
							 if(actArray[i]==null)
							 {
								 actArray[i]="NULL";
							 }
						 }
						 
					 }
					 
					 HSSFCellStyle cellStyle = wb.createCellStyle();
				    /* if(expCount==actCount)
				     {
				    	*/ 
				    	   summaryRow = summaryWorksheet.createRow(iExpRow);
		            	   summaryRow.createCell(0).setCellValue(expArray[0].toString());
		            	   summaryRow.createCell(1).setCellValue(expArray[1].toString());
		            	   summaryRow.createCell(2).setCellValue(expArray[2].toString());
		            	   summaryRow.createCell(3).setCellValue(expArray[3].toString());
		            	   summaryRow.createCell(4).setCellValue(actArray[3].toString());
		            	   summaryRow.createCell(5).setCellValue(expArray[4].toString());
		            	   summaryRow.createCell(6).setCellValue(actArray[4].toString());
			              // for(int x=1;x<expCount;x++)//expCount
			             //  {          
		            	   
			            	   
			            	   HSSFCell CellResult = summaryRow.createCell(7);
		                          if (expArray[3]!=null && actArray[3]!=null && expArray[4]!=null && actArray[4]!=null)
		                          {
		                             if (expArray[3].trim()== actArray[3].trim() && expArray[4].trim()== actArray[4].trim())
		                             {
	
		                            	 CellResult.setCellValue("Pass");
		                            	 summaryRow.createCell(8).setCellValue("Web Before Checkout Price ["+expArray[3].trim()+"] matching  Before Checkout Price ["+actArray[3].trim()+"] and Web After Checkout Price ["+expArray[4].trim()+"] matching  After Checkout Price ["+actArray[4].trim()+"]");
		                            	 cellStyle.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
		                             }
		                             else
		                             {
		                            	 CellResult.setCellValue("Fail");
		                            	 summaryRow.createCell(8).setCellValue("Either Web Before Checkout Price ["+expArray[3].trim()+"] NOT matching  Before Checkout Price ["+actArray[3].trim()+"] OR Web After Checkout Price ["+expArray[4].trim()+"] NOT matching  After Checkout Price ["+actArray[4].trim()+"]");
		                            	 cellStyle.setFillForegroundColor(HSSFColor.RED.index);
		                             }
		                                        
		                          }// end of if
			            //   }// end of for   //For future purpose
		                         cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		                         CellResult.setCellStyle(cellStyle);
				/*     }
				     else
				     {
				    	 System.out.println("Expected Row length is not matching with Actual Row length. Comparison Aborted..");
				     }
					*/
					
				}
			
				fileOutput = new FileOutputStream(sResultFilePath);
				wb.write(fileOutput);
				wb.close();
				fileOutput.flush();
				fileOutput.close();
				
			} catch (Exception e) {
				System.out.println("fnCompareExcelSheets--------------Failed");
				throw(e);
			}	
		}
	  
	 public String [] fnGetExcelRowData(HSSFRow row,int iColSize)
	 {
	                String[] arrData = new String[iColSize];
	         for(int k=0 ; k < iColSize ; k++)
	         {
	                                
	            try{
	                HSSFCell CellAk  = row.getCell((int) k);
	               
	                arrData[k] = CellAk.toString();
	                // log.info a[k].toString()
	                if(arrData[k] == "")
	                {
	                	arrData[k] = "NA";
	                }
	             
	             }catch(NullPointerException e)
	                {
	            	 arrData[k] = "NA" ;
	                }
	          }
	    //log.info "value of expected data:=" + a;
	    return arrData;
	 }
	 
} // end of BaseClass

