package com.cds.ptch.batch.records;

import java.util.Map;

import com.cds.ptch.batch.util.log.PTCHBatchLogger;
import com.cds.ptch.batch.util.log.PTCHBatchLoggerFactory;
import com.cds.ptch.batch.util.services.PTCHBatchUtil;

/**
 * This class formats file totals record for PTCH batch process
 * @author ShanthiNT
 * @version $Revision: 1.0 $
 */
public class FileTotalsRecord {
	
	private final static PTCHBatchLogger log =  PTCHBatchLoggerFactory.getPTCHBatchLogger("FileTotalsRecord");

	//File Totals Record Parameters
	private static String constant = "T RECS=";
	private  String fileRecordCount = null;
	private static String constant1 = "ORDS=";
	private  String fileOrderCount = null;
	private static String constant2 = "$TOT=";
	private  String fileTotalAmount = null;
	private static String constant3 = "$SALE=";
	private  String fileSaleAmount = null;	
	private static String constant4 = "$REFUND=";
	private  String fileRefundAmount = null;
	private  String reserved = null;
	
	/**
	 * Constructor for FileTotalsRecord.
	 */
	public FileTotalsRecord(){}
	
	
	/**
	 * Builds file totals record
	 * @param totalsRecordMap Map<String,String>	
	 * @return String fixed length file totals record
	 */
	public String getTotalsRecord(Map<String, String> totalsRecordMap){
		
		   try{
			   
			   PTCHBatchUtil ptechUtil = new PTCHBatchUtil();
			 
			    //Number of records in the batch.
			    String recordCnt = totalsRecordMap.get("FileRecordCount");
			    fileRecordCount = ptechUtil.fillZeros(recordCnt, 9);
			
			    //Number of transactions in the batch.
			    String orderCnt = totalsRecordMap.get("FileOrderCount");
			    fileOrderCount = ptechUtil.fillZeros(orderCnt, 9);
			
			    //Net dollar amount of the batch.
			    //(Total Debit amount and total credit amount).
			    String fileTotalAmt = totalsRecordMap.get("FileTotalAmount");		    
			    String fileTotalAmtTemp = ptechUtil.formatAmount(fileTotalAmt);
			    fileTotalAmount = ptechUtil.fillZeros(fileTotalAmtTemp, 14);
			    
			    //Sum of sale dollars in the batch
			    String saleAmt = totalsRecordMap.get("FileSaleAmount");
			    String saleAmtTemp = ptechUtil.formatAmount(saleAmt);
			    fileSaleAmount = ptechUtil.fillZeros(saleAmtTemp, 14);
				
			    // Sum of refund dollars 
			    String refundAmt = totalsRecordMap.get("FileRefundAmount");
			    String refundAmtTemp = ptechUtil.formatAmount(refundAmt);			 
			    fileRefundAmount = ptechUtil.fillZeros(refundAmtTemp, 14);
			     
			    reserved = ptechUtil.fillSpaces("",25);
		    
		   }catch (Exception ex) {
	        	ex.printStackTrace();
	        	log.error(ex, ex.getCause(), "Exception while formatting Totals Record");            
	            throw new RuntimeException(ex.getMessage());
	    
	       }
		   return this.toString();
		
	}
	
	/**
	 *@return String 
	 */
	public String toString(){
		
		    StringBuffer sb = new StringBuffer();
		    
			sb.append(constant);
			sb.append(fileRecordCount);
			sb.append(" ");
			sb.append(constant1);
			sb.append(fileOrderCount);
			sb.append(" ");			
			sb.append(constant2);			
			sb.append(fileTotalAmount);
			sb.append(" ");			
			sb.append(constant3);			
			sb.append(fileSaleAmount);
			sb.append(" ");	
			sb.append(constant4);			
			sb.append(fileRefundAmount);
			sb.append(reserved);
			sb.append("\r\n");
			
		    return sb.toString();
	}


}
