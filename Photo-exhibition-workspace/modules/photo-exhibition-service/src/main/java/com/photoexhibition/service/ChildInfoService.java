package com.photoexhibition.service;

import java.util.List;

import com.photoexhibition.service.model.ChildInfo;
import com.photoexhibition.service.search.criteria.ChildInfoSearchCriteria;

public interface ChildInfoService {
	public ChildInfo save(ChildInfo childInfo);
	public void saveOrUpdate(ChildInfo childInfo);
	public ChildInfo getChildInfoByChildRank(long childRank);
	public ChildInfo getChildInfoById(long childId);
	public List<ChildInfo> getChildInfoList();
	public List<ChildInfo> getChildInfoList(ChildInfoSearchCriteria childInfoSearchCriteria);
	public int getChildInfoCountBySearchCriteria(ChildInfoSearchCriteria childInfoSearchCriteria);
}
