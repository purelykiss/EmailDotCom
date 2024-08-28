package kr.ac.kopo.singleton;

import kr.ac.kopo.vo.EmailVO;

public class ViewManager {
	public static ViewManager instance;
	
	private ViewManager() {
		
	}
	
	public static ViewManager instanciate() throws Exception {
		if(instance == null)
			instance = new ViewManager();
		else
			throw new Exception("이미 ViewManager 싱글톤이 있습니다.");
		if(instance == null)
			throw new Exception("싱글톤 ViewManager 생성에 실패했습니다.");
		
		return instance;
	}
	
	public static ViewManager getInstance(){
		if(instance == null) {
			System.out.println("아직 ViewManager 싱글톤이 만들어지지 않았습니다.");
			return null;
		}else {
			return instance;
		}
	}
	
	public void ViewMail(int state, int code) {	//1.받은 편지  2. 보낸 편지  3. 휴지통
		EmailVO email = EmailService.getInstance().getEmail(state, code);
		System.out.printf("번호: %d\n", email.getCode());
		System.out.printf("제목: %s\n", email.getTitle());
		System.out.printf("보낸이: %s(%s)\n", email.getSenderName(), email.getSenderID());
		System.out.printf("받는이: %s(%s)\n", email.getReceiverName(), email.getReceiverID());
		System.out.printf("날짜: %s\n", email.getDate());
		System.out.printf("내용: \n%s\n", email.getDetail());
		System.out.println();
		System.out.println();
		
		boolean flagValid = false;
		while(!flagValid) {
			switch(state) {
			case 1:
			case 2:
				System.out.println("1. 휴지통으로  2. 뒤로");
				break;
			case 3:
				System.out.println("1. 삭제  2. 뒤로");
			}
			
			int input = InputManager.getInstance().nextInt();
			
			switch(input) {
			case 1:
				if(state != 3) {
					EmailService.getInstance().moveMailTo(state, 3, code);
					System.out.println("해당 메일이 휴지통으로 이동했습니다.");
					flagValid = true;
					break;
				}else {
					EmailService.getInstance().removeEmail(state, code);
					System.out.println("해당 메일이 삭제됐습니다.");
					flagValid = true;
					break;
				}
			case 2:
				flagValid = true;
				break;
			}
		}
		System.out.println();
		System.out.println();
	}
}
