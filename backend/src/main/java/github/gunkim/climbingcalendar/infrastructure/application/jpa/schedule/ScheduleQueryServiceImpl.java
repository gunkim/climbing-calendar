package github.gunkim.climbingcalendar.infrastructure.application.jpa.schedule;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import github.gunkim.climbingcalendar.application.ScheduleQueryService;
import github.gunkim.climbingcalendar.application.model.ScheduleSearchCriteria;
import github.gunkim.climbingcalendar.application.model.ScheduleSearchResult;
import github.gunkim.climbingcalendar.common.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.querydsl.core.types.Projections.constructor;
import static github.gunkim.climbingcalendar.infrastructure.domain.jpa.climbinggym.entity.QClimbingGymEntity.climbingGymEntity;
import static github.gunkim.climbingcalendar.infrastructure.domain.jpa.climbinggym.entity.QLevelEntity.levelEntity;
import static github.gunkim.climbingcalendar.infrastructure.domain.jpa.schedule.entity.QClearEntity.clearEntity;
import static github.gunkim.climbingcalendar.infrastructure.domain.jpa.schedule.entity.QScheduleEntity.scheduleEntity;
import static java.util.stream.Collectors.*;

@Service
@RequiredArgsConstructor
public class ScheduleQueryServiceImpl implements ScheduleQueryService {
    private final JPAQueryFactory query;

    @Override
    public List<ScheduleSearchResult> getSchedules(ScheduleSearchCriteria criteria) {
        List<ScheduleSearchResult> scheduleResults = fetchScheduleResults(criteria, criteria.pageable());
        if (scheduleResults.isEmpty()) {
            return scheduleResults;
        }

        List<Long> scheduleIds = scheduleResults.stream()
                .map(ScheduleSearchResult::id)
                .toList();

        Map<Long, List<ScheduleSearchResult.ClearDto>> clearInfoMap = fetchClearInfoMap(scheduleIds);
        attachClearInfo(scheduleResults, clearInfoMap);

        return scheduleResults;
    }

    private List<ScheduleSearchResult> fetchScheduleResults(ScheduleSearchCriteria criteria, Pageable pageable) {
        return query.select(constructor(
                        ScheduleSearchResult.class,
                        scheduleEntity.id,
                        scheduleEntity.title,
                        scheduleEntity.scheduleDate,
                        scheduleEntity.memo,
                        climbingGymEntity.id,
                        climbingGymEntity.name
                ))
                .from(scheduleEntity)
                .leftJoin(climbingGymEntity)
                .on(scheduleEntity.climbingGymId.eq(climbingGymEntity.id))
                .where(scheduleEntity.scheduleDate.month().eq(criteria.month().getValue()))
                .offset(pageable.offset())
                .limit(pageable.size())
                .fetch();
    }

    private Map<Long, List<ScheduleSearchResult.ClearDto>> fetchClearInfoMap(List<Long> scheduleIds) {
        List<Tuple> clearTuples = query
                .select(
                        clearEntity.scheduleId,
                        constructor(
                                ScheduleSearchResult.ClearDto.class,
                                clearEntity.id,
                                levelEntity.color,
                                levelEntity.startGrade,
                                levelEntity.endGrade,
                                clearEntity.count
                        )
                )
                .from(clearEntity)
                .innerJoin(levelEntity)
                .on(clearEntity.levelId.eq(levelEntity.id))
                .where(clearEntity.scheduleId.in(scheduleIds))
                .fetch();

        return clearTuples.stream()
                .collect(groupingBy(tuple -> tuple.get(0, Long.class),
                        mapping(tuple -> tuple.get(1, ScheduleSearchResult.ClearDto.class), toList())));
    }

    private void attachClearInfo(List<ScheduleSearchResult> schedules, Map<Long, List<ScheduleSearchResult.ClearDto>> clearInfoMap) {
        schedules.forEach(schedule -> {
            List<ScheduleSearchResult.ClearDto> clearList = clearInfoMap.getOrDefault(schedule.id(), Collections.emptyList());
            schedule.setClearList(clearList);
        });
    }
}