package com.cds.ptch.batch.util.services;

import java.io.File;

/**
 * 
 * @author ShanthiNT
 *
 */
public class FileSearch {

		/**
		 * @param fileNameToSearch
		 * @return
		 * searches file system to find a specifc files
		 */
		public String searchDirectory(String fileNameToSearch) {	
			String result = null;
			String slash = System.getProperty("file.separator");
			File directory = new File(System.getenv("CPSHOME")+slash+"resources"+slash+"batch"+slash+"ptch");	 

			if (directory.isDirectory()) {
				result = search(directory,fileNameToSearch);
			} else {
				System.out.println(directory.getAbsoluteFile() + " is not a directory!");
			}

			return result;

		}

		private String search(File file,String fileNameToSearch) {
			String result = null;
			if (file.isDirectory()) {			

				//do you have permission to read this directory?	
				if (file.canRead()) {
					for (File temp : file.listFiles()) {
						if (temp.isDirectory()) {
							search(temp,fileNameToSearch);
						} else {
							if (fileNameToSearch.equals(temp.getName())) {			
								result  = temp.getAbsoluteFile().toString();
								break;
							}
						}
					}

				} else {
					System.out.println(file.getAbsoluteFile() + "Permission Denied");
				}
			}
			
			return result;

		}


}
