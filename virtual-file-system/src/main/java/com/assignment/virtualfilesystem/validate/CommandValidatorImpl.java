package com.assignment.virtualfilesystem.validate;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.assignment.virtualfilesystem.entity.Command;
import com.assignment.virtualfilesystem.exception.VFSInvalidSyntaxException;

@Service
public class CommandValidatorImpl implements CommandValidator {

	@Override
	public Command createCommandValidate(String strCommand) {
		String[] parts = strCommand.split(" ");
		
		String data = "";
		String path = "";
		
		List<Integer> idx = new ArrayList<>();
		for (int index = strCommand.indexOf("\""); index >= 0; index = strCommand.indexOf("\"", index + 1)) {
			idx.add(index);
		}

		int idxEndPath = 0;
		if (idx.size() > 1 && strCommand.indexOf(" ") + 1 == idx.get(0)) {
			path = strCommand.substring(idx.get(0) + 1, idx.get(1));
			idxEndPath = idx.get(1);
		} 
		else {
			path = parts[1];
			idxEndPath = strCommand.indexOf(" ", 3) - 1;
			if (idxEndPath < -1) {
				idxEndPath = strCommand.length() - 1;
			}
		}

		// file
		if (idxEndPath != strCommand.length() - 1) {
			if (idxEndPath + 2 > strCommand.length() - 1) {
				throw new VFSInvalidSyntaxException("Invalid syntax!");
			}
			data = strCommand.substring(idxEndPath + 2);
			if (data.indexOf(" ") >= 0) {
				if (data.charAt(0) == '\"' && data.charAt(data.length() - 1) == '\"') {
					data = data.substring(1, data.length() - 1);
				}
				else {
					throw new VFSInvalidSyntaxException("Invalid syntax!");
				}
			}
		}
		// folder
		else {
			data = null;
		}
		
		Command command = new Command(EValidCommand.cr, true, new String[] {path}, null, null, data);
		
		return command;
	}

	@Override
	public Command catCommandValidate(String strCommand) {
		String path = strCommand.substring(4);
		if (path.indexOf(" ") >= 0) {
			if (path.charAt(0) == '\"' && path.charAt(path.length() - 1) == '\"') {
				path = path.substring(1, path.length() - 1);
			}
			else {
				throw new VFSInvalidSyntaxException("Invalid syntax!");
			}
		}
		
		Command command = new Command(EValidCommand.cat, true, new String[] {path}, null, null, null);
		
		return command;
	}

	@Override
	public Command listCommandValidate(String strCommand) {
		String path = strCommand.substring(3);
		if (path.indexOf(" ") >= 0) {
			if (path.charAt(0) == '\"' && path.charAt(path.length() - 1) == '\"') {
				path = path.substring(1, path.length() - 1);
			}
			else {
				throw new VFSInvalidSyntaxException("Invalid syntax!");
			}
		}
		
		Command command = new Command(EValidCommand.ls, true, new String[] {path}, null, null, null);
		
		return command;
	}

	@Override
	public Command moveCommandValidate(String strCommand) {
		List<Integer> paths = new ArrayList<>();
		for (int index = strCommand.indexOf("root"); index >= 0; index = strCommand.indexOf("root", index + 1)) {
			paths.add(index);
		}
		
		if (paths.size() != 2) {
			throw new VFSInvalidSyntaxException("Invalid syntax!");
		}
		
		String path = strCommand.substring(paths.get(0), paths.get(1) - 1);
		String folderPath = strCommand.substring(paths.get(1));

		if (path.charAt(path.length() - 1) == ' ')
			path = path.substring(0, path.length() - 1);
		if (path.charAt(path.length() - 1) == '\"')
			path = path.substring(0, path.length() - 1);
		
		System.out.println(path);
		
		if (folderPath.charAt(folderPath.length() - 1) == '\"') {
			folderPath = folderPath.substring(0, folderPath.length() - 1);
		}
		
		System.out.println(folderPath);
		
		Command command = new Command(EValidCommand.mv, true, new String[] {path}, folderPath, null, null);
		
		return command;
	}

	@Override
	public Command removeCommandValidate(String strCommand) {
		List<Integer> pathsIdx = new ArrayList<>();
		for (int index = strCommand.indexOf("root"); index >= 0; index = strCommand.indexOf("root", index + 1)) {
			pathsIdx.add(index);
		}
		
		if (pathsIdx.size() < 1) {
			throw new VFSInvalidSyntaxException("Invalid syntax!");
		}
		
		List<String> paths = new ArrayList<>();
		
		for (int i=0;i<pathsIdx.size()-1;i++) {
			String path = strCommand.substring(pathsIdx.get(i), pathsIdx.get(i+1) - 1);
			if (path.charAt(path.length() - 1) == ' ')
				path = path.substring(0, path.length() - 1);
			if (path.charAt(path.length() - 1) == '\"')
				path = path.substring(0, path.length() - 1);
			paths.add(path);
		}
		String path = strCommand.substring(pathsIdx.get(pathsIdx.size() - 1), strCommand.length());
		if (path.charAt(path.length() - 1) == '\"')
			path = path.substring(0, path.length() - 1);
		paths.add(path);
		
		Command command = new Command(EValidCommand.rm, true, paths.toArray(new String[paths.size()]), null, null, null);
		
		return command;
	}

}
