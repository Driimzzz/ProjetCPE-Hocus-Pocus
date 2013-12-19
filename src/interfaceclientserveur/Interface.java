package interfaceclientserveur;

import websocket.console.Message;
import websocket.console.SocketAnnotation;
import websocket.console.SocketAnnotation.MessageType;

public class Interface {
	public Interface()
	{
		
	}
	
	public void Console(String str)
	{
		Message message=new Message(websocket.console.SocketAnnotation.MessageType.Message,str);
		SocketAnnotation.broadcast(message);
	}
	

}
