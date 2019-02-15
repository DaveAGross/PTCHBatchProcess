package com.cds.ptch.batch.response.ptchspec;

/**
 * 
 * @author ShanthiNT
 * 
 * This object contains the group of records including group of product records as detail group
 *
 */
public class DetailRecordGroup {
			
		private Detail detail;
		private BatchTotal batchTotal;
		private ProductRecords productRecords;
		public Detail getDetail() {
			return detail;
		}
		public void setDetail(Detail detail) {
			this.detail = detail;
		}
		public ProductRecords getProductRecords() {
			return productRecords;
		}
		public void setProductRecords(ProductRecords productRecords) {
			this.productRecords = productRecords;
		}
		public BatchTotal getBatchTotal() {
			return batchTotal;
		}
		public void setBathcTotal(BatchTotal batchTotal) {
			this.batchTotal = batchTotal;
		}

}
