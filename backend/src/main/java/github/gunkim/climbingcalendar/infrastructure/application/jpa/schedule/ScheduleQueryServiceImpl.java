package github.gunkim.climbingcalendar.infrastructure.application.jpa.schedule;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import github.gunkim.climbingcalendar.application.ScheduleQueryService;
import github.gunkim.climbingcalendar.application.model.ScheduleSearchCriteria;
import github.gunkim.climbingcalendar.application.model.ScheduleSearchResult;
import github.gunkim.climbingcalendar.common.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.querydsl.core.types.Projections.constructor;
import static github.gunkim.climbingcalendar.infrastructure.domain.jpa.climbinggym.entity.QClimbingGymEntity.climbingGymEntity;
import static github.gunkim.climbingcalendar.infrastructure.domain.jpa.climbinggym.entity.QLevelEntity.levelEntity;
import static github.gunkim.climbingcalendar.infrastructure.domain.jpa.schedule.entity.QClearEntity.clearEntity;
import static github.gunkim.climbingcalendar.infrastructure.domain.jpa.schedule.entity.QScheduleEntity.scheduleEntity;

@Service
@RequiredArgsConstructor
public class ScheduleQueryServiceImpl implements ScheduleQueryService {
    private final JPAQueryFactory query;

    @Override
    public List<ScheduleSearchResult> getSchedules(ScheduleSearchCriteria criteria) {
        List<ScheduleSearchResult> schedules = fetchScheduleResults(criteria, criteria.pageable());
        if (schedules.isEmpty()) {
            return List.of();
        }

        Map<Long, List<ScheduleSearchResult.ClearDto>> clearInfoMap = fetchClearInfoMap(
                schedules.stream()
                        .map(ScheduleSearchResult::id)
                        .toList()
        );

        schedules.forEach(schedule -> schedule.setClearList(
                clearInfoMap.getOrDefault(schedule.id(), Collections.emptyList())
        ));

        return schedules;
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
                .where(combineConditions(
                        eqScheduleDateYear(criteria.year()),
                        eqScheduleDateMonth(criteria.month())
                ))
                .offset(pageable.offset())
                .limit(pageable.size())
                .fetch();
    }

    private Map<Long, List<ScheduleSearchResult.ClearDto>> fetchClearInfoMap(List<Long> scheduleIds) {
        if (scheduleIds.isEmpty()) {
            return Map.of();
        }

        List<Tuple> clearTuples = query.select(
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
                .join(levelEntity)
                .on(clearEntity.levelId.eq(levelEntity.id))
                .where(clearEntity.scheduleId.in(scheduleIds))
                .fetch();

        return clearTuples.stream()
                .collect(Collectors.groupingBy(
                        tuple -> tuple.get(0, Long.class),
                        Collectors.mapping(
                                tuple -> tuple.get(1, ScheduleSearchResult.ClearDto.class),
                                Collectors.toList()
                        )
                ));
    }

    private BooleanExpression eqScheduleDateYear(Integer year) {
        if (year == null) {
            return null;
        }
        return scheduleEntity.scheduleDate.year().eq(year);
    }

    private BooleanExpression eqScheduleDateMonth(Integer month) {
        if (month == null) {
            return null;
        }
        return scheduleEntity.scheduleDate.month().eq(month);
    }

    private BooleanExpression combineConditions(BooleanExpression... expressions) {
        return Arrays.stream(expressions)
                .filter(Objects::nonNull)
                .reduce(BooleanExpression::and)
                .orElse(null);
    }
}