package com.cds.ptch.batch.records;

import java.util.Map;

import com.cds.ptch.batch.factory.AuthenticationRecord;
import com.cds.ptch.batch.util.log.PTCHBatchLogger;
import com.cds.ptch.batch.util.log.PTCHBatchLoggerFactory;
import com.cds.ptch.batch.util.services.PTCHBatchUtil;

public class JCAuthenticationRecord implements AuthenticationRecord{
	
	private final static PTCHBatchLogger log =  PTCHBatchLoggerFactory.getPTCHBatchLogger("JCAuthenticationRecord");
	
	private String constant = null;
	private String extensionRecordMOPType = null;
	private String extensionRecordSeqNumber = null;
	private String JCAuthenticationVerificationValue = null;	
	private String transactionID = null;	
	private String reserved = null;
	
	/**
	 * Constructor for AXAuthenticationRecord.
	 */
	public JCAuthenticationRecord(){}
	
	/**
	 * Builds JCAuthentication Record for each detail record if the payment account type is JCB
	 * @param JCAuthenticationMap Map<String,String>  CDPT information fields	
	 * @return Fixed length JCAuthenticationRecord
	 */
	public String buildAuthenticationRecord(Map<String, String> JCAuthenticationMap) {		
		
		   try{
			   
			    PTCHBatchUtil ptechUtil = new PTCHBatchUtil();
				
				constant = "E";
				
				extensionRecordMOPType = "JC";
				
				extensionRecordSeqNumber = "005";
				
				String transactionType = JCAuthenticationMap.get("TranType");
				//If recurring payment, we need to pass the constant
				if("2".equals(transactionType)){
					
					JCAuthenticationVerificationValue = ptechUtil.fillSpaces("SUBSEQUENT000000000000000000",40);
					transactionID = ptechUtil.fillSpaces(" ", 40);
					
				}else if("5".equals(transactionType)){
					
					String JCAuthVerificationValue = JCAuthenticationMap.get("TransactionCryptogram").trim();
					int sizeOfCryptogram = JCAuthVerificationValue.length();
					
					//If cryptogram is 28 or 20 in length, send the whole cryptogram Authenctication
					if(sizeOfCryptogram == 28 || sizeOfCryptogram == 20 ){
						JCAuthenticationVerificationValue = ptechUtil.fillSpaces(JCAuthVerificationValue,40);
						transactionID = ptechUtil.fillSpaces(" ", 40);
						
						
					} else if(sizeOfCryptogram == 56 || sizeOfCryptogram == 40 ){						
						
						String JCVVBlockA = JCAuthVerificationValue.substring(0, (JCAuthVerificationValue.length()/2));
						String XIDBlockB = JCAuthVerificationValue.substring((JCAuthVerificationValue.length()/2));
						
						JCAuthenticationVerificationValue = ptechUtil.fillSpaces(JCVVBlockA,40);
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
		   sb.append(JCAuthenticationVerificationValue);	
		   sb.append(transactionID);
		   sb.append(reserved);
		   sb.append("\r\n");				
		   return sb.toString();
	}

}
