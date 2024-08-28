package kr.ac.kopo.ui;

import java.util.List;

import kr.ac.kopo.singleton.EmailService;
import kr.ac.kopo.singleton.InputManager;
import kr.ac.kopo.singleton.UIManager;
import kr.ac.kopo.ui.base.BaseUI;
import kr.ac.kopo.vo.AccountIDVO;
import kr.ac.kopo.vo.AccountVO;
import kr.ac.kopo.vo.ProfileVO;

public class FindUI extends BaseUI{
	private boolean flagPasswd;	// 0. 시작  1. 아이디찾음>>비밀번호 찾을지 질문  2. 매인메뉴로 
	
	public FindUI() {
		super("find", "아이디 비밀번호 찾기");
		flagPasswd = false;
	}

	@Override
	public void execute() {
		findID();
		if(flagPasswd) {
			findPasswd();
		}
		UIManager.getInstance().changeUI(0);
	}
	
	private void findID() {
		System.out.println("아이디를 찾습니다.");
		System.out.println();
		System.out.print("이름을 입력하십시오:");
		String name = InputManager.getInstance().nextLine();
		System.out.print("생년월일 8글자를 숫자로만 입력하십시오(예시: 19010101):");
		String birthday = InputManager.getInstance().nextLine();
		System.out.println();
		System.out.println();
		ProfileVO profile = new ProfileVO();
		profile.setName(name);
		profile.setBirthday(birthday);
		List<AccountIDVO> idList = EmailService.getInstance().getAccountIDList(profile);
		if(idList.size() > 0) {
			System.out.println("만족하는 아이디는 다음과 같습니다.");
			for(AccountIDVO item : idList) {
				System.out.println(item.getID());
			}
			flagPasswd = true;
		}else {
			System.out.println("조건을 만족하는 아이디가 없습니다.");
			flagPasswd = false;
		}
	}
	
	private void findPasswd() {
		System.out.println("비밀번호도 찾겠습니까? y/n");
		boolean flag = false;
		while(!flag) {
			StringBuilder sb = new StringBuilder(); 
			sb.append(InputManager.getInstance().nextLine());
			switch(sb.toString()){
			case "y":
			case "Y":
				flag = true;
				break;
			case "n":
			case "N":
				return;
			}
			sb.delete(0, sb.length()-1);
		}
		System.out.println("비밀번호를 찾습니다.");
		System.out.println();
		System.out.print("아이디를 입력하십시오:");
		String id = InputManager.getInstance().nextLine();
		System.out.print("이메일 주소를 입력하세요:");
		String email = InputManager.getInstance().nextLine();
		System.out.println();
		System.out.println();
		ProfileVO profile = new ProfileVO();
		profile.setID(id);
		profile.setEmail(email);
		AccountVO account = EmailService.getInstance().getAccount(profile);
		if(account != null) {
			System.out.println("아이디 비밀번호는 다음과 같습니다.");
			System.out.println(account.getID());
			System.out.println(account.getPassword());
		}else {
			System.out.println("조건을 만족하는 아이디 비밀번호가 없습니다.");
		}
	}
}
