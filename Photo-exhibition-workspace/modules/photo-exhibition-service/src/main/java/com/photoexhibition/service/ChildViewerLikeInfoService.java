package com.photoexhibition.service;

import java.util.List;

import com.photoexhibition.service.model.ChildViewerLikeInfo;

public interface ChildViewerLikeInfoService {
	public void saveOrUpdate(ChildViewerLikeInfo childViewerLikeInfo);
	public ChildViewerLikeInfo getChildVieweLikeInfoById(long childViewerLikeId);
	public List<ChildViewerLikeInfo> getChildViewerLikeInfoListByChildId(long childId);
	public List<ChildViewerLikeInfo> getChildViewerLikeInfoListByViewerId(long viewerId);
	public ChildViewerLikeInfo getChildViewerLikeInfoByChildAndViewerId(long childId, long viewerId);
}
