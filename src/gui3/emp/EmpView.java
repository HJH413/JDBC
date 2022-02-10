package gui3.emp;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class EmpView {

	// 화면 관련 멤버변수
	JFrame f;
	JTextField tfEmpno, tfEname, tfSal, tfJob;
	JButton bInsert, bUpdate, bDelete, bSelectAll;
	JTextArea ta;

	EmpModelImpl em;
	
	// 멤버변수 객체 생성
	EmpView(){
		f = new JFrame("나의 연습");
		tfEmpno = new JTextField(10);
		tfEname = new JTextField(10);
		tfSal = new JTextField(10);
		tfJob = new JTextField(10);
		bInsert = new JButton("입력");
		bUpdate = new JButton("수정");
		bDelete = new JButton("삭제");
		bSelectAll = new JButton("전체검색");
		ta = new JTextArea();

		try {
			em = new EmpModelImpl();
			System.out.println("드라이버 로딩 성공");
		} catch (Exception e) {
			System.out.println("드라이버 로딩 실패");
		}

	}
	
	// 화면 구성
	void addLayout() {
		JPanel pNorth = new JPanel();
		pNorth.setLayout(new GridLayout(1,2));
		
			JPanel pNorth1 = new JPanel();
			pNorth1.setLayout(new GridLayout(4,2));
			pNorth1.add(new JLabel("사번"));		pNorth1.add(tfEmpno);
			pNorth1.add(new JLabel("사원명"));		pNorth1.add(tfEname);
			pNorth1.add(new JLabel("월급"));		pNorth1.add(tfSal);
			pNorth1.add(new JLabel("업무"));		pNorth1.add(tfJob);
			
			JPanel pNorth2 = new JPanel();
			pNorth2.setLayout(new GridLayout(4,1));
			pNorth2.add(bInsert);
			pNorth2.add(bUpdate);
			pNorth2.add(bDelete);
			pNorth2.add(bSelectAll);
		
		pNorth.add(pNorth1);
		pNorth.add(pNorth2);
		
		f.add(pNorth, BorderLayout.NORTH);
		f.add(new JScrollPane(ta), BorderLayout.CENTER);

		f.setBounds(200, 200, 600, 500);
		f.setVisible(true);
	}
	
	// ******************************************************************************
	// 버튼 및 텍스트필드 이벤트 관련
	void eventProc() {
		// 입력 버튼이 눌렸을 때
		bInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				// 각각의 텍스트필드에서 사용자의 입력값을 얻어오기
				int empno =  Integer.parseInt(tfEmpno.getText());
				String ename= tfEname.getText();
				int sal = Integer.parseInt(tfSal.getText());
				String job = tfJob.getText();

				// 사용자 입력값들을 하나의 클래스로 만들기 InfoVO 멤버로 지정

					EmpVO vo = new EmpVO();
					vo.setEmpno(empno);
					vo.setEname(ename);
					vo.setSal(sal);
					vo.setJob(job);


				//EmpVO vo = new EmpVO(name, id, tel, sex, age, home);

				try {
					em.insert(vo);
					//값을 입력 받은후 텍스트필드를 null로 변경
					clearTextField ();
					selectAll();

				}catch(Exception ex) {
					JOptionPane.showMessageDialog(null, "입력실패 : " + ex.getMessage());
				}
			}
		});
		
		// 수정 버튼이 눌렸을때
		bUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				// 각각의 텍스트필드에서 사용자의 입력값을 얻어오기
				int empno =  Integer.parseInt(tfEmpno.getText());
				String ename= tfEname.getText();
				int sal = Integer.parseInt(tfSal.getText());
				String job = tfJob.getText();


				// 사용자 입력값들을 하나의 클래스로 만들기 InfoVO 멤버로 지정
				EmpVO vo = new EmpVO();
				vo.setEmpno(empno);
				vo.setEname(ename);
				vo.setSal(sal);
				vo.setJob(job);

				//InfoVO vo = new InfoVO(name, id, tel, sex, age, home);

				try {
					em.modify(vo);
					//값을 입력 받은후 텍스트필드를 null로 변경
					System.out.println("수정성공");
					clearTextField ();
					selectAll();

				}catch(Exception ex) {
					JOptionPane.showMessageDialog(null, "수정실패 : " + ex.getMessage());
				}
			}
		});


		// 삭제 버튼이 눌렸을때
		bDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				int empno = Integer.parseInt(tfEmpno.getText());
				try {
					int result = em.delete(empno);
					if(result == 1) {
						JOptionPane.showMessageDialog(null, "삭제 성공");
					}
					//화면 비우기
					clearTextField ();
					selectAll();

				}catch(Exception ex) {
					System.out.println("삭제 실패 : " + ex.getMessage());
				}
			}
		});
		// 전체검색 버튼이 눌렸을때
		bSelectAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				selectAll();
			}
		});

		// empno 엔터 쳣을때
		tfEmpno.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				selectByEmpno();
				selectAll();
			}
		});



	}// end of eventProc()

	// clear textfield
	void clearTextField () {
		tfEmpno.setText(null);
		tfEname.setText(null);
		tfSal.setText(null);
		tfJob.setText(null);
	}
	// empno
	void selectByEmpno () {
		int empno = Integer.parseInt(tfEmpno.getText());
		try {
			EmpVO result = em.selectByEmpno(empno);
			tfEmpno.setText(String.valueOf(result.getEmpno()));
			//나머지들
			tfEname.setText(result.getEname());
			tfSal.setText(String.valueOf(result.getSal()));
			tfJob.setText(result.getJob());

		}catch(Exception ex) {
			System.out.println("검색 실패 : " + ex.getMessage());
		}
	}

	//전체검색
	void selectAll() {
		try {
			ta.setText("-------------------검색결과------------------- \n\n");
			ArrayList<EmpVO> result = em.selectAll();
			for ( EmpVO vo: result) {
				ta.append(vo.toAlltext());
			}
		} catch (Exception ex) {
			System.out.println("전체 검색 실패: " + ex.getMessage());
		}
	}

	public static void main(String[] args) {
		EmpView view = new EmpView();
		view.addLayout();
		view.eventProc();

	}

}
