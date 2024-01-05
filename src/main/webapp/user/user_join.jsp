<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="../include/header.jsp" %>




	<div align="center" class="div_center">
		<h3>회원가입 클론코딩</h3>
		<hr>
		<form action="joinForm.user" method="post" name="reg_form">
			<table border="1">
				<tr>
					<td>아이디</td>
					<td>
						<input type="text" name="id" required pattern="[A-Za-z0-9]{5,}">
					</td>
				</tr>
				<tr>
					<td>비밀번호</td>
					<td>
						<input type="password" name="pw" required>
					</td>
				</tr>
				
				<tr>
					<td>비밀번호 확인</td>
					<td>
						<input type="password" name="pw_check" required>
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
						<input type="email" name="email" placeholder="abc@def.com" required>
					</td>
				</tr>
				<tr>
					<td>주소</td>
					<td>
						<input type="text" name="address">
					</td>
				</tr>
				<tr>
					<td>성별</td>
					<td>
						<input type="radio" name="gender" value="M"> 남자
						<input type="radio" name="gender" value="F" checked>여자
					</td>
				</tr>
			</table>
			
			<br>
			<input type="submit" value="회원가입" class="btn btn-default">

			<!-- <input type="button" value="회원가입" class="btn btn-default" onclick="joinConfirm()"> &nbsp;&nbsp; -->
			<input type="reset" value="취소" class="btn btn-default" onclick="">
			<div>${msg }</div> 
			
		</form>	
	</div>
	
<%@ include file="../include/footer.jsp" %>
	
	
	
	<script>
		function joinConfirm() {

			//document문서를 뜻함
			//name이 reg_form 안에, name이 id 인 값
			if(document.reg_form.id.value == 0) {
				alert('아이디는 필수사항 입니다');
				reg_form.id.focus(); //reg_form에 id에 마우스를 위치시킴
				return; //강제 메서드 종료
			} else if(document.reg_form.pw.value == 0) {
				alert('비밀번호는 필수사항 입니다');
				reg_form.pw.focus();
				return;
			} else if(document.reg_form.name.value == 0) {
				alert('이름은 필수사항 입니다');
				reg_form.name.focus();
				return;
			} else if(document.reg_form.id.value.length < 4) {
				alert('아이디는 4글자 이상이어야 합니다');
				reg_form.id.focus();
				return;
			} else if(document.reg_form.pw.value != document.reg_form.pw_check.value) {
				alert('비밀번호 확인란을 확인하세요');
				reg_form.pw_check.focus();
				return;
			} else if( confirm('회원가입을 하시겠습니까?') ) {
				//confirm() 메서드는 확인, 취소 여부를 물어보는 JS메서드
				//확인 클릭시 true, 취소버튼 클릭시 false반환
				reg_form.submit(); //자바스크립트 submit() 함수
			}
		}
	
	</script>
