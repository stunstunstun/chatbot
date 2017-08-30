package com.naverlabs.chatbot.v1.web;

import com.naverlabs.chatbot.EndPoints;
import com.naverlabs.chatbot.domain.Chatbot;
import com.naverlabs.chatbot.v1.service.ChatbotService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public ResponseEntity<Chatbot> save(@Valid @RequestBody ChatbotResource resource) {
        return new ResponseEntity<Chatbot>(chatbotService.save(resource), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public ResponseEntity<Chatbot> update(@PathVariable Long id, @Valid @RequestBody ChatbotResource resource) {
        return new ResponseEntity<Chatbot>(chatbotService.update(id, resource), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        chatbotService.delete(id);
    }
}
