package github.gunkim.climbingcalendar.infrastructure.application.jpa.climbinggym;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import github.gunkim.climbingcalendar.application.ClimbingGymQueryService;
import github.gunkim.climbingcalendar.application.model.ClimbingGymOfVisitAndClearCountCriteria;
import github.gunkim.climbingcalendar.application.model.ClimbingGymOfVisitAndClearCountResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.querydsl.core.types.Projections.constructor;
import static github.gunkim.climbingcalendar.infrastructure.domain.jpa.climbinggym.entity.QClimbingGymEntity.climbingGymEntity;
import static github.gunkim.climbingcalendar.infrastructure.domain.jpa.schedule.entity.QClearEntity.clearEntity;
import static github.gunkim.climbingcalendar.infrastructure.domain.jpa.schedule.entity.QScheduleEntity.scheduleEntity;

@Service
@RequiredArgsConstructor
public class ClimbingGymQueryServiceImpl implements ClimbingGymQueryService {
    private final JPAQueryFactory query;

    @Override
    public List<ClimbingGymOfVisitAndClearCountResult> getClimbingGymOfVisitAndClearCounts(ClimbingGymOfVisitAndClearCountCriteria criteria) {
        var queryFactory = query.select(constructor(
                        ClimbingGymOfVisitAndClearCountResult.class,
                        climbingGymEntity.name,
                        scheduleEntity.count(),
                        clearEntity.count.sum(),
                        scheduleEntity.scheduleDate.max()
                ))
                .from(climbingGymEntity)
                .leftJoin(scheduleEntity)
                .on(climbingGymEntity.id.eq(scheduleEntity.climbingGymId))
                .leftJoin(clearEntity)
                .on(scheduleEntity.id.eq(clearEntity.scheduleId))
                .where(scheduleEntity.userId.eq(criteria.userId().value()))
                .groupBy(climbingGymEntity.id, climbingGymEntity.name)
                .limit(criteria.limit());
        if (criteria.orderBy() != null) {
            queryFactory.orderBy(eqOrderBy(criteria.orderBy()));
        }
        return queryFactory.fetch();
    }

    private OrderSpecifier eqOrderBy(String orderBy) {
        if (orderBy.equals("visit")) {
            return scheduleEntity.count().desc();
        }
        return scheduleEntity.scheduleDate.max().desc();
    }
}
