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


@ServerEndpoint(value = "/websocket/chat")
public class ChatAnnotation {

    private static final String GUEST_PREFIX = "Guest";
    private static final AtomicInteger connectionIds = new AtomicInteger(0);
    private static List<Client> clients=new ArrayList();

    private Client client;

    public ChatAnnotation() {
    	client=new Client();
    	client.setNickname(GUEST_PREFIX + connectionIds.getAndIncrement());
    }


    @OnOpen
    public void start(Session session) {
        client.setSession(session);
        clients.add(client);
        String message = String.format("* %s %s", client.getNickname(), "has joined.");
        broadcast(message);
    }


    @OnClose
    public void end() {
        clients.remove(client);
        String message = String.format("* %s %s",
        		client.getNickname(), "has disconnected.");
        broadcast(message);
    }


    @OnMessage
    public void incoming(String message) {
        // Never trust the client
        String filteredMessage = String.format("%s: %s",
        		client.getNickname(), HTMLFilter.filter(message.toString()));
        broadcast(filteredMessage);
    }


    private static void broadcast(String msg) {
        for (Client client : clients) {
            try {
                client.getSession().getBasicRemote().sendText(msg);
            } catch (IOException e) {
                clients.remove(client);
                try {
                	client.getSession().close();
                } catch (IOException e1) {
                    // Ignore
                }
                String message = String.format("* %s %s",
                		client.getNickname(), "has been disconnected.");
                broadcast(message);
            }
        }
    }
}
