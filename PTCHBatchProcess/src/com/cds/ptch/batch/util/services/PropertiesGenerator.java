package com.cds.ptch.batch.util.services;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.cds.ptch.batch.util.log.PTCHBatchLogger;
import com.cds.ptch.batch.util.log.PTCHBatchLoggerFactory;

/**
 * This class is used to retrieve the property file dynamically
 * @author ShanthiNT
 * @version $Revision: 1.0 $
 */
public class PropertiesGenerator {
	
		private final static PTCHBatchLogger log =  PTCHBatchLoggerFactory.getPTCHBatchLogger("PTCHBatch");

		/**
		 * Searches property file and retrieve the necessary properties
		 * @param fileName String		
		 * @return Properties 
		 */
		public static Properties getPropertyInstance(String fileName, String propertyType) {
			Properties properties = new Properties();
			//"PTCHBatchConfig.properties"
			try {
				/** load a properties file from class path */
				
				String filePath = new PTCHBatchUtil().getPropertyFilePath(fileName, propertyType);
				//FileSearch fileSearch = new FileSearch();
				//String filename = fileSearch.searchDirectory(fileName, propertyType);				

				FileInputStream path = new FileInputStream(filePath);
				
				properties.load(path);
		
				return properties;

			} catch (IOException ex) {
				properties = null;
				//ex.printStackTrace();
				log.error(ex, ex.getCause(),"Properties file is not loaded from class path - PTCHBatch");
			}
			return properties;
		}
			

}
