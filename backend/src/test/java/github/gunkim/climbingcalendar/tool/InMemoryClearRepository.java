package github.gunkim.climbingcalendar.tool;

import github.gunkim.climbingcalendar.domain.schedule.model.Clear;
import github.gunkim.climbingcalendar.domain.schedule.model.id.ClearId;
import github.gunkim.climbingcalendar.domain.schedule.model.id.ScheduleId;
import github.gunkim.climbingcalendar.domain.schedule.repository.ClearRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryClearRepository implements ClearRepository {
    private final Map<ClearId, Clear> database = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public Clear save(Clear clear) {
        ClearId id = clear.id() == null ? ClearId.from(idGenerator.getAndIncrement()) : clear.id();
        Clear newClear = new Clear(
                id,
                clear.scheduleId(),
                clear.levelId(),
                clear.count(),
                clear.createdAt(),
                clear.updatedAt()
        );
        database.put(id, newClear);
        return newClear;
    }

    @Override
    public List<Clear> saveAll(List<Clear> list) {
        List<Clear> savedClears = new ArrayList<>();
        for (Clear clear : list) {
            savedClears.add(save(clear));
        }
        return savedClears;
    }

    @Override
    public void deleteByScheduleId(ScheduleId scheduleId) {
        database.entrySet().removeIf(entry -> entry.getValue().scheduleId().equals(scheduleId));
    }

    @Override
    public Optional<Clear> findById(ClearId id) {
        return Optional.ofNullable(database.get(id));
    }

    @Override
    public List<Clear> findAllByScheduleId(ScheduleId scheduleId) {
        List<Clear> result = new ArrayList<>();
        for (Clear clear : database.values()) {
            if (clear.scheduleId().equals(scheduleId)) {
                result.add(clear);
            }
        }
        return result;
    }

    @Override
    public List<Clear> findAllByScheduleIdIn(List<ScheduleId> scheduleIds) {
        List<Clear> result = new ArrayList<>();
        scheduleIds.forEach(
                scheduleId -> {
                    for (Clear clear : database.values()) {
                        if (clear.scheduleId().equals(scheduleId)) {
                            result.add(clear);
                        }
                    }
                }
        );
        return result;
    }
}
