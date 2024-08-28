package kr.ac.kopo.ui.base;

public abstract class BaseUI implements IUI {
	private String id;
	private String description;
	
	public BaseUI() {
		
	}
	
	public BaseUI(String id, String description) {
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
