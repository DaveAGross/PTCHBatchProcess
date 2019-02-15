package com.cds.ptch.batch.response.ptchspec;

public class PartialAuthRecord {
	
	private String recordIdentifier;
	private String recordType;
	private String sequenceNumber;
	private String partialRedemptionIndicator;
	private String currentBalance;
	private String redemptionAmount;
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
	public String getPartialRedemptionIndicator() {
		return partialRedemptionIndicator;
	}
	public void setPartialRedemptionIndicator(String partialRedemptionIndicator) {
		this.partialRedemptionIndicator = partialRedemptionIndicator;
	}
	
	public String getCurrentBalance() {
		return currentBalance;
	}
	public void setCurrentBalance(String currentBalance) {
		this.currentBalance = currentBalance;
	}
	
	public String getRedemptionAmount() {
		return redemptionAmount;
	}
	public void setRedemptionAmount(String redemptionAmount) {
		this.redemptionAmount = redemptionAmount;
	}
	public String getReserved() {
		return reserved;
	}
	public void setReserved(String reserved) {
		this.reserved = reserved;
	}
	@Override
	public String toString() {
		return "PartialAuthRecord [recordIdentifier=" + recordIdentifier
				+ ", recordType=" + recordType + ", sequenceNumber="
				+ sequenceNumber + ", partialRedemptionIndicator="
				+ partialRedemptionIndicator + ", currentBalance=" + currentBalance
				+ ", redemptionAmount=" + redemptionAmount + ", reserved="
				+ reserved + "]";
	}
	

}
