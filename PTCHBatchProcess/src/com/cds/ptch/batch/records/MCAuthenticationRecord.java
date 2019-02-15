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
 * This extension record is only for Master cards
 */
public class MCAuthenticationRecord implements AuthenticationRecord{
	
	private final static PTCHBatchLogger log =  PTCHBatchLoggerFactory.getPTCHBatchLogger("MCAuthenticationRecord");
	
	private String constant = null;
	private String extensionRecordMOPType = null;
	private String extensionRecordSeqNumber = null;
	private String MCAuthenticationVerificationValue = null;	
	private String reserved = null;

	/**
	 * Constructor for AuthenticationRecord.
	 */
	public MCAuthenticationRecord(){}
	
	/**
	 * Builds Authentication Record for each detail record if the payment account type is MasterCard (MC)
	 * @param detailMap Map<String,String>  CDPT information fields	
	 * @return Fixed length MCAuthenticationRecord
	 */
	public String buildAuthenticationRecord(Map<String, String> detailMap) {		
		
		   try{
			   
			    PTCHBatchUtil ptechUtil = new PTCHBatchUtil();
				
				constant = "E";
				
				extensionRecordMOPType = "MC";
				
				extensionRecordSeqNumber = "002";
				
				String transactionType = detailMap.get("TranType");
					//If recurring payment, we send the constant
				if("2".equals(transactionType)){
					
					MCAuthenticationVerificationValue = ptechUtil.fillSpaces("SUBSEQUENT000000000000000000",32);
					
				}else if("5".equals(transactionType)){		
					
					String MCAuthVerificationValue = detailMap.get("TransactionCryptogram").trim();
					int sizeOfCryptogram = MCAuthVerificationValue.length();
					
					if(sizeOfCryptogram == 28 || sizeOfCryptogram == 20 ){
						MCAuthenticationVerificationValue = ptechUtil.fillSpaces(MCAuthVerificationValue,32);
						
					} else{
						MCAuthenticationVerificationValue = ptechUtil.fillSpaces("",32);						
					}
				}				
				
			   reserved = ptechUtil.fillSpaces(" ", 82);
				
		   }catch (Exception ex) {
	        	//ex.printStackTrace();
	        	log.error(ex, ex.getCause(), "Exception while formatting MCAuthenticationRecord");            
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
		   sb.append(MCAuthenticationVerificationValue);	
		   sb.append(reserved);
		   sb.append("\r\n");				
		   return sb.toString();
	}

}
