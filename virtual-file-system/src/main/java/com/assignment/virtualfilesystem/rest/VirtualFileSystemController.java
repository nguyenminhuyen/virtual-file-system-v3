package com.assignment.virtualfilesystem.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.virtualfilesystem.entity.Command;
import com.assignment.virtualfilesystem.entity.Component;
import com.assignment.virtualfilesystem.entity.RawCommand;
import com.assignment.virtualfilesystem.service.VirtualFileSystemService;

@RestController
@RequestMapping("/api")
public class VirtualFileSystemController {

	@Autowired
	private VirtualFileSystemService vfsService;
	
	@PostMapping("/vfs")
	public String execute(@RequestBody String strCommand) {
		
		System.out.println("the strCommand: " + strCommand);
		String[] parts = strCommand.split(" ");
		
		String cmdCode = parts[0];
		System.out.println("cmd code: " + cmdCode);
		Command command = new Command();
		String res = "";
		
		switch (cmdCode) {
		case "cr":
			command.setCode(parts[0]);
			if (parts[1].equals("-p")) {
				command.setpFlag(true);
				command.setPaths(new String[] {parts[2]});
				if (parts.length > 3) {
					command.setData(parts[3]);
				}
			}
			else {
				command.setpFlag(false);
				command.setPaths(new String[] {parts[1]});
				if (parts.length > 2) {
					command.setData(parts[2]);
				}
			}
			
			vfsService.createComponent(command.getPaths()[0], command.getData());
			
			break;
		
		case "cat":

			command.setCode(parts[0]);
			command.setPaths(new String[] {parts[1]});
			
			String theData = vfsService.getData(command.getPaths()[0]);
			
			if (theData == null) {
				res = "Invalid path of file";
			}
			res = theData;
			
			break;
			
		case "ls":
			
			//command.setCode(parts[0]);
			//command.setPaths(new String[] {parts[1]});
			
			//String path = command.getPaths()[0];
			String path = parts[1];
			List<Component> theChilds = vfsService.getDescendants(path);
			
			res = String.valueOf(theChilds.size());
			
			for (Component c : theChilds) {
				res += ("Name: " + c.getName() + ". Created at: " + String.valueOf(c.getCreateAt())
						+ ". Size: " + String.valueOf(c.getSize()) + "\n");
			}
			
			System.out.println(res);
			
			break;
			
		case "mv":
			
			boolean result = vfsService.moveComponent(parts[1], parts[2]);
			
			if (result) {
				res = "Move " + parts[1] + " to path " + parts[2] + " successfully!";
			}
			else {
				res = "Invalid path or folder path";
			}
			
			break;
		
		case "rm":
			
			for (int i=1;i<parts.length;i++) {
				vfsService.deleteComponent(parts[i]);
			}
			
			res = "Deleted component(s) successfully!";
			
			break;
			
		case "up":
			command.setPaths(new String[] {parts[1]});
			command.setName(parts[2]);
			if (parts.length > 3) {
				command.setData(parts[3]);
			}
			else {
				command.setData(null);
			}
			
			result = vfsService.updateComponent(command.getPaths()[0], command.getName(), command.getData());
			
			if (result) {
				return "Update " + command.getPaths()[0] + " successfully!";
			}
			else {
				return "Invalid path";
			}
		}
		
		return res;
	}
	
	/*@PostMapping("/vfs")
	public Component createComponent(@RequestBody RawCommand rCommand) {
		String strCommand = rCommand.getCommand();
		System.out.println("the strCommand: " + strCommand);
		String[] parts = strCommand.split(" ");
		
		Command command = new Command();
		command.setCode(parts[0]);
		if (parts[1].equals("-p")) {
			command.setpFlag(true);
			command.setPaths(new String[] {parts[2]});
			if (parts.length > 3) {
				command.setData(parts[3]);
			}
		}
		else {
			command.setpFlag(false);
			command.setPaths(new String[] {parts[1]});
			if (parts.length > 2) {
				command.setData(parts[2]);
			}
		}
		
		String path = command.getPaths()[0];
		String data = command.getData();
		System.out.println("Path: " + path);
		System.out.println("Data: " + data);
		
		return vfsService.createComponent(path, data);
		
	}*/
	
	@GetMapping("/vfs")
	public String getData(@RequestBody RawCommand rCommand) {
		
		String strCommand = rCommand.getCommand();
		
		System.out.println("the strCommand: " + strCommand);
		String[] parts = strCommand.split(" ");
		
		Command command = new Command();
		command.setCode(parts[0]);
		command.setPaths(new String[] {parts[1]});
		
		String path = command.getPaths()[0];
		String theData = vfsService.getData(path);
		
		if (theData == null) {
			return "Invalid path of file";
		}
		
		return theData;
	}
	
	@GetMapping("/vfs/list")
	public List<Component> getComponent(@RequestBody Command command) {
		String path = command.getPaths()[0];
		List<Component> theChilds = vfsService.getDescendants(path);
		
		return theChilds;
	}
	
	@PutMapping("/vfs/move")
	public String moveComponent(@RequestBody Command theCommand) {
		
		String path = theCommand.getPaths()[0];
		String folderPath = theCommand.getFolderPath();
		
		boolean res = vfsService.moveComponent(path, folderPath);
		
		if (res) {
			return "Move " + path + " to path " + folderPath + " successfully!";
		}
		else {
			return "Invalid path or folder path";
		}
	}
	
	@DeleteMapping("/vfs")
	public String deleteComponent(@RequestBody Command theCommand) {
		
		String[] paths = theCommand.getPaths();
		
		for (String path : paths) {
			vfsService.deleteComponent(path);
		}
		
		return "Deleted component at path(s): " + paths;
	}
	
	@PutMapping("/vfs")
	public String updateComponent(@RequestBody Command theCommand) {
		
		String path = theCommand.getPaths()[0];
		String name = theCommand.getName();
		String data = theCommand.getData();
		
		boolean res = vfsService.updateComponent(path, name, data);
		
		if (res) {
			return "Update " + path + " successfully!";
		}
		else {
			return "Invalid path";
		}
	}
	
}
