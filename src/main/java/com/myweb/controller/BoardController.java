package com.myweb.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.myweb.board.model.BoardVO;
import com.myweb.board.service.BoardService;
import com.myweb.board.service.BoardServiceImpl;


@WebServlet("*.board") ///
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public BoardController() {
        super();
        
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response); ///
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response); ///
	}
	
	protected void doAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//3. URL주소를 분기(각 요청별로 코드를 만듦)
		request.setCharacterEncoding("utf-8"); // 한글처리
		
		String uri = request.getRequestURI();
		String path = uri.substring(request.getContextPath().length());
		System.out.println(path);
		
		
		///////////////////////////////////////////////////////////////
		// 서비스 영역 선언
		BoardService service = new BoardServiceImpl();
		
		if(path.equals("/board/list.board")) { // 목록화면
			
			// 목록데이터를 가지고 화면으로 이동
			ArrayList<BoardVO> list = service.getList(request, response);
			// list라는 이름으로 list의 값이 전달
			request.setAttribute("list", list);
			
			request.getRequestDispatcher("board_list.jsp").forward(request, response);
			
			
		} else if(path.equals("/board/write.board")) { //글쓰기화면
			request.getRequestDispatcher("board_write.jsp").forward(request, response);
			
		} else if(path.equals("/board/registForm.board")) { // 글 등록
			service.regist(request, response);
			//MVC2에서는 redirect를 다시 컨트롤러로 연결하는 형태로 사용
			response.sendRedirect("list.board");

		} else if(path.equals("/board/content.board")) {
			
			BoardVO vo = service.getContent(request, response);
			// 값을 데리고 나감
			request.setAttribute("vo", vo);
			
			request.getRequestDispatcher("board_content.jsp").forward(request, response);
			
		} else if(path.equals("/board/modify.board")) {
			
			/*
			 1. 화면에서는 필요한 값을 넘겨주세요
			 2. getContent메서드 재활용
			 3. 포워드방식으로 board_modify.jsp로 이동
			 4. 화면에 수정할 데이터를 미리 보여주면 됨
			 */
			BoardVO vo = service.getContent(request, response);
			request.setAttribute("vo", vo);
			
			request.getRequestDispatcher("board_modify.jsp").forward(request, response);
			
		} else if(path.equals("/board/update.board")) {
			/*
			 1. service, dao에 int update(매개값)메서드 만드셈
			 2. update() 메서드에서는 sql문으로 수정을 진행
			 3. 성공, 실패 여부는 정수형으로 반환을 받으셈
			 4. 컨트롤러에서는 성공 시에 상세화면으로 redirect, 실패 시에는 수정화면으로 redirect
			 */
			int result = service.update(request, response);
			System.out.println(result);
			
			if(result == 1) { // 성공
				 response.sendRedirect("content.board?bno=" + request.getParameter("bno")); // bno값 넘겨줌
			} else { // 실패
				response.sendRedirect("modify.board?bno=" + request.getParameter("bno")); 
			}
			
			
		}
	}
}
