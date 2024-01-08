<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>


	<div align="center" class="div_center">
		<form action="updateForm.user" method="post">
			<h3>회원정보 수정 페이지</h3>
			<hr>
			<table>
				<tr>
					<td>아이디<td>
					<td>
						<input type="text" name="id">
					</td>
				</tr>
				
				<tr>
					<td>비밀번호<td>
					<td>
						<input type="password" name="pw">
					</td>
				</tr>
				
				<tr>
					<td>비밀번호 확인<td>
					<td>
						<input type="password" name="pw">
					</td>
				</tr>
				
				<tr>
					<td>이름<td>
					<td>
						<input type="text" name="name">
					</td>
				</tr>
				
				<tr>
					<td>이메일<td>
					<td>
						<input type="email" name="email">
					</td>
				</tr>
				
			
			</table>
			
		
		<br>
		<input type="submit" value="수정" class="btn btn-default" onclick="">&nbsp;&nbsp;
		<input type="reset" value="취소" class="btn btn-default" onclick="">

		
		</form>

	</div>



</body>
</html>