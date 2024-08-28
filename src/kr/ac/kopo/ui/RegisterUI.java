package kr.ac.kopo.ui;

import kr.ac.kopo.singleton.EmailService;
import kr.ac.kopo.singleton.InputManager;
import kr.ac.kopo.singleton.UIManager;
import kr.ac.kopo.ui.base.BaseUI;
import kr.ac.kopo.vo.AccountIDVO;
import kr.ac.kopo.vo.ProfileVO;

public class RegisterUI extends BaseUI{
	
	public RegisterUI() {
		super("register", "회원가입");
	}

	@Override
	public void execute() {
		System.out.println("새로 회원가입을 합니다.");
		boolean flag = true;
		AccountIDVO id = new AccountIDVO();
		while(flag) {
			System.out.println("아이디를 입력하십시오.");
			id.setID(InputManager.getInstance().nextLine());
			flag = EmailService.getInstance().IsAccountIDExist(id);
			if(flag)
				System.out.println("중복되는 아이디가 있습니다. 다른 아이디를 입력해주십시오.");
		}
		
		String passwd = null;
		flag = true;
		while(flag) {
			System.out.println("비밀번호를 입력하십시오.");
			passwd = new String(InputManager.getInstance().nextLine());
			if(passwd.length() >= 9)
				flag = false;
			else
				System.out.println("비밀번호는 9자리 이상 입력하십시오.");
		}
		
		String name = null;
		flag = true;
		while(flag) {
			System.out.println("사용자 이름을 입력하십시오.");
			name = new String(InputManager.getInstance().nextLine());
			if(name.length() > 0)
				flag = false;
			else
				System.out.println("이름은 공란으로 둘 수 없습니다.");
		}
		
		String birthday = null;
		flag = true;
		while(flag) {
			System.out.println("생년월일을 숫자로만 8자리로 입력하십시오.(예시: 19010101)");
			birthday = new String(InputManager.getInstance().nextLine());
			if(birthday.length() == 8) {
				flag = false;
				for(int i = 0; i < birthday.length(); i++) {
					if(birthday.charAt(i)-'0'<0 || birthday.charAt(i)-'0' > 9) {
						flag = true;
					}
				}
			}
			if(flag)
				System.out.println("양식이 잘못됐습니다.");
		}
		
		String email = null;
		flag = true;
		while(flag) {
			System.out.println("이메일 주소를 입력하십시오.");
			email = InputManager.getInstance().nextLine();
			ProfileVO profile = new ProfileVO(); 
			profile.setEmail(email);
			flag = EmailService.getInstance().IsEmailExist(profile);
			if(flag)
				System.out.println("중복되는 이메일이 있습니다. 다른 이메일을 입력해주십시오.");
		}
		
		ProfileVO profile = new ProfileVO();
		profile.setID(id.getID());
		profile.setPassword(passwd);
		profile.setName(name);
		profile.setBirthday(birthday);
		profile.setEmail(email);
		
		boolean flagSucceed = EmailService.getInstance().createProfile(profile);
		
		if(flagSucceed) {
			System.out.println("계정을 성공적으로 만들었습니다.");
		}else {
			System.out.println("계정을 만드는 도중 문제가 생겼습니다.");
		}
		System.out.println("로비로 돌아갑니다.");
		
		UIManager.getInstance().changeUI(0);
	}
}
