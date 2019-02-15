package com.cds.ptch.batch.response.ptchspec;

/**
 * 
 * @author ShanthiNT
 * This object contains the group of product records
 */
public class ProductRecords {
	
		private STCHTokenRecord stchTokenRecord;
		private DigitalPANRecord digitalPANRecord;
		private VisaAuthRecord visaAuthRecord;
		private PartialAuthRecord partialAuthRecord;
			
		public STCHTokenRecord getTokenRecord() {
			return stchTokenRecord;
		}
		public void setTokenRecord(STCHTokenRecord stchTokenRecord) {
			this.stchTokenRecord = stchTokenRecord;
		}
		public DigitalPANRecord getDigitalPANRecord() {
			return digitalPANRecord;
		}
		public void setDigitalPANRecord(DigitalPANRecord digitalPANRecord) {
			this.digitalPANRecord = digitalPANRecord;
		}
		
		public VisaAuthRecord getVisaAuthRecord() {
			return visaAuthRecord;
		}
		public void setVisaAuthRecord(VisaAuthRecord visaAuthRecord) {
			this.visaAuthRecord = visaAuthRecord;
		}
		
		public PartialAuthRecord getPartialAuthRecord() {
			return partialAuthRecord;
		}
		public void setPartialAuthRecord(PartialAuthRecord partialAuthRecord) {
			this.partialAuthRecord = partialAuthRecord;
		}
				

}
