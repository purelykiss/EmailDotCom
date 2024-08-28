package kr.ac.kopo.ui.recycleState;

import kr.ac.kopo.singleton.EmailService;
import kr.ac.kopo.singleton.InputManager;
import kr.ac.kopo.singleton.ViewManager;
import kr.ac.kopo.ui.mail.BaseMailState;
import kr.ac.kopo.ui.mail.IMailUI;
import kr.ac.kopo.vo.EmailVO;

public class RecycleBycodeState extends BaseMailState {
	public RecycleBycodeState(IMailUI parent) {
		super(parent, "byCode", "코드로 찾기");
		//emailPerPage = ;
		//curPage = ;
	}

	@Override
	public void execute() {
		System.out.print("열람할 메일의 코드를 입력하십시오 : ");
		int input = InputManager.getInstance().nextInt();
		
		EmailVO email = EmailService.getInstance().getEmail(3, input);
		
		if(email != null)
			ViewManager.getInstance().ViewMail(3, input);
		else
			System.out.println("해당하는 메일이 없습니다.");
		System.out.println();
		System.out.println();
		
		parent.changeState(0);
	}
}
