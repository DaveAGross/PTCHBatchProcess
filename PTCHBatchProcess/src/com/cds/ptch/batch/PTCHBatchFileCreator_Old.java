package com.cds.ptch.batch;

import java.io.File;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.List;

import com.cds.ptch.batch.records.BatchAddressRecord;
import com.cds.ptch.batch.records.BatchDetailRecord;
import com.cds.ptch.batch.records.BatchSoftDescriptorPS1Record;
import com.cds.ptch.batch.records.BatchSoftDescriptorPS2Record;
import com.cds.ptch.batch.records.FileHeaderRecord;
import com.cds.ptch.batch.records.BatchMerchantRecord;
import com.cds.ptch.batch.records.FileTotalsRecord;
import com.cds.ptch.batch.records.FileTrailerRecord;
import com.cds.ptch.batch.records.BatchTotalsRecord;
import com.cds.ptch.batch.util.log.PTCHBatchLogger;
import com.cds.ptch.batch.util.log.PTCHBatchLoggerFactory;
import com.cds.ptch.batch.dao.PTCHBatchDAO;
import com.cds.ptch.batch.factory.AuthenticationRecord;
import com.cds.ptch.batch.factory.AuthenticationRecordFactory;
import com.cds.ptch.batch.util.services.PTCHBatchUtil;

/**
 * This class creates the Batch request file for PTCH
 * @author ShanthiNT 
 * @version $Revision: 1.0 $
 */
public class PTCHBatchFileCreator_Old {
	
	private PTCHBatchDAO ptchBatchDAO = null;
	private final static PTCHBatchLogger log =  PTCHBatchLoggerFactory.getPTCHBatchLogger("PTCHBatch");
		
	/**
	 * Constructor for PTCHBatchFileCreator.
	 */
	public PTCHBatchFileCreator_Old(){
		ptchBatchDAO = new PTCHBatchDAO();	
	}	
		
	/**
	 * Creates PTCH Batch file
	
	 */
	public void buildBatchFile() {
		
		PTCHBatchUtil util = new PTCHBatchUtil();
		// Create file name 
	    String fileName = null;//"PTECHBatch.INPROGRESS"; 
		String totalAmount = null;
		int fileOrderCount = 0;
		String creationStatus = "SUCCESS";
		String fileID = null;
		int batchRecords = 0;
		String path = null;	
		String exceptionPath = null;
	
		try{			
			
			Map<String, String> processorInfoMap = ptchBatchDAO.getProcessorProperties();
			
			
			//path = util.getfilePath("requestpath");
			//exceptionPath = util.getfilePath("exceptionpathout");	
			
			path = processorInfoMap.get("ProcessorDirectoryOUT");
			exceptionPath = processorInfoMap.get("ProcessorExceptionDirectoryOUT");	
		
					
		
			log.info("Getting File Header Record..");
			// Get File HeaderRecord
			Map<String, String> headerRecordMap = getHeaderRecord();
			
			// GET File Sequence Number - to create the file name - to transfer the file through SFTP
			String fileSeqNumber = ptchBatchDAO.getFileNumber();
			fileName ="t"+headerRecordMap.get("PresenterID")+".INPROGRESS";	
			
			File file = new File(path+fileName);
			if(file.exists()){
				util.moveFile(fileName, path , exceptionPath);							
			}
			
			int fileRecordCount = 0;
		
			log.info("Formatting File Header Record.. ");
			// Header record
			String fileHeaderRecord = this.getFileHeaderRecord(headerRecordMap);
			
			log.info("Getting merchant list..");
			List<Map<String,String>> merchantList = this.getMerchantList();
			
			int merchantListSize = merchantList.size();
			System.out.println("No of batches : "+ merchantListSize);
			  
			// only format the request if there is transactions   -- CHANGED BY SHANTHINT ON 7/7/2010
			if(merchantListSize > 0){
			  
				int batchRecordCount = 1;					
				 
				log.info("Writing File Header Record in to the file.. ");
				util.writeIntoFile(fileHeaderRecord, fileName, path);
			
				BigDecimal fileTotalAmount = new BigDecimal("0");
				BigDecimal fileSaleAmount = new BigDecimal("0");
				BigDecimal fileRefundAmount = new BigDecimal("0");		
		       
				log.info("Start looping through the merchant list.."); 
				Iterator <Map<String,String>> i = merchantList.iterator();
				
			    BigDecimal batchTotalAmount= new BigDecimal("0");
			    BigDecimal batchSaleAmount = new BigDecimal("0");
			    BigDecimal batchRefundAmount = new BigDecimal("0");
		
			   int batchOrderCount = 0;
				      
				while(i.hasNext()){
					
					Map<String,String> merchantMap = i.next();			
					
					String cointid = merchantMap.get("CoIntID");
					String merchantId = merchantMap.get("MerchantID");
						
					// We don't send 'M' record for now -- We need to add 'PSM001' if we get soft decriptor in the batch file
					// Modified on 07/08/2013' by NTS
					//batchRecordCount =+1;
					//log.info("Getting 'M' record for CoIntID - "+cointid);
					// Format "M" Record
					//String merchantDescriptorRecord = this.getMRecord(merchantMap);		
					
					//log.info("Writing 'M' Record in to the file for CoIntID - "+cointid);
					//util.writeIntoFile(merchantDescriptorRecord, fileName, folder);
				
				  			   
			        log.info("Getting detail record list for CoIntID - "+cointid);
			        // Detail record
					List<Map<String, String>> detailRecordList = this.getDetailRecordList(merchantId, cointid);	
					
					log.info("No of detail records : "+ detailRecordList.size() +" for CoIntID :" +cointid);
						
					log.info("Start looping through the detail record list for CoIntID :" +cointid);
					Iterator <Map<String,String>> j = detailRecordList.iterator();
					while(j.hasNext()){				
				      
						Map<String,String> detailRecordMap = new HashMap<String, String> ();
						detailRecordMap = j.next();
					    
						batchOrderCount +=1;
						batchRecordCount +=1;				
						String detailRecord = this.getDetailRecord(detailRecordMap);
						util.writeIntoFile(detailRecord, fileName, path);
												
						// CERTIFIED TO SEND SOFT MERCHANT INFORMATION 
						String actionCode = detailRecordMap.get("ActionCode");
						
						// Adding CDPT extension record to the file only for Auth, Condition Deposit and Settlement - Added by ShanthiNT on 02/10/2017
						String tokenSource = detailRecordMap.get("TokenSource");
						if("APPY".equals(tokenSource)){
							if("AU".equals(actionCode) || "DC".equals(actionCode) || "DP".equals(actionCode)){
								String cardType = detailRecordMap.get("MOP");
								// Get instance of Authentication Record instance based on the card type
								AuthenticationRecord authRecordInstance = new AuthenticationRecordFactory().getAuthenticationRecordInstance(cardType);
								batchRecordCount +=1;
								String authenticationrecord = authRecordInstance.buildAuthenticationRecord(detailRecordMap);
								util.writeIntoFile(authenticationrecord, fileName, path);
							}
						}
						
						if("AU".equals(actionCode) || "DP".equals(actionCode) || "DC".equals(actionCode) || "RF".equals(actionCode)){
							// If softDescriptorCode is 0- don't send soft descriptor record , 1 - Use default merchant set up, 2 - Use the one sent in the file
							String softDescriptorCode = detailRecordMap.get("SoftDescriptorCode");
							if("1".equals(softDescriptorCode)){
									batchRecordCount +=1;
									String descriptorRecordPS1 = this.getPS1DescriptorRecord(merchantMap);
									util.writeIntoFile(descriptorRecordPS1, fileName, path);	
									
									batchRecordCount +=1;
									String descriptorRecordPS2 = this.getPS2DescriptorRecord(merchantMap);
									util.writeIntoFile(descriptorRecordPS2, fileName, path);	
									
							}else if ("2".equals(softDescriptorCode)){
									batchRecordCount +=1;
									String descriptorRecord = this.getPS1DescriptorRecord(detailRecordMap);
									util.writeIntoFile(descriptorRecord, fileName, path);	
									
									batchRecordCount +=1;
									String descriptorRecordPS2 = this.getPS2DescriptorRecord(detailRecordMap);
									util.writeIntoFile(descriptorRecordPS2, fileName, path);
							}							
						
						}
											
						
						batchRecordCount +=1;				
						// Address Record
						String addressRecord = this.getAddressRecord(detailRecordMap);
						util.writeIntoFile(addressRecord, fileName, path);					
						
						//batchRecordCount +=1;
					
						// batch total amount  
						//(ABSOLUTE AMOUNT sales +ABSOLUTE AMOUNT refunds +ABSOLUTE AMOUNT authorizations)
						BigDecimal amount = new BigDecimal(detailRecordMap.get("Amount"));
						batchTotalAmount = batchTotalAmount.add(amount);
					
						BigDecimal amtRefund;
						BigDecimal amtSale;
						// Total amount of all sale records in the batch.
					    if("DP".equals(actionCode) || "DC".equals(actionCode)){
					    	amtSale = new BigDecimal(detailRecordMap.get("Amount"));
					    	batchSaleAmount = batchSaleAmount.add(amtSale);
					    	
						}//Total amount of all refund records in the batch.
					    else if ("RF".equals(actionCode)){
							//amtRefund = Double.parseDouble(detailRecordMap.get("Amount"));
					    	//batchRefundAmount = batchRefundAmount + amtRefund;
					    	amtRefund = new BigDecimal(detailRecordMap.get("Amount"));
					    	batchRefundAmount = batchRefundAmount.add(amtRefund);
							
						}
					}
					
					// create batch and file total records only if we have detail information for that merhcant
					if (detailRecordList != null && !detailRecordList.isEmpty()){
				
						// round up the decimal places in the amounts
						batchTotalAmount = batchTotalAmount.setScale (2, java.math.BigDecimal.ROUND_HALF_UP );			
						BigDecimal batchSaleAmt = batchSaleAmount.setScale (2, java.math.BigDecimal.ROUND_HALF_UP );
						BigDecimal batchRefundAmt = batchRefundAmount.setScale (2, java.math.BigDecimal.ROUND_HALF_UP );
						
						//log.info("Formatting batchTotalsRecord for CoIntID :" +cointid);
						
						// batch totals record
						String batchTotalRecord =  this.getBatchTotalsRecord(batchTotalAmount, batchRecordCount, batchSaleAmt, batchOrderCount, batchRefundAmt); 
					
						//log.info("Writing batchTotalsRecord in to the file for CoIntID :" +cointid);
						util.writeIntoFile(batchTotalRecord,fileName, path);	
					
						fileRecordCount +=1;
						
						fileRecordCount = fileRecordCount+ batchRecordCount;
						fileOrderCount  = fileOrderCount + batchOrderCount;
				
						batchRecords = batchOrderCount;
						batchOrderCount  = 0;
						batchRecordCount =0;
						
						fileTotalAmount = fileTotalAmount.add(batchTotalAmount);
						fileSaleAmount  = fileSaleAmount.add(batchSaleAmount);
						fileRefundAmount = fileRefundAmount.add(batchRefundAmount);					
											
					    batchTotalAmount= new BigDecimal("0");
					    batchSaleAmount = new BigDecimal("0");
					    batchRefundAmount = new BigDecimal("0");	
					}
		
					    
					//UPDATE THE RECORDS WITH ProcessCode - 1  -- PROCESSOR REQUEST CREATED
					this.updateProcessCode(merchantMap);
				
					// INSERT AN AUDIT TRAIL FOR UPDATER PROCESS
					String processTask ="PTCH_BATCH_CREATION_"+cointid;					
					String origFileID = merchantMap.get("FileID").trim();
					String origFileName = merchantMap.get("FileName").trim();
					int processCode = 1;					
					ptchBatchDAO.insertFileProcessingLog(processTask, origFileName, origFileID, batchRecords, 0, 
							processCode, "SUCCESS", 0, fileTotalAmount.toString());
							
				}
								
				log.info("Ends looping through Merchant List..");
				
				// round up the decimal places in the amounts
				BigDecimal fileTotAmount = fileTotalAmount.setScale (2, java.math.BigDecimal.ROUND_HALF_UP );
				BigDecimal fileSaleAmt = fileSaleAmount.setScale (2, java.math.BigDecimal.ROUND_HALF_UP );
				BigDecimal fileRefundAmt = fileRefundAmount.setScale (2, java.math.BigDecimal.ROUND_HALF_UP );
			
				log.info("Formatting File totals Record..");		
				 // get File Totals record as String
				String totalsRecord = this.getTotalsRecord(fileTotAmount,  fileRecordCount, 
											fileSaleAmt, fileOrderCount,  fileRefundAmt);
				
				log.info("Writing TotalsRecord in to the file..");		
				// write the file totals record in to the settlement file	
			    util.writeIntoFile(totalsRecord,fileName, path);			
			
				log.info("Formatting File Trailer Record..");	
			    // get trailer record as String
				String trailerRecord = this.getTrailerRecord(headerRecordMap);
				
				log.info("Writing File Trailer Record in to the settlement File..");
				// write the trailer record in to the settlement file	
			    util.writeIntoFile(trailerRecord,fileName, path);	
			    
			    //status = "SUCCESS";
			    totalAmount = fileTotAmount.toString();
			    
			}
		
			fileID = headerRecordMap.get("FileID");
			String fileNameFinal =null;
			try{
				// Rename file with PTCH naming convention
				fileNameFinal = fileName.replace(".INPROGRESS", "."+util.fillZeros(fileSeqNumber, 3));
				File file2 = new File(path+fileNameFinal);
				// Rename file (or directory)
				if(file2.exists()){
					util.moveFile(fileNameFinal, path , "exceptionpathout");							
				}
			
				boolean success = file.renameTo(file2);
				if (!success) {
					 log.error("PTCH BATCH file renaming exception- Please check", ("FileName: " +fileNameFinal));
				}
				
			}catch (Exception ex) {
	        	ex.printStackTrace();
	        	log.error("CPS PTCH-Batch file renaming failed, please check ", 
	        				("PTCHFileName: "+ fileName ));	
	        	throw new RuntimeException (ex.getMessage());
		
	        }	
			
			// INSERT AN AUDIT TRAIL FOR BATCH PROCESS in tblFileProcessingLog
			String processTask ="PTCH_BATCH_FILE_CREATION";					
			int processCode = 1;
			ptchBatchDAO.insertFileProcessingLog(processTask, fileNameFinal, fileID, fileOrderCount, 0, 
					processCode, creationStatus, 0, totalAmount);
		
			
		}catch (Exception ex) {
        	ex.printStackTrace();
        	log.error("CPS PTCH-Batch file creation failed and " +
        			"  incomplete-File(if presents) is moved to exceptions folder", 
        				("PTCHFileName: "+ fileName + ", PTCHFileID: "+fileID));
        	// exceptions are already logged in       	
        
			// move the file since there is exception
			util.moveFile(fileName, path, exceptionPath);
			
			creationStatus = "FAILURE";
			// INSERT AN AUDIT TRAIL FOR BATCH PROCESS in tblFileProcessingLog
			String processTask ="PTCH_BATCH_FILE_CREATION";					
			int processCode = 1;
			ptchBatchDAO.insertFileProcessingLog(processTask, fileName, fileID, fileOrderCount, 0, 
					processCode, creationStatus, 0, totalAmount);
			
			throw new RuntimeException(ex.getMessage());
	
        }
		
		
		System.out.println("Finished creating PTCH Batch File");
		
		/*Map<String, String> fileMap = new HashMap<String, String>();
		fileMap.put("FileID", fileID);
		fileMap.put("FileName", fileName);
		fileMap.put("FileOrderCount", String.valueOf(fileOrderCount));
		fileMap.put("TotalAmount", totalAmount);*/
		
		//return fileMap;	
		
	}

	/**
	 * Gets File Header Record fields from database
	 * @return Map<String,String> 
	 */
	private Map<String, String> getHeaderRecord() {
		return ptchBatchDAO.getHeaderRecord("BATCH");
	}	
	
	/**
	 * Gets file header record
	 * @param fileHeaderMap Map<String,String>	
	 * @return String 
	 */
	private String getFileHeaderRecord(Map <String, String> fileHeaderMap){
		return new FileHeaderRecord(fileHeaderMap).toString();	
	}
	
	/**
	 * Get merchants list from database
	 * @return List<Map<String,String>> * @throws RuntimeException 
	 */
	private List<Map<String,String>> getMerchantList() throws RuntimeException {		
		return ptchBatchDAO.getMerchantList("BATCH");		
	}
	
	/**
	 * Gets Merchant descriptor record
	 * @param merchantDescriptorRecordMap Map<String,String>
	 * @return String 
	 */
	private String getMRecord(Map <String, String> merchantDescriptorRecordMap) {
		return new BatchMerchantRecord(merchantDescriptorRecordMap).toString();	
	}
		
	/**
	 * Gets detail record list from database
	 * @param merchantId String
	 * @param cointid String	
	 * @return List<Map<String,String>> 
	 */
	private List<Map<String, String>> getDetailRecordList(String merchantId, String cointid) {
		return ptchBatchDAO.getBatchDetailRecords(merchantId, cointid,"","");
	}
	
	/**
	 * Gets detail record 
	 * @param detailRecordMap Map<String,String>	
	 * @return String 
	 */
	private String getDetailRecord(Map<String, String> detailRecordMap) {
		return new BatchDetailRecord().buildDetailRecord(detailRecordMap) ;
	}

	/**
	 * Gets Address record
	 * @param detailRecordMap Map<String,String>
	 * @return String 
	 */
	private String getAddressRecord(Map<String, String> detailRecordMap) {
		return new BatchAddressRecord().buildAddressRecord(detailRecordMap) ;
	}
	
	/**
	 * Gets Batch Totals Record
	 * @param batchTotalAmount BigDecimal
	 * @param batchRecordCount int
	 * @param batchSaleAmount BigDecimal
	 * @param batchOrderCount int
	 * @param batchRefundAmount BigDecimal	
	 * @return String 
	 */
	private String getBatchTotalsRecord(BigDecimal batchTotalAmount, int batchRecordCount, 
			BigDecimal batchSaleAmount, int batchOrderCount, BigDecimal batchRefundAmount) {
		
		Map<String, String> batchTrailerMap = new HashMap<String, String>();
		
		batchTrailerMap.put("TotalAmount", String.valueOf(batchTotalAmount));
		batchTrailerMap.put("RecordCount", String.valueOf(batchRecordCount));
		batchTrailerMap.put("SaleAmount", String.valueOf(batchSaleAmount));
		batchTrailerMap.put("OrderCount", String.valueOf(batchOrderCount));		
		batchTrailerMap.put("RefundAmount", String.valueOf(batchRefundAmount));
	
		return new BatchTotalsRecord().getBatchTotalsRecord(batchTrailerMap);		
	}
	
	/**
	 * Gets File Totals record
	 * @param fileTotalAmount BigDecimal
	 * @param fileRecordCount int
	 * @param fileSaleAmount BigDecimal
	 * @param fileOrderCount int
	 * @param fileRefundAmount BigDecimal
	
	 * @return String 
	 */
	private String getTotalsRecord(BigDecimal fileTotalAmount, int fileRecordCount, 
			BigDecimal fileSaleAmount, int fileOrderCount, BigDecimal fileRefundAmount) {
		
		Map<String, String> totalsRecordMap = new HashMap<String, String>();
		
		totalsRecordMap.put("FileTotalAmount", String.valueOf(fileTotalAmount));
		totalsRecordMap.put("FileRecordCount", String.valueOf(fileRecordCount));
		totalsRecordMap.put("FileSaleAmount", String.valueOf(fileSaleAmount));
		totalsRecordMap.put("FileOrderCount", String.valueOf(fileOrderCount));		
		totalsRecordMap.put("FileRefundAmount", String.valueOf(fileRefundAmount));
	
		return new FileTotalsRecord().getTotalsRecord(totalsRecordMap);		
	}
	
	/**
	 * Gets File Trailer Record
	 * @param fileHeaderMap Map<String,String>	
	
	 * @return String 
	 */
	private String getTrailerRecord(Map <String, String> fileHeaderMap){
		return new FileTrailerRecord(fileHeaderMap).toString();	
	}
		
	private String getPS1DescriptorRecord(Map<String, String> descriptorRecordMap) {
		return new BatchSoftDescriptorPS1Record(descriptorRecordMap).toString() ;
	}
	
	private String getPS2DescriptorRecord(Map<String, String> descriptorRecordMap) {
		return new BatchSoftDescriptorPS2Record(descriptorRecordMap).toString() ;
	}
	
	
	/**
	 * UPDATES THE RECORDS WITH ProcessCode - 1  -- PROCESSOR REQUEST CREATED	
	 * @param merchantMap Map<String,String>
	 */
	private void updateProcessCode (Map<String, String> merchantMap){
		
		try{
					
			String origFileID = merchantMap.get("FileID").trim();
			String SystemID = merchantMap.get("SystemID").trim();
			String coIntID = merchantMap.get("CoIntID").trim();
			String merchantID = merchantMap.get("MerchantID").trim();
			
			Map<String, String> respMap = new HashMap<String, String>();
			respMap.put("ProcessCodeToUpdate", "1");
			respMap.put("FileID", origFileID);
			respMap.put("OrigProcessCode", "0");
			respMap.put("SystemID", SystemID);
			respMap.put("CoIntID",coIntID);
			respMap.put("MerchantID",merchantID);
			
			ptchBatchDAO.updateProcessCode(respMap);
			
		}catch (Exception ex) {
        	ex.printStackTrace();
        	throw new RuntimeException(ex.getMessage());	
        }
			
	}
	
	
	
	/*public static void main(String[] args ) {
		
		PTCHBatchFileCreator settle = new PTCHBatchFileCreator();
	
		try{			
					
				settle.buildBatchFile(); 
				
		}catch (Exception ex) {
			ex.printStackTrace();
			// exceptions are already logged in		
			System.exit(3);
		} 
		System.exit(0);
		
	} */
	

}
