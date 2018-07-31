package com.photoexhibition.service.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="child_")
public class ChildInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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
		return "ChildInfo [id=" + id + ", firstName=" + firstName + ", middleName=" + middleName + ", lastName="
				+ lastName + ", contactNo=" + contactNo + ", dlFileId=" + dlFileId + ", childRank=" + childRank + "]";
	}
}
