package z_info;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Database {
	
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
	String user = "scott";
	String pass = "tiger";
		//1. 드라이버 로딩
	public Database() throws ClassNotFoundException {
		Class.forName(driver);
	}
	
	public void insert(InfoVO vo) throws Exception { // 예외처리는 필수
		Connection con = null;
		PreparedStatement ps = null;
		
		try {
			//2. 연결 객체 얻어오기
			con = DriverManager.getConnection(url,user,pass);
			System.out.println("DB 연결 성공");
			
			//3. SQL 만들기
			
			String sql = "INSERT INTO info_test(name, id, tel, sex, age, home) " 
							+ " VALUES(?, ?, ?, ?, ?, ?)";
			
			//4. SQL 전송 객체 얻어오기
			ps = con.prepareStatement(sql);
			
			ps.setString(1, vo.getName());
			ps.setString(2, vo.getId());
			ps.setString(3, vo.getTel());
			ps.setString(4, vo.getSex());
			ps.setInt(5, vo.getAge());
			ps.setString(6, vo.getHome());
			
			//5.전송하기
			int result = ps.executeUpdate();
			System.out.println(result + "행을 실행");
		} finally {
			//6. 닫기
			ps.close();
			con.close();
		}
		
	}
	
	public InfoVO searchByTel(String tel) throws Exception {
		InfoVO vo = new InfoVO();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			//2.연결객체 얻어오기
			con = DriverManager.getConnection(url,user,pass);
			System.out.println("DB 연결 성공");
			
			//3.sql문장
			String sql = "SELECT * FROM info_test WHERE tel = ? "; // ? 를사용하면 넘겨 받을 값 세팅하기
			//4. 전송객체 얻어오기
			ps = con.prepareStatement(sql);
			ps.setString(1, tel);
			//5. 전송
			rs = ps.executeQuery();
			//6. 결과처리
			if(rs.next()) {
				vo.setName(rs.getString("NAME"));
				vo.setId(rs.getString("ID"));
				vo.setTel(rs.getString("TEL"));
				vo.setSex(rs.getString("SEX"));
				vo.setAge(rs.getInt("AGE"));
				vo.setHome(rs.getString("HOME"));
			}
			//7. 닫기 - finally구문으로
		} finally {
			rs.close();
			ps.close();
			con.close();
			
		}
		return vo;
		
	}
	
	public int delete(String tel) throws Exception {
		int result = 0;
		Connection con = null;
		PreparedStatement ps = null;
		
		//2.연결객체 가져오기
		try {
			con = DriverManager.getConnection(url,user,pass);
			System.out.println("DB 연결 성공");
		//3. sql문장 만들기
		String sql = "DELETE FROM info_test WHERE tel = ?";
		//4. 전송객체 얻어오기
		ps = con.prepareStatement(sql);
		ps.setString(1, tel);
		//5. 전송
		result = ps.executeUpdate();
		System.out.println(result + "행을 실행");
		
		//6. 닫기
		} finally {
			ps.close();
			con.close();
		}
		
		return result;
	}


} // end of class

	
