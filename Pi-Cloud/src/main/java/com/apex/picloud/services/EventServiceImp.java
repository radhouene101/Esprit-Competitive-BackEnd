package com.apex.picloud.services;

import com.apex.picloud.models.Event;
import com.apex.picloud.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
@Service
public class EventServiceImp implements EventService{
    @Autowired
    private EventRepository eventRepository;

    @Override
    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public List<Event> getAllEvent() {
        return eventRepository.findAll();
    }

    @Override
    public Event getEventById(Long event_id) {
        Optional<Event> eventOptional = eventRepository.findById(event_id);
        return eventOptional.orElse(null);
    }

    @Override
    public Event updateEvent(Event event) {
        Optional<Event> existingEventOptional = eventRepository.findById(event.getId());
        if (!existingEventOptional.isPresent()) {
        }

        Event existingevent = existingEventOptional.get();
        existingevent.setName(event.getName());
        existingevent.setLocation(event.getLocation());
        existingevent.setOrganizer(event.getOrganizer());
        existingevent.setPrize(event.getPrize());
        existingevent.setGroups(event.getGroups());

        eventRepository.save(existingevent);
        return existingevent;
    }

    @Override
    public void deleteEvent(Long event_id) {
        Optional<Event> existingEventOptional = eventRepository.findById(event_id);
        if (!existingEventOptional.isPresent()) {
        }
        eventRepository.deleteById(event_id);

    }
    @Override
    public void uploadEventImage(Long eventId, MultipartFile file) throws IOException {
        Optional<Event> optionalEvent = eventRepository.findById(eventId);
        if (optionalEvent.isPresent()) {
            Event event = optionalEvent.get();
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            Path uploadDir = Paths.get("C:\\Users\\USER\\OneDrive\\Bureau\\Frontend\\Angular-Chat-App-FrontEnd\\src\\assets\\images");
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }
            try (InputStream inputStream = file.getInputStream()) {
                Path filePath = uploadDir.resolve(fileName);
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                event.setImageUrl("/images/" + fileName);
                eventRepository.save(event);
            } catch (IOException e) {
                throw new IOException("Could not save image: " + fileName, e);
            }
        } else {
            throw new IllegalArgumentException("Event with ID " + eventId + " not found");
        }
    }
}
