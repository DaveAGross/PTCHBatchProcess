package com.cds.ptch.batch;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.List;
import com.cds.ptch.batch.records.FileHeaderRecord;
import com.cds.ptch.batch.records.FileTrailerRecord;
import com.cds.ptch.batch.util.log.PTCHBatchLogger;
import com.cds.ptch.batch.util.log.PTCHBatchLoggerFactory;
import com.cds.ptch.batch.dao.PTCHBatchDAO;
import com.cds.ptch.batch.util.services.PTCHBatchFileSplitter;
import com.cds.ptch.batch.util.services.PTCHBatchUtil;

/**
 * This class creates the Batch request file for PTCH
 * 
 * @author ShanthiNT
 * @version $Revision: 1.0 $
 */
public class PTCHBatchFileCreator {

	private PTCHBatchDAO ptchBatchDAO = null;
	

	private final static PTCHBatchLogger log = PTCHBatchLoggerFactory.getPTCHBatchLogger("PTCHBatch");

	/**
	 * Constructor for PTCHBatchFileCreator.
	 */
	public PTCHBatchFileCreator() {
		ptchBatchDAO = new PTCHBatchDAO();
	}

	/**
	 * Creates PTCH Batch file
	 * 
	 */
	public void buildBatchFile(int maxFileSize) {

		try {

			PTCHBatchUtil util = new PTCHBatchUtil();
			boolean processStatus = true;

			Map<String, String> processorInfoMap = ptchBatchDAO.getProcessorProperties();

			log.info("Getting File Header Record..");
			//Get File HeaderRecord
			Map<String, String> headerRecordMap = getHeaderRecord();
			//General File Name
			String fileName ="t"+headerRecordMap.get("PresenterID")+".INPROGRESS";	

			log.info("Formatting File Header Record.. ");
			//Header record
			String fileHeaderRecord = this.getFileHeaderRecord(headerRecordMap);
			String fileID = headerRecordMap.get("FileID");

			log.info("Getting merchant list..");
			List<Map<String, String>> merchantList = this.getMerchantList();

			int merchantListSize = merchantList.size();
			System.out.println("No of batches : " + merchantListSize);

			log.info("Formatting File Trailer Record..");
			//Get trailer record as String
			String trailerRecord = this.getTrailerRecord(headerRecordMap);

			if (merchantList == null || merchantList.isEmpty()) {
				processStatus = false;
			}
			if (processStatus) {
				//int maxFileSize = 30;
				PTCHBatchFileSplitter splitter = new PTCHBatchFileSplitter(maxFileSize);

				List<List<Map<String, String>>> files = splitter.splitter(merchantList);

				if (files == null || files.isEmpty()) {
					processStatus = false;
				} else {

					ExecutorService executor = Executors.newFixedThreadPool(files.size());
                    int reference=0;
					for (List<Map<String, String>> merchants : files) {
						Runnable worker = new PTCHBatchWorker(ptchBatchDAO, util, fileHeaderRecord, trailerRecord,
								processorInfoMap, merchants, fileID,fileName,reference);
						reference++;
						executor.execute(worker);
					}
					executor.shutdown();
					while (!executor.isTerminated()) {
						// Wait till All Threads are executed
					}
				}
			}

		} catch (Exception ex) {

			throw new RuntimeException(ex.getMessage());

		}

	}

	/**
	 * Gets File Header Record fields from database
	 * 
	 * @return Map<String,String>
	 */
	private Map<String, String> getHeaderRecord() {
		return ptchBatchDAO.getHeaderRecord("BATCH");
	}

	/**
	 * Gets file header record
	 * 
	 * @param fileHeaderMap
	 *            Map<String,String>
	 * @return String
	 */
	private String getFileHeaderRecord(Map<String, String> fileHeaderMap) {
		return new FileHeaderRecord(fileHeaderMap).toString();
	}

	/**
	 * Get merchants list from database
	 * 
	 * @return List<Map<String,String>> * @throws RuntimeException
	 */
	private List<Map<String, String>> getMerchantList() throws RuntimeException {
		return ptchBatchDAO.getMerchantList("BATCH");
	}

	/**
	 * @return String
	 */
	private String getTrailerRecord(Map<String, String> fileHeaderMap) {
		return new FileTrailerRecord(fileHeaderMap).toString();
	}

	
	  public static void main(String[] args ) {
	  
	  PTCHBatchFileCreator settle = new PTCHBatchFileCreator();
	  
	  try{
	  
	  settle.buildBatchFile(20);
	  
	  }catch (Exception ex) { ex.printStackTrace(); // exceptions are already
	  //logged in System.exit(3); } System.exit(0);
	  
	  }
	  } 

}
