package com.cds.ptch.batch.records;

import java.util.Map;

import com.cds.ptch.batch.util.log.PTCHBatchLogger;
import com.cds.ptch.batch.util.log.PTCHBatchLoggerFactory;
import com.cds.ptch.batch.util.services.PTCHBatchUtil;

/**
 * This class Formats detail record for PTCH batch file
 * @author ShanthiNT 
 * @version $Revision: 1.0 $
 * 
 */
public class BatchDetailRecord {

	private final static PTCHBatchLogger log =  PTCHBatchLoggerFactory.getPTCHBatchLogger("PTCHBatch");	
	private String constant = null;
	private String divisionNumber = null;	
	private String merchantOrderNumber = null;
	private String actionCode = null;
	private String methodOfPayment = null;
	private String accountNumber = null;
	private String expirationDate = null;
	private String amount = null;	
	private String currencyCode = null;
	private String responseReasonCode = null;
	private String transactionType = null;
	private String responseDate = null;
	private String authVerificationCode = null;
	private String avsResponseCode = null;
	private String billPaymentIndicator = null;
	private String encryptionFlag = null;
	private String reserved1 = null;
	private String reserved2 = null;
	private String reserved3 = null;	
	private String reserved4 = null;
	private String reserved5 = null;
	private String merchantSpace = null;
	
	/**
	 * Constructor for BatchDetailRecord.
	 */
	public BatchDetailRecord(){}
	
	
	/**
	 * Builds a detail record
	 * @param detailRecordMap Map<String,String> contains detail record information	
	 * @return String fixed length detail record
	 */
	public String buildDetailRecord(Map<String, String> detailRecordMap ) {		
		
		   String histid  =null;
		 
		   try{
			    PTCHBatchUtil ptechUtil = new PTCHBatchUtil();
				
				constant = "S";
				
				// Assigned and provided to merchant by PTECH
				String divisionNo= detailRecordMap.get("DivisionNumber");	   
				divisionNumber = ptechUtil.fillZeros(divisionNo, 10);
				
				//String productID = detailRecordMap.get("ProductID");	
				String pt_actionCode = detailRecordMap.get("ActionCode");
				
				if("AR".equals(pt_actionCode) || "DP".equals(pt_actionCode)){
					
					histid = detailRecordMap.get("MerchantOrderNumber");					
				}

				merchantSpace = ptechUtil.fillSpaces(detailRecordMap.get("HistID").substring(1),8);				

				if(histid == null){
				
					histid = detailRecordMap.get("HistID");
				}
			    
			    actionCode = detailRecordMap.get("ActionCode");				
				
				methodOfPayment = detailRecordMap.get("MOP");		
				
				merchantOrderNumber = ptechUtil.fillSpaces(histid, 22);
				
				String accountNo = detailRecordMap.get("AccountNumber");
				accountNumber = ptechUtil.fillSpaces(accountNo, 19);				
				
				// fill blanks for debit transactions in expdate
				if("PA".equals(pt_actionCode) || "PR".equals(pt_actionCode)){
					expirationDate = ptechUtil.fillSpaces("", 4);
					
				}else{
					
					// if card has expired , fill blanks in expdate
					String dExpire = detailRecordMap.get("dExpire");
					if(dExpire != null && !"".equals(dExpire)){
						String expmonth = null;
						String expyear = null;
						if(dExpire.length() == 6){
							expmonth= dExpire.substring(0,2);
							expyear = dExpire.substring(4,6);
						}else{
							expmonth= dExpire.substring(0,2);
							expyear = dExpire.substring(2,4);
						}
						
						boolean notExpired = ptechUtil.validateExpDate(expmonth, expyear);
						if(notExpired){
							expirationDate = expmonth+expyear;
							
						}else{
							expirationDate = ptechUtil.fillSpaces(" ", 4);
							
						}
					}else{
						expirationDate = ptechUtil.fillSpaces(" ", 4);
					}
				}
				
				String transAmount = detailRecordMap.get("Amount").replace(".", "");
				amount = ptechUtil.fillZeros(transAmount, 12);		
			
				currencyCode = detailRecordMap.get("CurrencyCode");
				
				String responseCode = detailRecordMap.get("ResponseCode").trim();
				if(responseCode != null && "".equals(responseCode)){
					responseReasonCode = ptechUtil.fillSpaces(responseCode, 3);
				}else{
					responseReasonCode = responseCode;
				}
				
				
				/*
				 * space Retail
				 * 1 Mail Telephone Order, CSR Payments, GATEWAY Payments
				 * 2 Recurring
				 * 7 Encrypted E-commerce
				 * I IVR Payment
				 * R Retail
				 * 5 Consumer Digital Payment Token
				 */
				String transType = detailRecordMap.get("TranType");
				transactionType = ptechUtil.fillSpaces(transType,1);
				
				reserved1 = " ";
				
				String authVerifyCode = detailRecordMap.get("AuthVerifyCode").trim();			
				String respDate = detailRecordMap.get("ResponseDate").trim();
				if("DP".equals(actionCode)){
					
					responseDate = ptechUtil.fillSpaces(respDate,6);
					authVerificationCode= ptechUtil.fillSpaces(authVerifyCode,6);
					
					String avsCode = detailRecordMap.get("AVSCode");
					avsResponseCode= ptechUtil.fillSpaces(avsCode,2);
					if("".equals(responseCode)){
						responseReasonCode = "100";
					}
					
				}else{
					
					if(respDate == null || "".equals(respDate) || "000000".equals(respDate)){
						responseDate= ptechUtil.fillSpaces("",6);
						
					}else{
						responseDate = ptechUtil.fillSpaces(respDate,6);
					}
					 
					if(authVerifyCode == null || "".equals(authVerifyCode)){
						authVerificationCode= ptechUtil.fillSpaces("",6);
						
					}else{
						authVerificationCode = ptechUtil.fillSpaces(authVerifyCode,6);
					}
					
					avsResponseCode= ptechUtil.fillSpaces("",2);
			
				}
				
				reserved2 = " ";
				
				/*if(custName.equals("PAYDQ RETAILER")){
					billPaymentIndicator = " ";
					
				}else{*/
				
				billPaymentIndicator = detailRecordMap.get("BillPaymentIndicator");
				if(billPaymentIndicator == null || "".equals(billPaymentIndicator)){
				    billPaymentIndicator = " ";
				}
				//}				
				
				encryptionFlag = detailRecordMap.get("EncryptionFlag");
				if(encryptionFlag== null || "".equals(encryptionFlag)){
					encryptionFlag = ptechUtil.fillSpaces(" ",3);
				}
				
				reserved3 = ptechUtil.fillSpaces(" ",2);
				
				reserved4 = ptechUtil.fillSpaces("",1);
				
				reserved5 = ptechUtil.fillSpaces(" ",10);
				
				
		
		   } catch (Exception ex) {
	        	ex.printStackTrace();
	        	log.error(ex, ex.getCause(), "Exception while formatting detail Record in DetailRecord class for histID - "+histid);            
	            throw new RuntimeException(ex.getMessage());
	    
	       }
		   return this.toString();
		
	}
		
	/**
	 * 
	 * @return String 
	 */
	public String toString() {
		
		   StringBuffer sb = new StringBuffer();
		   sb.append(constant);
		   sb.append(divisionNumber);
		   sb.append(merchantOrderNumber);	
		   sb.append(actionCode);
		   sb.append(methodOfPayment);		
		   sb.append(accountNumber);		
		   sb.append(expirationDate);		
		   sb.append(amount);
		   sb.append(currencyCode);
		   sb.append(responseReasonCode);
		   sb.append(transactionType);
		   sb.append(reserved1);   // blank
		   sb.append(responseDate);
		   sb.append(authVerificationCode);
		   sb.append(avsResponseCode);	
		   sb.append(reserved2);
		   sb.append(billPaymentIndicator);
		   sb.append(encryptionFlag);
		   sb.append(reserved3);	
		   sb.append(reserved4);
		   sb.append(reserved5);
		   sb.append(merchantSpace);
		   sb.append("\r\n");
			
		   return sb.toString();
	}
	

}
