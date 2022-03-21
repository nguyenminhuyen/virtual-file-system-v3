package com.assignment.virtualfilesystem.validate;

import com.assignment.virtualfilesystem.entity.Command;

public interface CommandValidator {
	
	public Command createCommandValidate(String strCommand);
	
	public Command catCommandValidate(String strCommand);
	
	public Command listCommandValidate(String strCommand);
	
	public Command moveCommandValidate(String strCommand);
	
	public Command removeCommandValidate(String strCommand);
	
}
