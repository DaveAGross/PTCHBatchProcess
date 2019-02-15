package com.cds.ptch.batch.response.ptchspec;

public class DigitalPANRecord {
	
	private String recordIdentifier;
	private String recordType;
	private String sequenceNumber;
	private String tokenAssuranceLevel;
	private String accountStatus;
	private String tokenRequestorID;
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
	public String getTokenAssuranceLevel() {
		return tokenAssuranceLevel;
	}
	public void setTokenAssuranceLevel(String tokenAssuranceLevel) {
		this.tokenAssuranceLevel = tokenAssuranceLevel;
	}
	
	public String getAccountStatus() {
		return accountStatus;
	}
	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}
	
	public String getTokenRequestorID() {
		return tokenRequestorID;
	}
	public void setTokenRequestorID(String tokenRequestorID) {
		this.tokenRequestorID = tokenRequestorID;
	}
	public String getReserved() {
		return reserved;
	}
	public void setReserved(String reserved) {
		this.reserved = reserved;
	}
	@Override
	public String toString() {
		return "DigitalPANRecord [recordIdentifier=" + recordIdentifier
				+ ", recordType=" + recordType + ", sequenceNumber="
				+ sequenceNumber + ", tokenAssuranceLevel="
				+ tokenAssuranceLevel + ", accountStatus=" + accountStatus
				+ ", tokenRequestorID=" + tokenRequestorID + ", reserved="
				+ reserved + "]";
	}
	

}
