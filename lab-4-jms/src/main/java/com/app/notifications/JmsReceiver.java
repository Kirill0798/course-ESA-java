package com.app.notifications;

import com.app.entities.Email;
import com.app.entities.Event;
import com.app.repositories.EmailRepository;
import com.app.repositories.EventRepository;
import org.springframework.jms.annotation.JmsListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JmsReceiver {

    private final EmailSenderService emailSenderService;
    private final EventRepository eventRepo;
    private final EmailRepository emailRepo;

    @Autowired
    public JmsReceiver(EmailSenderService emailSenderService, EventRepository eventRepo, EmailRepository emailRepo) {
        this.emailSenderService = emailSenderService;
        this.eventRepo = eventRepo;
        this.emailRepo = emailRepo;
    }

    @JmsListener(destination = "eventbox", containerFactory = "myFactory")
    public void receiveEvent(Event event) {
        eventRepo.save(event);
    }

    @JmsListener(destination = "mailbox", containerFactory = "myFactory")
    public void receiveMessage(Email email) {
        emailSenderService.send(email);
        emailRepo.save(email);
    }
}