package com.app.controllers;

import com.app.entities.Box;
import com.app.entities.Gift;
import com.app.repositories.BoxRepository;
import com.app.repositories.GiftRepository;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;

@Controller
@RequestMapping("/xml")
public class XmlController {

    private final BoxRepository boxRepository;
    private final GiftRepository giftRepository;

    @Autowired
    public XmlController(BoxRepository boxRepository, GiftRepository giftRepository) {
        this.boxRepository = boxRepository;
        this.giftRepository = giftRepository;
    }

    @ResponseBody
    @GetMapping(path = "/boxes", produces = MediaType.APPLICATION_XML_VALUE)
    private ModelAndView getBoxes() throws JsonProcessingException {
        Iterable<Box> list =  boxRepository.findAll();
        return getModelAndView(list, "boxesXSL");
    }

    @ResponseBody
    @GetMapping(path = "/gifts", produces = MediaType.APPLICATION_XML_VALUE)
    private ModelAndView getGifts() throws JsonProcessingException{
        Iterable<Gift> list =  giftRepository.findAll();
        return getModelAndView(list, "giftsXSL");
    }

    private ModelAndView getModelAndView(Iterable<?> list, String viewName) throws JsonProcessingException {
        String str = new XmlMapper().writeValueAsString(list);
        ModelAndView mod = new ModelAndView(viewName);
        Source src = new StreamSource(new StringReader(str));
        mod.addObject("ArrayList", src);
        return mod;
    }
}
