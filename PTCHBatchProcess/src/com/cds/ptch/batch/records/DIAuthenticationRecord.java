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
 * This extension record is only for Discover cards
 */
public class DIAuthenticationRecord implements AuthenticationRecord{
	
	private final static PTCHBatchLogger log =  PTCHBatchLoggerFactory.getPTCHBatchLogger("DIAuthenticationRecord");
	
	private String constant = null;
	private String extensionRecordMOPType = null;
	private String extensionRecordSeqNumber = null;
	private String DIAuthenticationVerificationValue = null;	
	private String CAVVResponse = null;	
	private String reserved = null;
	private String reserved1 = null;

	/**
	 * Constructor for DIAuthenticationRecord.
	 */
	public DIAuthenticationRecord(){}
	
	/**
	 * Builds DIAuthentication Record for each detail record if the payment account type is Discover (DI or DC)
	 * @param DIAuthenticationMap Map<String,String>  CDPT information fields	
	 * @return Fixed length DIAuthenticationRecord
	 */
	public String buildAuthenticationRecord(Map<String, String> detailMap) {		
		
		   try{
			   
			    PTCHBatchUtil ptechUtil = new PTCHBatchUtil();
				
				constant = "E";
				
				extensionRecordMOPType = "DI";
				
				extensionRecordSeqNumber = "002";
				
				String transactionType = detailMap.get("TranType");
					//If recurring payment, we send the constant
				if("2".equals(transactionType)){
					
					DIAuthenticationVerificationValue = ptechUtil.fillSpaces("SUBSEQUENT000000000000000000",28);
					
				}else if("5".equals(transactionType)){		
					
					String DIAuthVerificationValue = detailMap.get("TransactionCryptogram").trim();
					int sizeOfCryptogram = DIAuthVerificationValue.length();
					
					if(sizeOfCryptogram == 28 || sizeOfCryptogram == 20 ){
						DIAuthenticationVerificationValue = ptechUtil.fillSpaces(DIAuthVerificationValue,28);
						
					} else{
						DIAuthenticationVerificationValue = ptechUtil.fillSpaces("",28);						
					}
				}
				
				reserved = ptechUtil.fillSpaces(" ", 40);
				
				// This field will be returned in response
				CAVVResponse = ptechUtil.fillSpaces(" ",1);
				
				reserved1 = ptechUtil.fillSpaces(" ", 45);
				
		   }catch (Exception ex) {
	        	//ex.printStackTrace();
	        	log.error(ex, ex.getCause(), "Exception while formatting DIAuthenticationRecord");            
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
		   sb.append(DIAuthenticationVerificationValue);	
		   sb.append(reserved);
		   sb.append(CAVVResponse);
		   sb.append(reserved1);
		   sb.append("\r\n");				
		   return sb.toString();
	}

}
