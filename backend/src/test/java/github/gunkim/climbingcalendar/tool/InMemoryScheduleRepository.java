package github.gunkim.climbingcalendar.tool;

import github.gunkim.climbingcalendar.domain.schedule.model.Schedule;
import github.gunkim.climbingcalendar.domain.schedule.model.id.ScheduleId;
import github.gunkim.climbingcalendar.domain.schedule.repository.ScheduleRepository;
import github.gunkim.climbingcalendar.domain.user.model.id.UserId;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryScheduleRepository implements ScheduleRepository {
    private final Map<ScheduleId, Schedule> scheduleDatabase = new HashMap<>();
    private final AtomicLong scheduleIdGenerator = new AtomicLong(1);

    @Override
    public Schedule save(Schedule schedule) {
        Schedule scheduleWithId = assignIdIfNecessary(schedule);

        scheduleDatabase.put(scheduleWithId.id(), scheduleWithId);
        return scheduleWithId;
    }

    @Override
    public Schedule update(Schedule schedule) {
        if (!scheduleDatabase.containsKey(schedule.id())) {
            throw new IllegalArgumentException("등록된 스케줄이 아닙니다. 스케줄 ID: " + schedule.id());
        }
        scheduleDatabase.put(schedule.id(), schedule);
        return schedule;
    }

    @Override
    public void deleteById(ScheduleId scheduleId) {
        scheduleDatabase.remove(scheduleId);
    }

    @Override
    public Optional<Schedule> findById(ScheduleId id) {
        return Optional.ofNullable(scheduleDatabase.get(id));
    }

    @Override
    public List<Schedule> findByUserId(UserId userId) {
        return scheduleDatabase.values().stream()
                .filter(schedule -> schedule.userId().equals(userId))
                .toList();
    }

    private Schedule assignIdIfNecessary(Schedule schedule) {
        ScheduleId id = (schedule.id() == null)
                ? ScheduleId.from(scheduleIdGenerator.getAndIncrement())
                : schedule.id();

        return new Schedule(
                id,
                schedule.userId(),
                schedule.climbingGymId(),
                schedule.title(),
                schedule.memo(),
                schedule.scheduleDate(),
                schedule.createdAt(),
                schedule.updatedAt()
        );
    }
}