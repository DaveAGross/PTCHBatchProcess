package com.cds.ptch.batch;

/**
 * 
 * @author ShanthiNT
 * This class distributes the request to the appropriate action classes
 * @version $Revision: 1.0 $
 */
public class PTCHBatchManager {
		    
	   /**
	    * Method main.
	    * @param arg String[]
	    */
	   public static void main (String arg[]){
		   
		      String destination = arg[0];
		      String maxFileSize= arg[1];
		      //String destination = "PARSE_PTCH_BATCH_RESPONSE";
		      try{
		    	  
			      switch ( destination ) {
			      	
			      case "CREATE_PTCH_BATCH_REQUEST": 
			    	    new PTCHBatchFileCreator().buildBatchFile(Integer.parseInt(maxFileSize));
			    	    break;
			      case "PARSE_PTCH_BATCH_RESPONSE":
			    	  new PTCHBatchResponseParser().parseBatchResponse();
			    	  break;
			      default:
			    	  System.out.println("INVALID COMMAND ARGUMENT");
			    	  
			      }
			      
		      }catch(Exception ex){
		    	  System.exit(3);
		      }
		      System.exit(0);
		      
	   }

}
