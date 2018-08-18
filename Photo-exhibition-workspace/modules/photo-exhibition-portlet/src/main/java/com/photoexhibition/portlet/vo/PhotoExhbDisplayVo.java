package com.photoexhibition.portlet.vo;

public class PhotoExhbDisplayVo {

	private long id;
	private String name;
	private String link;
	private boolean isAdvertise;

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
}
