
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