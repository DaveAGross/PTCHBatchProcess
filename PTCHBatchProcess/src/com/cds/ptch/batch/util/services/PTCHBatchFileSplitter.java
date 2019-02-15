package com.cds.ptch.batch.util.services;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author rmujyambere
 * This class is used to generate PTCH files<br/>
 * 
 */
public class PTCHBatchFileSplitter {
	
	

	public PTCHBatchFileSplitter(int maxFileSize) {
		super();
		this.maxFileSize = maxFileSize;
	}

	int maxFileSize;
	Calendar cal = Calendar.getInstance();
	
	/**
	 * This method will generate a list of files to create depending on the size
	 * of records list<br/>
	 * The method logic is explained bellow :<br/>
	 * 
	 * 1# When the total records for all merchants is less than the maximum
	 * records allowed .All records will be in the same file.<br/>
	 * 
	 * 2# The list of merchants is sorted by the number of records they
	 * have<br/>
	 * 3# Loop through each merchant <br/>
	 * 
	 * 4# If merchant records size is greater than maximum allowed,We splitter
	 * records into different files<br/>
	 * 
	 * 5# If merchant records size is less than the maximum allowed , We add
	 * next merchant to the file till maximum allowed is reached<br/>
	 * -
	 * The method will return a list of files:
	 *      On each file :A list of merchants with file name ,
	 *      On each merchant :Merchant information with the range of records allowed for the merchant
	 *      
	 * @param merchantList
	 * @return
	 */     
	public List<List<Map<String, String>>> splitter(List<Map<String, String>> merchantList) {

		List<List<Map<String, String>>> files = new ArrayList<List<Map<String, String>>>();
		try {
			int totalRecords = Integer.parseInt(merchantList.get(0).get("AllRecordCount"));
			int fileRecords = 0;
			int numberOfFiles = 0;
			int MAX_VALUE = maxFileSize;
			
			/** 1# When the total records for all merchants is less than the maximum records allowed */
			if (totalRecords <= MAX_VALUE) {
				String fileName=this.currentDateTime() + ".AUPAYDQ1.TXT";			
				merchantList.get(0).put("PTCHFileName", fileName);
				files.add(merchantList);
			}
			/** 2# Sort the list of merchants order by the number of records they have.Highest to lowest */
			else {
				
				Collections.sort(merchantList, mapComparator);
				Collections.reverse(merchantList);

				int batchSize = merchantList.size();
				int startPoint = 0;
				boolean startPointMarker = true;
				boolean inQueue = false;
                /** 3# Loop through each merchant */
				for (int i = 0; i < batchSize; i++) {
					Map<String, String> merchant = merchantList.get(i);
					double records = Integer.parseInt(merchantList.get(i).get("BatchRecordCount"));

					/** 4# If merchant records size is greater than maximum allowed	 */
					if (records > MAX_VALUE) {

						double doubleFileNumbers = records / MAX_VALUE;
						BigDecimal value = new BigDecimal(doubleFileNumbers);
						value = value.setScale(0, RoundingMode.UP);
						numberOfFiles = value.toBigInteger().intValue();
						int eachRecords = (int) (records / numberOfFiles);
						int minRecID = Integer.parseInt(merchant.get("MinRecID"));
						int maxRecID = Integer.parseInt(merchant.get("MaxRecID"));

						for (int j = 0; j < numberOfFiles; j++) {
							List<Map<String, String>> fileMerchant = new ArrayList<Map<String, String>>();
							Map<String, String> merchantTemp=new HashMap<String,String>();
							merchantTemp.putAll(merchant);
							int min = minRecID;
							int max = min + eachRecords - 1;
							merchantTemp.put("MinRecID", Integer.toString(min));
							int m=j+1;//For last File
							if (m >= numberOfFiles) {
								merchantTemp.put("MaxRecID", Integer.toString(maxRecID));
								fileMerchant.add(merchantTemp);
								//String fileName=this.currentDateTime() + ".AUPAYDQ1.TXT";
								//fileMerchant.get(0).put("PTCHFileName", fileName);
								files.add(fileMerchant);
								break;
							}
							merchantTemp.put("MaxRecID", Integer.toString(max));
							fileMerchant.add(merchantTemp);	
							//String fileName=this.currentDateTime() + ".AUPAYDQ1.TXT";
							//fileMerchant.get(0).put("PTCHFileName", fileName);
							files.add(fileMerchant);
							minRecID = max + 1;
						}
						//Remove processed records from total records
						totalRecords = (int) (totalRecords - records);
						
						// Check if the total records are still high than records allowed
						if (totalRecords > 0 && totalRecords <= MAX_VALUE) {
							List<Map<String, String>> fileMerchants = new ArrayList<Map<String, String>>();
							if (i + 1 < batchSize) {
								//Add the rest of merchants in one file
								for (int n = ++i; n < batchSize; n++) {
									fileMerchants.add(merchantList.get(n));
								}
							}
							//String fileName=this.currentDateTime() + ".AUPAYDQ1.TXT";
							//fileMerchants.get(0).put("PTCHFileName", fileName);
							files.add(fileMerchants);
							break;
						}
					}
					/** 5# If merchant records size is less than the maximum allowed */
					else {
						// Add next merchant to file
						if (i + 1 < batchSize) {
							if (startPointMarker) {
								startPoint = i;
								//Mark the starting point of adding  merchants to list
								startPointMarker = false;
							}
							int nextMerchantRecords = Integer.parseInt(merchantList.get(i + 1).get("BatchRecordCount"));
							fileRecords += (int) (records + nextMerchantRecords);
							if (fileRecords <= MAX_VALUE) {
								inQueue = true;
								fileRecords-=nextMerchantRecords;
								continue;// Continue adding

							} else {
								List<Map<String, String>> fileMerchants = new ArrayList<Map<String, String>>();
								for (int f = startPoint; f <= i; f++) {
									fileMerchants.add(merchantList.get(f));
								}
								//String fileName=this.currentDateTime() + ".AUPAYDQ1.TXT";
								//fileMerchants.get(0).put("PTCHFileName", fileName);
								files.add(fileMerchants);
								startPointMarker = true;// Clean the Queue
								fileRecords = 0;
								inQueue = false;
							}
						}
						//For last Merchant and Queued Merchants
						else if (inQueue) {
							List<Map<String, String>> fileMerchants = new ArrayList<Map<String, String>>();
							for (int f = startPoint; f <= i; f++) {
								fileMerchants.add(merchantList.get(f));
							}
							//String fileName=this.currentDateTime() + ".AUPAYDQ1.TXT";
							//fileMerchants.get(0).put("PTCHFileName", fileName);
							files.add(fileMerchants);

						}
						//For last Merchant
						else {
							List<Map<String, String>> fileMerchants = new ArrayList<Map<String, String>>();
							fileMerchants.add(merchantList.get(i));
							//String fileName=this.currentDateTime() + ".AUPAYDQ1.TXT";
							//fileMerchants.get(0).put("PTCHFileName", fileName);
							files.add(fileMerchants);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return files;

	}

	private static Comparator<Map<String, String>> mapComparator = new Comparator<Map<String, String>>() {
		public int compare(Map<String, String> map1, Map<String, String> map2) {
			return Integer.parseInt(map1.get("BatchRecordCount")) - Integer.parseInt(map2.get("BatchRecordCount"));
		}
	};
	
	public  String currentDateTime() {

		String DATE_FORMAT_NOW = "MMddyy.hhmmss";
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		cal.add(Calendar.SECOND,1);
		return sdf.format(cal.getTime());	
	}
	

}
