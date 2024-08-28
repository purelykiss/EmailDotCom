package kr.ac.kopo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.ac.kopo.util.ConnectionFactory;
import kr.ac.kopo.vo.AccountIDVO;
import kr.ac.kopo.vo.AccountVO;
import kr.ac.kopo.vo.EmailVO;
import kr.ac.kopo.vo.ProfileVO;

public class ProfileDAO {
	public ProfileDAO() {
		
	}
	
	public boolean createProfile(ProfileVO profile) {
		//계정만들기 
		//테이블에 계정이 있는가?
		//없을 경우 테이블에 계정을 추가한다.
		boolean flag = false;
		
		StringBuilder sql = new StringBuilder();

		sql.append("INSERT INTO EMAILDOTCOM_PROFILE (ID, PASSWD, NM, BIRTHDAY, EMAIL, CHARGE, BLOCKED) "
				+ " VALUES(?, ?, ?, ?, ?, '0', '0') ");
		
		try(
			Connection conn = new ConnectionFactory().getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		){
			pstmt.setString(1, profile.getID());
			pstmt.setString(2, profile.getPassword());
			pstmt.setString(3, profile.getName());
			pstmt.setString(4, profile.getBirthday());
			pstmt.setString(5, profile.getEmail());
			pstmt.executeUpdate();
			flag = true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	public void deleteProfile(AccountVO account) {
		//계정 지우기
		//테이블에 계정이 있는가?
		//비밀번호가 동일한가?
		//모두 참일 경우 테이블에서 계정을 지운다
	}
	
	public List<AccountIDVO> getAccountIDList(ProfileVO profile) {
		List<AccountIDVO> idList = new ArrayList<AccountIDVO>();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ID "
				+ "FROM EMAILDOTCOM_PROFILE "
				+ "WHERE NM = ? "
				+ "  AND TO_CHAR(BIRTHDAY, 'YYYYMMDD') = ? ");
		
		try(
			Connection conn = new ConnectionFactory().getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		){
			pstmt.setString(1, profile.getName());
			pstmt.setString(2, profile.getBirthday());
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				String id = rs.getString("ID");
				idList.add(new AccountIDVO(id));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return idList;
	}
	
	public AccountVO getAccount(ProfileVO profile) {

		AccountVO account = null;
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ID, PASSWD "
				+ "FROM EMAILDOTCOM_PROFILE "
				+ "WHERE ID = ? "
				+ "  AND EMAIL = ? ");
		
		try(
			Connection conn = new ConnectionFactory().getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		){
			pstmt.setString(1, profile.getID());
			pstmt.setString(2, profile.getEmail());
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				account = new AccountVO();
				String id = rs.getString("ID");
				String passwd = rs.getString("PASSWD");
				account.setID(id);
				account.setPassword(passwd);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return account;
	}
	
	public ProfileVO getAccount(AccountIDVO id) {

		ProfileVO profile = null;
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * "
				+ "FROM EMAILDOTCOM_PROFILE "
				+ "WHERE ID = ? ");
		
		try(
			Connection conn = new ConnectionFactory().getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		){
			pstmt.setString(1, id.getID());
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				profile = new ProfileVO();
				String passwd = rs.getString("PASSWD");
				String name = rs.getString("NM");
				String birthday = rs.getString("BIRTHDAY");
				String email = rs.getString("EMAIL");
				profile.setID(id.getID());
				profile.setPassword(passwd);
				profile.setName(name);
				profile.setBirthday(birthday);
				profile.setEmail(email);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return profile;
	}
	
	public void sendEmail(EmailVO email) {
		StringBuilder sql = new StringBuilder();

		sql.append("INSERT INTO EMAILDOTCOM_"+ email.getReceiverID() +"_RECEIVED_MAIL (CODE, SENDER_ID, SENDER_NAME, RECEIVER_ID, RECEIVER_NAME, TITLE, DETAIL, BELONG) "
				+ " VALUES(SEQ_EMAILDOTCOM_RECEIVED_EMAIL_CODE.NEXTVAL, ?, ?, ?, ?, ?, ?, 'RECEIVED') ");
		
		try(
			Connection conn = new ConnectionFactory().getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		){
			pstmt.setString(1, email.getSenderID());
			pstmt.setString(2, email.getSenderName());
			pstmt.setString(3, email.getReceiverID());
			pstmt.setString(4, email.getReceiverName());
			pstmt.setString(5, email.getTitle());
			pstmt.setString(6, email.getDetail());
			pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addSentEmail(EmailVO email) {
		StringBuilder sql = new StringBuilder();

		sql.append("INSERT INTO EMAILDOTCOM_"+ email.getSenderID() +"_SENT_MAIL (CODE, SENDER_ID, SENDER_NAME, RECEIVER_ID, RECEIVER_NAME, TITLE, DETAIL, BELONG) "
				+ " VALUES(SEQ_EMAILDOTCOM_SENT_EMAIL_CODE.NEXTVAL, ?, ?, ?, ?, ?, ?, 'SENT') ");
		
		try(
			Connection conn = new ConnectionFactory().getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		){
			pstmt.setString(1, email.getSenderID());
			pstmt.setString(2, email.getSenderName());
			pstmt.setString(3, email.getReceiverID());
			pstmt.setString(4, email.getReceiverName());
			pstmt.setString(5, email.getTitle());
			pstmt.setString(6, email.getDetail());
			pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean IsAccountExist(AccountIDVO id) {
		
		boolean flagExist = false;
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ID "
				+ "FROM EMAILDOTCOM_PROFILE "
				+ "WHERE ID = ? ");
		
		try(
			Connection conn = new ConnectionFactory().getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		){
			pstmt.setString(1, id.getID());
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				flagExist = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return flagExist;
	}
	
	public boolean IsEmailExist(ProfileVO profile) {

		boolean flagExist = false;
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT EMAIL "
				+ "FROM EMAILDOTCOM_PROFILE "
				+ "WHERE EMAIL = ? ");
		
		try(
			Connection conn = new ConnectionFactory().getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		){
			pstmt.setString(1, profile.getEmail());
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				flagExist = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return flagExist;
	}
	
	public void createSentEmail(ProfileVO profile) {
		StringBuilder sql = new StringBuilder();

		sql.append("CREATE TABLE EMAILDOTCOM_"+ profile.getID() +"_SENT_MAIL(\n"
				+ "	CODE	INTEGER 	PRIMARY KEY,\n"
				+ "	SENDER_ID      VARCHAR(20)    NOT NULL,\n"
				+ "	SENDER_NAME    VARCHAR(20)  	 NOT NULL,\n"
				+ " RECEIVER_ID    VARCHAR(20)    NOT NULL,\n"
				+ " RECEIVER_NAME  VARCHAR(20)    NOT NULL,\n"
				+ "	SNT_DATE       DATE DEFAULT 	 SYSDATE,\n"
				+ " TITLE          VARCHAR(100)   NOT NULL,\n"
				+ " DETAIL         VARCHAR(1000)  NOT NULL,\n"
				+ " BELONG         VARCHAR(10)    NOT NULL\n"
				+ ")");
		
		try(
			Connection conn = new ConnectionFactory().getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		){
			pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void createReceivedEmail(ProfileVO profile) {
		StringBuilder sql = new StringBuilder();

		sql.append("CREATE TABLE EMAILDOTCOM_"+ profile.getID() +"_RECEIVED_MAIL(\n"
				+ "	CODE	INTEGER 	PRIMARY KEY,\n"
				+ "	SENDER_ID      VARCHAR(20)    NOT NULL,\n"
				+ "	SENDER_NAME    VARCHAR(20)  	 NOT NULL,\n"
				+ " RECEIVER_ID    VARCHAR(20)    NOT NULL,\n"
				+ " RECEIVER_NAME  VARCHAR(20)    NOT NULL,\n"
				+ "	SNT_DATE       DATE DEFAULT 	 SYSDATE,\n"
				+ " TITLE          VARCHAR(100)   NOT NULL,\n"
				+ " DETAIL         VARCHAR(1000)  NOT NULL,\n"
				+ " BELONG         VARCHAR(10)    NOT NULL\n"
				+ ")");
		
		try(
			Connection conn = new ConnectionFactory().getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		){
			pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void createRecycleEmail(ProfileVO profile) {
		StringBuilder sql = new StringBuilder();

		sql.append("CREATE TABLE EMAILDOTCOM_"+ profile.getID() +"_RECYCLEBIN_MAIL(\n"
				+ "	CODE	INTEGER 	PRIMARY KEY,\n"
				+ "	SENDER_ID      VARCHAR(20)    NOT NULL,\n"
				+ "	SENDER_NAME    VARCHAR(20)  	 NOT NULL,\n"
				+ " RECEIVER_ID    VARCHAR(20)    NOT NULL,\n"
				+ " RECEIVER_NAME  VARCHAR(20)    NOT NULL,\n"
				+ "	SNT_DATE       DATE DEFAULT 	 SYSDATE,\n"
				+ " TITLE          VARCHAR(100)   NOT NULL,\n"
				+ " DETAIL         VARCHAR(1000)  NOT NULL,\n"
				+ " BELONG         VARCHAR(10)    NOT NULL\n"
				+ ")");
		
		try(
			Connection conn = new ConnectionFactory().getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		){
			pstmt.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
