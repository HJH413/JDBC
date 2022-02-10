package basic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateTest {
	public static void main(String[] args) {
		
		String ur1 = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
		String user = "scott";
		String pass = "tiger";
		
		try {
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			Connection con = DriverManager.getConnection(ur1,user,pass);
			System.out.println("DB 연결 성공");
		
			int empno = 7788;
			String job = "개발";
			int sal = 8000;
			
			//String sql = "7788사원의 정보를 업무와 급여 수정";
			String sql = "UPDATE emp SET sal = ?, job = ? WHERE empno = ?";
			
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(3, empno);
			ps.setString(2, job);
			ps.setInt(1, sal);
			
			int result = ps.executeUpdate();
			
			System.out.println(result + "행을 실행");
			
			ps.close();
			con.close();
			
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 못 찾음 : " + e.getMessage() );	
		} catch (SQLException e) {
			System.out.println("DB관련 에러 : " + e.getMessage());
		}
		
		
		
	}
}


				