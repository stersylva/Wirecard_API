package br.com.wirecard.tests.models.payment;


public class Payment {
	
	private Integer installmentCount;
	private String statementDescriptor;
	private FundingInstrument fundingInstrument;
	public Integer getInstallmentCount() {
		return installmentCount;
	}
	
	public void setInstallmentCount(Integer installmentCount) {
		this.installmentCount = installmentCount;
	}
	public String getStatementDescriptor() {
		return statementDescriptor;
	}
	public void setStatementDescriptor(String statementDescriptor) {
		this.statementDescriptor = statementDescriptor;
	}
	public FundingInstrument getFundingInstrument() {
		return fundingInstrument;
	}
	public void setFundingInstrument(FundingInstrument fundingInstrument) {
		this.fundingInstrument = fundingInstrument;
	}

}
