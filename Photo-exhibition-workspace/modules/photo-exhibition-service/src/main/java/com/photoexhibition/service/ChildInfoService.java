package com.photoexhibition.service;

import java.util.List;

import com.photoexhibition.service.model.ChildInfo;

public interface ChildInfoService {
	public void saveOrUpdate(ChildInfo childInfo);
	public ChildInfo getChildInfoByChildRank(long childRank);
	public List<ChildInfo> getChildInfoList();
}
