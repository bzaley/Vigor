package com.project.websocket;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import com.project.userSteps.*;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
/**
 * 
 * @author Ben Zaley
 *
 */
@ServerEndpoint("/websocket/steps/{userId}")
@Component
public class WebSocketServer {

	
	private static Map<Session, Integer> sessionUserIdMap = new HashMap<>();
	private static Map<Integer, Session> userIdSessionMap = new HashMap<>();
	private final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);
	private userStepsService service = new userStepsService();
	/**
	 * Begins session on pipeline opening
	 * @param session
	 * @param userId
	 * @throws IOException
	 */
	@OnOpen
	public void onOpen(
			Session session, 
			@PathParam("userId") int userId) throws IOException 
	{
		logger.info("Entered into Open");

		sessionUserIdMap.put(session, userId);
		userIdSessionMap.put(userId, session);

	}
	/**
	 * socket response when message is received.
	 * @param session
	 * @param message
	 * @throws IOException
	 */
	@OnMessage
	public void onMessage(Session session, String message) throws IOException 
	{

		int userId = sessionUserIdMap.get(session);
		boolean sendNotification = false;
		logger.info("Entered into Message: Got Message:"+message);

		broadcast("here");
		if(sendNotification) {
			broadcast("Congratulations!");
		}

		broadcast("here2");
	}
	/**
	 * session response and pipeline close.
	 * @param session
	 * @throws IOException
	 */
	@OnClose
	public void onClose(Session session) throws IOException
	{
		logger.info("Entered into Close");

		int userId = sessionUserIdMap.get(session);
		sessionUserIdMap.remove(session);
		userIdSessionMap.remove(userId);

		String message= userId + " disconnected";
		broadcast(message);
	}
	/**
	 * pipeline response when receiving an error.
	 * @param session
	 * @param throwable
	 */
	@OnError
	public void onError(Session session, Throwable throwable) 
	{
		
		logger.info("Entered into Error "+throwable.getMessage());
	}
	/**
	 * Sends a message to a specific user given their userId.
	 * @param userId
	 * @param message
	 */
	public void sendMessageToParticularUser(int userId, String message) 
	{	
		if(userIdSessionMap.get(userId) != null) {
			try {

				userIdSessionMap.get(userId).getBasicRemote().sendText(message);
			} catch (IOException e) {
				logger.info("Exception: " + e.getMessage().toString());
				e.printStackTrace();
			}
		}
	}
	/**
	 * Broadcast message to everyone in socket.
	 * @param message
	 * @throws IOException
	 */
	public static void broadcast(String message) 
			throws IOException 
	{	  
		sessionUserIdMap.forEach((session, userId) -> {
			synchronized (session) {
				try {
					session.getBasicRemote().sendText(message);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
}

