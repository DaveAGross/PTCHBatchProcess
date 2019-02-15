package com.cds.ptch.batch.records;

import java.util.Map;

import com.cds.ptch.batch.util.log.PTCHBatchLogger;
import com.cds.ptch.batch.util.log.PTCHBatchLoggerFactory;
import com.cds.ptch.batch.util.services.PTCHBatchUtil;

/**
 * This class formats batch total record for PTCH Batch file
 * @author ShanthiNT * 
 * @version $Revision: 1.0 $
 */
public class BatchTotalsRecord {
	
	private final static PTCHBatchLogger log =  PTCHBatchLoggerFactory.getPTCHBatchLogger("BatchTotalsRecord");
	
	//Batch Trailer Record Parameters
	private static String constant = "B RECS=";
	private  String recordCount = null;
	private static String constant1 = "ORDS=";
	private  String batchOrderCount = null;
	private static String constant2 = "$TOT=";
	private  String batchTotalAmount = null;
	private static String constant3 = "$SALE=";
	private  String saleAmount = null;	
	private static String constant4 = "$REFUND=";
	private  String refundAmount = null;
	private  String reserved = null;
	
	/**
	 * Constructor for BatchTotalsRecord.
	 */
	public BatchTotalsRecord(){}
	
	/**
	 * Builds batch totals record.
	 * @param batchTotalRecordMap Map<String,String>	
	 * @return String fixed length batch totals record
	 */
	public String getBatchTotalsRecord(Map<String, String> batchTotalRecordMap){
		
		   try{
			   
			    PTCHBatchUtil ptechUtil = new PTCHBatchUtil();
			 
			    //Number of records in the batch.
			    String recordCnt = batchTotalRecordMap.get("RecordCount");
			    recordCount = ptechUtil.fillZeros(recordCnt, 9);
			
			    //Number of transactions in the batch.
			    String orderCnt = batchTotalRecordMap.get("OrderCount");
			    batchOrderCount = ptechUtil.fillZeros(orderCnt, 9);
			
			    //Net dollar amount of the batch.
			    //(Total Debit amount and total credit amount).
			    String batchTotalAmt = batchTotalRecordMap.get("TotalAmount");		    
			    String batchTotalAmtTemp = ptechUtil.formatAmount(batchTotalAmt);
			    batchTotalAmount = ptechUtil.fillZeros(batchTotalAmtTemp, 14);
			    
			    //Sum of sale dollars in the batch
			    String saleAmt = batchTotalRecordMap.get("SaleAmount");
			    String saleAmtTemp = ptechUtil.formatAmount(saleAmt);
			    saleAmount = ptechUtil.fillZeros(saleAmtTemp, 14);
				
			    // Sum of refund dollars 
			    String refundAmt = batchTotalRecordMap.get("RefundAmount");
			    String refundAmtTemp = ptechUtil.formatAmount(refundAmt);			 
			    refundAmount = ptechUtil.fillZeros(refundAmtTemp, 14);
			     
			    reserved = ptechUtil.fillSpaces("",25);
			    
		   }catch (Exception ex) {
	        	ex.printStackTrace();
	        	log.error(ex, ex.getCause(), "Exception while formatting BatchTotals Record");            
	            throw new RuntimeException(ex.getMessage());
	       }
		   return this.toString();
		
	}
	
	/**
	 * @return String 
	 */
	public String toString(){
		
		    StringBuffer sb = new StringBuffer();
		    
			sb.append(constant);
			sb.append(recordCount);
			sb.append(" ");
			sb.append(constant1);
			sb.append(batchOrderCount);
			sb.append(" ");			
			sb.append(constant2);			
			sb.append(batchTotalAmount);
			sb.append(" ");			
			sb.append(constant3);			
			sb.append(saleAmount);
			sb.append(" ");	
			sb.append(constant4);			
			sb.append(refundAmount);
			sb.append(reserved);
			sb.append("\r\n");
			
		    return sb.toString();
	}
	

}
