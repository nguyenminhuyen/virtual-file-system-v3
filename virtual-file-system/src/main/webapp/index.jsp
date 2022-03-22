
<html>
<head>

<title>Virtual File System</title>

<style>
body {
	font-family: "Courier New", Courier, monospace
}
.main {
	margin: 50px auto;
	width: 600px;
}

.title {
	text-align: center;
}

.result-console {
	height: 300px;
	overflow-y: auto;
	padding: 5px;
	background: #000;
	color: #fff;
	font-size: 14px;
}

.form-input {
	width: 100%;
	height: 30px;
	border: solid 1px #dedede;
	border-radius: 3px;
}

.result-console p {
	margin: 10px 0;
}

.success-message {
	color: #0ebf0e;
}

.error-message {
	color: red;
}
</style>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>


</head>
<body>

<main class="main">
	<h1 class="title">Virtual File System</h1>
	
	<form id="form">
		<div class="command-console">
			<input class="form-input" type="text" id="command" autofocus autocomplete="off"/>
			<div class="submit-button">
			</div>
			
		</div>
	</form>
	
	<section class="result-console" id="result"></section>
	
	<br><br>
	
	<section class="instruction" id="inst">
		<h3>Command syntax</h3>
		<dl>
		  <dt><b>cr PATH [DATA]</b></dt>
		  <dd>Create a new file (if DATA is specified, otherwise create a new folder) at the specified PATH</dd>
		  
		  <dt><b>cat FILE_PATH</b></dt>
		  <dd>Show the content of a file at FILE_PATH</dd>
		  
		  <dt><b>ls FOLDER_PATH</b></dt>
		  <dd>List out all items directly under a folder</dd>
		  
		  <dt><b>mv PATH FOLDER_PATH</b></dt>
		  <dd>Move a file/folder at PATH into the destination FOLDER_PATH</dd>
		  
		  <dt><b>rm PATH [PATH2 PATH3...]</b></dt>
		  <dd>Remove files/folders at the specified PATH(s)</dd>
		  
		  <dt><b>clear</b></dt>
		  <dd>Clear the console</dd>
		</dl>
		
		<br>
		
		<strong>Note that:</strong>
		<ul>
			<li>PATH must start with <i>root</i>.</li>
			<li>If file/folder's name or DATA has a space, put the path or DATA in "".</li>
		</ul>
		
		<br>
		
		Example:
		<ul>
			<li>cr root/file1 "welcome to virtual system file"</li>
			<li>mv root/file1 "root/folder 123"</li>
		</ul>
	</section>
</main>


<script>
	
	$("#form").on('submit', function(event) {
		event.preventDefault();
	
		var command = $("#command").val();
		
		if (command.length > 0) {
			var result = $("#result");
			if(command === 'clear') {
				result.empty();
				$("#command").val("");
				return;
			}
			
			result.append("<p>> " + command + "</p>");
			
			$.ajax({
				type : "POST",
				contentType : "application/json",
				url : "http://localhost:8080/virtual-file-system/api/vfs",
				data : command,
				dataType : 'text',
				success : function(data, textStatus, xhr) {
					const response = JSON.parse(data).message;
					// Handle response multiple line
					if(response.includes('\n')) {
						const messages = response.split('\n');
						messages.forEach(mess => {
							result.append('<p class="success-message">' + mess + '</p>');
						})
					} else {
						result.append('<p class="success-message">' + response + '</p>');
					}
				},
			    error: function(xhr, status, error){
					const response = JSON.parse(xhr.responseText).message;
					result.append('<p class="error-message">' + response + '</p>');
			    },
				complete : function(response) {
					$("#command").val("");
	
					result.animate({
						scrollTop : result.prop("scrollHeight")
					}, 0);
				}
			});
		}		
	})
</script>
</body>
</html>