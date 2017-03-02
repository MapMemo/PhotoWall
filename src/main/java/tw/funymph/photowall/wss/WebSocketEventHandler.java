/* WebSocketEventHandler.java created on Feb 20, 2017.
 * 
 * Copyright (C) Funymph all rights reserved.
 *
 * This file is a part of the PhotoWall project.
 */
package tw.funymph.photowall.wss;

import static java.lang.String.format;
import static org.slf4j.LoggerFactory.getLogger;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

/**
 * This class handle the Web Socket requests.
 * 
 * @author Spirit Tu
 * @version 1.0
 * @since 1.0
 */
@WebSocket
public class WebSocketEventHandler {

	public static final String EventPath = "/ws/events";

	private static final Queue<Session> sessions = new ConcurrentLinkedQueue<>();

	@OnWebSocketConnect
	public void connected(Session session) throws IOException {
		if (session.getUpgradeRequest().getParameterMap().get("token") == null) {
			// the request is not authenticated, disconnect it
			session.disconnect();
		}
		sessions.add(session);
	}

	@OnWebSocketClose
	public void disconnected(Session session, int statusCode, String reason) {
		sessions.remove(session);
	}

	@OnWebSocketMessage
	public void boardcast(Session source, String message) {
		sessions.stream().forEach((session) -> {
			try {
				session.getRemote().sendString(message);
			} catch (IOException e) {
				getLogger(getClass()).error(format("failed to boardcast message to %s", session));
			}
		});
	}
}
