package com.example.draw.controller;

import com.example.draw.decoder.LineDecoder;
import com.example.draw.encoder.LineEncoder;
import com.example.dto.LineDto;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/draw/{room-id}", encoders = {LineEncoder.class}, decoders = {LineDecoder.class})
@ApplicationScoped
public class DrawController {

    Map<String, Session> rooms = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("room-id") String roomId) {
        // TODO: send full drawing
        rooms.put(roomId, session);
    }

    @OnClose
    public void onClose(Session session, @PathParam("room-id") String roomId) {
        System.out.println("onClose> " + roomId);
    }

    @OnError
    public void onError(Session session, @PathParam("room-id") String roomId, Throwable throwable) {
        System.out.println("onError> " + roomId + ": " + throwable);
    }

    @OnMessage
    public void onMessage(LineDto lineDto, @PathParam("room-id") String roomId) {
        // TODO: save line
        broadcast(lineDto, roomId);
    }

    private void broadcast(LineDto lineDto, String roomId) {
        rooms.get(roomId).getOpenSessions().forEach(session -> {
            if (session.isOpen()) {
                session.getAsyncRemote().sendObject(lineDto);
            }
        });
    }
}