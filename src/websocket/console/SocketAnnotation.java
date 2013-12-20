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

import interfaceclientserveur.Interface;

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

import com.sun.xml.internal.ws.client.ClientSchemaValidationTube;

import websocket.console.Message.ParseException;

@ServerEndpoint(value = "/websocket/console")
public class SocketAnnotation {

	private static final String GUEST_PREFIX = "Guest";
	private static final AtomicInteger connectionIds = new AtomicInteger(0);

	private static Salle salle = new Salle();

	public static enum MessageType {
		Error(0), Message(1), Console(2);

		private int index;

		private MessageType(int index) {
			this.index = index;
		}

		public static MessageType getMessageType(int index) {
			for (MessageType l : MessageType.values()) {
				if (l.index == index)
					return l;
			}
			throw new IllegalArgumentException("MessageType not found");
		}

	}

	private Client client;
	

	public static Salle getSalle() {
		return salle;
	}

	public static void setSalle(Salle salle) {
		SocketAnnotation.salle = salle;
	}

	public SocketAnnotation() {

	}

	// Appeler lors de l'ouverture de la socket
	@OnOpen
	public void start(Session session) {
		client = new Client();
		client.setId(connectionIds.getAndIncrement());
		client.setNickname(GUEST_PREFIX + client.getId());
		client.setSession(session);
		Salle.getClients().add(client);
		//met à jour les mesages buffered
		//bufferedMessages();
		String mess = String.format("%s %s", client.getNickname(),
				"has joined.");
		Message message = new Message(MessageType.Message, mess,
				client.getId());
		broadcast(message);

	}

	// Appeler lors de la fermeture de la fenêtre donc de la socket
	@OnClose
	public void end() {
		Salle.getClients().remove(client);

		String mess = String.format("%s %s", client.getNickname(),
				"has disconnected.");
		Message message = new Message(MessageType.Message, mess,
				client.getId());
		broadcast(message);
	}

	// Réception des messages du client
	@OnMessage
	public void incoming(String data) {
		// Never trust the client
		String mess = String.format(data.toString());
		Message message = new Message();
		try {
			message = message.parseFromString(mess);
			message.setAuteur(client.getId());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			broadcast(new Message(MessageType.Error, e.getMessage()));
		}
		//broadcast(message);
		Interface.gestionMessage(message);
	}

	// le serveur envoie le message au client
	public static void broadcast(Message message) {
		salle.addMessage(message);
		if (message.getDestination() == -1)
			send2All(message);
		else {
			send2User(message);
		}
	}

	// envoie à'lutilsisateur concerné
	private static void send2User(Message message) {
		try {

			Client client = Salle.getClients().get(
					(int) message.getDestination());
			client.setLastMessage(message.getiDmessage());
			try {
				client.getSession().getBasicRemote()
						.sendText(message.toString());
			} catch (IOException e) {
				broadcast(new Message(MessageType.Error, e.getMessage()));
				Salle.getClients().remove(client);
				try {
					client.getSession().close();
				} catch (IOException e1) {
					broadcast(new Message(MessageType.Error, e1.getMessage()));
				}
				String mess = String.format("%s %s", client.getNickname(),
						"has been disconnected.");
				message = new Message(MessageType.Message, mess,
						client.getId());
				broadcast(message);
			}
		} catch (Exception e) {
			broadcast(new Message(MessageType.Error, e.getMessage()));
		}

	}

	// envoie à tous les utilisateurs
	private static void send2All(Message message) {
		for (Client client : Salle.getClients()) {
			try {
				client.getSession().getBasicRemote()
						.sendText(message.toString());
				client.setLastMessage(message.getiDmessage());
			} catch (IOException e) {
				broadcast(new Message(MessageType.Error, e.getMessage()));
				Salle.getClients().remove(client);
				try {
					client.getSession().close();
				} catch (IOException e1) {
					broadcast(new Message(MessageType.Error, e1.getMessage()));
				}
				String mess = String.format("%s %s", client.getNickname(),
						"has been disconnected.");
				message = new Message(MessageType.Message, mess,
						client.getId());
				broadcast(message);
			}
		}
	}

	// affiche tous les messages bufferisés dans la salle
	private void bufferedMessages() {
		for (int i = client.getLastMessage(); i < Salle.getMessages().size(); i++) {
			Message message = Salle.getMessages().get(i);
			message.setDestination(client.getId());
			send2User(message);
		}

	}

}
