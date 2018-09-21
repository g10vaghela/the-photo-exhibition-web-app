package com.photoexhibition.service;

import java.util.List;

import com.photoexhibition.service.model.ChildViewerLikeInfo;
import com.photoexhibition.service.vo.ChildInfoVO;

public interface ChildViewerLikeInfoService {
	public void saveOrUpdate(ChildViewerLikeInfo childViewerLikeInfo);
	public ChildViewerLikeInfo getChildVieweLikeInfoById(long childViewerLikeId);
	public List<ChildViewerLikeInfo> getChildViewerLikeInfoListByChildId(long childId);
	public List<ChildViewerLikeInfo> getChildViewerLikeInfoListByViewerId(long viewerId);
	public ChildViewerLikeInfo getChildViewerLikeInfoByChildAndViewerId(long childId, long viewerId);
	public List<ChildInfoVO> getChildInfoVOByTopLimit(int topLimit);
	public int countTotalLikeByChildId(long childId);
	public int countTotalLikeByViewerId(long viewerId);
}
