package com.photoexhibition.service.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="advertise_")
public class AdvertiseInfo {
	@Id
	/*@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "advertise_info_seq")
	@SequenceGenerator(name = "advertise_info_seq",  sequenceName = "advertise_info_seq",allocationSize=1, initialValue = 1)
	@Column(name = "advertise_id")*/
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long advertiseId;
	
	@Column(name="advertise_name")
	private String advertiseName;
	
	@Column(name="advertise_photo_dlfile_id")
	private long advertisePhotoDLFileId;
	
	@Column(name="advertise_status")
	private boolean activeStatus;
	
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
	public long getAdvertisePhotoDLFileId() {
		return advertisePhotoDLFileId;
	}
	public void setAdvertisePhotoDLFileId(long advertisePhotoDLFileId) {
		this.advertisePhotoDLFileId = advertisePhotoDLFileId;
	}
	public boolean isActiveStatus() {
		return activeStatus;
	}
	public void setActiveStatus(boolean activeStatus) {
		this.activeStatus = activeStatus;
	}
	@Override
	public String toString() {
		return "AdvertiseInfo [advertiseId=" + advertiseId + ", advertiseName=" + advertiseName
				+ ", advertisePhotoDLFileId=" + advertisePhotoDLFileId + ", activeStatus=" + activeStatus + "]";
	}
}
