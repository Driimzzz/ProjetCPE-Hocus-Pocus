package websocket.console;

import javax.websocket.Session;

public class Client {
	private int id;
	private Session session;
	private String nickname;
	
	
	public Client() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Client(int id, Session session, String nickname) {
		super();
		this.id = id;
		this.session = session;
		this.nickname = nickname;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Session getSession() {
		return session;
	}
	public void setSession(Session session) {
		this.session = session;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	

}
