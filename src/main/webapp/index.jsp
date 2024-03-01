<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- Bootstrap CSS -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous">

<title>Insert title here</title>

<style type="text/css">
body{
background: #DCDCDC;
}
form {
	position: absolute;
	top: 30%;
	left: 35%;
	right: 35%
}

h5 {
	position: absolute;
	top: 20%;
	left: 35%;
}

h7 {
	position: absolute;
	top: 65%;
	left: 35%;
}

input {
	padding: 5px 10px;
}
</style>
</head>
<body>
	<h5>Bem Vindo ao curos de JSP</h5>

	<%
	out.print("seu sucesso � garantido");
	%>

	<form action="<%=request.getContextPath() %>/ServletLogin" method="post"
		class="row g-3 needs-validation"novalidate>
		<input type="hidden" value="<%=request.getParameter("url")%>"
			name="url">

		<div class="mb-3">
			<label class="form-label">Login</label> <input type="text"
				class="form-control" name="login" required>
			<div class="valid-feedback">Ok!</div>
			<div class="invalid-feedback">Obrigat�rio</div>
		</div>

		<div class="mb-3">
			<label class="form-label">Senha</label> <input type="password"
				class="form-control" name="senha" required>
			<div class="valid-feedback">Ok!</div>
			<div class="invalid-feedback">Obrigat�rio</div>
		</div>

		<div style="text-align: left;">
			<input type="submit" value="enviar" class="btn btn-primary"
				style="font-size: 15px; padding: 5px 40px; border-radius: 30px;">
		</div>
	</form>

	<h7 style="color: red;">${msg}</h7>
	/*resposta da mensagem*/

	<!-- Option 1: Bootstrap Bundle with Popper -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
		crossorigin="anonymous"></script>

	<script type="text/javascript">
		// Example starter JavaScript for disabling form submissions if there are invalid fields
		(function() {
			'use strict'

			// Fetch all the forms we want to apply custom Bootstrap validation styles to
			var forms = document.querySelectorAll('.needs-validation')

			// Loop over them and prevent submission
			Array.prototype.slice.call(forms).forEach(function(form) {
				form.addEventListener('submit', function(event) {
					if (!form.checkValidity()) {
						event.preventDefault()
						event.stopPropagation()
					}

					form.classList.add('was-validated')
				}, false)
			})
		})()
	</script>
</body>
</html>