package com.myweb.user.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.myweb.user.model.UserDAO;
import com.myweb.user.model.UserVO;

public class UserServiceImpl implements UserService{
	
	UserDAO dao = UserDAO.getInstance();
	
	@Override
	public int join(HttpServletRequest request, HttpServletResponse response) {

		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String address = request.getParameter("address");
		String gender = request.getParameter("gender");
		
		System.out.println("넘어온값: " + id);
		
		// 가입에 대한 프로세스 -> 생각
		// id값 중복체크
		// id가 중복되지 않으면 insert -> 이렇게 가야함 -> 이건 userDAO파일에서 할거임 
		int result = dao.idCheck(id);
		
		if(result == 1) { // 아이디 중복
			return result;
		} else { // 회원가입 진행 가능
			UserVO vo = new UserVO(id, pw, name, email, address, gender, null);
			dao.insertUser(vo); // 회원가입
			
			
			return 0;
		}
		
		
	}

	@Override
	public UserVO login(HttpServletRequest request, HttpServletResponse response) {

			String id = request.getParameter("id");
			String pw = request.getParameter("pw");
			
			UserVO vo = dao.login(id, pw);
			
			return vo;
			
		
	}

	@Override
	public UserVO getUserInfo(HttpServletRequest request, HttpServletResponse response) {

		// 회원아이디는 세션에 있음
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("user_id");
		
		UserVO vo = dao.getUserInfo(id);
		
		
		
		
		return vo;
	}

	@Override
	public int update(HttpServletRequest request, HttpServletResponse response) {
		
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		String name = request.getParameter("name");
		String address = request.getParameter("gender");
		String gender = request.getParameter("gender");
		
		UserVO vo = new UserVO(id, pw, name, name, address, gender, null);
		int result = dao.update(vo);
		
		if(result == 1) { // 성공 시 세션의 값도 변경
			HttpSession session = request.getSession();
			session.setAttribute("user_name", name);
			
		}
		
		
		
		
		return result;
	}

	@Override
	public int delete(HttpServletRequest request, HttpServletResponse response) {
		
		// 이미 로그인이 돼있는 상황이니, 세션에서 아이디를 얻을 수 있음
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("user_id");
		String pw = request.getParameter("pw");
		
		UserVO vo = dao.login(id, pw);
		
		if(vo != null) { // 비밀번호가 일치, 왜? 로그인을 성공한 거니까
			// delete 작업
			dao.delete(id);
			session.invalidate();
			return 1;
			
		} else { // 비밀번호가 일치하지 않음
			return 0;
		}
	}
	
	
	

	
	
	
	
	
	
	
	
	
	
}
