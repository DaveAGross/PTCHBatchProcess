package com.cds.ptch.batch.records;

import java.util.Map;

import com.cds.ptch.batch.util.log.PTCHBatchLogger;
import com.cds.ptch.batch.util.log.PTCHBatchLoggerFactory;
import com.cds.ptch.batch.util.services.PTCHBatchUtil;
/**
 * This class formats debit record for PTCH batch file - not in production yet (not ceritfied with PTCH)
 * @author ShanthiNT 
 * @version $Revision: 1.0 $
 */
public class ProductDebitRecord {
	
	private final static PTCHBatchLogger log =  PTCHBatchLoggerFactory.getPTCHBatchLogger("Level2Record");

	private String constant = null;
	private String recordType = null;	
	private String PRecordSequence = null;	
	private String debitTraceNumber = null;
	private String billerReferenceNumber = null;
	private String reserved = null;
	private String lineFeed = null;
	
	/**
	 * Constructor for ProductDebitRecord.
	 */
	public ProductDebitRecord(){}
	
	/**
	 * Builds Product Debit record
	 * @param detailRecordMap Map<String,String>
	 * @param PRecordSeqNo int	
	 * @return String fixed length debit record
	 */
	public String buildDebitRecord(Map<String, String> detailRecordMap, int PRecordSeqNo ) {		
		
		   try{
			   
			    PTCHBatchUtil ptechUtil = new PTCHBatchUtil();
				
				constant = "P";
				
				recordType = "DE";
						
				String PRecordSequenceNo = String.valueOf(PRecordSeqNo);
				PRecordSequence = ptechUtil.fillZeros(PRecordSequenceNo, 3);
				
				String traceNumber = detailRecordMap.get("DebitTrace");
				if(traceNumber == null){
					debitTraceNumber =ptechUtil.fillSpaces(" ", 8);
				}else{
					debitTraceNumber =ptechUtil.fillSpaces(traceNumber, 8);
				}
				
				String customerid = detailRecordMap.get("CustomerID");	
				billerReferenceNumber = ptechUtil.fillSpaces(customerid, 25);				
				
				reserved = ptechUtil.fillSpaces(" ", 81);
				
				lineFeed = "\r\n";
				
		   }catch (Exception ex) {
	        	ex.printStackTrace();
	        	log.error(ex, ex.getCause(), "Exception while formatting Address Record in AddressRecord class");            
	            throw new RuntimeException(ex.getMessage());
	       }
		   
		   return this.toString();
		
	}	
	
	/**
	 * @return String 
	 */
	public String toString() {
		
		   StringBuffer sb = new StringBuffer();
		   sb.append(constant);
		   sb.append(recordType);
		   sb.append(PRecordSequence);	
		   sb.append(debitTraceNumber);
		   sb.append(billerReferenceNumber);		
		   sb.append(reserved);
		   sb.append(lineFeed);				
		   return sb.toString();
	}

}
