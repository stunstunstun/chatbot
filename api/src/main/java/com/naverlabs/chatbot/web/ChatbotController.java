package com.naverlabs.chatbot.web;

import com.naverlabs.chatbot.EndPoints;
import com.naverlabs.chatbot.domain.Chatbot;
import com.naverlabs.chatbot.service.ChatbotService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author minhyeok
 */
@RestController
@RequestMapping(value = EndPoints.BOTS)
public class ChatbotController {

    private final ChatbotService chatbotService;

    public ChatbotController(ChatbotService chatbotService) {
        this.chatbotService = chatbotService;
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Iterable<Chatbot>> list() {
        return new ResponseEntity<Iterable<Chatbot>>(chatbotService.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Chatbot> findOne(@PathVariable Long id) {
        return new ResponseEntity<Chatbot>(chatbotService.findOne(id), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<Chatbot> save(@RequestBody Chatbot chatbot) {
        return new ResponseEntity<Chatbot>(chatbotService.save(chatbot), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public ResponseEntity<Chatbot> update(@RequestBody Chatbot chatbot, @PathVariable Long id) {
        chatbot.setId(id);
        return new ResponseEntity<Chatbot>(chatbotService.update(chatbot), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        chatbotService.delete(id);
    }
}