package kr.ac.kopo.dao;

import kr.ac.kopo.singleton.SessionGenerator;
import kr.ac.kopo.vo.AccountVO;
import kr.ac.kopo.vo.SessionVO;

public class SessionDAO {
	
	
	public SessionDAO() {
		
	}
	
	public SessionVO tryGetSession(AccountVO account){
		try {
			return SessionGenerator.getInstance().clientRequestGetSession(account);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public boolean tryGiveUpSession(AccountVO account, SessionVO session) {
		boolean flag = false;
		
		try {
			flag = SessionGenerator.getInstance().clientRequestRemoveSession(account, session);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	public boolean tryRefreshSession(AccountVO account, SessionVO session) {
		try {
			SessionGenerator.getInstance().clientRequestRefreshSession(account, session);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(session != null)
			return true;
		else
			return false;
	}
}
