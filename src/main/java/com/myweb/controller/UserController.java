package com.myweb.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.myweb.user.model.UserVO;
import com.myweb.user.service.UserService;
import com.myweb.user.service.UserServiceImpl;

@WebServlet("*.user") // 확장자패턴
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public UserController() {
        super();

    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}
	
	protected void doAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//3. URL주소를 분기(각 요청별로 코드를 만듦)
		request.setCharacterEncoding("utf-8"); // 한글처리
		
		String uri = request.getRequestURI();
		String path = uri.substring(request.getContextPath().length());
		System.out.println(path);
		
		// 서비스 선언 영역
		UserService service = new UserServiceImpl();
		
		if(path.equals("/user/join.user")) { // 가입화면
			// 화면이동 기본값 - forward형식이 되어야함
			request.getRequestDispatcher("user_join.jsp").forward(request, response);
		} else if(path.equals("/user/login.user")) { // 로그인화면
			request.getRequestDispatcher("user_login.jsp").forward(request, response);
		} else if(path.equals("/user/joinForm.user")) { // 회원가입
			
			int result = service.join(request, response);
			
			if(result == 1) { // 아이디 중복
				request.setAttribute("msg", "아이디가 중복되었습니다");
				request.getRequestDispatcher("user_join.jsp").forward(request, response);
			} else { // 회원가입성공
				// 데이터를 보낼 것도 없고, 화면이동만 하면 리다이렉트를 쓰면 됨,
//				response.sendRedirect("user_login.jsp"); // 이건 절반만 맞은거임, 왜냐면 확장자 뭐시기
				// 근데 MVC2방식에서 redirect를 하려면 이렇게 함
				response.sendRedirect("login.user"); // 그래서 다시 컨트롤러(위) 쪽으로 보냄
			}
			
		} else if(path.equals("/user/loginForm.user")) { // 로그인
			UserVO vo = service.login(request, response);
			
			if(vo != null) { // 로그인 성공
				// 서블릿에서는 request.getSession 현재세션을 얻을 수 있음
				HttpSession session = request.getSession();
				session.setAttribute("user_id", vo.getId());
				session.setAttribute("user_name", vo.getName());
				
				response.sendRedirect(request.getContextPath()); // 홈화면
				
				
			} else { // 로그인 실패
				request.setAttribute("msg", "아이디, 비밀번호를 확인하세요");
				request.getRequestDispatcher("user_login.jsp").forward(request, response);
				
			}
			
		} else if(path.equals("/user/logout.user")) { // 로그아웃
			HttpSession session = request.getSession();
			session.invalidate();
			
			response.sendRedirect(request.getContextPath()); // 홈화면
			
			
		} else if(path.equals("/user/mypage.user")) { // 마이페이지 화면
			request.getRequestDispatcher("user_mypage.jsp").forward(request, response);
			
			
		} else if(path.equals("/user/update.user")) { // 정보 수정 화면
			
			// 여기에서 회원에 대한 데이터를 가지고 화면으로 나갈거임
			/*
			 * 1. DAO에서는 id기준으로 회원정보를 조회해서 UserVO 저장
			 * 2. service영역에서는 리턴ㄴ해서 컨트롤러까지 회원정보를 가지고 나옴
			 * 3. 컨트롤러에서는 vo를 request에 저장. request.setAttribute 사용
			 * 4. 화면에서 EL태그를 사용해서 value속성에 찍어주면 됨
			 */
			UserVO vo = service.getUserInfo(request, response);
//			request.setAttribute("user_id1", vo.getId());
//			request.setAttribute("user_name1", vo.getName());
//			request.setAttribute("user_email1", vo.getEmail());
//			request.setAttribute("user_address1", vo.getAddress());

			// 값 설정, 이름:vo에다가 vo를 담는다
			request.setAttribute("vo", vo);
			
			// 이동한다
			request.getRequestDispatcher("user_update.jsp").forward(request, response);
			
		} else if(path.equals("/user/updateForm.user")) { // 회원정보수정
			
			// 0이면 실패, 1이면 성공
			int result = service.update(request, response);
			
			if(result == 1) {
				// 브라우저 화면에 직접 응답을 해주는 형태
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				//아래 나오는건 자바스크립트 문법이긴 함
				out.println("<script>");
				out.println("alert('업데이트에 성공했습니다');");
				out.println("location.href='mypage.user';");
				out.println("</script>");
				
				
			} else {
				response.sendRedirect("mypage.user");
			}
			
			
		} else if(path.equals("/user/delete.user")) { // 탈퇴화면
			request.getRequestDispatcher("user_delete.jsp").forward(request, response);
			
		} else if(path.equals("/user/deleteForm.user")) { // 회원탈퇴요청
			/*
			 * 1. service영역의 delete메서드로 연결
			 * 2. service에서는 먼저 login메서드를 이용해서 회원의 정보를 조회해서 가지고 나옴
			 * 3. 회원이 있다는 것은, 비밀번호가 일치한다는 의미
			 * 4. delete메서드를 호출시켜서 회원정보를 삭제하고, 세션도 삭제하고, 홈화면으로 리다이렉트
			 * 5. 비밀번호가 일치하지 않아서 실패한 경우에는, delete.jsp화면으로 메세지를 보내주세요
			 */
			
			int result = service.delete(request, response);
			
			if(result == 1) { // 삭제 성공
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				//아래 나오는건 자바스크립트 문법이긴 함
				out.println("<script>");
				out.println("alert('정상적으로 탈퇴되었습니다');");
				out.println("location.href='/JSPMyWeb';");
				out.println("</script>");
				
			} else { // 삭제 실패
				request.setAttribute("msg", "비밀번호를 확인하세요");
				request.getRequestDispatcher("user_delete.jsp").forward(request, response);
				
			}
			
			
		}
		
		
		
		
		
		
		
		
		
	}

}
