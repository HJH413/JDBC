package basic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class InsertTest2 {
	
	public static void main(String[] args) {
		String url= "jdbc:oracle:thin:@127.0.0.1:1521:xe";
		String user = "scott";
		String pass = "tiger";
		
		try {
			//1. 드라이버 메모리에 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			//2. 연결 객체 얻어오기
			Connection con = DriverManager.getConnection(url,user,pass);
			System.out.println("DB 연결 성공");
			
			//3. SQL 만들기
			int empno = 9915;
			String ename = "홍길자";
			String job = "유지";
			
			String sql = "INSERT INTO emp(empno, ename, job) "
							+ " VALUES(" + empno + ", '" + ename + "', '" + job + "')";
			
			//String sql = "UPDATE emp SET sal = 9000, deptno = 30 WHERE empno = 9911";			
			//String sql = "DELETE emp WHERE ename = '둘리'";

			
			//4. SQL 전송 객체 얻어오기
			// 	Statement	: 완벽한 쿼리
			// 	PreparedStatement (*) : 주로사용
			// 	CallableStatement : pl-sql의 함수를 호출할 때
			Statement ps = con.createStatement();
			//5. 전송
			//	- int executeUpdate () : INSERT, UPDATE, DELETE
			//  - ResultSet executeQuery () : SELECT
			int result = ps.executeUpdate(sql);
			
			//6. 결과처리
			System.out.println(result + "행을 실행");
			
			//7. 닫기
			ps.close();
			con.close();
			
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 못 찾음 : " + e.getMessage() );
		} catch (SQLException e) {
			System.out.println("DB관련 에러 : " + e.getMessage());
		}
	}

}


// 외부에서 날리는 것들은 sql내에서 rollback이 안됨 그래서 신중하게 할것