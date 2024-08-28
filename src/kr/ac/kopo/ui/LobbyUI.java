package kr.ac.kopo.ui;

import kr.ac.kopo.singleton.CredentialManager;
import kr.ac.kopo.singleton.InputManager;
import kr.ac.kopo.singleton.UIManager;
import kr.ac.kopo.ui.base.BaseUI;

public class LobbyUI extends BaseUI{
	private boolean flagLogo;
	
	public LobbyUI() {
		super("lobby","로비");
		flagLogo = true;
	}

	@Override
	public void execute() {
		Logo();
		if(CredentialManager.getInstance().getIsLogin()) {
			caseLogin();
		}else {
			caseLogout();
		}
		
	}
	
	private void Logo() {
		if(flagLogo) {
			flagLogo = false;
			System.out.println("********************");
			System.out.println("Email.com");
			System.out.println("********************");
			
			System.out.println();
			System.out.println();
		}
	}
	
	private void caseLogin() {
		System.out.println("로비) 원하시는 동작을 선택하십시오.");
		System.out.println("4. " + UIManager.getInstance().getUIDescription(4)); //받은메일함
		System.out.println("5. " + UIManager.getInstance().getUIDescription(5)); //보낸메일함
		System.out.println("6. " + UIManager.getInstance().getUIDescription(6)); //휴지통
		System.out.println("7. " + UIManager.getInstance().getUIDescription(7)); //계정정보 >> 계정정보변경, 회원탈퇴
		System.out.println("8. " + UIManager.getInstance().getUIDescription(8)); //로그아웃
		System.out.println();
		System.out.println();
		
		boolean flag = false;
		int input = 0;
		while(!flag) {
			flag = true;
			input = InputManager.getInstance().nextInt();
			if(input <= UIManager.getInstance().getUIListSize()) {
				UIManager.getInstance().changeUI(input);
			}else {
				System.out.println("잘못된 번호입니다. 다시 입력하십시오.");
			}
		}
	}
	
	private void caseLogout() {
		System.out.println("로비) 원하시는 동작을 선택하십시오.");
		System.out.println("1. " + UIManager.getInstance().getUIDescription(1)); //로그인
		System.out.println("2. " + UIManager.getInstance().getUIDescription(2)); //아이디 비밀번호 찾기
		System.out.println("3. " + UIManager.getInstance().getUIDescription(3)); //회원가입
		System.out.println();
		System.out.println();
		
		boolean flag = false;
		int input = 0;
		while(!flag) {
			flag = true;
			input = InputManager.getInstance().nextInt();
			if(input <= UIManager.getInstance().getUIListSize()) {
				UIManager.getInstance().changeUI(input);
			}else {
				System.out.println("잘못된 번호입니다. 다시 입력하십시오.");
			}
		}
	}
}
