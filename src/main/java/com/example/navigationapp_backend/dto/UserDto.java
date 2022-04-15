package com.example.navigationapp_backend.dto;

import com.example.navigationapp_backend.entity.TimeTable;

public class UserDto {

	private Long aisId;

	private String password;

	private TimeTable timeTable;

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

	public TimeTable getTimeTable() {
		return timeTable;
	}

	public void setTimeTable(TimeTable timeTable) {
		this.timeTable = timeTable;
	}
}
