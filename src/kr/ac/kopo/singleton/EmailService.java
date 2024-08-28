package kr.ac.kopo.singleton;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import kr.ac.kopo.dao.ProfileDAO;
import kr.ac.kopo.dao.EmailDAO;
import kr.ac.kopo.dao.SessionDAO;
import kr.ac.kopo.vo.AccountIDVO;
import kr.ac.kopo.vo.AccountVO;
import kr.ac.kopo.vo.EmailVO;
import kr.ac.kopo.vo.ProfileVO;
import kr.ac.kopo.vo.SessionVO;

public class EmailService {
	public static EmailService instance;

	private ProfileDAO profileDAO;
	private SessionDAO sessionDAO;
	private EmailDAO emailDAO;
	
	private EmailService() {
		profileDAO = new ProfileDAO();
		sessionDAO = new SessionDAO();
		emailDAO = new EmailDAO();
	}
	
	public static EmailService instanciate() throws Exception {
		if(instance == null)
			instance = new EmailService();
		else
			throw new Exception("이미 EmailService 싱글톤이 있습니다.");
		if(instance == null)
			throw new Exception("싱글톤 EmailService 생성에 실패했습니다.");
		
		return instance;
	}
	
	public static EmailService getInstance(){
		if(instance == null) {
			System.out.println("아직 EmailService 싱글톤이 만들어지지 않았습니다.");
			return null;
		}else {
			return instance;
		}
	}

	public boolean login(AccountVO account) {
		SessionVO session = getSession(account);	//session값 갱신 시도
		if(session != null) {
			CredentialManager.getInstance().login(account, session);
			return true;
		}
		
		return false;	//로그인 실패시 session = null
	}
	
	public void logOut(AccountVO account, SessionVO session) {
		giveUpSession(account, session);
		CredentialManager.getInstance().logout(account, session);
	}
	
	public boolean createProfile(ProfileVO profile) {
		boolean flag =  profileDAO.createProfile(profile);
		if(flag) {
			profileDAO.createSentEmail(profile);
			profileDAO.createReceivedEmail(profile);
			profileDAO.createRecycleEmail(profile);
		}
		return flag;
	}
	
	public void deleteProfile(AccountVO account) {
		profileDAO.deleteProfile(account);
	}
	
	public List<AccountIDVO> getAccountIDList(ProfileVO profile) {
		return profileDAO.getAccountIDList(profile);
	}
	
	public AccountVO getAccount(ProfileVO profile) {
		return profileDAO.getAccount(profile);
	}
	
	public boolean IsAccountIDExist(AccountIDVO id) {
		return profileDAO.IsAccountExist(id);
	}
	
	public boolean IsEmailExist(ProfileVO profile) {
		return profileDAO.IsEmailExist(profile);
	}

	/*public void changeAccountAuthority(AccountVO account) {
		
	}*/
	
	/*public void blockAccount(AccountVO myAccount, AccountIDVO targetID) {
		
	}*/
	
	
	public List<EmailVO> getEmailList(int state, int emailPerPage, int page) {//1.받은 편지  2. 보낸 편지  3. 휴지통
		return emailDAO.getEmailList(state, emailPerPage, page);
	}
	
	public EmailVO getEmail(int state, int code) {//1.받은 편지  2. 보낸 편지  3. 휴지통
		return emailDAO.getEmail(state, code);
	}
	
	public List<EmailVO> searchEmail(int mailState, int searchState, List<StringBuilder> searchPlusList, List<StringBuilder> searchMinusList){
		return emailDAO.searchEmail(mailState, searchState, searchPlusList, searchMinusList);
	}
	
	public List<EmailVO> searchEmail(int mailState, Set<Integer> searchStates, List<StringBuilder> searchPlusList, List<StringBuilder> searchMinusList){
		if(searchStates == null || searchStates.size() <= 0)
			return null;
		Set<EmailVO> searchSet = new HashSet<EmailVO>();
		for(Integer item : searchStates) {
			searchSet.addAll(emailDAO.searchEmail(mailState, item, searchPlusList, searchMinusList));	
		}
		List<EmailVO> result = new ArrayList<EmailVO>();
		result.addAll(searchSet);
		return result;
	}
	
	public void sendEmail(EmailVO email) {
		String receiverName = profileDAO.getAccount(new AccountIDVO(email.getReceiverID())).getName();
		String senderName = profileDAO.getAccount(new AccountIDVO(email.getSenderID())).getName();
		email.setReceiverName(receiverName);
		email.setSenderName(senderName);
		profileDAO.sendEmail(email);
		profileDAO.addSentEmail(email);
	}
	
	public void moveMailTo(int stateFrom, int stateTo, int code) {
		if(emailDAO.copyMailTo(stateFrom, stateTo, code))
			emailDAO.removeEmail(stateFrom, code);
	}
	
	public void removeEmail(int state, int code) {
		emailDAO.removeEmail(state, code);
	}
	
	
	public SessionVO getSession(AccountVO account) {
		return sessionDAO.tryGetSession(account);
	}
	
	public boolean refreshSession(AccountVO account, SessionVO session) {
		return sessionDAO.tryRefreshSession(account, session);
	}
	
	public boolean giveUpSession(AccountVO account, SessionVO session) {
		return sessionDAO.tryGiveUpSession(account, session);
	}
}
