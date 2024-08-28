package kr.ac.kopo.ui.recycleState;

import kr.ac.kopo.singleton.InputManager;
import kr.ac.kopo.singleton.UIManager;
import kr.ac.kopo.ui.mail.BaseMailState;
import kr.ac.kopo.ui.mail.IMailState;
import kr.ac.kopo.ui.mail.IMailUI;

public class RecycleLobbyState extends BaseMailState {
	
	public RecycleLobbyState(IMailUI parent){
		super(parent, "lobby", "로비");
	}
	
	@Override
	public void execute() {
		int choice = 0;
		System.out.println("휴지통)");
		System.out.println("1. " + parent.getStateDescription(1)); 
		System.out.println("2. " + parent.getStateDescription(2)); 
		System.out.println("3. " + parent.getStateDescription(3)); 
		System.out.println("0. 뒤로");
		System.out.println();
		boolean flagValid = false; 
		while(!flagValid) {
			System.out.print("원하는 동작을 선택하십시오:");
			choice = InputManager.getInstance().nextInt();
			
			flagValid = true;
			
			if(choice <= 3 && choice > 0) {
				parent.changeState(choice);
			}else if(choice == 0){
				System.out.println();
				System.out.println();
				parent.changeUI(0);
			}else{
				System.out.println("잘못된 번호입니다. 다시 눌러주십시오.");
				flagValid = false;
			}
				
		}
	}
}