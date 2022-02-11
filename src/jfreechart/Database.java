package jfreechart;

import java.sql.*;
import java.util.*;

public class Database {

	String URL = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
	String USER ="scott";
	String PASS = "tiger";

	public ArrayList<ArrayList> getData() {

		ArrayList<ArrayList> data = new ArrayList<ArrayList>();
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(URL, USER , PASS);	
			
			//***************************************************************
			//String sql = "SELECT sal, ename FROM emp";

			//1) 월별 -group by 입사한 사원수
//			String sql = " SELECT nvl(TO_CHAR(HIREDATE,'MM'), 'etc') HIRE_MONTH,COUNT(*) CNT "
//			+ " FROM emp "
//			+ " GROUP BY TO_CHAR(HIREDATE,'MM') "
//			+ " ORDER BY TO_CHAR(HIREDATE,'MM') ";

			//2) 업무별 평균 월급
			//String sql = "SELECT nvl(job, '임시') job, round(avg(nvl(sal,0))) avg FROM emp GROUP BY job";

			//3) 월급을 많이 받는 사원 10명
			String sql = " SELECT ename,sal "
			+ " FROM  (SELECT ename, sal FROM emp order by sal desc nulls last) "
			+ " WHERE ROWNUM <= 10 ";
			//***************************************************************
			
			PreparedStatement stmt = con.prepareStatement( sql );	

			ResultSet rset = stmt.executeQuery();

			
			while( rset.next() ){
				ArrayList temp = new ArrayList();
				temp.add( rset.getInt("sal"));				//****************
				temp.add( rset.getString("ename"));		//****************
				data.add(temp);
			}
			rset.close();
			stmt.close();
			con.close();
		} catch(Exception ex){
			System.out.println("에러 : " + ex.getMessage() );
		}
		return data;
	}
}
