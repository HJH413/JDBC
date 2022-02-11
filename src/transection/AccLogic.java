package transection;

//#################################################################
//	테이블명 : account
//	account_num		계좌번호		varchar2(20)
//	customer		고객명			varchar2(20)
//	amount			계좌금액		int


import java.sql.*;
public class AccLogic 
{
	// 연결 객체 생성시 필요한 변수 선언
	String url;
	String user;
	String pass;

	//===============================================
	// 드라이버를 드라이버매니저에 등록
	public AccLogic() throws Exception{
		/////////////////////////////////////////////////////////
		// 1. 드라이버를 드라이버 매니저에 등록
		Class.forName("oracle.jdbc.driver.OracleDriver");
		url = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
		user = "scott";
		pass = "tiger";
	}


	//====================================================
	// 보내는 계좌번호와 받는 계좌번호와 계좌금액을 넘겨받아 
	//	보내는계좌에서 계좌금액을 빼고 받는계좌에서 계좌금액을 더한다
	public int moveAccount(String sendAcc, String recvAcc, int amount)
	{
		Connection con = null;

		///////////////////////////////////////////////////////////
		//	 1. Connection 객체 생성
		//@@ 2. Auto-commit을 해제
		//	 3. 출금계좌에서 이체금액을 뺀다
		//	 4. 입금계좌에 이체금액을 더한다
		//@@ 5. commit을 전송한다
		//	 6. 객체 닫기
		//	 - 만일 정상적인 경우는 0을 리턴하고 도중에 잘못되었으면 트랜잭션을 롤백시키고 -1을 리턴

		try {
			con = DriverManager.getConnection(url, user, pass);
			con.setAutoCommit(false); // commit를 막음


			String sql1 = "UPDATE account SET amount = amount-? WHERE account_num = ?"; // 계좌에서 돈빼기
			PreparedStatement ps1 = con.prepareStatement(sql1);
			ps1.setInt(1,amount);
			ps1.setString(2, sendAcc);
			int resurlt1 = ps1.executeUpdate();
			ps1.close();
			if (resurlt1 == 0) {
				con.rollback();
				return -1;
			}

			String sql2 = "UPDATE account SET amount = amount+? WHERE account_num = ?"; // 계좌에서 돈더하기
			PreparedStatement ps2 = con.prepareStatement(sql2);
			ps2.setInt(1,amount);
			ps2.setString(2,recvAcc);
			int result2 = ps2.executeUpdate();
			ps2.close();
			if (result2 == 0) { // 0이라는 소리는 해당하는 계좌번호가 없다.
				con.rollback();
				return -1;
			}

			con.commit(); // commit 실행

		}catch (Exception ex) {
			System.out.println("예외발생 : " + ex.getMessage());
			ex.printStackTrace(); // 예외의 경로를 보기
		} finally {
			try{
				con.close();
			} catch (Exception ex) {

			}
		}

		return 0;
	}

	//=======================================================
	//	보내는계좌번호와 받는계좌번호를 넘겨받아
	//  필요한 정보 검색
	public void confirmAccount(String sendAcc) throws Exception{


	}

}


