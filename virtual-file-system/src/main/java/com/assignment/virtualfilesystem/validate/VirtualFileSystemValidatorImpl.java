package com.assignment.virtualfilesystem.validate;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assignment.virtualfilesystem.entity.Command;
import com.assignment.virtualfilesystem.exception.VFSInvalidSyntaxException;

@Service
public class VirtualFileSystemValidatorImpl implements VirtualFileSystemValidator {
	
	public static final String nameRegex = "^[a-zA-Z0-9 _-]+$";
	
	@Autowired
	private CommandValidator commandValidator;

	@Override
	public Command commandValidate(String strCommand) {

		String[] parts = strCommand.split(" ");
		
		if (!EValidCommand.contains(parts[0])){
			throw new VFSInvalidSyntaxException("Invalid syntax!");
		}
		
		EValidCommand cmdCode = EValidCommand.valueOf(parts[0]);

		List<Integer> idx = new ArrayList<>();
		for (int index = strCommand.indexOf("\""); index >= 0; index = strCommand.indexOf("\"", index + 1)) {
			idx.add(index);
		}

		switch (cmdCode) {
		case cr:
			return commandValidator.createCommandValidate(strCommand);
		case cat:
			return commandValidator.catCommandValidate(strCommand);
		case ls:
			return commandValidator.listCommandValidate(strCommand);
		case mv:
			return commandValidator.moveCommandValidate(strCommand);
		case rm:
			return commandValidator.removeCommandValidate(strCommand);
		}

		return null;
	}
	
	@Override
	public boolean nameValidate(String name) {
		return name.matches(nameRegex);
	}

}