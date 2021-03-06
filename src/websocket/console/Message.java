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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.concurrent.atomic.AtomicInteger;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import websocket.console.SocketAnnotation.MessageType;

/**
 * A message that represents a drawing action.
 * Note that we use primitive types instead of Point, Color etc.
 * to reduce object allocation.<br><br>
 *
 * TODO: But a Color objects needs to be created anyway for drawing this
 * onto a Graphics2D object, so this probably does not save much.
 */
public final class Message {
	private static final AtomicInteger messageIds = new AtomicInteger(0);
	
	private int iDmessage;
	private Client auteur;
    private int type;
    private String message;
    private int destination=-1;

    
	public int getiDmessage() {
		return iDmessage;
	}
	public void setiDmessage(int iDmessage) {
		this.iDmessage = iDmessage;
	}
	public Client getAuteur() {
		return auteur;
	}
	public void setAuteur(Client auteur) {
		this.auteur = auteur;
	}
	public MessageType getType() {
		return SocketAnnotation.MessageType.getMessageType(type);
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getDestination() {
		return destination;
	}
	public void setDestination(int destination) {
		this.destination = destination;
	}
	public Message() {
		super();
		iDmessage=messageIds.getAndIncrement();
		// TODO Auto-generated constructor stub
	}
	public Message(MessageType type, String message) {

        this.type = type.ordinal();
        this.message = message;
        iDmessage=messageIds.getAndIncrement();
    }
	public Message(int type, String message,Client auteur) {

        this.type = type;
        this.message = message;
        this.auteur=auteur;
        iDmessage=messageIds.getAndIncrement();
    }
	public Message(MessageType type, String message,Client auteur) {

        this.type = type.ordinal();
        this.message = message;
        this.auteur=auteur;
        iDmessage=messageIds.getAndIncrement();
    }


	/**
     * Converts this message into a String representation that
     * can be sent over WebSocket.<br>
     * 
     */
    @Override
    public String toString() {

        //return auteur + "," + type.toString() + "," + message+ "," + destination;
    	
    	//return message;
    	return toJson();
    }

    public String toJson(){
    	
    	JSONObject json = new JSONObject();

		try {
			json.put("type", this.type);
		} catch (JSONException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		if(this.getAuteur()!=null)
		{
			try {
				json.put("auteur", this.getAuteur().getNickname());
			} catch (JSONException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		}

		try {
			json.put("message", this.getMessage());
		} catch (JSONException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		return json.toString();
	}
    
    public Message parseFromString(String str) {
        int type=-1;
        String message="";

        JSONObject json = new JSONObject();
		try {
			json = new JSONObject(str);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			message = (String) json.get("message");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			type = (int) json.getInt("type");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return new Message(SocketAnnotation.MessageType.getMessageType(type),message);
    }



}
