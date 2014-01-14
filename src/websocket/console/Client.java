package websocket.console;

import javax.websocket.Session;

import org.json.JSONException;
import org.json.JSONObject;

public class Client {
	private int id;
	private Session session;
	private String nickname;
	private int lastMessage;
	
	
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
	public int getLastMessage() {
		return lastMessage;
	}
	public void setLastMessage(int lastMessage) {
		this.lastMessage = lastMessage;
	}
	public JSONObject toJson() {
		JSONObject json = new JSONObject();
		try {
			json.put("id", this.getId());
		} catch (JSONException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		try {
			json.put("nickname", this.getNickname());
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return json;
	}


}
