package kr.ac.kopo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.ac.kopo.singleton.CredentialManager;
import kr.ac.kopo.util.ConnectionFactory;
import kr.ac.kopo.vo.AccountIDVO;
import kr.ac.kopo.vo.EmailVO;

public class EmailDAO {
	public EmailDAO() {
		
	}//바뀔것: Email에서 번호만 있는 것이 필요
	
	public List<EmailVO> getEmailList(int state, int emailPerPage, int page){//1.받은 편지  2. 보낸 편지  3. 휴지통
		String stateName = mailStateToName(state);
		if(stateName == null)
			return null;
		
		List<EmailVO> emailList = new ArrayList();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT CODE, "
				+ "TITLE, "
				+ "SENDER_NAME, "
				+ "SENDER_ID, "
				+ "TO_CHAR(SNT_DATE, 'YYYY/MM/DD') AS SNT_DATE "
				+ "FROM EMAILDOTCOM_" + CredentialManager.getInstance().getID() + "_" + stateName
				+ " WHERE ROWNUM BETWEEN ? AND ? ");
		
		try(
			Connection conn = new ConnectionFactory().getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		){
			pstmt.setInt(1, ((page-1)*emailPerPage + 1 >0) ? (page-1)*emailPerPage + 1 : 1 );
			pstmt.setInt(2, (page*emailPerPage >0) ? page*emailPerPage : 1 );
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				EmailVO item = new EmailVO();
				item.setCode(rs.getInt(1));
				item.setTitle(rs.getString(2));
				item.setSenderID(rs.getString(4));
				item.setSenderName(rs.getString(3));
				item.setDate(rs.getString(5));
				emailList.add(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return emailList;
	}
	
	public EmailVO getEmail(int state, int code)/*어느 테이블, 몇번 이메일?*/ {//1.받은 편지  2. 보낸 편지  3. 휴지통
		String stateName = mailStateToName(state);
		if(stateName == null)
			return null;
		
		EmailVO email = new EmailVO();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT CODE, "
				+ "TITLE, "
				+ "SENDER_NAME,"
				+ "SENDER_ID, "
				+ "RECEIVER_NAME,"
				+ "RECEIVER_ID, "
				+ "TO_CHAR(SNT_DATE, 'YYYY/MM/DD') AS SNT_DATE, "
				+ "DETAIL "
				+ "FROM EMAILDOTCOM_"+ CredentialManager.getInstance().getID() +"_" + stateName
				+ " WHERE CODE = ? ");
		
		try(
			Connection conn = new ConnectionFactory().getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		){
			pstmt.setInt(1, code);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				email.setCode(rs.getInt(1));
				email.setTitle(rs.getString(2));
				email.setSenderName(rs.getString(3));
				email.setSenderID(rs.getString(4));
				email.setReceiverName(rs.getString(5));
				email.setReceiverID(rs.getString(6));
				email.setDate(rs.getString(7));
				email.setDetail(rs.getString(8));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return email;
	}
	
	public List<EmailVO> searchEmail(int mailState, int searchState, List<StringBuilder> searchPlusList, List<StringBuilder> searchMinusList){
		String mailStateName = mailStateToName(mailState);
		if(mailStateName == null)
			return null;
		
		String searchStateName = searchStateToName(searchState);
		if(searchStateName == null)
			return null;
		
		List<EmailVO> emailList = new ArrayList();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT CODE, "
				+ "TITLE, "
				+ "SENDER_NAME, "
				+ "SENDER_ID, "
				+ "TO_CHAR(SNT_DATE, 'YYYY/MM/DD') AS SNT_DATE "
				+ "FROM EMAILDOTCOM_" + CredentialManager.getInstance().getID() + "_" + mailStateName
				+ " WHERE ");
		
		boolean flagAnd = false;
		for(StringBuilder item : searchPlusList) {
			if(flagAnd) {
				sql.append("And ");
			}
			flagAnd = true;
			
			sql.append(searchStateName + " LIKE '%" + item.toString() + "%' ");
		}
		
		for(StringBuilder item : searchMinusList) {
			if(flagAnd) {
				sql.append("And ");
			}
			flagAnd = true;
			
			sql.append(searchStateName + " NOT LIKE '%" + item.toString() + "%' ");
		}
		
		try(
			Connection conn = new ConnectionFactory().getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		){
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				EmailVO item = new EmailVO();
				item.setCode(rs.getInt(1));
				item.setTitle(rs.getString(2));
				item.setSenderID(rs.getString(4));
				item.setSenderName(rs.getString(3));
				item.setDate(rs.getString(5));
				emailList.add(item);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return emailList;
	}
	
	public void addEmail(/*어느 테이블, 이메일*/) {//보낼때 서비스단에서 보낸 메일함에 하나, 받은 메일함에 하나
		
	}
	
	public boolean copyMailTo(int stateFrom, int stateTo, int code)/*어느 테이블, 보낼 인덱스, 몇번 이메일*/ {
		String stateFromName = mailStateToName(stateFrom);
		String stateToName = mailStateToName(stateTo);
		if (stateToName == null || stateFromName == null)
			return false;
		
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO EMAILDOTCOM_"+ CredentialManager.getInstance().getID() +"_" + stateToName
				+ " SELECT * "
				+ "FROM EMAILDOTCOM_"+ CredentialManager.getInstance().getID() +"_" + stateFromName
				+ " WHERE CODE = ? ");
		
		boolean result = false;
		try(
			Connection conn = new ConnectionFactory().getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		){
			pstmt.setInt(1, code);
			pstmt.executeUpdate();
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public void removeEmail(int state, int code) {
		String stateName = mailStateToName(state);
		if(stateName == null)
			return;
		
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM EMAILDOTCOM_"+ CredentialManager.getInstance().getID() +"_" + stateName
				+ " WHERE CODE = ? ");
		
		boolean result = false;
		try(
			Connection conn = new ConnectionFactory().getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		){
			pstmt.setInt(1, code);
			pstmt.executeUpdate();
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}	//add
	
	private String mailStateToName(int state) {
		switch(state) {
		case 1:
			return new String("RECEIVED_MAIL");
		case 2:
			return new String("SENT_MAIL");
		case 3:
			return new String("RECYCLEBIN_MAIL");
		default:
			return null;
		}
	}
	
	private String searchStateToName(int state) {
		switch(state) {
		case 1:
			return new String("TITLE");
		case 2:
			return new String("DETAIL");
		case 3:
			return new String("SENDER_ID");
		case 4:
			return new String("SENDER_NAME");
		case 5:
			return new String("RECEIVER_ID");
		case 6:
			return new String("RECEIVER_NAME");
		default:
			return null;
		}
	}
}
