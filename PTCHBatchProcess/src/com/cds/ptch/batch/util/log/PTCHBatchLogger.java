package com.cds.ptch.batch.util.log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetAddress;
import java.util.Map;
import org.slf4j.ext.LoggerWrapper;
import org.slf4j.Logger;
/**
 * This class is used to configure logging for PTCH Batch Process
 * @author ShanthiNT
 * @version $Revision: 1.0 $
 */
public class PTCHBatchLogger extends LoggerWrapper {

	/**
	 * @param logger
	 */
	public PTCHBatchLogger(Logger logger) {
		super(logger, LoggerWrapper.class.getName());
	}
	
	
	/**
	 * Method error.
	 * @param errorMsg String
	 * @param t Throwable
	 * @see org.slf4j.Logger#error(String, Throwable)
	 */
	public void error(String errorMsg, Throwable t) {

		StringBuffer sb = new StringBuffer();

		// gets the ip address where exception occur
		InetAddress ipAddress = null;
		try {

			ipAddress = InetAddress.getLocalHost();

		} catch (Exception e) {
			System.out
					.println("Sl4j  exception from Vaultapi application"
							+ e.getMessage());
		}

		sb.append("IP ADDRESS: " + ipAddress);
		sb.append("\n\n");
		sb.append("Input Parameter: " + errorMsg);
		sb.append("\n\n");
		sb.append("Exception: ");
		sb.append("\n\n");

		String stack = sb.toString();
		 t = new Throwable("From  : " + stack);
		super.error(errorMsg, t);

	}

	
	/**
	 * @param ex
	 * @param t
	 * @param paramMap
	 */
	public void error(Exception ex, Throwable t, Map<String, String> paramMap) {

		StringBuffer sb = new StringBuffer();

		// gets the ip address where exception occur
		InetAddress ipAddress = null;
		try {

			ipAddress = InetAddress.getLocalHost();

			sb.append("IP ADDRESS: " + ipAddress);
			sb.append("\n\n");
			sb.append("REMOTE IP ADDRESS: " + paramMap.get("RemoteIP"));
			sb.append("\n\n");
			paramMap.remove("RemoteIP");
			sb.append("Parameter Map: " + paramMap.toString());
			sb.append("\n\n");
			sb.append("Exception: ");
			sb.append("\n\n");

			if (ex != null) {
				StackTraceElement ste[] = ex.getStackTrace();

				for (int i = 0; i < ste.length; i++) {
					sb.append(ste[i]).append("\n");

				}
			}
		} catch (Exception e) {
			System.out.println("Sl4j exception from Vaultapi application"	+ e.getMessage());
		}

		String stack = sb.toString();

		super.error(stack, t);

	}

	/**
	 * @param ex
	 * @param t
	 * @param inputParam
	 */
	public void error(Exception ex, Throwable t, String inputParam) {

		StringBuffer sb = new StringBuffer();

		// gets the ip address where exception occur
		InetAddress ipAddress = null;
		try {

			ipAddress = InetAddress.getLocalHost();

		} catch (Exception e) {
			System.out.println("Sl4j  exception from Vaultapi application"+ e.getMessage());
		}

		sb.append("IP ADDRESS: " + ipAddress);
		sb.append("\n\n");
		sb.append("Input Parameter: " + inputParam);
		sb.append("\n\n");
		sb.append("Exception: ");
		sb.append("\n\n");

		if (ex != null) {
			StackTraceElement ste[] = ex.getStackTrace();
			for (int i = 0; i < ste.length; i++) {
				sb.append(ste[i]).append("\n");

			}
		}
		String stack = sb.toString();
		super.error(stack, t);

	}

	/**
	 * @param errorMsg
	 * @param inputParam
	 */
	public void error(String errorMsg, String inputParam) {

		StringBuffer sb = new StringBuffer();

		// gets the ip address where exception occur
		InetAddress ipAddress = null;
		try {

			ipAddress = InetAddress.getLocalHost();

		} catch (Exception e) {
			System.out.println("Sl4j  exception from Vaultapi application"+ e.getMessage());
		}

		sb.append("IP ADDRESS: " + ipAddress);
		sb.append("\n\n");
		sb.append("Input Parameter: " + inputParam);
		sb.append("\n\n");
		sb.append("ErrorMessage: ");
		sb.append("\n\n");

		String stack = sb.toString();
		Throwable throwInstance = new Throwable("  : " + errorMsg);
		super.error(stack, throwInstance);

	}

	/**
	 * @param t
	
	
	 * @return String */
	public static String getStackTrace(Throwable t) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		t.printStackTrace(pw);
		pw.flush();
		return sw.toString();
	}
}
