package kr.ac.kopo.ui;

import kr.ac.kopo.singleton.CredentialManager;
import kr.ac.kopo.singleton.EmailService;
import kr.ac.kopo.singleton.InputManager;
import kr.ac.kopo.singleton.UIManager;
import kr.ac.kopo.ui.base.BaseUI;
import kr.ac.kopo.vo.AccountIDVO;
import kr.ac.kopo.vo.EmailVO;

public class WriteUI extends BaseUI{

	public WriteUI() {
		super("write", "메일 보내기");
	}
	
	@Override
	public void execute() {
		System.out.println("메일 보내기)");
		System.out.println("  (공백으로 돌아가기)");
		System.out.println();
		System.out.println();
		
		boolean flagValid = false;
		AccountIDVO receiver = new AccountIDVO();
		
		while(!flagValid) {
			System.out.print("받는사람 아이디: ");
			String input = new String(InputManager.getInstance().nextLine());
			if(input.equals("")) {
				UIManager.getInstance().changeUI(0);
				return;	
			}
			receiver.setID(input);
			flagValid = EmailService.getInstance().IsAccountIDExist(receiver);
			if(!flagValid) {
				System.out.println("일치하는 아이디가 없습니다. 다시 확인하십시오.");
			}
		}
		System.out.println();
		System.out.println();
		
		System.out.print("제목 : ");
		String title = new String(InputManager.getInstance().nextLine());
		if(title.equals("")) {
			UIManager.getInstance().changeUI(0);
			return;	
		}
		System.out.println();
		System.out.println();
		
		System.out.println("내용(공백 2번으로 마무리) : ");
		StringBuilder detail = new StringBuilder();
		int flagStack = 0;
		while(flagStack < 2) {
			String input = InputManager.getInstance().nextLine();
			if(input.equals("")) {
				flagStack++;
			}else {
				flagStack = 0;
			}
			detail.append(input + "\n");
		}
		
		EmailVO email = new EmailVO(0, CredentialManager.getInstance().getID(), null, receiver.getID(), null, null, title, detail.toString(), null);
		EmailService.getInstance().sendEmail(email);
		System.out.println("메일을 보냈습니다.");
		System.out.println();
		System.out.println();
		
		UIManager.getInstance().changeUI(0);
	}
	
}
