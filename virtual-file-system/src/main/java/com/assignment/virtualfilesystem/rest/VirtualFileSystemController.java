package com.assignment.virtualfilesystem.rest;

import java.util.Calendar;
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
import com.assignment.virtualfilesystem.validate.EValidCommand;
import com.assignment.virtualfilesystem.validate.VirtualFileSystemValidator;

@RestController
@RequestMapping("/api")
public class VirtualFileSystemController {

	@Autowired
	private VirtualFileSystemService vfsService;
	
	@Autowired
	private VirtualFileSystemValidator vfsValidator;
	
	@PostMapping("/vfs")
	public ResponseEntity<VFSResponseMessage> execute(@RequestBody String strCommand) {
		
		Command command = new Command();
		command = vfsValidator.commandValidate(strCommand);
		
		EValidCommand cmdCode = command.getCode();
		
		String res = "";
		
		VFSResponseMessage response = new VFSResponseMessage();
		
		switch (cmdCode) {
		case cr:
			
			res = "Create " + command.getPaths()[0]; 
			
			try {
				res += vfsService.createComponent(command.getPaths()[0], command.getData(), command.ispFlag()) 
						? " successfully!" : " unsuccessfully!";
			} catch (Exception e) {
				res = e.getMessage();
			}
			
			break;
		
		case cat:

			String theData = vfsService.getData(command.getPaths()[0]);
			
			if (theData == null) {
				res = "Invalid path of file";
			}
			res = theData;
			
			break;
			
		case ls:
			
			List<Component> theChilds = vfsService.getDescendants(command.getPaths()[0]);
			
			for (Component c : theChilds) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTimeInMillis(c.getCreateAt());
				int mYear = calendar.get(Calendar.YEAR);
				int mMonth = calendar.get(Calendar.MONTH) + 1;
				int mDay = calendar.get(Calendar.DAY_OF_MONTH);
				int mHour = calendar.get(Calendar.HOUR);
				int mMin = calendar.get(Calendar.MINUTE);
				int mSecond = calendar.get(Calendar.SECOND);
				res += ("Name: " + c.getName() 
						+ ". Created at: " 
						+ String.valueOf(mHour) 
						+ ":" + String.valueOf(mMin) 
						+ ":" + String.valueOf(mSecond) 
						+ " " + String.valueOf(mDay) 
						+ "/" + String.valueOf(mMonth) 
						+ "/" + String.valueOf(mYear)
						+ ". Size: " + String.valueOf(c.getSize()) + "\n");
			}
			
			break;
			
		case mv:
			
			boolean result = vfsService.moveComponent(command.getPaths()[0], command.getFolderPath());
			
			if (result) {
				res = "Move " + command.getPaths()[0] + " to path " + command.getFolderPath() + " successfully!";
			}
			else {
				res = "Invalid path or folder path";
			}
			
			break;
		
		case rm:
			
			for (String path : command.getPaths()) {
				vfsService.deleteComponent(path);
			}
			
			res = "Deleted component(s) successfully!";
			
			break;
		}
		
		response.setMessage(res);
		
		return new ResponseEntity<VFSResponseMessage>(new VFSResponseMessage(response.getMessage()), HttpStatus.OK);
	}
	
}
