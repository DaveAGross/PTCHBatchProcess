package com.cds.ptch.batch.factory;

import com.cds.ptch.batch.records.AXAuthenticationRecord;
import com.cds.ptch.batch.records.DIAuthenticationRecord;
import com.cds.ptch.batch.records.JCAuthenticationRecord;
import com.cds.ptch.batch.records.MCAuthenticationRecord;
import com.cds.ptch.batch.records.VIAuthenticationRecord;

/**
 * 
 * @author ShanthiNT
 * This factory is used to get instance of Authentication Record instance based on the card type
 */
public class AuthenticationRecordFactory {

   AuthenticationRecord authRecordInstance;	
   
   public AuthenticationRecordFactory() {        
   }
       
   public AuthenticationRecord getAuthenticationRecordInstance(String cardType) {
	   
       switch(cardType) {
       
       case "JC":
    	   authRecordInstance = new JCAuthenticationRecord();
    	   break; 
       case "AX":
    	   authRecordInstance = new AXAuthenticationRecord();
    	   break; 
       case "MC":
    	   authRecordInstance = new MCAuthenticationRecord();
    	   break;
       case "DI":
       case "DC":
    	   authRecordInstance = new DIAuthenticationRecord();
    	   break;
       case "VI":
    	   authRecordInstance = new VIAuthenticationRecord();
    	   break;
       default:
    	   throw new RuntimeException("Invalid Card Type");
        	   
       }               
       return authRecordInstance;
   }   
}
