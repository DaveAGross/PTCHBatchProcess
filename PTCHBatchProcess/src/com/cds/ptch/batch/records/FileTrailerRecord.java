package com.cds.ptch.batch.records;

import java.util.Map;

import com.cds.ptch.batch.util.log.PTCHBatchLogger;
import com.cds.ptch.batch.util.log.PTCHBatchLoggerFactory;
import com.cds.ptch.batch.util.services.PTCHBatchUtil;

/**
 * This class formats file trailer record for PTCH batch file
 * @author ShanthiNT
 * @version $Revision: 1.0 $
 */
public class FileTrailerRecord {
	
	private final static PTCHBatchLogger log =  PTCHBatchLoggerFactory.getPTCHBatchLogger("FileTrailerRecord");
	//File Trailer Record Parameters
	private String presenterID= null;
	private String pidPassword= null;
	private String submitterID= null;
	private String sidPassword=null;
	private String creationDate=null;

	/**
	 * Constructor for FileTrailerRecord.
	 */
	public FileTrailerRecord(){}
	
	/**
	 * Constructor for FileTrailerRecord.Initializes Trailer record fields same as Header fields
	 * @param headerMap Map<String,String>
	 */
	public FileTrailerRecord(Map<String, String> headerMap){
		    
		   this.presenterID = headerMap.get("PresenterID");
		   this.pidPassword = headerMap.get("PIDPassword");
		   this.submitterID = headerMap.get("SubmitterID");
		   this.sidPassword = headerMap.get("SubmitterPassword");
		   this.creationDate = headerMap.get("CreationDate");
		  
	}
	
	/**
	 * Builds the file trailer record	
	 * @return String fixed length file trailer record
	 */
	public String toString(){
		
		    StringBuffer sb = new StringBuffer();
		    try {
		    	
		    	PTCHBatchUtil ptechUtil = new PTCHBatchUtil();
			    
				sb.append("PID=");
				sb.append(this.presenterID);
				sb.append(ptechUtil.fillSpaces("",1));
				sb.append(this.pidPassword);
				sb.append(ptechUtil.fillSpaces("",1));
				sb.append("SID=");
				sb.append(this.submitterID);
				sb.append(ptechUtil.fillSpaces("",1));
				sb.append(this.sidPassword);
				sb.append(ptechUtil.fillSpaces("",1));
				sb.append("END");
				sb.append(ptechUtil.fillSpaces("",2));
				sb.append(this.creationDate);
				sb.append(ptechUtil.fillSpaces("",69)); // reserverd
				sb.append("\r\n"); 
				//sb.append("EOFEOFEOF");
				
		    }catch (Exception ex) {
	        	ex.printStackTrace();
	        	log.error(ex, ex.getCause(), "Exception while formatting File Trailer  Record");            
	            throw new RuntimeException(ex.getMessage());
	    
	        }
		    return sb.toString();
	}
	
}