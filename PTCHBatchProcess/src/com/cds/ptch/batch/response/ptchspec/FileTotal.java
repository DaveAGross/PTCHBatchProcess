package com.cds.ptch.batch.response.ptchspec;

public class FileTotal {
	
	public String constant;	
	public String fileRecordCount;
	public String filler1;
	public String constantOrders;
	public String fileOrderCount;
	public String filler2;
	public String constantTotal;
	public String fileAmountTotal;
	public String filler3;
	public String constantSale;
	public String fileAmountSales;
	public String filler4;
	public String constantRefund;	
	public String fileAmountRefunds;
	public String reserved;
		
	//GETTTERS
	public String getConstant() {
		return constant;
	}
	public String getFileRecordCount() {
		return fileRecordCount;
	}
	public String getFiller1() {
		return filler1;
	}
	public String getConstantOrders() {
		return constantOrders;
	}
	public String getFileOrderCount() {
		return fileOrderCount;
	}
	public String getFiller2() {
		return filler2;
	}
	public String getConstantTotal() {
		return constantTotal;
	}
	public String getFileAmountTotal() {
		return fileAmountTotal;
	}
	public String getFiller3() {
		return filler3;
	}
	public String getConstantSale() {
		return constantSale;
	}	
	public String getFileAmountSales() {
		return fileAmountSales;
	}
	public String getFiller4() {
		return filler4;
	}
	
	public String getConstantRefund() {
		return constantRefund;
	}
	
	public String getFileAmountRefunds() {
		return fileAmountRefunds;
	}
	public String getReserved() {
		return reserved;
	}
	
		
	//SETTERS
	public void setConstant(String constant) {
		this.constant = constant;
	}
	public void setFileRecordCount(String fileRecordCount) {
		this.fileRecordCount = fileRecordCount;
	}
	public void setFiller1(String filler1) {
		this.filler1 = filler1;
	}
	public void setConstantOrders(String constantOrders) {
		this.constantOrders = constantOrders;
	}
	public void setFileOrderCount(String fileOrderCount) {
		this.fileOrderCount = fileOrderCount;
	}
	public void setFiller2(String filler2) {
		this.filler2 = filler2;
	}
	public void setConstantTotal(String constantTotal) {
		this.constantTotal = constantTotal;
	}
	public void setFileAmountTotal(String fileAmountTotal) {
		this.fileAmountTotal = fileAmountTotal;
	}
	public void setFiller3(String filler3) {
		this.filler3 = filler3;
	}
	public void setConstantSale(String constantSale) {
		this.constantSale = constantSale;
	}	
	public void setFileAmountSales(String fileAmountSales) {
		this.fileAmountSales = fileAmountSales;
	}
	public void setFiller4(String filler4) {
		this.filler4 = filler4;
	}
	
	public void setConstantRefund(String constantRefund) {
		this.constantRefund = constantRefund;
	}
	public void setFileAmountRefunds(String fileAmountRefunds) {
		this.fileAmountRefunds = fileAmountRefunds;
	}
	public void setReserved(String reserved) {
		this.reserved = reserved;
	}
	@Override
	public String toString() {
		return "FileTotal [constant=" + constant + ", fileRecordCount="
				+ fileRecordCount + ", filler1=" + filler1
				+ ", constantOrders=" + constantOrders + ", fileOrderCount="
				+ fileOrderCount + ", filler2=" + filler2 + ", constantTotal="
				+ constantTotal + ", fileAmountTotal=" + fileAmountTotal
				+ ", filler3=" + filler3 + ", constantSale=" + constantSale
				+ ", fileAmountSales=" + fileAmountSales + ", filler4="
				+ filler4 + ", constantRefund=" + constantRefund
				+ ", fileAmountRefunds=" + fileAmountRefunds + ", reserved="
				+ reserved + "]";
	}
	

}
