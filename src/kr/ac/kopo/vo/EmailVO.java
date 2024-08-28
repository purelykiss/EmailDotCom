package kr.ac.kopo.vo;

public class EmailVO {
	int code;
	String senderID;
	String senderName;
	String receiverID;
	String receiverName;
	String date;
	String title;
	String detail;
	String belong;
	
	public EmailVO() {
		super();
	}
	public EmailVO(int code, String senderID, String senderName, String receiverID, String receiverName, String date,
			String title, String detail, String belong) {
		super();
		this.code = code;
		this.senderID = senderID;
		this.senderName = senderName;
		this.receiverID = receiverID;
		this.receiverName = receiverName;
		this.date = date;
		this.title = title;
		this.detail = detail;
		this.belong = belong;
	}
	

	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getSenderID() {
		return senderID;
	}
	public void setSenderID(String senderID) {
		this.senderID = senderID;
	}
	public String getSenderName() {
		return senderName;
	}
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	public String getReceiverID() {
		return receiverID;
	}
	public void setReceiverID(String receiverID) {
		this.receiverID = receiverID;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getBelong() {
		return belong;
	}
	public void setBelong(String belong) {
		this.belong = belong;
	}
}
