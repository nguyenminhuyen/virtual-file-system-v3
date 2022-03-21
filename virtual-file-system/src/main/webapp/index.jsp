
<html>
<head>

<title>Virtual File System</title>

<style>

.main {
	width: 600px;
}

.result-console {
	height: 300px;
	overflow-y: auto;
	padding: 5px;
	background: #eee;
}
</style>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>


</head>
<body>

<div class="main">

	<h1>Virtual File System</h1>
	
	<hr>
	
	<form id="form">
		<div class="command-console">
			<input class="form-input" type="text" id="command" />
			<div class="submit-button">
				<button type="submit" id="sub-btn" >
					Submit
				</button>
			</div>
			
		</div>
	
	</form>
	
	
	<div class="result-console" id="result"></div>

</div>


<script>
	$("#form").on('submit', function(event) {
		event.preventDefault();

		var command = $("#command").val();
		
		if (command.length > 0) {
			var result = $("#result");
			result.append(command + "<br>");
			$("#sub-btn").prop("disabled", true);
			
			$.ajax({
				type : "POST",
				contentType : "application/json",
				url : "http://localhost:8080/virtual-file-system/api/vfs",
				data : command,
				dataType : 'text',
				success : function(data, textStatus, xhr) {
					result.append(data.message + "<br>");
				},
				complete : function(response) {
					$("#sub-btn").prop("disabled", false);
					$("#command").val("");
					result.append(response.responseText + "<br>");

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