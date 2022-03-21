package com.assignment.virtualfilesystem.validate;

import com.assignment.virtualfilesystem.entity.Command;

public interface VirtualFileSystemValidator {

	public Command commandValidate(String rawCommand);
	
	public boolean nameValidate(String name);
	
}
