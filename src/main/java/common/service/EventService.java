package common.service;

import common.dto.CreateEventDto;
import common.dto.EventDto;
import common.model.Event;
import common.model.User;
import common.model.enums.EventType;
import common.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final UserService userService;

    @Autowired
    public EventService(EventRepository eventRepository, UserService userService) {
        this.eventRepository = eventRepository;
        this.userService = userService;
    }

    public List<EventDto> addEvents(List<CreateEventDto> createEventDtos) {
        return createEventDtos.stream().map(this::addEvent).collect(Collectors.toList());
    }

    public EventDto addEvent(CreateEventDto createEventDto) {
        User user = (User) userService.loadUserByUsername(createEventDto.getUsername());

        Event newEvent = new Event();
        newEvent.setType(EventType.valueOf(createEventDto.getType()));
        newEvent.setDate(createEventDto.getDate());
        newEvent.setUserId(user.getId());

        removeIfExists(user.getEvents(), newEvent);
        return buildEventDto(eventRepository.save(newEvent).block());
    }

    public static EventDto buildEventDto(Event event) {
        return EventDto.builder()
                .id(event.getId())
                .date(event.getDate())
                .type(String.valueOf(event.getType()))
                .username(event.getUserId())
                .build();
    }

    private void removeIfExists(List<Event> events, Event newEvent) {
        for (Event event : events) {
            if (areEventsInSameDay(newEvent.getDate(), event.getDate())) {
                eventRepository.delete(event);
            }
        }
    }

    private boolean areEventsInSameDay(Date d1, Date d2) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(d1).equals(formatter.format(d2));
    }
}