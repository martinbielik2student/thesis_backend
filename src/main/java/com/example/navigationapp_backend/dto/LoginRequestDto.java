package com.example.navigationapp_backend.dto;

public class LoginRequestDto {

	private Long aisId;

	private  String password;

	public LoginRequestDto() {
	}

	public Long getAisId() {
		return aisId;
	}

	public void setAisId(Long aisId) {
		this.aisId = aisId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
