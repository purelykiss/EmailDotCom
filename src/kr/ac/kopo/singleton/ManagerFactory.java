package kr.ac.kopo.singleton;

public class ManagerFactory {
	public static void activate() throws Exception{
		UIManager.instanciate();
		InputManager.instanciate();
		EmailService.instanciate();
		SessionGenerator.instanciate();
		CredentialManager.instanciate();
		ViewManager.instanciate();
	}
}
