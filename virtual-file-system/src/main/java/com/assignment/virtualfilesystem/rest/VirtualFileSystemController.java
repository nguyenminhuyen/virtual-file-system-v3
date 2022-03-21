package com.assignment.virtualfilesystem.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.virtualfilesystem.entity.Command;
import com.assignment.virtualfilesystem.entity.Component;
import com.assignment.virtualfilesystem.service.VirtualFileSystemService;

@RestController
@RequestMapping("/api")
public class VirtualFileSystemController {

	@Autowired
	private VirtualFileSystemService vfsService;
	
	@PostMapping("/vfs")
	public ResponseEntity<VFSResponseMessage> execute(@RequestBody String strCommand) {
		
		String[] parts = strCommand.split(" ");
		
		String cmdCode = parts[0];
		Command command = new Command();
		String res = "";
		
		VFSResponseMessage response = new VFSResponseMessage();
		
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
			
			res = "Create " + command.getPaths()[0]; 
			
			try {
				res += vfsService.createComponent(command.getPaths()[0], command.getData(), command.ispFlag()) 
						? " successfully!" : " unsuccessfully!";
			} catch (Exception e) {
				res = e.getMessage();
			}
			
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
			
			//res = String.valueOf(theChilds.size());
			
			for (int i=0;i<theChilds.size();i++) {
				res += ("Name: " + theChilds.get(i).getName() 
						+ ". Created at: " + String.valueOf(theChilds.get(i).getCreateAt())
						+ ". Size: " + String.valueOf(theChilds.get(i).getSize()) + "\n");
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
				res = "Update " + command.getPaths()[0] + " successfully!";
			}
			else {
				res = "Invalid path";
			}
		}
		
		response.setMessage(res);
		
		return new ResponseEntity<VFSResponseMessage>(new VFSResponseMessage(response.getMessage()), HttpStatus.OK);
	}
	
}
