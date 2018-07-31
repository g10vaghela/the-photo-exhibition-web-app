package com.photoexhibition.service.impl;

import com.photoexhibition.service.EmployeeInfoService;
import com.photoexhibition.service.dao.EmployeeInfoDao;
import com.photoexhibition.service.model.EmployeeInfo;

public class EmployeeInfoServiceImpl implements EmployeeInfoService{
	private EmployeeInfoDao employeeInfoDao;

	public void setEmployeeInfoDao(EmployeeInfoDao employeeInfoDao) {
		this.employeeInfoDao = employeeInfoDao;
	}

	@Override
	public void saveOrUpdateEmployee(EmployeeInfo employeeInfo) {
		employeeInfoDao.saveOrUpdateEmployeeInfo(employeeInfo);
	}
}
