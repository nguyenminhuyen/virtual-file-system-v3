package com.assignment.virtualfilesystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.assignment.virtualfilesystem.dao.VirtualFileSystemDAO;
import com.assignment.virtualfilesystem.entity.Component;
import com.assignment.virtualfilesystem.entity.File;
import com.assignment.virtualfilesystem.entity.Folder;

@Service
public class VirtualFileSystemServiceImpl implements VirtualFileSystemService {
	
	@Autowired
	private VirtualFileSystemDAO vfsDAO;
	
	public void increaseAncestorSize(Component component, long size) {
		
		Component theAncestor = component.getParent();
		while (theAncestor != null) {
			vfsDAO.increaseComponentSize(theAncestor.getId(), size);
			theAncestor = theAncestor.getParent();
		}
		
	}
	
	public void decreaseAncestorSize(Component component, long size) {
		
		Component theAncestor = component.getParent();
		while (theAncestor != null) {
			vfsDAO.decreaseComponentSize(theAncestor.getId(), size);
			theAncestor = theAncestor.getParent();
		}
		
	}
	
	public boolean checkUniqueName(String path) {
		
		Component component = vfsDAO.getComponentFromPath(path);
		
		if (component != null)
			return false;
		
		return true;
	}

	@Override
	@Transactional
	public Component createComponent(String thePath, String theData) {
		
		if (checkUniqueName(thePath) == false) {
			return null;
		}
		
		String[] partOfPath = thePath.split("-");
		String theName = partOfPath[partOfPath.length - 1];
		
		String theParentPath = "";
		for (int i = 0; i < partOfPath.length - 1; i++) {
			theParentPath += partOfPath[i];
			if (i < partOfPath.length - 2)
				theParentPath += "-";
		}
		System.out.println("The parent path: " + theParentPath);
		
		Component theParent = vfsDAO.getComponentFromPath(theParentPath);
		
		if (theParent == null) {
			return null;
		}
		
		if (theData != null) {
			File theComponent = new File();
			theComponent.setId(0);
			int dataSize = theData.length();
			System.out.println("Data size: " + dataSize);
			theComponent.setSize(dataSize);
			theComponent.setCreateAt(System.currentTimeMillis());
			theComponent.setName(theName);
			theComponent.setParent(theParent);
			theComponent.setData(theData);

			increaseAncestorSize(theComponent, dataSize);
			
			vfsDAO.createComponent(theComponent);
			
			return theComponent;
		}
		else {
			Folder theComponent = new Folder();
			theComponent.setId(0);
			theComponent.setCreateAt(System.currentTimeMillis());
			theComponent.setName(theName);
			theComponent.setParent(theParent);
			theComponent.setSize(0);
			vfsDAO.createComponent(theComponent);
			
			return theComponent;
		}

	}

	@Override
	@Transactional
	public String getData(String thePath) {
		
		Component theComponent = vfsDAO.getComponentFromPath(thePath);
		
		if (theComponent == null) {
			return null;
		}
		
		if (theComponent.getClass().getSimpleName().equals("File")) {
			return ((File) theComponent).getData();
		}
		
		return null;
	}

	@Override
	@Transactional
	public List<Component> getDescendants(String thePath) {
		
		Component theComponent = vfsDAO.getComponentFromPath(thePath);
		
		return vfsDAO.getDescendants(theComponent.getId());
	}

	@Override
	@Transactional
	public boolean moveComponent(String thePath, String destPath) {
		Component theComponent = vfsDAO.getComponentFromPath(thePath);
		Component destComponent = vfsDAO.getComponentFromPath(destPath);
		
		if (destComponent == null || theComponent == null || destComponent.getClass().getSimpleName().equals("File")) {
			return false;
		}
		
		if (destPath.contains(thePath)) {
			return false;
		}
		
		long compSize = theComponent.getSize();
		
		decreaseAncestorSize(theComponent, compSize);

		vfsDAO.moveComponent(theComponent.getId(), destComponent.getId());
		theComponent.setParent(destComponent);
		
		increaseAncestorSize(theComponent, compSize);
		
		return true;
	}

	@Override
	@Transactional
	public void deleteComponent(String thePath) {
		
		Component theComponent = vfsDAO.getComponentFromPath(thePath);
		
		long compSize = theComponent.getSize();
		
		decreaseAncestorSize(theComponent, compSize);
		
		vfsDAO.deleteComponent(theComponent.getId());

	}
	
	@Override
	@Transactional
	public boolean updateComponent(String thePath, String name, String data) {
		
		Component theComponent = vfsDAO.getComponentFromPath(thePath);
		
		if (theComponent == null) {
			return false;
		}
		
		if (theComponent.getClass().getSimpleName().equals("Folder")) {
			data = null;
		}
		
		vfsDAO.updateComponent(theComponent.getId(), name, data);
		return true;
	}

}
