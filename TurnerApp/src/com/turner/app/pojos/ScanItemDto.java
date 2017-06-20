package com.turner.app.pojos;

import java.util.Arrays;

public class ScanItemDto {

	private String profitcenter;
	private String companycode;
	private String assetsno;
	private String scandatetime;
	private boolean isselect = false;
	private String subno;
	private String gpscoordinate;
	private String comments;
	private byte[] imageOne;
	private byte[] imageTwo;
	private String status;
	private int uniqueId;

	public ScanItemDto() {
	}

	public ScanItemDto(String profitcenter, String companycode, String assetsno, String scandatetime, boolean isselect,
			String subno, String gpscoordinate, String comments, byte[] imageOne, byte[] imageTwo, String status,
			int uniqueId) {
		this.profitcenter = profitcenter;
		this.companycode = companycode;
		this.assetsno = assetsno;
		this.scandatetime = scandatetime;
		this.isselect = isselect;
		this.subno = subno;
		this.gpscoordinate = gpscoordinate;
		this.comments = comments;
		this.imageOne = imageOne;
		this.imageTwo = imageTwo;
		this.status = status;
		this.uniqueId = uniqueId;
	}

	public String getProfitcenter() {
		return profitcenter;
	}

	public void setProfitcenter(String profitcenter) {
		this.profitcenter = profitcenter;
	}

	public String getCompanycode() {
		return companycode;
	}

	public void setCompanycode(String companycode) {
		this.companycode = companycode;
	}

	public String getAssetsno() {
		return assetsno;
	}

	public void setAssetsno(String assetsno) {
		this.assetsno = assetsno;
	}

	public String getScandatetime() {
		return scandatetime;
	}

	public void setScandatetime(String scandatetime) {
		this.scandatetime = scandatetime;
	}

	public boolean isIsselect() {
		return isselect;
	}

	public void setIsselect(boolean isselect) {
		this.isselect = isselect;
	}

	public String getSubno() {
		return subno;
	}

	public void setSubno(String subno) {
		this.subno = subno;
	}

	public String getGpscoordinate() {
		return gpscoordinate;
	}

	public void setGpscoordinate(String gpscoordinate) {
		this.gpscoordinate = gpscoordinate;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public byte[] getImageOne() {
		return imageOne;
	}

	public void setImageOne(byte[] imageOne) {
		this.imageOne = imageOne;
	}

	public byte[] getImageTwo() {
		return imageTwo;
	}

	public void setImageTwo(byte[] imageTwo) {
		this.imageTwo = imageTwo;
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
		return "ScanItemDto [profitcenter=" + profitcenter + ", companycode=" + companycode + ", assetsno=" + assetsno
				+ ", scandatetime=" + scandatetime + ", isselect=" + isselect + ", subno=" + subno + ", gpscoordinate="
				+ gpscoordinate + ", comments=" + comments + ", imageOne=" + Arrays.toString(imageOne) + ", imageTwo="
				+ Arrays.toString(imageTwo) + ", status=" + status + ", uniqueId=" + uniqueId + "]";
	}

}
