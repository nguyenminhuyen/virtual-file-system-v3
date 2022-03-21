package com.assignment.virtualfilesystem.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity  
@DiscriminatorValue("folder")  
public class Folder extends Component {

	//@OneToMany(mappedBy="parent", fetch = FetchType.LAZY)
	//private List<Component> subordinates = new ArrayList<Component>();

	public Folder() {
		
	}

	/*
	public List<Component> getSubordinates() {
		return subordinates;
	}

	public void setSubordinates(List<Component> subordinates) {
		this.subordinates = subordinates;
	}
	*/
}
