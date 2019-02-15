package com.cds.ptch.batch;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.beanio.BeanReader;
import org.beanio.StreamFactory;
import org.beanio.UnexpectedRecordException;
import org.beanio.UnidentifiedRecordException;

import com.cds.ptch.batch.response.ptchspec.BatchTotal;
import com.cds.ptch.batch.response.ptchspec.Detail;
import com.cds.ptch.batch.response.ptchspec.DetailRecordGroup;
import com.cds.ptch.batch.response.ptchspec.EndOfFile;
import com.cds.ptch.batch.response.ptchspec.FileTotal;
import com.cds.ptch.batch.response.ptchspec.Header;
import com.cds.ptch.batch.response.ptchspec.Trailer;
import com.cds.ptch.batch.util.log.PTCHBatchLogger;
import com.cds.ptch.batch.util.log.PTCHBatchLoggerFactory;
import com.cds.ptch.batch.dao.PTCHBatchDAO;
import com.cds.ptch.batch.util.services.FileSearch;
import com.cds.ptch.batch.util.services.PTCHBatchUtil;


/**
 * This class scans the ptech batch response file and inserts the response in tblResponse_PTCH
 * @author ShanthiNT 
 * @version $Revision: 1.0 $
 */
public class PTCHBatchResponseParser {
	 
	private final static PTCHBatchLogger log =  PTCHBatchLoggerFactory.getPTCHBatchLogger("PTCHBatch");
    
    private PTCHBatchDAO ptechDAO = null;
  	private PTCHBatchUtil util = null;
    private Map<String, String> processorInfoMap = null;

     /**
      * Constructor for PTCHBatchResponseParser.
      */
     public PTCHBatchResponseParser(){
 		ptechDAO = new PTCHBatchDAO();	
 		util = new PTCHBatchUtil();
 		processorInfoMap = ptechDAO.getProcessorProperties();
		
 		 
 	}
     	  
	 /**
	  * Loops through the file folders to find response files. If there is any response file,
	  * Parses the response file and moves it to processed folder
	  */
	 public final void parseBatchResponse(){
		
		    //String slash = System.getProperty("file.separator");
		    
		   // String path ="C:"+slash+"Output"+slash+"BigBatch"+slash+"Batch"
		   // 		             +slash+"PTECH"+slash+"IN"+slash;
			 
		 	
			//String path = util.getfilePath("responsepath");
			String path = processorInfoMap.get("ProcessorDirectoryIN");
		    File dir = new File(path);
		    try{
				    // get the list of file from the directory
				    String [] fileList = dir.list();
				    
				    // get the current date as 'MMDD'
				    String currentdate = new PTCHBatchUtil().currentDateTime().substring(0,4);			       

			        int listLength = fileList.length;
					if (listLength == 0) {
					    // Either directory does not exist or is not a directory
						System.out.println("No PTECH Batch file found to parse");
							
					}else{	
						// if the directory has more than 1 file, loop through it and find the current file				
						
					   for (String filename:fileList){
							
				    	      System.out.println(filename);
				    	      
				    	      String [] fileParts = filename.split("\\.");//.substring(1,5);
				    	      String fileExtension = fileParts[2].substring(0,4);
				    	      
					          // make sure it is today's file 
					       	  if("DONE".equals(fileExtension.toUpperCase())){
					      		  boolean isSuccess = this.parseFile(path,filename);
					      		  
					      		  if(isSuccess){
					      			  this.moveToProcessed(filename,path);
					      		  }else{
					      			// move the file since there is exception
						 		    String exceptionPath = processorInfoMap.get("ProcessorExceptionDirectoryIN");//	util.getfilePath("exceptionpathin");	 				
						 			util.moveFile(filename, path, exceptionPath);
						 			
					      		  }
					          }
					     }
						
					}
					//System.out.println("Out of for loop" );
		    }catch (FileNotFoundException e){
		    	e.printStackTrace();
 		        log.error(e,e.getCause(),"PTECHBatch File not found exception");
 			
		    }catch (Exception e){
		    	e.printStackTrace();
 		        log.error(e,e.getCause(),"Error in PTECHBatch Parser");
 			
		    }	        	 
	       
	  }
  
	 /**
       * Parses the response File
       * @param dir String
       * @param fileName String      
       * @throws FileNotFoundException 
       */
      private boolean parseFile(String dir, String fileName) throws FileNotFoundException{    	  
    	    
	        	File fFile =  new File(dir+fileName);
	        	
	        	//Scanner scanner = new Scanner(fFile);
	      		
	        	String status = "SUCCESS";
	       	    int failureCnt = 0;
	       	    int successCnt = 0;
	       	    String fileTotalAmount = null;
	       	    int fileOrderCount = 0;
	       	    int j =0;
	       	    boolean isSuccessFile = true;
	       	    Connection con = ptechDAO.makeConnection();
	       	 
	            try {	            	 	    	 		  
	 		      
		 		       int YNProcessed = 0;
		 		      	
		 		      	StreamFactory factory = StreamFactory.newInstance();
		 		      	FileSearch fileSearch = new FileSearch();
		 				String resourceFilename = fileSearch.searchDirectory("PTCHSpecMapping.xml");;
		 		     	factory.load(resourceFilename);
		 			
		 		        BeanReader beanReader = factory.createReader("PTCHBatchResponseParser", fFile);
						Object record = null;
						Header headerRecord = null;
						BatchTotal batchTotalRecord = null;
						FileTotal fileTotalRecord = null;
						Trailer trailerRecord = null;
						//EndOfFile eofRecord = null;
						boolean isEnfOFFile = false;
						
						boolean handleUnidentifiedRecord = true;
						
						// Changes to handle undentified records -- NTS 04/25/2017
						/**
						 * Added do-while loop with Label READERLOOP
						 * Added to catch undentified record and unexpected record exceptions
						 * 
						 */
						READERLOOP:
						do{
					
						try{
														
						while ((record = beanReader.read()) != null) {
									
									String recordName = beanReader.getRecordName();
									if (recordName != null) 
									{
										switch (recordName) 
										{
											case "header": 
											{
												headerRecord = (Header) record;									
												break;
											}
											case "details": {
												
												DetailRecordGroup details = (DetailRecordGroup) record;
												YNProcessed = 1;
												Detail detailRecord=details.getDetail();
												String responseCode =  detailRecord.getResponseCode();
						 		    	    	int responseCodeInt = Integer.parseInt(responseCode);
						 		    	    	if( (responseCodeInt >= 100) && (responseCodeInt < 112) ){
						 			    	    	 successCnt++;
						 			    	    	 YNProcessed = 1;
						 			    	     }else{
						 			    	    	failureCnt++;
						 			    	    	 YNProcessed = 3;
						 			    	     }
												ptechDAO.insertPTECHResponse(details,con, YNProcessed);									
												break;
												
											}									
											case "batchTotal": {
												batchTotalRecord = (BatchTotal) record;
												break;
											}
											case "fileTotal": {
												fileTotalRecord = (FileTotal) record;
												
												fileTotalAmount =this.getTotalAmount(fileTotalRecord.getFileAmountTotal());
						 		    	    	fileOrderCount = Integer.parseInt(fileTotalRecord.getFileOrderCount());	
												break;
											}
											case "trailer": {
												trailerRecord = (Trailer) record;
												isEnfOFFile = true;
												break;
											}
											/*case "EOF": {
												eofRecord = (EndOfFile) record;
												break;
											}	*/
											
										}
										String fileType = headerRecord.getMerchantSpace();
										if(!"CPSBATCH".equals(fileType)){
											isSuccessFile = false;
											handleUnidentifiedRecord = false; // break the second loop since it is not batch response file
											break;  //break the while loop since it is not batch file response
										}
										// To avoid any empty lines after trailer record
										if(isEnfOFFile){
											handleUnidentifiedRecord = false; // break the second loop since it is end of the the file
											break; //break the while loop
										}
										/*if(eofRecord != null && "EOFEOFEOF".equals(eofRecord.getConstant())){
											break;  //break the while loop
										}*/
										
									} // IF statement ends
							
						}  // While loop ends
						
					
						
						}catch (UnidentifiedRecordException uex){
							 log.error(uex,uex.getCause(),"PTECHBatch Parser UnidentifiedRecord error : File Name: " + fileName);						 		
							 continue READERLOOP;
						}catch (UnexpectedRecordException ux){
						    log.error(ux,ux.getCause(),"PTECHBatch Parser UnexpectedRecord error : File Name: " + fileName);								 
							continue READERLOOP;
					   }
						
							
					   }while(handleUnidentifiedRecord); // for handling unidentified and unexpected records
					
					   beanReader.close();		
						
						
		 		      // close the database connection	 
				 	  try{
				 	         if(con != null) {
				 	             con.close();
				 	         }
				 	                
				 	   } catch (SQLException ex) {
				 	       log.error(ex,ex.getCause(), "- Closing Database connection after processing batch response" );
				 	   }	 
				 
						
	 			}catch(Exception e){	 				
	 		    	e.printStackTrace();
	 		        log.error(e,e.getCause(),"PTECHBatch Parser error : File Name: " + fileName);
	 		        status = "FAILURE";	
	 		        isSuccessFile = false;
	 				  
	 		    }finally {		    
	 		      //ensure the underlying stream is always closed
	 		     // scanner.close();
	 		     // close the database connection	 
			 	  try{
			 	         if(con != null) {
			 	             con.close();
			 	         }			 	         
			  	                
			 	   } catch (SQLException ex) {
			 	       log.error(ex,ex.getCause(), "- Closing Database connection after processing batch response" );
			 	   }
	 		    }
	            
	 		    	 		   	
	 		   	// INSERT AUDIT TRAIL CPS BATCH in tblFileProcessingLog
	 		    try{
	 		    	
	 		        int processCode = 1;
		 			  
		 			// INSERT AN AUDIT TRAIL FOR BATCH PROCESS
			 		String processTask ="PTECH_BATCH_RESPONSE_PROCESSED";					
			 		ptechDAO.insertFileProcessingLog(processTask, fileName, "", 
			 										fileOrderCount, failureCnt, 
			 										processCode, status, 0, fileTotalAmount);
				
					
	 		    }catch(Exception e){
	 		    	e.printStackTrace();
	 		        log.error(e,e.getCause(),"PTECHBatch Parser - error in insert file processinglog");
	 				  
	 		    }
	 		    
	 		    return isSuccessFile;
	        	  
      }
      	
	 
	   /**
	    * Moves file to processed folder
	    * @param fileName String
	    * @param sourcepath String
	    */
	   private void moveToProcessed(String fileName, String sourcepath){
   	  
	 	    String destinationPath = processorInfoMap.get("ProcessorProcessedDirectoryIN");//util.getfilePath("responseprocessedpath");	 				
		 		    
	 	    util.moveFile(fileName, sourcepath, destinationPath);
	 	 
       }	   
	   
	   /**
	    * Gets Total amount from trailer record
	    * @param strArray String[]
	   
	    * @return String */
	   private String getTotalAmount(String fileTotalamount){
		   
			 BigDecimal amount = new BigDecimal(fileTotalamount);
    	  	 
    	  	 String amountToReturn = amount.divide(new BigDecimal(100)).toString();
    	   
    	  	 return amountToReturn;
	   }
	  
	 
      public static void main(String[] args){
		  
    	  	 PTCHBatchResponseParser parser = new PTCHBatchResponseParser();

    	  	try{
    			
   	  		parser.parseBatchResponse();

    	  		
    		}catch (Exception ex) {
    			ex.printStackTrace();
    			// exceptions are already logged in		
    			System.exit(3);
    		} 
    		System.exit(0);
    		
    	   
	  } 

}
