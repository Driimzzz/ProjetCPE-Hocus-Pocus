/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package websocket.console;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import websocket.console.Message.ParseException;



@ServerEndpoint(value = "/websocket/console")
public class SocketAnnotation {

    private static final String GUEST_PREFIX = "Guest";
    private static final AtomicInteger connectionIds = new AtomicInteger(0);
    

   
    private static Salle salle=new Salle();
    
  
    public static enum MessageType {
        Error(0),
        Connection(1),
        Disconnection(2),
        Message(3);
        
        private int index;
        private MessageType(int index) { this.index =index; }

        public static MessageType getMessageType(int index) {
           for (MessageType l : MessageType.values()) {
               if (l.index == index) return l;
           }
           throw new IllegalArgumentException("MessageType not found");
        }

    }


    private Client client;

    public SocketAnnotation() {
    
    }


    @OnOpen
    public void start(Session session) {
    	client=new Client();
    	client.setId(connectionIds.getAndIncrement());
    	client.setNickname(GUEST_PREFIX +client.getId() );
        client.setSession(session);
        salle.getClients().add(client);
        String mess = String.format("%s %s", client.getNickname(), "has joined.");
        Message message=new Message(MessageType.Connection,mess,client.getId());
        broadcast(message);
    }


    @OnClose
    public void end() {
        salle.getClients().remove(client);
        
        String mess = String.format("%s %s",
        		client.getNickname(), "has disconnected.");
        Message message=new Message(MessageType.Disconnection,mess,client.getId());
        broadcast(message);
    }


    @OnMessage
    public void incoming(String data) {
        // Never trust the client
        String mess = String.format(HTMLFilter.filter(data.toString()));
        Message message=new Message();
        try {
			message=message.parseFromString(mess);
			message.setAuteur(client.getId());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			broadcast(new Message(MessageType.Error,e.getMessage()));
		}
        broadcast(message);
    }


    public static void broadcast(Message message) {
    	salle.addMessage(message);
    	if(message.getDestination()==-1)
    		send2All(message);
    	else
    	{
    		send2User(message);
    	}
    }
    
    private static void send2User(Message message)
    {
    	Client client=salle.getClients().get((int)message.getDestination());
        try {
            client.getSession().getBasicRemote().sendText(message.toString());
        } catch (IOException e) {
        	broadcast(new Message(MessageType.Error,e.getMessage()));
            salle.getClients().remove(client);
            try {
            	client.getSession().close();
            } catch (IOException e1) {
            	broadcast(new Message(MessageType.Error,e1.getMessage()));
            }
            String mess = String.format("%s %s",
            		client.getNickname(), "has been disconnected.");
            message=new Message(MessageType.Disconnection,mess,client.getId());
            broadcast(message);
        }
    	
    }
    private static void send2All(Message message)
    {
    	for (Client client : salle.getClients()) {
            try {
                client.getSession().getBasicRemote().sendText(message.toString());
            } catch (IOException e) {
            	broadcast(new Message(MessageType.Error,e.getMessage()));
                salle.getClients().remove(client);
                try {
                	client.getSession().close();
                } catch (IOException e1) {
                	broadcast(new Message(MessageType.Error,e1.getMessage()));
                }
                String mess = String.format("%s %s",
                		client.getNickname(), "has been disconnected.");
                message=new Message(MessageType.Disconnection,mess,client.getId());
                broadcast(message);
            }
        }
    }
    
}
