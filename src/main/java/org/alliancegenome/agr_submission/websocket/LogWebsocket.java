package org.alliancegenome.agr_submission.websocket;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.alliancegenome.agr_submission.entities.log.LogApiDTO;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

@ApplicationScoped
@ServerEndpoint("/apilog")
public class LogWebsocket {

	private static final Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());
	private ObjectMapper mapper = new ObjectMapper();
	
	@OnOpen
	public void onOpen(final Session session) {
		try {
			sessions.add(session);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@OnClose
	public void onClose(final Session session) {
		try {
			sessions.remove(session);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@OnMessage
	public void message(String message, Session session) {
		//session.getOpenSessions().forEach(s -> s.getAsyncRemote().sendText(message));
	}

	public void observeOrderBook(@Observes LogApiDTO info) {
		try {
			String json = mapper.writeValueAsString(info);
			sessions.forEach(s -> s.getAsyncRemote().sendText(json));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
}
