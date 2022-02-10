package gui3.emp;

import java.sql.*;
import java.util.*;

public class EmpModelImpl implements EmpModel{

	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
	String user = "scott";
	String pass = "tiger";

	/*---------------------------------------------
	 * 생성자 함수 
	 	1. DB 연동
	 		- 드라이버 등록
	*/
	public EmpModelImpl() throws Exception{
		Class.forName(driver);
	}

	/*-------------------------------------------------------
	* insert() :  입력한 값 받아서 데이타베이스에 추가
		0. 연결객체 얻어오기
		1. sql문 만들기 ( insert 구문 )
		2. PreparedStatement 객체 생성 
		3. PreparedStatement에 인자 지정
		4. sql문 전송 ( executeUpdate() 이용 )
		5. PreparedStatement 닫기
		6. 연결 닫기
	*/
	public void insert( EmpVO r ) throws SQLException{
		Connection con = null;
		PreparedStatement ps = null;

		try {
			//2. 연결 객체 얻어오기
			con = DriverManager.getConnection(url,user,pass);
			System.out.println("DB 연결 성공");

			//3. SQL 만들기

			String sql = "INSERT INTO emp (empno, ename, sal, job) VALUES (?, ?, ?, ?)";

			//4. SQL 전송 객체 얻어오기
			ps = con.prepareStatement(sql);

			ps.setInt(1, r.getEmpno());
			ps.setString(2, r.getEname());
			ps.setInt(3, r.getSal());
			ps.setString(4, r.getJob());

			//5.전송하기
			int result = ps.executeUpdate();
			System.out.println(result + "행을 실행");
		} catch (Exception ex) {
			System.out.println("입력 실패 : " + ex.getMessage());
		}
		finally {
			//6. 닫기
			ps.close();
			con.close();
		}
	}



	/*-------------------------------------------------------
	* modify() : 화면 입력값 받아서 수정
		0. 연결객체 얻어오기
		1. sql문 만들기 ( update 구문 )
		2. PreparedStatement 객체 생성 ( 또는 Statement )
		3. PreparedStatement에 인자 지정
		4. sql문 전송 ( executeUpdate() 이용 )
		5. PreparedStatement 닫기
		6. 연결 닫기
		*  empno를 기준으로 수정하기
	*/
	public void modify( EmpVO r ) throws SQLException{
		Connection con = null;
		PreparedStatement ps = null;

		try {
			//2. 연결 객체 얻어오기
			con = DriverManager.getConnection(url,user,pass);
			System.out.println("DB 연결 성공");

			//3. SQL 만들기
			//수정
			String sql = "UPDATE emp set ename = ?,  sal = ?, job = ? WHERE empno = ? ";

			//4. SQL 전송 객체 얻어오기
			// 수정
			ps = con.prepareStatement(sql);

			ps.setString(1, r.getEname());
			ps.setInt(2, r.getSal());
			ps.setString(3, r.getJob());
			ps.setInt(4, r.getEmpno());

			//5.전송하기
			int result = ps.executeUpdate();
			System.out.println(result + "행을 실행");
		} finally {
			//6. 닫기
			ps.close();
			con.close();
		}
	}
	
	/*-------------------------------------------------------
	* selectByEmpno() :  입력받은 사번을 받아서 해당 레코드 검색
		0. 연결객체 얻어오기
		1. sql문 만들기 ( select 구문 )
		2. PreparedStatement 객체 얻기 ( Statement  가능 )
		4. sql문 전송 ( executeQuery() 이용 )
		5. 결과집합(ResultSet)에서 값을 읽어서 EmpVO에 저장
		6. ResultSet/ PreparedStatement 닫기
		7. 연결 닫기
		8. EmpVO 객체 리턴
	*/	
	public EmpVO selectByEmpno( int empno ) throws SQLException{
		EmpVO vo = new EmpVO();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			//2.연결객체 얻어오기
			con = DriverManager.getConnection(url,user,pass);
			System.out.println("DB 연결 성공");

			//3.sql문장
			String sql = "SELECT * FROM emp WHERE empno = ? "; // ? 를사용하면 넘겨 받을 값 세팅하기
			//4. 전송객체 얻어오기
			ps = con.prepareStatement(sql);
			ps.setInt(1, empno);
			//5. 전송
			rs = ps.executeQuery();
			//6. 결과처리
			if(rs.next()) {
				vo.setEmpno(rs.getInt("EMPNO"));
				vo.setEname(rs.getString("ENAME"));
				vo.setSal(rs.getInt("SAL"));
				vo.setJob(rs.getString("JOB"));
			}
			//7. 닫기 - finally구문으로
		} finally {
			rs.close();
			ps.close();
			con.close();

		}
		return vo;	
	}
	
	/*--------------------------------------------------------
	* delete() : 사원번호 값을 받아 해당 레코드 삭제
		0. 연결객체 얻어오기
		1. sql문 만들기 ( delete 구문 )
		2. PreparedStatement 객체 얻기
		3. sql문 전송 ( executeUpdate() 이용 )
		4. PreparedStatement 닫기
		5. 연결 닫기
	*/
	public int delete( int empno ) throws SQLException{
		int resultCnt = 0;
		Connection con = null;
		PreparedStatement ps = null;

		//2.연결객체 가져오기
		try {
			con = DriverManager.getConnection(url,user,pass);
			System.out.println("DB 연결 성공");
			//3. sql문장 만들기
			String sql = "DELETE FROM emp WHERE empno = ?";
			//4. 전송객체 얻어오기
			ps = con.prepareStatement(sql);
			ps.setInt(1, empno);
			//5. 전송
			resultCnt = ps.executeUpdate();
			System.out.println(resultCnt + "행을 실행");

			//6. 닫기
		} finally {
			ps.close();
			con.close();
		}
		return resultCnt;
	}
	
	/*-------------------------------------------------------
	* selectAll() :  전체 레코드 검색
		0. 연결객체 얻어오기
		1. sql문 만들기 ( select 구문 )
		2. PreparedStatement 객체 얻기 ( Statement  가능 )
		4. sql문 전송 ( executeQuery() 이용 )
		5. 결과집합(ResultSet)에서 값을 읽어서 ArrayList에 저장
		6. ResultSet/ PreparedStatement 닫기
		7. 연결 닫기
		8. ArrayList 객체 리턴
	*/	
	public ArrayList<EmpVO> selectAll() throws SQLException{
		ArrayList<EmpVO> list = new ArrayList<EmpVO>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(url,user,pass);
			System.out.println("DB 연결 성공");

			String sql = "SELECT * FROM emp";

			ps = con.prepareStatement(sql);

			rs = ps.executeQuery();
			//레코드가 여러개니까 하나씩 읽어야함
			while (rs.next()) {
				EmpVO vo = new EmpVO();
				vo.setEmpno(rs.getInt("EMPNO"));
				vo.setEname(rs.getString("ENAME"));
				vo.setSal(rs.getInt("SAL"));
				vo.setJob(rs.getString("JOB"));
				list.add(vo); // arraylist 안에다가 저장하기
			}
		} finally {
			rs.close();
			ps.close();
			con.close();
		}
		return list;
	}


}