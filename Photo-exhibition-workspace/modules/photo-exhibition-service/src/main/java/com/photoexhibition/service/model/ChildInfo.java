package com.photoexhibition.service.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="child_")
public class ChildInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "child_info_seq")
	@SequenceGenerator(name = "child_info_seq",  sequenceName = "child_info_seq",allocationSize=1, initialValue = 1)
	@Column(name = "child_id")
	private long childId;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="middle_name")
	private String middleName;
	
	@Column(name="last_name")
	private String lastName;
	
	@Column(name="contact_no")
	private String contactNo;
	
	@Column(name="dl_file_id")
	private long dlFileId;
	
	@Column(name="rank")
	private long childRank;

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

	public long getDlFileId() {
		return dlFileId;
	}

	public void setDlFileId(long dlFileId) {
		this.dlFileId = dlFileId;
	}

	public long getChildRank() {
		return childRank;
	}

	public void setChildRank(long childRank) {
		this.childRank = childRank;
	}

	@Override
	public String toString() {
		return "ChildInfo [childId=" + childId + ", firstName=" + firstName + ", middleName=" + middleName
				+ ", lastName=" + lastName + ", contactNo=" + contactNo + ", dlFileId=" + dlFileId + ", childRank="
				+ childRank + "]";
	}
}
