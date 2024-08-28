package kr.ac.kopo.singleton;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Random;

import kr.ac.kopo.util.ConnectionFactory;
import kr.ac.kopo.vo.AccountVO;
import kr.ac.kopo.vo.SessionVO;

public class SessionGenerator {	//원래 로그인하는 서버에서 시즌을 만들어야 하지만 따로 서버까지 만들기 힘드므로 대신 만듦
	public static SessionGenerator instance;
	Random rd;
	Date date;
	
	private SessionGenerator() {
		rd = new Random();
		date = new Date();
	}
	
	public static SessionGenerator instanciate() throws Exception {
		if(instance == null)
			instance = new SessionGenerator();
		else
			throw new Exception("이미 SessionGenerator 싱글톤이 있습니다.");
		if(instance == null)
			throw new Exception("싱글톤 SessionGenerator 생성에 실패했습니다.");
		
		return instance;
	}
	
	public static SessionGenerator getInstance(){
		if(instance == null) {
			System.out.println("아직 SessionGenerator 싱글톤이 만들어지지 않았습니다.");
			return null;
		}else {
			return instance;
		}
	}
	
	
	public SessionVO clientRequestGetSession(AccountVO account) {
		if(checkAccountValid(account)) {	//추가로 이미 시즌이 있는지 검사해야함
			SessionVO session = generateSession(account);
			setSessionForServer(session);
			return session;
		}else {
			return null;
		}
	}
	
	public boolean clientRequestRemoveSession(AccountVO account, SessionVO session) {
		if(checkAccountValid(account)) {
			removeSessionFromServer(session);	//순서 중요
			session = null;
			return true;
		}
		return false;
	}
	
	public void clientRequestRefreshSession(AccountVO account, SessionVO session) {
		boolean flag = true;
		if(!checkAccountValid(account)) {
			flag = false;
		}
		if(!checkSessionValid(session)) {
			flag = false;
		}
		refreshSessionForServer(session);
		if(!flag) {
			session = null;
		}
	}
	
	
	public void setSessionForServer(SessionVO session) {
		//DB에 해당 시즌 정보 추가
		StringBuilder sql = new StringBuilder();

		sql.append("INSERT INTO EMAILDOTCOM_SESSION (SESSION_, ID) "
				+ "VALUES(?, ?)");
		
		try(
			Connection conn = new ConnectionFactory().getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		){
			pstmt.setLong(1, session.getSession());
			pstmt.setString(2, session.getID());
			System.out.println(sql.toString());	//디버그
			pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void removeSessionFromServer(SessionVO session) {
		//DB에서 해당 시즌 정보 삭제
	}
	
	public void refreshSessionForServer(SessionVO session) {
		//DB에서 해당 시즌 정보 기한 갱신
	}
	
	
	public boolean checkAccountValid(AccountVO account) {
		boolean result = false;
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ID, PASSWD "
				+ "FROM EMAILDOTCOM_PROFILE "
				+ "WHERE ID = ? "
				+ "  AND PASSWD = ? ");
		
		try(
			Connection conn = new ConnectionFactory().getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		){
			pstmt.setString(1, account.getID());
			pstmt.setString(2, account.getPassword());
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				String id = rs.getString("ID");
				String passwd = rs.getString("PASSWD");
				
				System.out.println(id + " " + passwd);
				if(id.equals(account.getID()) && passwd.equals(account.getPassword()))
					result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
		return result;
	}
	
	public boolean checkSessionValid(SessionVO session) {
		//DB에서 해당 시즌 정보가 맞는지 검사
		return false;
	}
	
	public SessionVO generateSession(AccountVO account) {
		String str = account.getID();
		long seed = 0;
		for(int i = 0; i < str.length(); i++ ) {
			int tmp = (int)Math.pow(36, i);
			char ch = str.charAt(str.length() - i - 1);
			if(ch >= '0' && ch <= '9') {
				ch -= '0';
			}else if (ch >= 'a' && ch <= 'z') {
				ch = (char)(ch - 'a' + 10);
			}else if (ch >= 'A' && ch <= 'Z') {
				ch = (char)(ch - 'A' + 10);
			}
			tmp *= (int)ch;
			seed += tmp;
		}
		
		long tmp = date.getTime()/1000;
		seed *= tmp;
		
		rd.setSeed(seed);
		return new SessionVO(account.getID(), rd.nextLong(100000000));
	}
}
