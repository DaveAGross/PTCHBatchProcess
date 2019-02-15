package com.cds.ptch.batch.response.ptchspec;

public class VisaAuthRecord {

	private String recordIdentifier;
	private String recordType;
	private String sequenceNumber;
	private String transactionID;
	private String cavValue;
	private String cavvResponseCode;
	private String reserved;
	
	public String getRecordIdentifier() {
		return recordIdentifier;
	}
	public void setRecordIdentifier(String recordIdentifier) {
		this.recordIdentifier = recordIdentifier;
	}
	
	public String getRecordType() {
		return recordType;
	}
	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}
	
	public String getSequenceNumber() {
		return sequenceNumber;
	}
	public void setSequenceNumber(String sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	public String getTransactionID() {
		return transactionID;
	}
	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
	}
	public String getCavValue() {
		return cavValue;
	}
	public void setCavValue(String cavValue) {
		this.cavValue = cavValue;
	}
		
	public String getCavvResponseCode() {
		return cavvResponseCode;
	}
	public void setCavvResponseCode(String cavvResponseCode) {
		this.cavvResponseCode = cavvResponseCode;
	}
	
	public String getReserved() {
		return reserved;
	}
	public void setReserved(String reserved) {
		this.reserved = reserved;
	}
	
	@Override
	public String toString() {
		return "VisaAuthRecord [recordIdentifier=" + recordIdentifier
				+ ", recordType=" + recordType + ", sequenceNumber="
				+ sequenceNumber + ", transactionID=" + transactionID
				+ ", cavValue=" + cavValue + ", cavvResponseCode="
				+ cavvResponseCode + ", reserved=" + reserved + "]";
	}	
	

}
