package com.photoexhibition.service.vo;

import com.photoexhibition.service.model.AdvertiseInfo;
import com.photoexhibition.service.model.ChildInfo;

public class PhotoExhbDisplayVo {

	private ChildInfo childInfo;
	private AdvertiseInfo advertise;
	private long id;
	private String name;
	private String link;
	private boolean isAdvertise;
	private int orientation;
	private int totalLike;
	
	public int getTotalLike() {
		return totalLike;
	}
	public void setTotalLike(int totalLike) {
		this.totalLike = totalLike;
	}
	public ChildInfo getChildInfo() {
		return childInfo;
	}
	public void setChildInfo(ChildInfo childInfo) {
		this.childInfo = childInfo;
	}
	public AdvertiseInfo getAdvertise() {
		return advertise;
	}
	public void setAdvertise(AdvertiseInfo advertise) {
		this.advertise = advertise;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public boolean isAdvertise() {
		return isAdvertise;
	}
	public void setAdvertise(boolean isAdvertise) {
		this.isAdvertise = isAdvertise;
	}
	public int getOrientation() {
		return orientation;
	}
	public void setOrientation(int orientation) {
		this.orientation = orientation;
	}
}
