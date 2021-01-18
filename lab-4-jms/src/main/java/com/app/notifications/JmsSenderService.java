package com.app.notifications;

import com.app.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class JmsSenderService {

    private final JmsTemplate jmsTemplate;

    @Autowired
    public JmsSenderService(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void sendBoxCreate(Box box, EventType eventType){
        Email email = new Email();
        email.setReceiver(box.getEmail());
        email.setSubject("Box [" + eventType.name() + ']');
        String bodyPattern = "Hello,!\n\n" +
                "New gift was added to post list!\n" +
                "Event Type: %s\n\n" +
                "Box: %s";
        String body = String.format(
                bodyPattern,
                eventType.name(),
                box.toString());
        email.setBody(body);
        jmsTemplate.convertAndSend("mailbox", email);
    }

    public <T> void sendEvent(Class<T> entityClass, T entity, EventType eventType){
        Event event = new Event();
        event.setEventType(eventType);
        event.setEntity(entity.toString());
        event.setEntityClass(entityClass.getSimpleName());
        jmsTemplate.convertAndSend("eventbox", event);
    }
}
