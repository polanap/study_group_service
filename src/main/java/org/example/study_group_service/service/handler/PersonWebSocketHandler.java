package org.example.study_group_service.service.handler;

import org.example.study_group_service.models.entity.PersonEntity;
import org.example.study_group_service.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

@Component
public class PersonWebSocketHandler implements WebSocketHandler {

    @Autowired
    private PersonService personService;

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    public void sendPeopleUpdate() {
        Page<PersonEntity> people = personService.getAllFiltered(null, null, null, null, null);
        messagingTemplate.convertAndSend("/topic/people", people);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {

    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {

    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
