package kr.ac.kopo.vo;

public class SessionVO {
	String id;
	long session;
	
	public SessionVO(String id, long session) {
		super();
		this.id = id;
		this.session = session;
	}
	
	public SessionVO(long session) {
		super();
		this.session = session;
	}
	
	public SessionVO() {
		super();
	}
	
	public String getID() {
		return id;
	}
	
	public void setID(String id) {
		this.id = id;
	}

	public long getSession() {
		return session;
	}

	public void setSession(long session) {
		this.session = session;
	}
	
	
}
