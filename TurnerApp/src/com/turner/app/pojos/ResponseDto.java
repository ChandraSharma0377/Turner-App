package com.turner.app.pojos;

public class ResponseDto {

	private String status;
	private int uniqueId;

	public ResponseDto() {
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(int uniqueId) {
		this.uniqueId = uniqueId;
	}

	@Override
	public String toString() {
		return "ResponseDto [status=" + status + ", uniqueId=" + uniqueId + "]";
	}

}
