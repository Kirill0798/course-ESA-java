package com.app.controllers;

import com.app.entities.Box;
import com.app.entities.EventType;
import com.app.entities.Gift;
import com.app.notifications.JmsSenderService;
import com.app.repositories.BoxRepository;
import com.app.repositories.GiftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class BoxController {

    private final BoxRepository boxRepository;
    private final GiftRepository giftRepository;
    private final JmsSenderService jmsSenderService;

    @Autowired
    public BoxController(BoxRepository boxRepository, GiftRepository giftRepository, JmsSenderService jmsSenderService) {
        this.boxRepository = boxRepository;
        this.giftRepository = giftRepository;
        this.jmsSenderService = jmsSenderService;
    }

    @GetMapping(path = "/boxes", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    private Iterable<Box> findAll(){
        return boxRepository.findAll();
    }

    @PostMapping(path = "/boxes/add", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    private Iterable<Box> add(@RequestBody Box newBox){
        giftRepository.save(newBox.getGift());
        Box b = boxRepository.save(newBox);
        jmsSenderService.sendBoxCreate(b, EventType.CREATE);
        jmsSenderService.sendEvent(Box.class, b, EventType.CREATE);
        return boxRepository.findAll();
    }

    @DeleteMapping(path = "boxes/delete/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    private Iterable<Box> delete(@PathVariable Long id){
        Box box = boxRepository.findById(id).orElse(null);
        if (box == null){
            return boxRepository.findAll();
        }
        boxRepository.delete(box);
        Gift gift = giftRepository.findById(box.getGift().getId()).orElse(null);
        if (gift != null){
            try{
                giftRepository.delete(gift);
            } catch (Exception exception){
                exception.printStackTrace();
            }
        }
        jmsSenderService.sendEvent(Box.class, box, EventType.DELETE);
        return boxRepository.findAll();
    }
}