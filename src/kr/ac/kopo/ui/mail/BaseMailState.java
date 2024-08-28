package kr.ac.kopo.ui.mail;

public abstract class BaseMailState implements IMailState{
	public IMailUI parent;
	
	String id;
	String description; 
	
	public BaseMailState(IMailUI parent, String id, String description){
		this.parent = parent;
		this.id = id;
		this.description = description;
	}

	@Override
	public String getID() {
		return id;
	}

	@Override
	public String getDescription() {
		return description;
	}
}
