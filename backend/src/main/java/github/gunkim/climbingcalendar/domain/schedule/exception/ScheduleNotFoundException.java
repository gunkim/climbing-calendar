package github.gunkim.climbingcalendar.domain.schedule.exception;

import github.gunkim.climbingcalendar.domain.DomainException;
import github.gunkim.climbingcalendar.domain.schedule.model.id.ScheduleId;

public class ScheduleNotFoundException extends DomainException {
    public ScheduleNotFoundException(String message) {
        super(message);
    }

    public static ScheduleNotFoundException fromId(ScheduleId id) {
        return new ScheduleNotFoundException("Schedule not found: %s".formatted(id));
    }
}
