package com.photoexhibition.service.vo;

public class ChildInfoVO {
	private long childId;
	private String childName;
	private String contactNo;
	private int totalLike;
	public long getChildId() {
		return childId;
	}
	public void setChildId(long childId) {
		this.childId = childId;
	}
	public String getChildName() {
		return childName;
	}
	public void setChildName(String childName) {
		this.childName = childName;
	}
	public String getContactNo() {
		return contactNo;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	public int getTotalLike() {
		return totalLike;
	}
	public void setTotalLike(int totalLike) {
		this.totalLike = totalLike;
	}
	@Override
	public String toString() {
		return "ChildInfoVO [childId=" + childId + ", childName=" + childName + ", contactNo=" + contactNo
				+ ", totalLike=" + totalLike + "]";
	}
}
