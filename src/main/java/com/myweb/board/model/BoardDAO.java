package com.myweb.board.model;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.naming.InitialContext;

import org.apache.tomcat.jdbc.pool.DataSource;

import com.myweb.user.model.UserDAO;
import com.myweb.util.JdbcUtil;

public class BoardDAO {
	//싱글톤
	//1. 객체 1개 생성
	private static BoardDAO instance = new BoardDAO();
	//2. 생성자 private
	private BoardDAO() {
		try {
			//1. 드라이버 호출
			//드라이버 호출문장, 이걸 왜 안 하냐면 커넥션 풀로 미치 설정을 해뒀음
			//Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// 초기설정 값을 얻을 수 있는 객체
			InitialContext init = new InitialContext();
			dataSource = (DataSource)init.lookup("java:comp/env/jdbc/oracle");
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	//3. getter메서드
	public static BoardDAO getInstance() {
		return instance;
	}
	
	//////////////////////////////////////////
//	private String url = JdbcUtil.url;
//	private String uid = JdbcUtil.uid;
//	private String upw = JdbcUtil.upw;
	
	// 커넥션풀에서 얻어온 디비개게 정보
	private DataSource dataSource;
	
	
	public void insert(String writer, String title, String content) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "insert into board(bno, writer, title, content) "
				+ "values(board_seq.nextval, ?, ?, ?";
		
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, writer);
			pstmt.setString(2, title);
			pstmt.setString(3, content);
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn, pstmt, null);
		}
		
		
	}
	
	
	

}
