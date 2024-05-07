package com.apex.picloud.services;

import com.apex.picloud.models.Event;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface EventService {
    Event createEvent(Event event);
    List<Event> getAllEvent();
    Event getEventById(Long event_id);
    Event updateEvent(Event event);
    void deleteEvent(Long event_id);
    public void uploadEventImage(Long eventId, MultipartFile file) throws IOException;

    }
