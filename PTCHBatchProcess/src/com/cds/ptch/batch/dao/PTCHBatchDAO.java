package com.cds.ptch.batch.dao;

import java.sql.*;
import java.util.*;

import com.cds.ptch.batch.response.ptchspec.Detail;
import com.cds.ptch.batch.response.ptchspec.DetailRecordGroup;
import com.cds.ptch.batch.response.ptchspec.DigitalPANRecord;
import com.cds.ptch.batch.response.ptchspec.ProductRecords;
import com.cds.ptch.batch.response.ptchspec.STCHTokenRecord;
import com.cds.ptch.batch.util.log.PTCHBatchLogger;
import com.cds.ptch.batch.util.log.PTCHBatchLoggerFactory;
import com.cds.ptch.batch.util.services.PropertiesGenerator;

/**
 * 
 * Database Access Object to connect and access database objects for PTCH batch process
 * @author ShanthiNT
 * @version $Revision: 1.0 $
 */
public class PTCHBatchDAO {	
  	
	private Properties properties = null;
	private String url;
	private String username;
	private String password;
	private String driver;
  	private final static PTCHBatchLogger cdslog =  PTCHBatchLoggerFactory.getPTCHBatchLogger("PTCHBatch");
	
	/**
	 * Initializes Database connection values from properties file
	 */
    public PTCHBatchDAO() {    	
    	
    	properties = PropertiesGenerator.getPropertyInstance("DB_Config.properties", "DBCONFIG");
    	url = properties.getProperty("url");
    	username = properties.getProperty("username");
    	password = properties.getProperty("password");
    	driver = properties.getProperty("driver");
    }

    /**
     * Makes Connection to the database    
     * @return DB Connection 
     * 
     */
    public Connection makeConnection() { 
    	
    	Connection con = null;

        try {
            if( (con == null) || (con.isClosed()) ) {
                Class.forName(driver).newInstance();
                con = DriverManager.getConnection(url, username, password);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            cdslog.error(ex, ex.getCause(), "DB Connection error");
        }
        return con;
    }
    
    /**
     * Gets file sequence number    
     * @return  fileNumber 
     */
    public String getFileNumber() {
		
           CallableStatement cstmt = null;
           ResultSet rs = null;
	 	   String fileNumber = null;
	 	   Connection con =null;
	 	   String processorName = "PTCH";
	 	   String processName ="BATCH";
	 	   try {
	 		  	con = makeConnection(); 
     	     	 	
		 		String sql = "{call cps_GetBatchNumber(?,?,?)}";
		 			
		 		cstmt = con.prepareCall(sql);
		 	    cstmt.setString(1, processorName);   
		 	    cstmt.setString(2, processName);   
		           	
		 	   cstmt.registerOutParameter(3, java.sql.Types.INTEGER);
	 	    	
		 	   cstmt.executeUpdate();
		 			
		 		/*while (rs.next()) {
		 			fileNumber = rs.getString(1);
		 		}*/
		 		fileNumber = String.valueOf(cstmt.getInt(3));
	 		
	 			
		 		cstmt.close();
		 			
	 		   } catch (SQLException ex) {
	 			    ex.printStackTrace();
	 			    cdslog.error(ex, ex.getCause(),"Exception when getting File Sequence Number for PTCH Batch file");
	 			    throw new RuntimeException(ex.getMessage());
		 				
	 		   } finally {
	 			   
	 			   try{
		                 if(con != null) {                	
		                     con.close();
		                 }
		                 if(rs != null){
		                     rs.close();
		                 }               

		             } catch (SQLException ex) {
		             	ex.printStackTrace();
		             }	 
		       } 		
		    return fileNumber;
		    
	}

    /**
     * Gets header record information from database. 
     * @param source For batch file the value for source should be 'BATCH'
     * @return resultMap contains header record information
     */
    public Map<String, String> getHeaderRecord(String source) {        

        Connection con = null;
        CallableStatement cstmt = null;
        ResultSet rs = null;

        Map<String, String>  resultMap = new HashMap<String, String>();      
 
        try { 
            String sql = "{call cps_PTCH_GetHeaderRecord(?)}";
            con = makeConnection();
            cstmt = con.prepareCall(sql);
            cstmt.setString(1, source);   
            
            rs = cstmt.executeQuery();

            if(rs.next()) {
                ResultSetMetaData mData = rs.getMetaData();
                int i = mData.getColumnCount();
                
                for(int j=1; j<=i; j++) {
                    String colName = mData.getColumnName(j);
                    resultMap.put(colName, rs.getString(j));
                }
            }  
            
            cstmt.close();            

        } catch (SQLException ex) {
        	ex.printStackTrace();
        	cdslog.error(ex, ex.getCause(), "SQLException while getting Header Record in PTCHBatchDAO");            
            throw new RuntimeException(ex.getMessage());
    
        }catch (Exception ex) {
        	ex.printStackTrace();
        	cdslog.error(ex, ex.getCause(), "Exception while getting Header Record in PTCHBatchDAO");            
            throw new RuntimeException(ex.getMessage());
    
        }finally {
            try{
                if(con != null) {                	
                    con.close();
                }
                if(rs != null){
                    rs.close();
                }               

            } catch (SQLException ex) {
            	ex.printStackTrace();
            }
        }
        
        return resultMap;

    }
	
    /**
     * Gets the list of of merchants those have PTCH batch transactions to be send. 
     * @param source  For batch files, the value for source should be 'BATCH'   
     * @return a List of Maps and each map contains single merchant information
     */
	public List <Map<String, String>> getMerchantList(String source) {
				
		Connection con = null;
		CallableStatement cstmt = null;
        ResultSet rs = null;
        List<Map<String,String>> resultList = new ArrayList<Map<String,String>>();       
        
        try {    
        	 
        	String sql = "{call cps_PTCH_GetMerchantList(?)}";
             
            con = makeConnection();
            cstmt = con.prepareCall(sql);
            cstmt.setString(1, source);   
            
            rs = cstmt.executeQuery();
            
            while(rs.next()) {
            
            	Map<String, String> resultMap = new HashMap<String, String>();
            	ResultSetMetaData mData = rs.getMetaData();
            	int i = mData.getColumnCount();
            	
            	for(int j=1; j<=i; j++) {
            		String colName = mData.getColumnName(j);
            		resultMap.put(colName, rs.getString(j));
            	}
            	resultList.add(resultMap);
            }
            
            cstmt.close();            

        } catch (SQLException ex) {
        	ex.printStackTrace();
        	cdslog.error(ex, ex.getCause(), "SQLException while getting merchant list in PTCHBatchDAO");
            throw new RuntimeException(ex.getMessage());
    
        }catch (Exception e) {
        	e.printStackTrace();
        	cdslog.error(e, e.getCause(), "Exception while getting merchant list in PTCHBatchDAO");
            throw new RuntimeException(e.getMessage());
    
        }finally {
            try{
                if(con != null) {                	
                    con.close();
                }
                if(rs != null){
                    rs.close();
                }               

            } catch (SQLException ex) {
            	ex.printStackTrace();
                //log.error(ex,YNEmail);
            }
        }
        
        return resultList;

	}
	  
    
	
	/**
	 * Saves the response in the database 
	 * @param respMap contains PTCH response fields
	 * @param con
	 * 
	 */
 	public void insertPTECHResponse(Map<String, String> respMap, Connection con){
	        
	         CallableStatement cstmt = null;
	         //Connection con = null;
	         String histid = respMap.get("HistID");
	         String requestSource = "BATCH";
	         try {
	            
	        	String MOP = respMap.get("MOP");
		        String actionCode = respMap.get("ActionCode");
		        
	            String sql = "{call [cps_PTCH_Batch_InsertResponse](?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	            
	            cstmt = con.prepareCall(sql);
	            
	            cstmt.setString(1, actionCode);
	            cstmt.setInt(2,Integer.parseInt(histid));		          
	            cstmt.setString(3, respMap.get("RecordType"));
	            cstmt.setString(4, respMap.get("ResponseReasonCode"));
	            cstmt.setString(5, respMap.get("ResponseDate"));
	            cstmt.setString(6, respMap.get("AuthVerificationCode"));
	            cstmt.setString(7, respMap.get("AVSResponseCode"));
	            cstmt.setString(8, respMap.get("CardSVResponseCode"));
	            cstmt.setString(9, respMap.get("AccountNumber"));
	            cstmt.setString(10, respMap.get("ExpirationDate"));
	            cstmt.setString(11, MOP);
	            cstmt.setString(12, respMap.get("RecurringAdviceCode"));
	            cstmt.setString(13, respMap.get("Amount"));
	            
	            // if Debit card transaction, insert the debit only response fields
	            if("DP".equals(MOP) || "NP".equals(MOP)|| "SP".equals(MOP) || "PP".equals(MOP) || "AP".equals(MOP)){
			    	 cstmt.setString(14, respMap.get("FormatIndicator"));
			         cstmt.setString(15, respMap.get("TotalAmount"));
			         cstmt.setString(16, respMap.get("SurchargeAmount"));
			         cstmt.setString(17, respMap.get("TraceNumber"));				        
	            	
			    }else{
			     	 cstmt.setString(14, null);
			         cstmt.setString(15, null);
			         cstmt.setString(16, null);
			         cstmt.setString(17, null);				        
	          
			    }
	            cstmt.setString(18, respMap.get("TransactionType"));
	            
	            if("BI".equals(actionCode)){
	             	 cstmt.setString(19, respMap.get("FormatIndicatorBI"));
			         cstmt.setString(20, respMap.get("CurrentBalance"));
			         cstmt.setString(21, respMap.get("CurrentBalanceSign"));
			         cstmt.setString(22, respMap.get("CurBalCurrencyCode"));				        
	         
	            }else{
			     	 cstmt.setString(19, null);
			         cstmt.setString(20, null);
			         cstmt.setString(21, null);
			         cstmt.setString(22, null);				        
	          
			    }
	            cstmt.setString(23, respMap.get("DepositFlag"));
	            cstmt.setString(24, respMap.get("YNProcessed"));	
	            
	            String PTCHToken = respMap.get("PTCHToken");
	            if(PTCHToken ==null || PTCHToken.isEmpty()){
	            	cstmt.setString(25, null);
	            	cstmt.setString(26, null);
	            }else{
	              cstmt.setString(25, respMap.get("PTCHToken"));
	              cstmt.setString(26, respMap.get("TokenSource"));
	            }
	            
	            cstmt.setString(27, requestSource);
	            
	            String CDPTRecord = respMap.get("FormatIndicator");
	            if(CDPTRecord ==null || CDPTRecord.isEmpty()){
	            	cstmt.setString(28, null);
	            	cstmt.setString(29, null);
	            	cstmt.setString(30, null);
	            }else{
	                cstmt.setString(26, respMap.get("TokenSource"));
	                cstmt.setString(28, respMap.get("TokenAssuranceLevel"));
	                cstmt.setString(29, respMap.get("AccountStatus"));
	                cstmt.setString(30, respMap.get("TokenRequestorID"));
	            }
	            	       
	            cstmt.execute();
	            
	            cstmt.close();
	            
	         } catch (SQLException ex) {
	        	   ex.printStackTrace(); 
	        	   cdslog.error(ex,ex.getCause(), histid);
	               throw new RuntimeException("error.system");
	            
	         }catch (Exception e){
	        	 	cdslog.error(e,e.getCause(), histid);
	                throw new RuntimeException("error.system");
	                
	         } finally {
	            try{
	              	            	
	            	if(cstmt != null) {
	            		cstmt.close();
	                }
	                
	            } catch (SQLException ex) {
	                cdslog.error(ex, ex.getCause(), histid);
	            }
	         }	        
	       
	}
 	
 	/**
	 * Saves the response in the database 
	 * @param respMap contains PTCH response fields
	 * @param con
	 * 
	 */
 	public void insertPTECHResponse(DetailRecordGroup detailRecords, Connection con, int YNProcessed){
	        
	         CallableStatement cstmt = null;
	         
	         Detail detailRecord=null;
	 		 STCHTokenRecord tokenRecord=null;
	 		 ProductRecords productRecords=null;
	 		 DigitalPANRecord cdptRecord=null;	 		
	 		
	 		 detailRecord=detailRecords.getDetail();
	 		
	 		 productRecords=detailRecords.getProductRecords();
	 		 if (productRecords != null) 
	 		 {
	 			if (productRecords.getTokenRecord() != null) {
	 				tokenRecord = productRecords.getTokenRecord();
	 			}
	 			if (productRecords.getDigitalPANRecord() != null) {
	 				cdptRecord = productRecords.getDigitalPANRecord();
	 			}	 		
	 		 }
	 		  
	         
	         String requestSource = "BATCH";
	         String histid = null;
	         try {
	            
	        	 String MOP = detailRecord.getMop();
	        	 
		         String actionCode = detailRecord.getActionCode();
		         
		         // PTCH batch table has seeded with 4 so the first digit of the reciID will have have 4 always.
		         if("AR".equals(actionCode) || "DP".equals(actionCode)){
						
						histid = "4"+detailRecord.getMerchantSpace();
						
				 }else{
					 
					   histid = detailRecord.getMerchantOrderID();
				 }
		         //histid = detailRecord.getMerchantOrderID();
		        
		        
	            String sql = "{call [cps_PTCH_Batch_InsertResponse](?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
	            
	            cstmt = con.prepareCall(sql);
	            
	            cstmt.setString(1, actionCode);
	            cstmt.setInt(2,Integer.parseInt(histid));		          
	            cstmt.setString(3, detailRecord.getConstant()); //recordType
	            cstmt.setString(4, detailRecord.getResponseCode());
	            cstmt.setString(5, detailRecord.getResponseDate());
	            cstmt.setString(6, detailRecord.getAuthVerificationCode());
	            cstmt.setString(7, detailRecord.getAvsResponseCode());
	            cstmt.setString(8, detailRecord.getCvvResponse());
	            cstmt.setString(9, detailRecord.getAccountNumber());
	            cstmt.setString(10, detailRecord.getExpirationDate());
	            cstmt.setString(11, MOP);
	            cstmt.setString(12, detailRecord.getPaymentAdviceCode());
	            cstmt.setString(13, detailRecord.getAmount());
	            
	            // if Debit card transaction, insert the debit only response fields
	            cstmt.setString(14, null);
			    cstmt.setString(15, null);
			    cstmt.setString(16, null);
			    cstmt.setString(17, null);				        
	          
	            cstmt.setString(18, detailRecord.getTransactionType());
	            
	            //BI format fields
	            cstmt.setString(19, null);
			    cstmt.setString(20, null);
			    cstmt.setString(21, null);
			    cstmt.setString(22, null);				        
	          
	            cstmt.setString(23, detailRecord.getDepositFlag());
	            cstmt.setString(24, Integer.toString(YNProcessed));	
	            
	            if (tokenRecord == null) {
	            	cstmt.setString(25, null);
	            	cstmt.setString(26, null);
	            }else{
	              cstmt.setString(25, tokenRecord.getTokenID());
	              cstmt.setString(26, "STCH");  // TokenSource
	            }
	            
	            cstmt.setString(27, requestSource);
	            
	            if(cdptRecord ==null){
	            	cstmt.setString(28, null);
	            	cstmt.setString(29, null);
	            	cstmt.setString(30, null);
	            }else{
	                cstmt.setString(26, "APPY");  //TokenSource
	                cstmt.setString(28, cdptRecord.getTokenAssuranceLevel());
	                cstmt.setString(29, cdptRecord.getAccountStatus());
	                cstmt.setString(30, cdptRecord.getTokenRequestorID());
	            }
	            	       
	            cstmt.execute();
	            
	            cstmt.close();
	            
	         } catch (SQLException ex) {
	        	   ex.printStackTrace(); 
	        	   cdslog.error(ex,ex.getCause(), histid);
	               throw new RuntimeException("error.system");
	            
	         }catch (Exception e){
	        	 	cdslog.error(e,e.getCause(), histid);
	                throw new RuntimeException("error.system");
	                
	         } finally {
	            try{
	              	            	
	            	if(cstmt != null) {
	            		cstmt.close();
	                }
	                
	            } catch (SQLException ex) {
	                cdslog.error(ex, ex.getCause(), histid);
	            }
	         }	        
	       
	}
 	
	 	
 	/**
 	 * Gets the detail records information as a list for given MerchantId and CoIntID 
 	 * @param merchantID - Paymentech specific division number
 	 * @param coIntID 	 - CPS internal company number
 	 * @return a list of maps with all the detail records for the give merchantID
 	 */
 	public List <Map<String, String>> getBatchDetailRecords(String merchantID, String coIntID,String maxRecID,String minRecID) {
		
		Connection con = null;
        CallableStatement cstmt = null;
        ResultSet rs = null;
        List<Map<String,String>> resultList = new ArrayList<Map<String,String>>();      
        
        try {    
        	
         	String sql = "{call cps_PTCH_GetBatchDetailRecordList(?,?,?,?)}";
            
            con = makeConnection();
            cstmt = con.prepareCall(sql);
            cstmt.setString(1, merchantID);
            cstmt.setInt(2, Integer.parseInt(coIntID));	
            cstmt.setLong(3, Long.parseLong(maxRecID));	
			cstmt.setLong(4, Long.parseLong(minRecID));	
            
            rs = cstmt.executeQuery();
            
            while(rs.next()) {
            
            	Map<String, String> resultMap = new HashMap<String, String>();
            	ResultSetMetaData mData = rs.getMetaData();
            	int i = mData.getColumnCount();
            	
            	for(int j=1; j<=i; j++) {
            		String colName = mData.getColumnName(j);
            		resultMap.put(colName, rs.getString(j));
            	}
            	resultList.add(resultMap);
            }
 
            
            cstmt.close();            

        } catch (SQLException ex) {
        	ex.printStackTrace();
        	cdslog.error(ex, ex.getCause(), "SQLException while getting DetailRecordList in ptechDAO");            
            throw new RuntimeException(ex.getMessage());
    
        }catch (Exception ex) {
        	ex.printStackTrace();
        	cdslog.error(ex, ex.getCause(), "Exception while getting DetailRecordList from database in ptechDAO");            
            throw new RuntimeException(ex.getMessage());
    
        }finally {
            try{
                if(con != null) {                	
                    con.close();
                }
                if(rs != null){
                    rs.close();
                }               

            } catch (SQLException ex) {
            	ex.printStackTrace();
                //log.error(ex,YNEmail);
            }
        }
        
        return resultList;

	}
 	
 	/**
 	 * Updates the process code in the table
 	 * when the file is created, when the response is processed
 	 * @param processMap - contains process code to be updated and criteria to use
 	 */
	public void updateProcessCode(Map<String, String> processMap) {

		CallableStatement cstmt = null;
		Connection con = null;
		String fileID = processMap.get("FileID");
		String processorName = "PTCH";
		try {

			con = makeConnection();

			String sql = "{call cps_PTCH_Batch_UpdateProcessCode(?,?,?,?,?,?,?,?,?)}";

			cstmt = con.prepareCall(sql);

			cstmt.setInt(1, Integer.parseInt(processMap.get("OrigProcessCode")));
			cstmt.setInt(2, Integer.parseInt(processMap.get("ProcessCodeToUpdate")));
			cstmt.setString(3, fileID);
			cstmt.setString(4, processMap.get("SystemID"));
			cstmt.setString(5, processorName);
			cstmt.setInt(6, Integer.parseInt(processMap.get("CoIntID")));
			cstmt.setString(7, processMap.get("MerchantID"));
			cstmt.setLong(8, Long.parseLong(processMap.get("MaxRecID")));
			cstmt.setLong(9, Long.parseLong(processMap.get("MinRecID")));

			cstmt.execute();

			cstmt.close();

		} catch (SQLException ex) {
			ex.printStackTrace();
			cdslog.error(ex, ex.getCause(), "- Updater CointID : " + fileID);
			throw new RuntimeException("error.system");

		} catch (Exception e) {
			cdslog.error(e, e.getCause(), "- Updater CointID : " + fileID);
			throw new RuntimeException("error.system");

		} finally {
			try {
				if (con != null) {
					con.close();
				}

			} catch (SQLException ex) {
				cdslog.error(ex, ex.getCause(), "- Updater CointID : " + fileID);
			}
		}
	}
	
	
	/**
	 * Inserts a new record into tblFileProcessingLog for Audit
	 * @param processTask 	 Type of action performed for example PTCH_BATCH_FILE_CREATION
	 * @param fileName   	 Name of the file that is being processed
	 * @param fileId	 	 Unique Identifier for the file	
	 * @param recordCount 	 Record count in the file
	 * @param invalidRecords Invalid records if there is any in the response file
	 * @param processCode    Code that indicates whether the process is successful or failure
	 * @param status		 Verbiage of Status - Success, Failure, Pending, Exception
	 * @param coIntId        Internal CPS Company Number
	 * @param amount         Total amount in the file
	 * 
	 */
	public void insertFileProcessingLog(String processTask, String fileName,String fileId, int recordCount, int invalidRecords,
									int processCode, String status, int coIntId, String amount) {
		CallableStatement callstmt = null;
		Connection con = null;

		String processName = "CPS_BATCH";
		//String amount = null;
		String systemID = "CPS";
		String processorName = "PTCH";

		try {

			String sql = "{call [cps_InsertFileProcessingLog](?,?,?,?,?,?,?,?,?,?,?,?)}";

			con = makeConnection();

			callstmt = con.prepareCall(sql);

			callstmt.setString(1, fileId);
			callstmt.setString(2, fileName);
			callstmt.setInt(3, recordCount);
			callstmt.setInt(4, invalidRecords);
			callstmt.setString(5, amount);
			callstmt.setString(6, processName);
			callstmt.setString(7, processTask);
			callstmt.setInt(8, processCode);

			// SET NULL to CoIntID if it is not set
			if (coIntId == 0) {
				callstmt.setNull(9, java.sql.Types.INTEGER);

			} else {
				callstmt.setInt(9, coIntId);
			}
			callstmt.setString(10, systemID);
			callstmt.setString(11, status);
			callstmt.setString(12, processorName);

			callstmt.execute();

			callstmt.close();

		} catch (SQLException ex) {
			ex.printStackTrace();
			cdslog.error(ex, ex.getCause(), processTask);

		} finally {
			try {
				if (con != null) {
					con.close();
				}

			} catch (SQLException ex) {
				ex.printStackTrace();
				// log.error(ex,YNEmail);
			}
		}

	}
	
	  /**
     * 
     * @return
     * 
     * Gets processor information
     */
    public Map<String, String> getProcessorProperties()  {	      
	       
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;       
	      Map<String, String> result = new HashMap<String, String>();
	      Connection con = null;
	      String processorName="PTCH";
	    
	      try {
	        	
	        	String sql = "SELECT * FROM fnGet_ProcessorInfo (?)";
	            con = makeConnection();
	          
	            pstmt = con.prepareCall(sql);
	            pstmt.setString(1, processorName);	              
	            
	            rs = pstmt.executeQuery();
	            
	            if(rs.next()) {
	                
	                result = new HashMap<String, String>();
	                ResultSetMetaData mData = rs.getMetaData();
	                int i = mData.getColumnCount();
	                
	                for(int j=1; j<=i; j++) {
	                    
	                    String colName = mData.getColumnName(j);
	                    result.put(colName, rs.getString(j));
	                }	                
	            }	
	            
	            rs.close();
	            pstmt.close();
	            
	        } catch(SQLException ex) {
	            cdslog.error(ex, ex.getCause(), "SQLException in getProcessorProperties");
	            throw new RuntimeException(ex.getMessage());
	            
	        }catch (Exception e){
	            cdslog.error(e, e.getCause(), "Exception in getProcessorProperties");	            
	            throw new RuntimeException(e.getMessage());
	            
	        }finally {
	            try{
	                if(con != null) {
	                    con.close();
	                }
	                
	            } catch (SQLException ex) {
	            	  cdslog.error(ex, ex.getCause(), "Exception in getProcessorProperties");	            
	  	            throw new RuntimeException(ex.getMessage());
	            }
	        }	        
	        
	        return result;
	    }	
    
	
		
}



