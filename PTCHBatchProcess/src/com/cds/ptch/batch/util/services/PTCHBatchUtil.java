package com.cds.ptch.batch.util.services;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * This class is an utility class that serves different helper methods for the application
 * @author ShanthiNT * 
 * @version $Revision: 1.0 $
 */
public class PTCHBatchUtil {

	
	/**
	 * Fills zeros for right justified fields
	 * @param str String
	 * @param maxL int	
	 * @return String 
	 */
	public String fillZeros(String str, int maxL) {
		
			if (str == null) {
				throw new RuntimeException("FnboUtil fillZeros. Method does not take null arguments.");
			}
			
			int numIterations = 0;
			numIterations = maxL - str.length();		 
			
			if(numIterations < 0 ){
				throw new RuntimeException("FnboUtil fillZeros. Maximum length must larger or equal to size of the first argument.");
			}
			
		
			return fillString(str, numIterations, "0");
	}
	
	/**
	 * Fills spaces for left justified fields
	 * @param str String
	 * @param maxL int
	 * @return String 
	 */
	public String fillSpaces(String str, int maxL) {
		
		if (str == null) {
			throw new RuntimeException("ptechUtil fillSpaces. Method does not take null arguments.");
		}
		
		int numIterations = 0;
		numIterations = maxL - str.length();		 
		
		if(numIterations < 0 ){
			throw new RuntimeException("ptechUtil fillSpaces. Maximum lenght must larger or equal to size of the first argument.");
		}
		
	
		return fillString(str, numIterations, " ");
	}
	
	/**
	 * Fills the string with zero or spaces based on the argument passed in
	 * @param str String
	 * @param maxL int
	 * @param filler String	
	 * @return String 
	 */
	public String fillString(String str, int maxL, String filler) {

		String result = null;	
		
		result = str;
		if(" ".equals(filler)) {
			for (int i = 0; i < maxL; i++) {
				result = result + " ";
			}
		} else if("0".equals(filler)){
			for (int i = 0; i < maxL; i++) {
				result = "0" + result;
			}
		} else {
			throw new RuntimeException("ptechUtil fillString. Invalid filler");
		}
		
		return result;

	}
	
	/**
	 * Gets file path from properties file 
	 * @param propertyname
	 * @return String 
	 */
	public String getfilePath(String propertyname) {		  
			
		   String slash = System.getProperty("file.separator");
		   String path = PropertiesGenerator.getPropertyInstance("PTCHBatchResourceConfig.properties", "FILEPATH").getProperty(propertyname);			
		   path = path.replace("/", slash);
		   return path;
	}
	
	
	/**
	 * 
	 * Writes a string in to a file Using Java FileWriter and BufferWriter
	 * @param data
	 * @param filename
	 * @param path
	 * 
	 */
	public void writeIntoFile(String data, String filename, String path){
		
		try{
			
			FileWriter fileWriter = new FileWriter(path + filename, true);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		    
			bufferedWriter.write(data);  
		
			bufferedWriter.flush();  
			bufferedWriter.close();
			fileWriter.close();  
		    
		} catch (IOException e){ 
			e.printStackTrace(); 
			throw new RuntimeException(e.getCause());
		} 
	}
		
	/**
	 * Formats amount
	 * @param amt String	
	 * @return String 
	 */
	public String formatAmount(String amt){
		
		   String resultAmt = null;
		   String [] dec = (amt.replace(".", "/")).split("/");
		   int length = dec[1].length();
		   if( length == 1){
			   resultAmt = amt.replace(".", "").concat("0");
			   
		   }else{
			   resultAmt = amt.replace(".", "");
		   }
		   
		   return resultAmt;
	}
	
	/**
	 * Validates credit card expiration date
	 * @param month String
	 * @param year String	
	 * @return boolean 
	 */
	public boolean validateExpDate(String month, String year) {

		int ccmonth = Integer.parseInt(month);
		int ccyear = Integer.parseInt(year);

		SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
		SimpleDateFormat yearFormat = new SimpleDateFormat("yy");

		Date date = new Date();

		int thismonth = Integer.parseInt(monthFormat.format(date));
		int thisyear = Integer.parseInt(yearFormat.format(date));

		if ((ccyear < thisyear)) { // Credit Card year is past
			return false;
		}		
		
		// if month is entered more than 12
		if(ccmonth > 12){
			return false;
		}
		if ((ccyear == thisyear) && (ccmonth < thismonth)) { // Credit Card
			// Month is past
			return false;
		} else {
			return true; // expiration date is okay
		}
		
		
	}
	
	/**
	 * Rounds amount to 2 dec. places
	 * @param amount BigDecimal	
	 * @return java.math.BigDecimal
	 */
	public java.math.BigDecimal roundAmount(BigDecimal amount){
		
		java.math.BigDecimal round_Amt;  
	     /* round to 2 dec. places */ 
		round_Amt = amount.setScale ( 2, java.math.BigDecimal.ROUND_HALF_UP ) ; 
		
		return round_Amt;
	}
	
	/**
	 * Rounds amount to 2 dec. places
	 * @param amount double	
	 * @return java.math.BigDecimal
	 */
	public java.math.BigDecimal roundAmount(double amount){
		
		java.math.BigDecimal round_Amt = new java.math.BigDecimal ( amount ) ;  
	     /* round to 2 dec. places */ 
		round_Amt = round_Amt.setScale ( 2, java.math.BigDecimal.ROUND_HALF_UP ) ; 
		
		return round_Amt;
	}
	
	
	/**
	 * Gets current system date time
	 * @return String 
	 */
	public String currentDateTime() {
    	
	      String DATE_FORMAT_NOW = "yyMMdd.hhmmss";
	      Calendar cal = Calendar.getInstance();
	      SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
	      return sdf.format(cal.getTime());

	}
	
	// 
	/**
	 * Deletes the file if there are no transactions to include
	 * @param filename String
	 * @param path String
	 * @return boolean 
	 */
	public boolean deleteFile(String filename, String path){		
			
	
	    // A File object to represent the filename
	    File f = new File(path+filename);

	    // Make sure the file or directory exists and isn't write protected
	    if (!f.exists()){
	      throw new RuntimeException("Delete: no such file or directory: " + filename);
	    }

	    if (!f.canWrite()){
	      throw new RuntimeException("Delete: write protected: "+ filename);
	    }
	    
	    // Attempt to delete it
	    boolean success = f.delete();

	    if (!success){
	      throw new IllegalArgumentException("Delete: deletion failed");
	  }
	    
	    return success;

	}
	
	/**
	 * Moves the file to specified folder
	 * @param filename String
	 * @param sourcepath String
	 * @param destinationpath String
	 */
	public void moveFile(String filename, String sourcePath, String destinationpath){
		
		try {
			Path sourceDir = Paths.get(sourcePath+filename);
			Path processedDir = Paths.get(destinationpath + filename);		
			Files.move(sourceDir, processedDir, StandardCopyOption.REPLACE_EXISTING);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public String getPropertyFilePath(String fileName, String propertyType){
		
		String slash = System.getProperty("file.separator");
		
		File directory = new File(System.getenv("CPSHOME")+slash+"resources"+slash+"batch"+slash+"ptch");	 
		
		//DBCONFIG for Database Connection information, LOGBACK for logging configuration file, FILEPATH for OTHER Properties
		if("DBCONFIG".equals(propertyType)){
			directory = new File(System.getenv("CPSHOME")+slash+"resources"+slash);	
			
		}
		
		String filePath = directory+slash+fileName;
		return filePath;
	
	}
	
	/*public static void main(String args[]){
		 
		//ptechUtil util = new ptechUtil();
		//util.writeIntoFile("Line 6");
		//util.formatAmount("1.1");
		double amt1 = 325.78;
		double amt2 = 11.44;
		double amt3 = amt1+amt2;
		java.math.BigDecimal round_hfinal = new java.math.BigDecimal ( amt3 ) ;  
	      // /* round to 4 dec. places 
	  //   round_hfinal = round_hfinal.setScale ( 2, java.math.BigDecimal.ROUND_HALF_UP ) ; 
		//System.out.println(round_hfinal);
		
	}*/
	
	
}
