package com.cds.ptch.batch.response.ptchspec;

public class Trailer {
	
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
	public String constantEnd;
	public String reserved1;
	public String creationDate;
	public String reserved2;
		
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
	public String getConstantEnd() {
		return constantEnd;
	}
	
	public String getCreationDate() {
		return creationDate;
	}
	
	public String getReserved1() {
		return reserved1;
	}
	public String getReserved2() {
		return reserved2;
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
	public void setConstantEnd(String constantEnd) {
		this.constantEnd = constantEnd;
	}
	public void setReserved1(String reserved1) {
		this.reserved1 = reserved1;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	public void setReserved2(String reserved2) {
		this.reserved2 = reserved2;
	}
	
	@Override
	public String toString() {
		return "Trailer [constantPID=" + constantPID + ", presenterID="
				+ presenterID + ", filler1=" + filler1 + ", passwordPID="
				+ passwordPID + ", filler2=" + filler2 + ", constantSID="
				+ constantSID + ", submitterID=" + submitterID + ", filler3="
				+ filler3 + ", passwordSID=" + passwordSID + ", filler4="
				+ filler4 + ", constantEnd=" + constantEnd + ", reserved1="
				+ reserved1 + ", creationDate=" + creationDate + ", reserved2="
				+ reserved2 + "]";
	}
	

}
