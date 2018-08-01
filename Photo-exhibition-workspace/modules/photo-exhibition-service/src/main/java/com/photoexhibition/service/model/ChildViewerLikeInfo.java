package com.photoexhibition.service.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="child_view_like")
public class ChildViewerLikeInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "child_viewer_like_info_seq")
	@SequenceGenerator(name = "child_viewer_like_info_seq",  sequenceName = "child_viewer_like_info_seq",allocationSize=1, initialValue = 1)
	@Column(name = "id")
	private long likeId;

	@ManyToOne
	@JoinColumn(name="child_id")
	private ChildInfo childInfo;

	@ManyToOne
	@JoinColumn(name = "viewer_id")
	private ViewerInfo viewerInfo;

	@Column(name="is_like")
	private boolean isLike;

	public long getLikeId() {
		return likeId;
	}

	public void setLikeId(long likeId) {
		this.likeId = likeId;
	}

	public ChildInfo getChildInfo() {
		return childInfo;
	}

	public void setChildInfo(ChildInfo childInfo) {
		this.childInfo = childInfo;
	}

	public ViewerInfo getViewerInfo() {
		return viewerInfo;
	}

	public void setViewerInfo(ViewerInfo viewerInfo) {
		this.viewerInfo = viewerInfo;
	}

	public boolean isLike() {
		return isLike;
	}

	public void setLike(boolean isLike) {
		this.isLike = isLike;
	}

	@Override
	public String toString() {
		return "ChildViewerLikeInfo [likeId=" + likeId + ", childInfo=" + childInfo + ", viewerInfo=" + viewerInfo
				+ ", isLike=" + isLike + "]";
	}
}
