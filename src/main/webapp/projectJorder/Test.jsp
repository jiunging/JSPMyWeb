<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>지엉의 JORDER 사용하기</title>
    <style>
        body {
            display: flex;
            align-items: center; 
            justify-content: center; 
            height: 100vh;
            margin: 0;
        }

        h1 {
            text-align: center;
        } 

        submit {
        	position: relative;
            padding: 10px 20px;
            font-size: 16px;
            margin: 20px;
            cursor: pointer;
        }

         #loginButton {
            background-color: #4CAF50; /* Green */
            color: white;
        }

        #signupButton {
            background-color: #008CBA; /* Blue */
            color: white;
        } 
        
        .x {
        	display: flex;
        	align-content: center;
			flex-direction:column;
        	
        }
		.x .sub {
			text-align: center;
		}
    </style>
</head>
<body>

	<div class="x">
	    <h1>JORDER를 이용하시려면 로그인을 해주세요</h1>
	    
	
 		<div class="sub">
	    <a href="user_login.jsp"><button id="loginButton">로그인</button></a>
	

	    <a href="user_join.jsp"><button id="signupButton">회원가입</button></a>
		
		</div>
		
<!-- 		<div class="sub">
	    <input type="button" value="로그인" onclick="">
	    <a href="user_login.jsp">로그인</a>
	    <input type="button" value="회원가입" onclick="">
	    <a href="user_join.jsp">회원가입</a> -->
<!-- 	    </div> -->
    </div>
    
</body>
</html>