/*
 * package com.assignment.virtualfilesystem.validate;
 * 
 * import java.util.ArrayList; import java.util.List;
 * 
 * import org.springframework.stereotype.Service;
 * 
 * import com.assignment.virtualfilesystem.entity.Command; import
 * com.assignment.virtualfilesystem.exception.VFSInvalidSyntaxException;
 * 
 * @Service public class VirtualFileSystemValidatorImpl implements
 * VirtualFileSystemValidator {
 * 
 * @Override public Command commandValidate(String strCommand) {
 * 
 * String[] parts = strCommand.split(" ");
 * 
 * EValidCommand cmdCode = EValidCommand.valueOf(parts[0]);
 * 
 * List<Integer> idx = new ArrayList<>(); for (int index =
 * strCommand.indexOf("\""); index >= 0; index = strCommand.indexOf("\"", index
 * + 1)){ idx.add(index); }
 * 
 * Command command = new Command(); String data = "";
 * 
 * if (cmdCode == EValidCommand.cr) { command.setCode(cmdCode); if
 * (parts[1].equals("-p")) { command.setpFlag(true); command.setPaths(new
 * String[] {parts[2]}); int idxEndPath = 0; if (idx.size() > 1 &&
 * strCommand.indexOf(" ", 4) + 1 == idx.get(0)) { String path =
 * strCommand.substring(idx.get(0) + 1, idx.get(1)); idxEndPath = idx.get(1); }
 * else { idxEndPath = strCommand.indexOf(" ", 6); }
 * 
 * if (strCommand.contains(".")) { if (strCommand.indexOf(" ", idxEndPath + 2)
 * >= 0 && strCommand.charAt(idxEndPath) + 2 == '\"' &&
 * strCommand.charAt(strCommand.length()-1) == '\"') { data =
 * strCommand.substring(idxEndPath + 3, strCommand.length() - 2); } else if
 * (strCommand.indexOf(" ", idxEndPath + 2) < 0){ data =
 * strCommand.substring(idxEndPath + 2); } else { throw new
 * VFSInvalidSyntaxException("Invalid syntax!"); } } else {
 * 
 * } } else { command.setpFlag(false); command.setPaths(new String[]
 * {parts[1]}); if (parts.length > 2) { command.setData(parts[2]); } }
 * 
 * }
 * 
 * return null; }
 * 
 * }
 */