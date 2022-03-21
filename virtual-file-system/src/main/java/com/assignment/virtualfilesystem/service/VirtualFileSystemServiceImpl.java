package com.assignment.virtualfilesystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.assignment.virtualfilesystem.dao.VirtualFileSystemDAO;
import com.assignment.virtualfilesystem.entity.Component;
import com.assignment.virtualfilesystem.entity.File;
import com.assignment.virtualfilesystem.entity.Folder;
import com.assignment.virtualfilesystem.exception.VFSDatabaseException;
import com.assignment.virtualfilesystem.exception.VFSExistedNameException;
import com.assignment.virtualfilesystem.exception.VFSMismatchTypeException;
import com.assignment.virtualfilesystem.exception.VFSNotFoundException;
import com.assignment.virtualfilesystem.exception.VFSParentNotFoundException;

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
	public boolean createComponent(String thePath, String theData, boolean pFlag) {
		
		if (checkUniqueName(thePath) == false) {
			throw new VFSExistedNameException("The file/folder has already existed");
		}
		
		String[] partOfPath = thePath.split("/");
		String theName = partOfPath[partOfPath.length - 1];
		
		String theParentPath = "";
		for (int i = 0; i < partOfPath.length - 1; i++) {
			theParentPath += partOfPath[i];
			if (i < partOfPath.length - 2)
				theParentPath += "/";
		}
		System.out.println("The parent path: " + theParentPath);
		
		Component theParent = vfsDAO.getComponentFromPath(theParentPath);
		
		if (theParent == null) {
			throw new VFSParentNotFoundException("The parent folder not found");
		}
		
		if (theParent.getClass().getSimpleName().equals("File")) {
			throw new VFSMismatchTypeException("Can not create new item in a file");
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
			
			if (!vfsDAO.createComponent(theComponent)) {
				throw new VFSDatabaseException("Can not create file at path: " + thePath);
			}
			else {
				return true;
			}
		}
		else {
			Folder theComponent = new Folder();
			theComponent.setId(0);
			theComponent.setCreateAt(System.currentTimeMillis());
			theComponent.setName(theName);
			theComponent.setParent(theParent);
			theComponent.setSize(0);
			
			if (!vfsDAO.createComponent(theComponent)) {
				throw new VFSDatabaseException("Can not create folder at path: " + thePath);
			}
			else {
				return true;
			}
		}

	}

	@Override
	@Transactional
	public String getData(String thePath) {
		
		Component theComponent = vfsDAO.getComponentFromPath(thePath);
		
		if (theComponent == null) {
			throw new VFSNotFoundException("No file at path " + thePath);
		}
		
		if (theComponent.getClass().getSimpleName().equals("Folder")) {
			throw new VFSMismatchTypeException("No file at path " + thePath);
		}
		
		return ((File) theComponent).getData();
	}

	@Override
	@Transactional
	public List<Component> getDescendants(String thePath) {
		
		Component theComponent = vfsDAO.getComponentFromPath(thePath);
		
		if (theComponent == null) {
			throw new VFSNotFoundException("No file/folder at path " + thePath);
		}
		
		return vfsDAO.getDescendants(theComponent.getId());
	}

	@Override
	@Transactional
	public boolean moveComponent(String thePath, String destPath) {
		Component theComponent = vfsDAO.getComponentFromPath(thePath);
		Component destComponent = vfsDAO.getComponentFromPath(destPath);
		
		if (theComponent == null) {
			throw new VFSNotFoundException("No file/folder at path " + thePath);
		}
		
		if (destComponent == null) {
			throw new VFSNotFoundException("No file/folder at path " + destPath);
		}
		
		if (destComponent.getClass().getSimpleName().equals("File")) {
			throw new VFSMismatchTypeException("Can move an item to a file");
		}
		
		if (destPath.contains(thePath)) {
			throw new VFSMismatchTypeException("Can not move a folder to become a subfolder of itself");
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
		
		if (theComponent == null) {
			throw new VFSNotFoundException("No file/folder at path " + thePath);
		}
		
		long compSize = theComponent.getSize();
		
		decreaseAncestorSize(theComponent, compSize);
		
		vfsDAO.deleteComponent(theComponent.getId());

	}
	
	@Override
	@Transactional
	public boolean updateComponent(String thePath, String name, String data) {
		
		Component theComponent = vfsDAO.getComponentFromPath(thePath);
		
		if (theComponent == null) {
			throw new VFSNotFoundException("No file/folder at path " + thePath);
		}
		
		if (theComponent.getClass().getSimpleName().equals("Folder")) {
			data = null;
		}
		
		vfsDAO.updateComponent(theComponent.getId(), name, data);
		return true;
	}

}
