package com.cds.ptch.batch.records;

import java.util.Map;

import com.cds.ptch.batch.factory.AuthenticationRecord;
import com.cds.ptch.batch.util.log.PTCHBatchLogger;
import com.cds.ptch.batch.util.log.PTCHBatchLoggerFactory;
import com.cds.ptch.batch.util.services.PTCHBatchUtil;

/**
 * 
 * @author ShanthiNT
 * This class builds extension record for CDPT ( Consumer Digital Payment Token ) like ApplePay
 * This extension record is only for Visa cards
 */
public class VIAuthenticationRecord implements AuthenticationRecord{
	
	private final static PTCHBatchLogger log =  PTCHBatchLoggerFactory.getPTCHBatchLogger("VIAuthenticationRecord");
	
	private String constant = null;
	private String extensionRecordMOPType = null;
	private String extensionRecordSeqNumber = null;
	private String VIAuthenticationVerificationValue = null;	
	private String transactionID = null;
	private String CAVVResponse = null;	
	private String reserved = null;

	/**
	 * Constructor for AuthenticationRecord.
	 */
	public VIAuthenticationRecord(){}
	
	/**
	 * Builds Authentication Record for each detail record if the payment account type is Visa (VI)
	 * @param VIAuthenticationMap Map<String,String>  CDPT information fields	
	 * @return Fixed length VIAuthenticationRecord
	 */
	public String buildAuthenticationRecord(Map<String, String> detailMap) {		
		
		   try{
			   
			    PTCHBatchUtil ptechUtil = new PTCHBatchUtil();
				
				constant = "E";
				
				extensionRecordMOPType = "VI";
				
				extensionRecordSeqNumber = "002";
				
				transactionID = ptechUtil.fillSpaces(" ", 40);
				
				String transactionType = detailMap.get("TranType");
					//If recurring payment, we send the constant
				if("2".equals(transactionType)){
					
					VIAuthenticationVerificationValue = ptechUtil.fillSpaces("SUBSEQUENT000000000000000000",40);
					
				}else if("5".equals(transactionType)){		
					
					String VIAuthVerificationValue = detailMap.get("TransactionCryptogram").trim();
					VIAuthenticationVerificationValue = ptechUtil.fillSpaces(VIAuthVerificationValue,40);					
				}				
				
				// This field will be returned in response
				CAVVResponse = ptechUtil.fillSpaces(" ",1);
				
				reserved = ptechUtil.fillSpaces(" ", 33);
				
		   }catch (Exception ex) {
	        	//ex.printStackTrace();
	        	log.error(ex, ex.getCause(), "Exception while formatting VIAuthenticationRecord");            
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
		   sb.append(extensionRecordMOPType);
		   sb.append(extensionRecordSeqNumber);
		   sb.append(transactionID);
		   sb.append(VIAuthenticationVerificationValue);		  
		   sb.append(CAVVResponse);
		   sb.append(reserved);
		   sb.append("\r\n");				
		   return sb.toString();
	}

}
