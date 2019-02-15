package com.cds.ptch.batch.response.ptchspec;

public class BatchTotal {
	
	public String constant;	
	public String batchRecordCount;
	public String filler1;
	public String constantOrders;
	public String batchOrderCount;
	public String filler2;
	public String constantTotal;
	public String batchAmountTotal;
	public String filler3;
	public String constantSale;
	public String batchAmountSales;
	public String filler4;
	public String constantRefund;	
	public String batchAmountRefunds;
	public String reserved;
		
	//GETTTERS
	public String getConstant() {
		return constant;
	}
	public String getBatchRecordCount() {
		return batchRecordCount;
	}
	public String getFiller1() {
		return filler1;
	}
	public String getConstantOrders() {
		return constantOrders;
	}
	public String getFiller2() {
		return filler2;
	}
	public String getConstantTotal() {
		return constantTotal;
	}
	public String getBatchAmountTotal() {
		return batchAmountTotal;
	}
	public String getFiller3() {
		return filler3;
	}
	public String getConstantSale() {
		return constantSale;
	}	
	public String getBatchAmountSales() {
		return batchAmountSales;
	}
	public String getFiller4() {
		return filler4;
	}
	
	public String getConstantRefund() {
		return constantRefund;
	}
	
	public String getBatchAmountRefunds() {
		return batchAmountRefunds;
	}
	public String getReserved() {
		return reserved;
	}
	
		
	//SETTERS
	public void setConstant(String constant) {
		this.constant = constant;
	}
	public void setBatchRecordCount(String batchRecordCount) {
		this.batchRecordCount = batchRecordCount;
	}
	public void setFiller1(String filler1) {
		this.filler1 = filler1;
	}
	public void setConstantOrders(String constantOrders) {
		this.constantOrders = constantOrders;
	}
	public void setFiller2(String filler2) {
		this.filler2 = filler2;
	}
	public void setConstantTotal(String constantTotal) {
		this.constantTotal = constantTotal;
	}
	public void setBatchAmountTotal(String batchAmountTotal) {
		this.batchAmountTotal = batchAmountTotal;
	}
	public void setFiller3(String filler3) {
		this.filler3 = filler3;
	}
	public void setConstantSale(String constantSale) {
		this.constantSale = constantSale;
	}	
	public void setBatchAmountSales(String batchAmountSales) {
		this.batchAmountSales = batchAmountSales;
	}
	public void setFiller4(String filler4) {
		this.filler4 = filler4;
	}
	
	public void setConstantRefund(String constantRefund) {
		this.constantRefund = constantRefund;
	}
	public void setbatchAmountRefunds(String batchAmountRefunds) {
		this.batchAmountRefunds = batchAmountRefunds;
	}
	public void setReserved(String reserved) {
		this.reserved = reserved;
	}
	
	@Override
	public String toString() {
		return "BatchTotal [constant=" + constant + ", batchRecordCount="
				+ batchRecordCount + ", filler1=" + filler1
				+ ", constantOrders=" + constantOrders + ", batchOrderCount="
				+ batchOrderCount + ", filler2=" + filler2 + ", constantTotal="
				+ constantTotal + ", batchAmountTotal=" + batchAmountTotal
				+ ", filler3=" + filler3 + ", constantSale=" + constantSale
				+ ", batchAmountSales=" + batchAmountSales + ", filler4="
				+ filler4 + ", constantRefund=" + constantRefund
				+ ", batchAmountRefunds=" + batchAmountRefunds + ", reserved="
				+ reserved + "]";
	}

}
