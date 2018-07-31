package com.photoexhibition.service;

import com.photoexhibition.service.model.ChildInfo;

public interface ChildInfoService {
	public void saveOrUpdate(ChildInfo childInfo);
	public ChildInfo getChildInfoByChildRank(long childRank);
}
