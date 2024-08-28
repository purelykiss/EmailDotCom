package kr.ac.kopo.singleton;

import kr.ac.kopo.vo.AccountVO;
import kr.ac.kopo.vo.SessionVO;

public class CredentialManager {
	public static CredentialManager instance;
	
	private AccountVO curAccount;
	private SessionVO curSession;
	boolean isLogin;
	
	private CredentialManager() {
		curAccount = null;
		curSession = null;
		isLogin = false;
	}
	
	public static CredentialManager instanciate() throws Exception {
		if(instance == null)
			instance = new CredentialManager();
		else
			throw new Exception("이미 CredentialManager 싱글톤이 있습니다.");
		if(instance == null)
			throw new Exception("싱글톤 CredentialManager 생성에 실패했습니다.");
		
		return instance;
	}
	
	public static CredentialManager getInstance(){
		if(instance == null) {
			System.out.println("아직 CredentialManager 싱글톤이 만들어지지 않았습니다.");
			return null;
		}else {
			return instance;
		}
	}
	
	public void login(AccountVO account, SessionVO session) {
		curAccount = account;
		curSession = session;
		isLogin = true;
	}
	
	public void logout(AccountVO account, SessionVO session) {
		curAccount = null;
		curSession = null;
		isLogin = false;
	}
	
	public boolean getIsLogin() {
		return isLogin;
	}
	
	public String getID() {
		return curAccount.getID();
	}
}
