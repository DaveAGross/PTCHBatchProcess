package com.cds.ptch.batch.records;

import java.util.Map;

import com.cds.ptch.batch.util.log.PTCHBatchLogger;
import com.cds.ptch.batch.util.log.PTCHBatchLoggerFactory;
import com.cds.ptch.batch.util.services.PTCHBatchUtil;

/**
 * This class formats file header record for PTCH batch file
 * @author ShanthiNT * 
 * @version $Revision: 1.0 $
 */
public class FileHeaderRecord {
	
	//File Header Record Parameters
	private String presenterID= null;
	private String pidPassword= null;
	private String submitterID= null;
	private String sidPassword=null;
	private String creationDate=null;
	private String ptchConstant=null;
	private String fileID= null;	
	
	private final static PTCHBatchLogger cpslog =  PTCHBatchLoggerFactory.getPTCHBatchLogger("FileHeaderRecord");
	
	/**
	 * Constructor for FileHeaderRecord.
	 */
	public FileHeaderRecord(){}
	
	/**
	 * Constructor for FileHeaderRecord - Initializes file header fields
	 * @param fileHeaderMap Map<String,String>
	 */
	public FileHeaderRecord(Map<String, String> fileHeaderMap){
		    
		   this.presenterID = fileHeaderMap.get("PresenterID");
		   this.pidPassword = fileHeaderMap.get("PIDPassword");
		   this.submitterID = fileHeaderMap.get("SubmitterID");
		   this.sidPassword = fileHeaderMap.get("SubmitterPassword");
		   this.creationDate = fileHeaderMap.get("CreationDate");
		   this.ptchConstant = fileHeaderMap.get("PTECHConstant");
		   this.fileID = fileHeaderMap.get("FileID");
	}
	
	
	/**
	 * Builds File header record	
	 * @return String fixed length File Header Record
	 */
	public String toString(){
		
		    StringBuffer sb = new StringBuffer();
		    
		    try{
		    
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
				sb.append("START");
				sb.append(ptechUtil.fillSpaces("",2));
				sb.append(this.creationDate);
				sb.append(ptechUtil.fillSpaces("",1));
				sb.append(this.ptchConstant);          // value is 3.0.0
				sb.append(ptechUtil.fillSpaces("",1));
				sb.append(ptechUtil.fillSpaces("",11)); // submission number will be returned in the reply
				sb.append(ptechUtil.fillSpaces("",41)); // reserved
				sb.append(this.fileID);                // unique file identified
				sb.append("\r\n"); 
			
		    } catch (Exception ex) {
	        	ex.printStackTrace();
	        	cpslog.error(ex, ex.getCause(), "Exception while formatting Header Record in Header Record class");            
	            throw new RuntimeException(ex.getMessage());
	    
	        }
		    
		    return sb.toString();
	}

}
