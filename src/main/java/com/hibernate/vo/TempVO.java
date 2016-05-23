package com.hibernate.vo;

public class TempVO extends BaseVO{

	private static final long serialVersionUID = 1L;
	
	private Integer integerValue;
	private Long longValue;
	private String stringValue;
	

	public Integer getIntegerValue() {
		return integerValue;
	}
	public void setIntegerValue(Integer integerValue) {
		this.integerValue = integerValue;
	}
	public Long getLongValue() {
		return longValue;
	}
	public void setLongValue(Long longValue) {
		this.longValue = longValue;
	}
	public String getStringValue() {
		return stringValue;
	}
	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}

}
