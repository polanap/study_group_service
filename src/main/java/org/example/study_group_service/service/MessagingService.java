package org.example.study_group_service.service;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessagingService {

    private final SimpMessagingTemplate messagingTemplate;

    public MessagingService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendMessageToTopic(String topic, String message) {
        messagingTemplate.convertAndSend(topic, message);
    }
}
