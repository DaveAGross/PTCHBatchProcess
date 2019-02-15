package com.cds.ptch.batch.records;

import java.util.Map;

import com.cds.ptch.batch.util.log.PTCHBatchLogger;
import com.cds.ptch.batch.util.log.PTCHBatchLoggerFactory;
import com.cds.ptch.batch.util.services.PTCHBatchUtil;

/**
 * This class formats the address record for PTCH batch file
 * @author ShanthiNT * 
 * @version $Revision: 1.0 $
 */
public class BatchAddressRecord {
	
	private final static PTCHBatchLogger log =  PTCHBatchLoggerFactory.getPTCHBatchLogger("PTCHBatch");
	
	private String constant = null;
	private String addressType = null;	
	private String zip = null;	
	private String telephoneType = null;
	private String telephoneNo = null;
	private String countryCode = null;
	private String reserved = null;
	private String lineFeed = null;
	
	/**
	 * Constructor for BatchAddressRecord.
	 */
	public BatchAddressRecord(){}
	
	/**
	 * Builds Address Record for each detail record
	 * @param detailRecordMap Map<String,String>  Address information fields	
	 * @return Fixed length AddressRecord
	 */
	public String buildAddressRecord(Map<String, String> detailRecordMap ) {		
		
		   try{
			   
			    PTCHBatchUtil ptechUtil = new PTCHBatchUtil();
				
				constant = "A";
				
				addressType = "B";
						
				//String custName = detailRecordMap.get("CustName").toUpperCase();
				//customerName = ptechUtil.fillSpaces(custName, 30);
				String zipCode = detailRecordMap.get("Zip").toUpperCase();
				zip = ptechUtil.fillSpaces(zipCode, 30);
				
				telephoneType =" ";
				
				telephoneNo = ptechUtil.fillSpaces(" ", 14);
				
				countryCode = detailRecordMap.get("CountryCode").toUpperCase();
				
				reserved = ptechUtil.fillSpaces(" ", 71);
				
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
		   sb.append(addressType);
		   sb.append(zip);	
		   sb.append(telephoneType);
		   sb.append(telephoneNo);		
		   sb.append(countryCode);	
		   sb.append(reserved);
		   sb.append(lineFeed);				
		   return sb.toString();
	}
	
}