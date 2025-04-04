package github.gunkim.climbingcalendar.domain.schedule.exception;

import github.gunkim.climbingcalendar.domain.DomainException;

public class UnauthorizedScheduleException extends DomainException {
    public UnauthorizedScheduleException(String message) {
        super(message);
    }
}
