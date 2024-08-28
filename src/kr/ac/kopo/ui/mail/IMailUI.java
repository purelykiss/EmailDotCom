package kr.ac.kopo.ui.mail;

public interface IMailUI {
	public void initialize();
	public void execute();
	public void changeState(String id);
	public void changeState(int index);
	public void changeUI(String id);
	public void changeUI(int index);
	public String getStateDescription(String id);
	public String getStateDescription(int index);
}
