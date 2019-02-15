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
public class BatchSoftDescriptorPS1Record {
	
	   //Soft Merchant Information Record Parameters
		private String merchantDescription= null;
		private String merchantLocationID= null;
		private String merchantPhoneNumber= null;
		private String recordIdentifier = "P";
		private String recordType = "SM";
		private String productRecordSeqNo = "001";
		
		private PTCHBatchLogger cdslog =  PTCHBatchLoggerFactory.getPTCHBatchLogger("BatchSoftDescriptorPS1Record");

		public BatchSoftDescriptorPS1Record(){}
		
		public BatchSoftDescriptorPS1Record(Map<String, String> descriptorRecordMap){
			
			   try{
				   
				   String name =  descriptorRecordMap.get("MerchantName");
				   
				   if(name != null && name.length() > 38){
					   this.merchantDescription = name.substring(0, 38);
				   }else{
				        this.merchantDescription = name;
				   }
				   
				   merchantLocationID =  descriptorRecordMap.get("MerchantLocationID");
				   
				   merchantPhoneNumber = descriptorRecordMap.get("MerchantPhone");				   
			    
			   }catch (Exception ex) {
		        	ex.printStackTrace();
		        	cdslog.error(ex, ex.getCause(), "Exception while formatting BatchSoftDescriptorPS1Record Record in BatchSoftDescriptorPS1Record class constructor");            
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
					sb.append(ptechUtil.fillSpaces(this.merchantDescription, 38));
					sb.append(ptechUtil.fillSpaces(this.merchantLocationID,15));
					sb.append(ptechUtil.fillSpaces(this.merchantPhoneNumber,40));
					sb.append(ptechUtil.fillSpaces("",21));
					sb.append("\r\n");	
					
			    }catch (Exception ex) {
		        	ex.printStackTrace();
		        	cdslog.error(ex, ex.getCause(), "Exception while formatting MerchantDescriptor Record in MerchantDescriptorRecord class, toString()");            
			        throw new RuntimeException(ex.getMessage());
		    
		        }
			    return sb.toString();
		}
		

		
		

}
