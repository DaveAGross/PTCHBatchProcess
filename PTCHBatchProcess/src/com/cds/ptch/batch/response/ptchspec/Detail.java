package com.cds.ptch.batch.response.ptchspec;

public class Detail {

	private String constant;
	private String divisioNumber;
	private String merchantOrderNumber;
	private String actionCode;
	private String mop;
	private String accountNumber;
	private String expirationDate;
	private String Amount;
	private String currencyCode;
	private String responseCode;
	private String transactionType;
	private String cvvResponse;
	private String responseDate;
	private String authVerificationCode;
	private String avsResponseCode;
	private String depositFlag;
	private String paymentIndicator;
	private String encryptionFlag;
	private String paymentAdviceCode;
	private String reserved1;
	private String partialAuthFlag;
	private String splitTenderIndicator;
	private String reserved2;
	private String merchantSpace;
	
	public String getConstant() {
		return constant;
	}

	public void setConstant(String constant) {
		this.constant = constant;
	}

	public String getCompanyNumber() {
		return divisioNumber;
	}

	public void setCompanyNumber(String divisioNumber) {
		this.divisioNumber = divisioNumber;
	}

	public String getMerchantOrderID() {
		return merchantOrderNumber;
	}

	public void setMerchantOrderID(String merchantOrderNumber) {
		this.merchantOrderNumber = merchantOrderNumber;
	}

	public String getActionCode() {
		return actionCode;
	}

	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}

	public String getMop() {
		return mop;
	}

	public void setMop(String mop) {
		this.mop = mop;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getAmount() {
		return Amount;
	}

	public void setAmount(String amount) {
		Amount = amount;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getCvvResponse() {
		return cvvResponse;
	}

	public void setCvvResponse(String cvvResponse) {
		this.cvvResponse = cvvResponse;
	}

	public String getResponseDate() {
		return responseDate;
	}

	public void setResponseDate(String responseDate) {
		this.responseDate = responseDate;
	}

	public String getAuthVerificationCode() {
		return authVerificationCode;
	}

	public void setAuthVerificationCode(String authVerificationCode) {
		this.authVerificationCode = authVerificationCode;
	}

	public String getAvsResponseCode() {
		return avsResponseCode;
	}

	public void setAvsResponseCode(String avsResponseCode) {
		this.avsResponseCode = avsResponseCode;
	}

	public String getDepositFlag() {
		return depositFlag;
	}

	public void setDepositFlag(String depositFlag) {
		this.depositFlag = depositFlag;
	}

	public String getPaymentIndicator() {
		return paymentIndicator;
	}

	public void setPaymentIndicator(String paymentIndicator) {
		this.paymentIndicator = paymentIndicator;
	}

	public String getEncryptionFlag() {
		return encryptionFlag;
	}

	public void setEncryptionFlag(String encryptionFlag) {
		this.encryptionFlag = encryptionFlag;
	}
	
	public String getPaymentAdviceCode() {
		return paymentAdviceCode;
	}
	public void setPaymentAdviceCode(String paymentAdviceCode) {
		this.paymentAdviceCode = paymentAdviceCode;
	}
	public String getReserved1() {
		return reserved1;
	}
	
	public void setReserved1(String reserved1) {
		this.reserved1 = reserved1;
	}
	
	public String getPartialAuthFlag() {
		return partialAuthFlag;
	}
	public void setPartialAuthFlag(String partialAuthFlag) {
		this.partialAuthFlag = partialAuthFlag;
	}
	
	public String getSplitTenderIndicator() {
		return splitTenderIndicator;
	}
	public void setSplitTenderIndicator(String splitTenderIndicator) {
		this.splitTenderIndicator = splitTenderIndicator;
	}
	
	public String getReserved2() {
		return reserved2;
	}
	
	public void setReserved2(String reserved2) {
		this.reserved2 = reserved2;
	}
	
	public String getMerchantSpace() {
		return merchantSpace;
	}
	public void setMerchantSpace(String merchantSpace) {
		this.merchantSpace = merchantSpace;
	}

	@Override
	public String toString() {
		return "Detail [constant=" + constant + ", divisioNumber="
				+ divisioNumber + ", merchantOrderNumber="
				+ merchantOrderNumber + ", actionCode=" + actionCode + ", mop="
				+ mop + ", accountNumber=" + accountNumber
				+ ", expirationDate=" + expirationDate + ", Amount=" + Amount
				+ ", currencyCode=" + currencyCode + ", responseCode="
				+ responseCode + ", transactionType=" + transactionType
				+ ", cvvResponse=" + cvvResponse + ", responseDate="
				+ responseDate + ", authVerificationCode="
				+ authVerificationCode + ", avsResponseCode=" + avsResponseCode
				+ ", depositFlag=" + depositFlag + ", paymentIndicator="
				+ paymentIndicator + ", encryptionFlag=" + encryptionFlag
				+ ", paymentAdviceCode=" + paymentAdviceCode + ", reserved1="
				+ reserved1 + ", partialAuthFlag=" + partialAuthFlag
				+ ", splitTenderIndicator=" + splitTenderIndicator
				+ ", reserved2=" + reserved2 + ", merchantSpace="
				+ merchantSpace + "]";
	}
	
	
}
