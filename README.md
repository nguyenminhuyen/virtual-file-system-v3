# Virtual File System
## Introduction
This project is a web-based application that can support file system management like the UNIX command line. In particular, it supports *create*, *cat*, *list*, *move*, *remove* commands on file and folder in the file system. The file system is stored on a server-side SQL database.

## Demo
https://drive.google.com/file/d/1FjSFm8mpldGUVuXPNcxXzcFAfCR3Z_SJ/view?usp=sharing

## Technology
- Java 1.8
- Spring REST
- Hibernate
- JSP Servlet 
- JQuery 
- MySQL

## Folder structure
```bash
├── src/main/java
│   ├── com.assignment.virtualfilesystem.config
│         └── DemoAppConfig.java
|         └── MySpringMvcDispatcherServletInitializer.java
|         └── TestJdbc.java
│   ├── com.assignment.virtualfilesystem.dao
│         └── VirtualFileSystemDAO.java
|         └── VirtualFileSystemDAOImpl.java
│   ├── com.assignment.virtualfilesystem.entity
│          └── Command.java
│          └── Component.java
│          └── File.java
|          └── Folder.java
│   ├── com.assignment.virtualfilesystem.exception
│          └── VFSDatabaseException.java
│          └── VFSErrorResponse.java
│          └── VFSExistedNameException.java
│          └── VFSInvalidNameException.java
│          └── VFSInvalidSyntaxException.java
│          └── VFSMismathTypeException.java
│          └── VFSNotFoundException.java
│          └── VFSParentNotFoundException.java
│          └── VFSResponseMessage.java
│          └── VFSRestExceptionHandler.java
│   ├── com.assignment.virtualfilesystem.rest
│          └── VirtualFileSystemController.java
│   ├── com.assignment.virtualfilesystem.service
│          └── VirtualFileSystemService.java
│          └── VirtualFileSystemServiceImpl.java
│   ├── com.assignment.virtualfilesystem.validate
│          └── CommandValidator.java
│          └── CommandValidatorImpl.java
│          └── EValidCommand.java
│          └── VirtualFileSystemValidator.java
│          └── VirtualFileSystemValidatorImpl.java
├── src/main/resources
|   ├── persistence-mysql.properties
├── src/main/webapp
|   ├── index.jsp
```

## Preview
![Preview 1](/images/preview1.png)


![Preview 2](/images/preview2.png)


![Preview 3](/images/preview3.png)


![Preview 4](/images/preview4.png)

## How to install and run project
- Clone this project
- In Eclipse, import this project at `File/Import/Maven/Existing Maven Projects`
- Add Tomcat server to Eclipse workspace
- In MySQL, run SQL scripts in folder `sql-scripts` to init the databse
- In Eclipse, run the project on server
- Open [http://localhost:8080/virtual-file-system/](http://localhost:8080/virtual-file-system/) to view it in your browser.
