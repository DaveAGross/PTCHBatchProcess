package com.cds.ptch.batch.util.log;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;

import com.cds.ptch.batch.util.services.PTCHBatchUtil;

import org.slf4j.LoggerFactory;

/**
 * 
 * @author ShanthiNT
 *
 * @version $Revision: 1.0 $
 */
public class PTCHBatchLoggerFactory{

	/**
	 * Method getLogger.
	 * @param name String
	
	 * @return PTCHBatchLogger */
	public static PTCHBatchLogger getLogger(String name) {
		return new PTCHBatchLogger(LoggerFactory.getLogger(name));
	}

	/**
	 * Get a new PTCHBatchLogger instance by class. The returned PTCHBatchLogger will be
	 * named after the class.
	 * 
	
	
	 * @param arg Class<?>
	 * @return Logger instance by name */
	public static PTCHBatchLogger getPTCHBatchLogger(Class<?> arg) {
		return getLogger(arg.getName());
	}

	/**
	 * Get a new PTCHBatchLogger instance by class. The returned PTCHBatchLogger will be
	 * named as a string name.
	 * 
	
	
	 * @param name String
	 * @return PTCHBatchLogger instance by name */

	public static PTCHBatchLogger getPTCHBatchLogger(String name) 
	{	
		  LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
		  JoranConfigurator jc = new JoranConfigurator();
		  jc.setContext(context);
		  context.reset(); // override default configuration
		  // inject the name of the current application as "application-name"
		  // property of the LoggerContext
		  context.putProperty("application-name", "PTCHBatchProcess");
		  //String path=new PTCHBatchLoggerFactory().getClass().getClassLoader().getResource(".").getPath();
		
		  //FileSearch fileSearch = new FileSearch();
		  //String path = fileSearch.searchDirectory("PTCHBatchlogback.xml","LOGBACK");				

		  //path="./resources/AMEXUpdaterlogback.xml";
		  String path = new PTCHBatchUtil().getPropertyFilePath("PTCHBatchlogback.xml","LOGBACK");
			
	
		  try {
			    jc.doConfigure(path);
			
		  } catch (JoranException e) {
			// TODO Auto-generated catch block
			  e.printStackTrace();
		
		  }
		return getLogger(name);
	}
}
