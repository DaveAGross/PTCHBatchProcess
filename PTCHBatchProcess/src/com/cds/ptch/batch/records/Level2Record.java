package com.cds.ptch.batch.records;

import java.util.Map;

import com.cds.ptch.batch.util.log.PTCHBatchLogger;
import com.cds.ptch.batch.util.log.PTCHBatchLoggerFactory;
import com.cds.ptch.batch.util.services.PTCHBatchUtil;

/**
 * This class formats the Item Level 2 Record - not in  production yet (not certified with PTCH)
 * @author ShanthiNT 
 * @version $Revision: 1.0 $
 */
public class Level2Record {


	//Procurement Level2 Record Parameters
	private String recordIdentifier= "P";
	private String recordType= "PC";
	private String recordseqNo= "001";
	private String customerRefNo= null;
	private String salesTax= null;
	private String requesterName= null;
	private String destinationZip=null;
	private String exemptIndicator=null;
	
	private final static PTCHBatchLogger log =  PTCHBatchLoggerFactory.getPTCHBatchLogger("Level2Record");
	
	/**
	 * Constructor for Level2Record.
	 */
	public Level2Record(){}
	
	/**
	 * Constructor for Level2Record.Initializes leve2 record fields
	 * @param level2Map Map<String,String>
	 */
	public Level2Record(Map<String, String> level2Map){
		    
		   this.customerRefNo = level2Map.get("CustomerReferenceNumber");
		   this.salesTax = level2Map.get("SalesTax");
		   this.requesterName = level2Map.get("RequesterName");
		   this.destinationZip = level2Map.get("DestinationZip");
		   this.exemptIndicator = level2Map.get("ExemptIndicator");
		
	}
		
	/**
	 * Builds level 2 record	
	 * @return String fixed length level 2 record
	 */
	public String toString(){
		
		    StringBuffer sb = new StringBuffer();
		    
		    try{
		    
		    	PTCHBatchUtil ptechUtil = new PTCHBatchUtil();
			       
				sb.append(this.recordIdentifier);
				sb.append(this.recordType);
				sb.append(this.recordseqNo);
				sb.append(ptechUtil.fillSpaces(this.customerRefNo,17));
				sb.append(ptechUtil.fillZeros(this.salesTax,12));
				sb.append(ptechUtil.fillSpaces(this.requesterName,38));
				sb.append(ptechUtil.fillSpaces(this.destinationZip,15));
				sb.append(ptechUtil.fillSpaces(this.exemptIndicator,1));
				sb.append(ptechUtil.fillSpaces("",31)); // reserved
				sb.append("\r\n"); 
			
		    } catch (Exception ex) {
	        	ex.printStackTrace();
	        	log.error(ex, ex.getCause(), "Exception while formatting Header Record");            
	            throw new RuntimeException(ex.getMessage());
	    
	        }
		    
		    return sb.toString();
	}

}
