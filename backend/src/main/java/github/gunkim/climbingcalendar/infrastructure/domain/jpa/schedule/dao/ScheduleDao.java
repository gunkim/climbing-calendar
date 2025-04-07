package github.gunkim.climbingcalendar.infrastructure.domain.jpa.schedule.dao;

import github.gunkim.climbingcalendar.infrastructure.domain.jpa.schedule.entity.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScheduleDao extends JpaRepository<ScheduleEntity, Long> {
    List<ScheduleEntity> findByUserId(Long userId);

    int countByUserId(Long userId);

    @Query(
            """
                    SELECT COUNT(s)
                    FROM schedule s
                    WHERE s.userId = :userId AND FUNCTION('YEAR', s.scheduleDate) = :year
                    """
    )
    int countByUserIdAndYear(@Param("userId") Long userId, @Param("year") int year);

    @Query(
            """
                    SELECT COUNT(s)
                    FROM schedule s
                    WHERE s.userId = :userId AND FUNCTION('MONTH', s.scheduleDate) = :month
                    """
    )
    int countByUserIdAndMonth(@Param("userId") Long userId, @Param("month") int month);
}
