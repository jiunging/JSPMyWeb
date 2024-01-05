<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../include/header.jsp" %>
<head>

<style>
	.div_center {
		margin-bottom: 20px;
		margin-top:20px;
		padding: 30px 15px;
	}
	.div_center p {
		color:red;
	}
</style>
</head>
<body>


	<div align="center" class="div_center">
		<h3>현재 비밀번호를 입력하세요.</h3>
		<hr>

		<form action="" method="post"> 
			비밀번호: <input type="password" name="pw">&nbsp;
			<input type="submit" value="확인" class="btn btn-default"> 
		</form>

	</div>
	
</body>

<%@ include file="../include/footer.jsp" %>