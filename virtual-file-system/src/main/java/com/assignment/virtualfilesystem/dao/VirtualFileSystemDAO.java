package com.assignment.virtualfilesystem.dao;

import java.util.List;

import com.assignment.virtualfilesystem.entity.Component;

public interface VirtualFileSystemDAO {
	
	public Component getComponentFromPath(String path);
	
	public long getComponentSize(int theId);
	
	public void updateComponentSize(int theId, long size);
	
	public void increaseComponentSize(int theId, long size);
	
	public void decreaseComponentSize(int theId, long size);

	public void createComponent(Component theComponent);
	
	public Component getComponent(int theId);
	
	public String getData(int theId);
	
	public List<Component> getDescendants(int theId);
	
	public void moveComponent(int theId, int destId);

	public void deleteComponent(int theId);
	
	public void updateComponent(int theId, String name, String data);
	
}
