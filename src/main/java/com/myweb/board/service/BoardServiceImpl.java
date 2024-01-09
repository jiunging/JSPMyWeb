package com.myweb.board.service;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.myweb.board.model.BoardDAO;
import com.myweb.board.model.BoardVO;

public class BoardServiceImpl implements BoardService{
	
	// dao
	private BoardDAO dao = BoardDAO.getInstance();

	@Override
	public void regist(HttpServletRequest request, HttpServletResponse response) {
		
		String writer = request.getParameter("writer");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		
		dao.insert(writer, title, content);
		
		
		
	}

	@Override
	public ArrayList<BoardVO> getList(HttpServletRequest request, HttpServletResponse response) {
		
		ArrayList<BoardVO> list = dao.getList();
		
		return list;
	}

	@Override
	public BoardVO getContent(HttpServletRequest request, HttpServletResponse response) {
		
		String bno = request.getParameter("bno");
		BoardVO vo = dao.getContent(bno);
		
		return vo;
	}

	@Override
	public int update(HttpServletRequest request, HttpServletResponse response) {
		
		// 화면에서 전달된 값
		String bno = request.getParameter("bno");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		
		int result = dao.update(bno, title, content);
		
		
		
		return result;
	}
	
}
