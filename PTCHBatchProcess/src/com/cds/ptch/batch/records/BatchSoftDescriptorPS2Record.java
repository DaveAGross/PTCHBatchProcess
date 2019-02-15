package com.cds.ptch.batch.records;

import java.util.Map;

import com.cds.ptch.batch.util.log.PTCHBatchLogger;
import com.cds.ptch.batch.util.log.PTCHBatchLoggerFactory;
import com.cds.ptch.batch.util.services.PTCHBatchUtil;



/**
 * 
 * @author ShanthiNT
 * This class formats soft descriptor information record 1 - PSM001 
 */
public class BatchSoftDescriptorPS2Record {
	
	   //Soft Merchant Information Record Parameters
		private String merchantPhoneNumber= null;
		private String recordIdentifier = "P";
		private String recordType = "SM";
		private String productRecordSeqNo = "002";
		
		private PTCHBatchLogger cdslog =  PTCHBatchLoggerFactory.getPTCHBatchLogger("BatchSoftDescriptorPS2Record");

		public BatchSoftDescriptorPS2Record(){}
		
		public BatchSoftDescriptorPS2Record(Map<String, String> descriptorRecordMap){
			
			   try{
				     
				   String phoneNumber = descriptorRecordMap.get("MerchantPhone").trim();	
				   //formatted to NNN-NNNNNNN
				   merchantPhoneNumber = phoneNumber.substring(0,4)+(phoneNumber.substring(4,(phoneNumber.length()))).replace("-", "");
			    
			   }catch (Exception ex) {
		        	ex.printStackTrace();
		        	cdslog.error(ex, ex.getCause(), "Exception while formatting BatchSoftDescriptorPS2Record Record in BatchSoftDescriptorPS2Record class constructor");            
		            throw new RuntimeException(ex.getMessage());
		    
		       }
		 }
		
		
		public String toString(){
			
			    StringBuffer sb = new StringBuffer();
			    
			    try{
			    	
			        PTCHBatchUtil ptechUtil = new PTCHBatchUtil();
				    
					sb.append(recordIdentifier);
					sb.append(recordType);
					sb.append(productRecordSeqNo);
					sb.append(ptechUtil.fillSpaces(" ", 38));					//Street
					sb.append(ptechUtil.fillSpaces(this.merchantPhoneNumber,21));//City - FDMS recommended to pass phone number for all card types
					sb.append(ptechUtil.fillSpaces("",21));                      //Region - 3, PostalCode - 15, CountryCode -3
					sb.append(ptechUtil.fillSpaces("",34));                      // Filler
					sb.append("\r\n");	
					
			    }catch (Exception ex) {
		        	ex.printStackTrace();
		        	cdslog.error(ex, ex.getCause(), "Exception while formatting MerchantDescriptor Record in MerchantDescriptorRecord class, toString()");            
			        throw new RuntimeException(ex.getMessage());
		    
		        }
			    return sb.toString();
		}
		

		
		

}
