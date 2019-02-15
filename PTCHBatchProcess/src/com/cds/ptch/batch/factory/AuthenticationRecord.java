package com.cds.ptch.batch.factory;

import java.util.Map;

/**
 * 
 * @author ShanthiNT
 * This interface is used to get hide and call buildAuthenticationRecord method from the card specific classes
 */
public interface AuthenticationRecord {
	
	public String buildAuthenticationRecord(Map<String, String> detailMap);		

}
