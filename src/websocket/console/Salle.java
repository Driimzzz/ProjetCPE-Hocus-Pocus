package websocket.console;

import java.util.ArrayList;
import java.util.List;

public class Salle {

	// listes des messages dans la room
	private static List<Message> messages = new ArrayList<Message>();
	 private static List<Client> clients=new ArrayList<Client>();

	public Salle() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	public static List<Client> getClients() {
		return clients;
	}


	public static void setClients(List<Client> clients) {
		Salle.clients = clients;
	}

	public static Client getClientByID(int ID)
	{
		for(Client c:clients)
		{
			if(c.getId()==ID)
				return c;
		}
		return null;
	}

	public static List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		Salle.messages = messages;
	}

	public void addMessage(Message message) {
		Salle.messages.add(message);
	}

}
