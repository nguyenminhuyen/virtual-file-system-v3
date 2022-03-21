package com.assignment.virtualfilesystem.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity  
@DiscriminatorValue("file")  
public class File extends Component {

	@Column(name = "data")
	private String data;
	
	public File() {
		
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
}
