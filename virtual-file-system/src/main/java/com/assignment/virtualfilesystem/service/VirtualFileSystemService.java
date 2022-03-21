package com.assignment.virtualfilesystem.service;

import java.util.List;

import com.assignment.virtualfilesystem.entity.Component;

public interface VirtualFileSystemService {

	public Component createComponent(String thePath, String theData);
	
	public String getData(String thePath);
	
	public List<Component> getDescendants(String thePath);
	
	public boolean moveComponent(String thePath, String destPath);

	public void deleteComponent(String thePath);
	
	public boolean updateComponent(String thePath, String name, String data);

}
