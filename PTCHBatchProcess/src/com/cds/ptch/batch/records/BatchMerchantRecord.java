package com.cds.ptch.batch.records;

import java.util.Map;

import com.cds.ptch.batch.util.log.PTCHBatchLogger;
import com.cds.ptch.batch.util.log.PTCHBatchLoggerFactory;
import com.cds.ptch.batch.util.services.PTCHBatchUtil;

/**
 * This class formats Merchant Descriptor Record for PTCH Batch process
 * @author ShanthiNT
 * @version $Revision: 1.0 $
 */
public class BatchMerchantRecord {
	
	//Merchant Descriptor Record Parameters
	private String merchantName= null;
	private String merchantCity= null;
	private PTCHBatchUtil ptchUtil = new PTCHBatchUtil();
	private final static PTCHBatchLogger cpslog =  PTCHBatchLoggerFactory.getPTCHBatchLogger("BatchMerchantRecord");
	
	/**
	 * Builds a blank Merchant Descriptor Record
	 * @param none
	 */
	public BatchMerchantRecord(){
	   try{
			   
			   this.merchantName = ptchUtil.fillSpaces(" ", 22);
			   this.merchantCity = ptchUtil.fillSpaces(" ", 13);
		    
		   }catch (Exception ex) {
	        	ex.printStackTrace();
	        	cpslog.error(ex, ex.getCause(), "Exception while formatting blank MerchantDescriptor Record in MerchantDescriptorRecord class constructor");            
	            throw new RuntimeException(ex.getMessage());
	    
	       }
	}
	
	/**
	 * Builds Merchant Descriptor Record
	 * @param merchantDescriptorRecordMap Paymentech merchant information
	 */
	public BatchMerchantRecord(Map<String, String> merchantDescriptorRecordMap){
		
		   try{
			   
			   String name =  merchantDescriptorRecordMap.get("MerchantName");
			   if(name.length() > 22){
				   this.merchantName = name.substring(0, 22);
			   }else{
			        this.merchantName = name;
			   }
			   
			   String phone = merchantDescriptorRecordMap.get("MerchantPhone");
			   if(phone.length() > 13){
				   this.merchantCity = phone.substring(0, 13);
			   }else{
			        this.merchantCity = phone;
			   }
		    
		   }catch (Exception ex) {
	        	ex.printStackTrace();
	        	cpslog.error(ex, ex.getCause(), "Exception while formatting MerchantDescriptor Record in MerchantDescriptorRecord class constructor");            
	            throw new RuntimeException(ex.getMessage());
	    
	       }
	 }
	
	
	/**
	 * @return String 
	 */
	public String toString(){
		
		    StringBuffer sb = new StringBuffer();
		    
		    try{
		    	
		    	PTCHBatchUtil ptechUtil = new PTCHBatchUtil();
			    
				sb.append("M");
				sb.append(ptechUtil.fillSpaces(this.merchantName, 22));
				sb.append(ptechUtil.fillSpaces("",4));
				sb.append(ptechUtil.fillSpaces(this.merchantCity,13));
				sb.append(ptechUtil.fillSpaces("",80));
				sb.append("\r\n");	
				
		    }catch (Exception ex) {
	        	ex.printStackTrace();
	        	cpslog.error(ex, ex.getCause(), "Exception while formatting MerchantDescriptor Record in MerchantDescriptorRecord class, toString()");            
		        throw new RuntimeException(ex.getMessage());
	    
	        }
		    return sb.toString();
	}
	

}
