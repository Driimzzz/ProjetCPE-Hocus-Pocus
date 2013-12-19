package interfaceclientserveur;

import websocket.console.Message;
import websocket.console.SocketAnnotation;
import websocket.console.SocketAnnotation.MessageType;

public class Interface {
	public Interface()
	{
		
	}
	
	public static void Console(String str)
	{
		System.out.println(str);
		Message message=new Message(websocket.console.SocketAnnotation.MessageType.Message,str);
		SocketAnnotation.broadcast(message);
	}
	

}
