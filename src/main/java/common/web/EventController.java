package common.web;

import common.dto.CreateEventDto;
import common.dto.EventDto;
import common.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @RequestMapping("/event/add")
    public ResponseEntity<EventDto> addEvent(@Valid @RequestBody CreateEventDto createEventDto) {
        return ResponseEntity.ok(eventService.addEvent(createEventDto));
    }

    @RequestMapping("/events/add")
    public ResponseEntity<List<EventDto>> addEvents(@Valid @RequestBody List<CreateEventDto> createEventDtos) {
        return ResponseEntity.ok(eventService.addEvents(createEventDtos));
    }
}