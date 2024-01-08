<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<title>지엉의 회원가입 양식</title>
 	<style>
		body {
         background-image: url(data:image/jpeg;base64,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);
            background-size: 90%;
        }
	
	
	</style>
</head>
<body>

<!-- 	<img src = "../img\um.png"> -->

	<div align="center" class="div_center">
		<h3>JORDER 회원가입</h3>
		<hr>
		<form action="" method="post" name="reg_form">
			<table>
				<tr>
					<td>아이디</td>
					<td>
						<input type="text" name="id" required>
					</td>
				</tr>
				<tr>
					<td>비밀번호</td>
					<td>
						<input type="password" name="pw" required>
					</td>
				</tr>
				<tr>
					<td>이름</td>
					<td>
						<input type="text" name="name" required>
					</td>
				</tr>
				<tr>
					<td>이메일</td>
					<td>
						<input type="email" name="email" required>
					</td>
				</tr>
			</table>
			
			<input type="submit" value="회원가입" class="btn btn-default">
			<input type="reset" value="취소" class="btn btn-default" onclick="">
		
		
		
		
		
		
		
		</form>
		
	
	
		
	
	
	
	
	
	
	</div>

</body>
</html>