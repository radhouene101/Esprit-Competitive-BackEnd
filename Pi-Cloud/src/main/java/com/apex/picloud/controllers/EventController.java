package com.apex.picloud.controllers;

import com.apex.picloud.models.Event;
import com.apex.picloud.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/event")
public class EventController {
    @Autowired
    private EventService eventService ;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @PostMapping("/addevent")
    public Event createEvent(@RequestBody Event event){
        return eventService.createEvent(event);
    }
    @GetMapping("/geteventbyid/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        Event event = eventService.getEventById(id);
        return ResponseEntity.ok(event);
    }

    @GetMapping("/getallevents")
    public ResponseEntity<List<Event>> getAllEvents(){
        List<Event> events = eventService.getAllEvent();
        return ResponseEntity.ok(events);
    }

    @PutMapping("/updatevent/{eventid}")
    public ResponseEntity<Event> updateEvent(@PathVariable Long eventid, @RequestBody Event event) {
        event.setId(eventid);
        Event updatedEvent = eventService.updateEvent(event);
        return ResponseEntity.ok(updatedEvent);
    }

    @DeleteMapping("/deletevent/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/{eventId}/image")
    public ResponseEntity<?> uploadEventImage(@PathVariable Long eventId, @RequestParam("file") MultipartFile file) {
        try {
            eventService.uploadEventImage(eventId, file);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/sendNotification")
    public void sendNotification() {
        messagingTemplate.convertAndSend("/topic/notification", "New notification!");
    }


}
