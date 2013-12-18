package websocket.console;

import javax.websocket.Session;

public class Client {
	private long id;
	private Session session;
	private String nickname;
	
	
	public Client() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Client(long id, Session session, String nickname) {
		super();
		this.id = id;
		this.session = session;
		this.nickname = nickname;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
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
