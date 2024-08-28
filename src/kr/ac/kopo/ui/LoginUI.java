package kr.ac.kopo.ui;

import kr.ac.kopo.singleton.CredentialManager;
import kr.ac.kopo.singleton.EmailService;
import kr.ac.kopo.singleton.InputManager;
import kr.ac.kopo.singleton.UIManager;
import kr.ac.kopo.ui.base.BaseUI;
import kr.ac.kopo.vo.AccountVO;
import kr.ac.kopo.vo.SessionVO;

public class LoginUI extends BaseUI{
	public LoginUI() {
		super("login", "로그인");
	}

	@Override
	public void execute() {
		if(CredentialManager.getInstance().getIsLogin()) {
			caseLogIn();
		}else{
			caseLogOut();
		}
		UIManager.getInstance().changeUI("lobby");
	}
	
	private void caseLogIn() {
		System.out.println("이미 로그인한 상태입니다.");
	}
	
	private void caseLogOut(){
		System.out.print("아이디:");
		String id = InputManager.getInstance().nextLine();
		System.out.print("비밀번호:");
		String passwd = InputManager.getInstance().nextLine();
		
		AccountVO account = new AccountVO(id, passwd);
		EmailService.getInstance().login(account);
		
		if(CredentialManager.getInstance().getIsLogin()) {
			System.out.println("로그인 성공");
		}else{
			System.out.println("로그인 실패. 아이디나 비밀번호를 다시 확인하십시오.");
		}
	}
}
