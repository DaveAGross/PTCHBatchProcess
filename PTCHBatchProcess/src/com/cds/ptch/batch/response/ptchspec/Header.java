package com.cds.ptch.batch.response.ptchspec;

public class Header {
	
	public String constantPID;	
	public String presenterID;
	public String filler1;
	public String passwordPID;
	public String filler2;
	public String constantSID;
	public String submitterID;
	public String filler3;
	public String passwordSID;
	public String filler4;
	public String constantStart;
	public String filler5;
	public String creationDate;
	public String filler6;
	public String constant;
	public String reserved1;
	public String submissionNumber;
	public String reserved2;
	public String authLogIndicator;
	public String reserved3;
	public String reserved4;
	public String merchantSpace;
	
	//GETTTERS
	public String getConstantPID() {
		return constantPID;
	}
	public String getPresenterID() {
		return presenterID;
	}
	public String getFiller1() {
		return filler1;
	}
	public String getPasswordPID() {
		return passwordPID;
	}
	public String getFiller2() {
		return filler2;
	}
	public String getConstantSID() {
		return constantSID;
	}
	public String getSubmitterID() {
		return submitterID;
	}
	public String getFiller3() {
		return filler3;
	}
	public String getPasswordSID() {
		return passwordSID;
	}
	public String getFiller4() {
		return filler4;
	}
	public String getConstantStart() {
		return constantStart;
	}
	public String getFiller5() {
		return filler5;
	}
	public String getCreationDate() {
		return creationDate;
	}
	public String getFiller6() {
		return filler6;
	}
	public String getConstant() {
		return constant;
	}
	public String getReserved1() {
		return reserved1;
	}
	public String getSubmissionNumber() {
		return submissionNumber;
	}
	public String getReserved2() {
		return reserved2;
	}
	public String getAuthLogIndicator() {
		return authLogIndicator;
	}
	public String getReserved3() {
		return reserved3;
	}
	public String getReserved4() {
		return reserved4;
	}
	public String getMerchantSpace() {
		return merchantSpace;
	}
	
	//SETTERS
	public void setConstantPID(String constantPID) {
		this.constantPID = constantPID;
	}
	public void setPresenterID(String presenterID) {
		this.presenterID = presenterID;
	}
	public void setFiller1(String filler1) {
		this.filler1 = filler1;
	}
	public void setPasswordPID(String passwordPID) {
		this.passwordPID = passwordPID;
	}
	public void setFiller2(String filler2) {
		this.filler2 = filler2;
	}
	public void setConstantSID(String constantSID) {
		this.constantSID = constantSID;
	}
	public void setSubmitterID(String submitterID) {
		this.submitterID = submitterID;
	}
	public void setFiller3(String filler3) {
		this.filler3 = filler3;
	}
	public void setPasswordSID(String passwordSID) {
		this.passwordSID = passwordSID;
	}
	public void setFiller4(String filler4) {
		this.filler4 = filler4;
	}
	public void setConstantStart(String constantStart) {
		this.constantStart = constantStart;
	}
	public void setFiller5(String filler5) {
		this.filler5 = filler5;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	public void setFiller6(String filler6) {
		this.filler6 = filler6;
	}
	public void setConstant(String constant) {
		this.constant = constant;
	}
	public void setReserved1(String reserved1) {
		this.reserved1 = reserved1;
	}
	public void setSubmissionNumber(String submissionNumber) {
		this.submissionNumber = submissionNumber;
	}
	public void setReserved2(String reserved2) {
		this.reserved2 = reserved2;
	}
	public void setAuthLogIndicator(String authLogIndicator) {
		this.authLogIndicator = authLogIndicator;
	}
	public void setReserved3(String reserved3) {
		this.reserved3 = reserved3;
	}
	public void setReserved4(String reserved4) {
		this.reserved4 = reserved4;
	}
	public void setMerchantSpace(String merchantSpace) {
		this.merchantSpace = merchantSpace;
	}
	@Override
	public String toString() {
		return "Header [constantPID=" + constantPID + ", presenterID="
				+ presenterID + ", filler1=" + filler1 + ", passwordPID="
				+ passwordPID + ", filler2=" + filler2 + ", constantSID="
				+ constantSID + ", submitterID=" + submitterID + ", filler3="
				+ filler3 + ", passwordSID=" + passwordSID + ", filler4="
				+ filler4 + ", constantStart=" + constantStart + ", filler5="
				+ filler5 + ", creationDate=" + creationDate + ", filler6="
				+ filler6 + ", constant=" + constant + ", reserved1="
				+ reserved1 + ", submissionNumber=" + submissionNumber
				+ ", reserved2=" + reserved2 + ", authLogIndicator="
				+ authLogIndicator + ", reserved3=" + reserved3
				+ ", reserved4=" + reserved4 + ", merchantSpace="
				+ merchantSpace + "]";
	}
	

}
