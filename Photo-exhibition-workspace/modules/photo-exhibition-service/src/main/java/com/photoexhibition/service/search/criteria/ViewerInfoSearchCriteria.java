package com.photoexhibition.service.search.criteria;

public class ViewerInfoSearchCriteria extends BaseSearchCriteria {
	private long viewerId;
	private String mobileNumber;
	public long getViewerId() {
		return viewerId;
	}
	public void setViewerId(long viewerId) {
		this.viewerId = viewerId;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	@Override
	public String toString() {
		return "ViewerInfoSearchCriteria [viewerId=" + viewerId + ", mobileNumber=" + mobileNumber + "]";
	}
}
