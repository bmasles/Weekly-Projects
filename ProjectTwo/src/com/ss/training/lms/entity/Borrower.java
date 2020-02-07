package com.ss.training.lms.entity;

import java.io.Serializable;

public class Borrower implements Serializable{
	private static final long serialVersionUID = 6250847434965224499L;
	private Integer cardNo;
	private String name;
	private String address;
	public Integer getCardNo() {
		return cardNo;
	}
	public void setCardNo(Integer cardNo) {
		this.cardNo = cardNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	private String phone;
}
