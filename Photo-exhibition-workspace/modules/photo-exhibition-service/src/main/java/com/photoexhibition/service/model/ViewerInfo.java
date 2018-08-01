package com.photoexhibition.service.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="viewer_")
public class ViewerInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long viewerId;
	
	@Column(name="mobile_number")
	private String mobileNumber;
	
	@Column(name="device_number")
	private String deviceNumber;
	
	@Column(name="opt")
	private String otp;
	
	@Column(name="is_otp_verified")
	private boolean isOtpVerified;

	public long getViewerId() {
		return viewerId;
	}

	public void setViewerId(long viewerId) {
		this.viewerId = viewerId;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getDeviceNumber() {
		return deviceNumber;
	}

	public void setDeviceNumber(String deviceNumber) {
		this.deviceNumber = deviceNumber;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public boolean isOtpVerified() {
		return isOtpVerified;
	}

	public void setOtpVerified(boolean isOtpVerified) {
		this.isOtpVerified = isOtpVerified;
	}

	@Override
	public String toString() {
		return "ViewerInfo [viewerId=" + viewerId + ", mobileNumber=" + mobileNumber + ", deviceNumber=" + deviceNumber
				+ ", otp=" + otp + ", isOtpVerified=" + isOtpVerified + "]";
	}
}
