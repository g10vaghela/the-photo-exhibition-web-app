package com.photoexhibition.service.search.criteria;

public class AdvertiseInfoSearchChiteria extends BaseSearchCriteria{
	private long advertiseId;
	private String advertiseName;
	private int status;
	public long getAdvertiseId() {
		return advertiseId;
	}
	public void setAdvertiseId(long advertiseId) {
		this.advertiseId = advertiseId;
	}
	public String getAdvertiseName() {
		return advertiseName;
	}
	public void setAdvertiseName(String advertiseName) {
		this.advertiseName = advertiseName;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}
