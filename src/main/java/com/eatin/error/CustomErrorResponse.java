package com.eatin.error;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class CustomErrorResponse {

	private String errorCode;
	private List<String> errorMsg;
	private int status;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
	private LocalDateTime timestamp;

	public CustomErrorResponse(String errorCode, List<String> errorMsg) {
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;

	}
}