package com.photoexhibition.service.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="general_config")
public class GeneralConfigurationInfo implements Serializable{
	
	@Id
	@Column(name = "config_key")
	private String key;
	
	@Column(name="config_value")
	private String value;
	
	@Column(name="config_message")
	private String message;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "GeneralConfigurationInfo [key=" + key + ", value=" + value + ", message=" + message + "]";
	}
}
