package com.myweb.user.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import com.myweb.util.JdbcUtil;

public class UserDAO {
	
	//싱글톤
	//1. 객체 1개 생성
	private static UserDAO instance = new UserDAO();
	//2. 생성자 private
	private UserDAO() {
		try {
			//1. 드라이버 호출
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	//3. getter메서드
	public static UserDAO getInstance() {
		return instance;
	}
	
	////////////////////////////////////////////////
	private String url = JdbcUtil.url;
	private String uid = JdbcUtil.uid;
	private String upw = JdbcUtil.upw;
	
	
	
	public int idCheck(String id) {
		int result = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		//이 아래 듯이 뭐냐, count를 하는데 id가 ???인 사람, 카운트가 0이면 없는거고, 1 이상이면 있는거임
		String sql = "select * from users where id = ?";
		
		try {
			// conn객체 생성
			conn = DriverManager.getConnection(url, uid, upw);
			// pstmt객체생성
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id); // 매개변수로 받았던 거
			// sql실행 -> select는 executeQuery, DML -> executeUpdate
			rs = pstmt.executeQuery();
			
			// 이게 중복 판결인듯
			if(rs.next()) {	// true라는 것은 -> 결과가 있다는 뜻
				result = 1; // 중복의 이미
			} else {	//false라는 것은 -> 중복된 결과가 없다는 뜻
				result = 0; // 중복이 x
			}
			
		} catch (Exception e) {
			
		} finally {
			JdbcUtil.close(conn, pstmt, rs);
		}
		
		return result;
	}

	// 회원가입기능
	public void insertUser(UserVO vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "insert into users(id, pw, name, email, address, gender) "
				+ "values(?, ?, ?, ?, ?, ?)";
		
		try {
			conn = DriverManager.getConnection(url, uid, upw);
			pstmt = conn.prepareStatement(sql);
			// 값 넣어주는거, ? 개수만큼 만들어주기
			pstmt.setString(1, vo.getId());
			pstmt.setString(2, vo.getPw());
			pstmt.setString(3, vo.getName());
			pstmt.setString(4, vo.getEmail());
			pstmt.setString(5, vo.getAddress());
			pstmt.setString(6, vo.getGender());
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn, pstmt, null);
		}
		
		
		
	}
	
	// 로그인기능
	public UserVO login(String id, String pw) {
		
		UserVO vo = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from users where id = ? and pw = ?";
		
		try {
			conn = DriverManager.getConnection(url, uid, upw);
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) { // 로그인 성공(UserVO에 필요한 값을 저장)
				vo = new UserVO();
				vo.setId(id);
				vo.setName(rs.getString("name"));
				
			}
			
		} catch (Exception e) {
			
		} finally {
			JdbcUtil.close(conn, pstmt, rs);
		}
		
		
		
		return vo;
		
	}
	
	public UserVO getUserInfo(String id) {
		UserVO vo = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "select * from users where id = ?";
		
		try {
			conn = DriverManager.getConnection(url, uid, upw);
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				// vo객체 넣어주기
//				vo = new UserVO();
//				// 데이터 ORM작업
//				vo.setId(rs.getString("ID"));
//				vo.setName(rs.getString("PW"));
//				vo.setEmail(rs.getString("NAME"));
//				vo.setAddress(rs.getString("ADDRESS"));
				
				// 이렇게 해도 됨
				String name = rs.getString("name");
				String email = rs.getString("email");
				String address = rs.getString("address");
				String gender = rs.getString("gender");
				Timestamp regdate = rs.getTimestamp("regdate");
				
				vo = new UserVO(id, null, name, email, address, gender, regdate);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn, pstmt, rs);
		}
		
		
		
		return vo;
	}
	
	
	public int update(UserVO vo) {
		int result = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "update users set pw = ?, name = ?, email = ?, address = ?, gender = ? where id = ?";
		
		try {
			
			conn = DriverManager.getConnection(url, uid, upw);
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, vo.getPw());
			pstmt.setString(2, vo.getName());
			pstmt.setString(3, vo.getEmail());
			pstmt.setString(4, vo.getAddress());
			pstmt.setString(5, vo.getGender());
			pstmt.setString(6, vo.getId());
			
			result = pstmt.executeUpdate(); // 0이면 실패, 1이면 성공
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn, pstmt, null);
		}
		
		
		
		return result;
	}
	
	
	
	
	
	
	
	
	
	

}
