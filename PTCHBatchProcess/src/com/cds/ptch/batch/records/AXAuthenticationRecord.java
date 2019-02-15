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
 * This extension record is only for AMEX cards
 */
public class AXAuthenticationRecord implements AuthenticationRecord{
	
	private final static PTCHBatchLogger log =  PTCHBatchLoggerFactory.getPTCHBatchLogger("AXAuthenticationRecord");
	
	private String constant = null;
	private String extensionRecordMOPType = null;
	private String extensionRecordSeqNumber = null;
	private String AXAuthenticationVerificationValue = null;	
	private String transactionID = null;	
	private String reserved = null;
	
	/**
	 * Constructor for AXAuthenticationRecord.
	 */
	public AXAuthenticationRecord(){}
	
	/**
	 * Builds AXAuthentication Record for each detail record if the payment account type is AX
	 * @param AXAuthenticationMap Map<String,String>  CDPT information fields	
	 * @return Fixed length AXAuthenticationRecord
	 */
	public String buildAuthenticationRecord(Map<String, String> AXAuthenticationMap) {		
		
		   try{
			   
			    PTCHBatchUtil ptechUtil = new PTCHBatchUtil();
				
				constant = "E";
				
				extensionRecordMOPType = "AX";
				
				extensionRecordSeqNumber = "004";
				
				String transactionType = AXAuthenticationMap.get("TranType");
				//If recurring payment, we need to pass constant
				if("2".equals(transactionType)){
					
					AXAuthenticationVerificationValue = ptechUtil.fillSpaces("SUBSEQUENT000000000000000000",40);
					transactionID = ptechUtil.fillSpaces(" ", 40);
					
				}else if("5".equals(transactionType)){
					
					String AXAuthVerificationValue = AXAuthenticationMap.get("TransactionCryptogram").trim();
					int sizeOfCryptogram = AXAuthVerificationValue.length();
					
					//If cryptogram is 28 or 20 in length, send the whole cryptogram Authenctication 
					if(sizeOfCryptogram == 28 || sizeOfCryptogram == 20 ){
						AXAuthenticationVerificationValue = ptechUtil.fillSpaces(AXAuthVerificationValue,40);
						transactionID = ptechUtil.fillSpaces(" ", 40);
						
						
					} else if(sizeOfCryptogram == 56 || sizeOfCryptogram == 40 ){						
						
						String AEVVBlockA = AXAuthVerificationValue.substring(0, (AXAuthVerificationValue.length()/2));
						String XIDBlockB = AXAuthVerificationValue.substring((AXAuthVerificationValue.length()/2));
						
						AXAuthenticationVerificationValue = ptechUtil.fillSpaces(AEVVBlockA,40);
						transactionID = ptechUtil.fillSpaces(XIDBlockB,40);
					}
				}
				
				reserved = ptechUtil.fillSpaces(" ", 34);
								
		   }catch (Exception ex) {
	        	//ex.printStackTrace();
	        	log.error(ex, ex.getCause(), "Exception while formatting AXAuthenticationRecord");            
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
		   sb.append(AXAuthenticationVerificationValue);	
		   sb.append(transactionID);
		   sb.append(reserved);
		   sb.append("\r\n");				
		   return sb.toString();
	}

}
