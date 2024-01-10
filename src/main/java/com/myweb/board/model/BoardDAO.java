package com.myweb.board.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.sql.DataSource;

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
	
	// 글 등록
	public void insert(String writer, String title, String content) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "insert into board(bno, writer, title, content) "
				+ "values(board_seq.nextval, ?, ?, ?)";
		
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
	
	// 목록 조회
	public ArrayList<BoardVO> getList() {
		ArrayList<BoardVO> list = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		// 최신 글이 맨 위로 와야하니까 desc 붙여줌
		String sql = "select * from board order by bno desc";
		
		try {
			// 이미 커넥션 풀로 설정해서 이렇게 해도 됨
			conn = dataSource.getConnection();
			
			pstmt = conn.prepareStatement(sql);
			
			// ?가 없으니 그냥 바로 실행시켜주면 됨
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int bno = rs.getInt("bno");
				String title = rs.getString("title");
				String content = rs.getString("content");
				String writer = rs.getString("writer");
				int hit = rs.getInt("hit");
				Timestamp regdate = rs.getTimestamp("regdate");
				
				BoardVO vo = new BoardVO(bno, writer, title, content, hit, regdate);
				list.add(vo);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn, pstmt, rs);
		}
		
		return list;
	}
	
	
	// 내용 조회
	public BoardVO getContent(String bno) {
		
		BoardVO vo = new BoardVO(); //
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "select * from board where bno = ?";
		
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bno);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				int bno2 = rs.getInt("bno");
				String writer = rs.getString("writer");
				String title = rs.getString("title");
				String content = rs.getString("content");
				int hit = rs.getInt("hit");
				Timestamp regdate = rs.getTimestamp("regdate");
				
				vo.setBno(bno2);
				vo.setWriter(writer);
				vo.setTitle(title);
				vo.setContent(content);
				vo.setHit(hit);
				vo.setRegdate(regdate);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn, pstmt, rs);
		}
		
		return vo;
		

	}
	
	// 매개변수에 작성자 빼줌(어차피 id라 못 바꾸잖음 ㅋㅋ)
	public int update(String bno, String title, String content) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "update board set title = ?, content = ? where bno = ?";
		
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setString(3, bno); // 이거 대기
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn, pstmt, null);
		}
		
		return result;
	}
	
	// 삭제기능(삭제는 나중에 컬럼을 하나 만들고 사용하지 않음 처리를 함 Y, N)
	public void delete(String bno) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "delete from board where bno = ?";
		
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bno);
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn, pstmt, null);
		}
		
	}
	
	
	// 조회수 작업
	public void hitUpdate(String bno) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "update board set hit = hit + 1 where bno = ?";
		
		try {
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bno);
			pstmt.executeUpdate();
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn, pstmt, null);
		}
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
