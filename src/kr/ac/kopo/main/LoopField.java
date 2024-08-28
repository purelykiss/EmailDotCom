package kr.ac.kopo.main;
import kr.ac.kopo.singleton.ManagerFactory;
import kr.ac.kopo.singleton.UIManager;

public class LoopField {
	public LoopField() {}
	
	public void Activate() {
		try {
			ManagerFactory.activate();
			while(true) {
				UIManager.getInstance().activate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
