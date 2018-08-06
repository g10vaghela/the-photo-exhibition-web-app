package com.photoexhibition.service.search.criteria;

public class ChildInfoSearchCriteria extends BaseSearchCriteria{
	private long childId;
	private String contactNo;
	private boolean status;
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public long getChildId() {
		return childId;
	}
	public void setChildId(long childId) {
		this.childId = childId;
	}
	public String getContactNo() {
		return contactNo;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	@Override
	public String toString() {
		return "ChildInfoSearchCriteria [childId=" + childId + ", contactNo=" + contactNo + "]";
	}
}
