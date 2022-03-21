package com.assignment.virtualfilesystem.entity;

import com.assignment.virtualfilesystem.validate.EValidCommand;

public class Command {

	private EValidCommand code;
	private boolean pFlag;
	private String[] paths;
	private String folderPath;
	private String name;
	private String data;
	
	public Command() {
		
	}

	public Command(EValidCommand code, boolean pFlag, String[] paths, String folderPath, String name, String data) {
		this.code = code;
		this.pFlag = pFlag;
		this.paths = paths;
		this.folderPath = folderPath;
		this.name = name;
		this.data = data;
	}

	public String getFolderPath() {
		return folderPath;
	}
	
	public void setFolderPath(String folderPath) {
		this.folderPath = folderPath;
	}

	public EValidCommand getCode() {
		return code;
	}

	public void setCode(EValidCommand code) {
		this.code = code;
	}

	public String[] getPaths() {
		return paths;
	}

	public void setPaths(String[] paths) {
		this.paths = paths;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean ispFlag() {
		return pFlag;
	}

	public void setpFlag(boolean pFlag) {
		this.pFlag = pFlag;
	}

}
