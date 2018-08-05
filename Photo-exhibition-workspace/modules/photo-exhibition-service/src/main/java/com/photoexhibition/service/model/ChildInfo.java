package com.photoexhibition.service.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.liferay.portal.kernel.util.StringPool;

@Entity
@Table(name="child_")
public class ChildInfo {
	@Id
	/*@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "child_info_seq")
	@SequenceGenerator(name = "child_info_seq",  sequenceName = "child_info_seq",allocationSize=1, initialValue = 1)
	@Column(name = "child_id")*/
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long childId;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="middle_name")
	private String middleName;
	
	@Column(name="last_name")
	private String lastName;
	
	@Column(name="contact_no")
	private String contactNo;
	
	@Column(name="photo_url")
	private long photoUrl;
	
	@Column(name="rank")
	private long childRank;
	
	@Column(name="status")
	private boolean status;
	
	@Column(name="date_of_birth", nullable = true)
	private Date dateOfBirth;

	public long getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(long photoUrl) {
		this.photoUrl = photoUrl;
	}
	
	public long getChildId() {
		return childId;
	}

	public void setChildId(long childId) {
		this.childId = childId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public long getChildRank() {
		return childRank;
	}

	public void setChildRank(long childRank) {
		this.childRank = childRank;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	
	public String getFullName() {
		StringBuilder fullName = new StringBuilder();
		fullName.append(firstName);
		fullName.append(StringPool.SPACE);
		fullName.append(middleName);
		fullName.append(StringPool.SPACE);
		fullName.append(lastName);
		return fullName.toString();
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	@Override
	public String toString() {
		return "ChildInfo [childId=" + childId + ", firstName=" + firstName + ", middleName=" + middleName
				+ ", lastName=" + lastName + ", contactNo=" + contactNo + ", photoUrl=" + photoUrl + ", childRank="
				+ childRank + ", status=" + status + ", dateOfBirth=" + dateOfBirth + "]";
	}
}
