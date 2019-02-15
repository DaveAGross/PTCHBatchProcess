package com.cds.ptch.batch.response.ptchspec;

public class STCHTokenRecord {
	
	private String recordIdentifier;
	private String recordType;
	private String sequenceNumber;
	private String tokenID;
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
	public String getTokenID() {
		return tokenID;
	}
	public void setTokenID(String tokenID) {
		this.tokenID = tokenID;
	}
	
	public String getReserved() {
		return reserved;
	}
	public void setReserved(String reserved) {
		this.reserved = reserved;
	}	

	
	@Override
	public String toString() {
		return "STCHTokenRecord [recordIdentifier=" + recordIdentifier
				+ ", recordType=" + recordType + ", sequenceNumber="
				+ sequenceNumber + ", tokenID=" + tokenID + ", reserved="
				+ reserved + "]";
	}

}
