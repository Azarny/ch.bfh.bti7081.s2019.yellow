package ch.bfh.bti7081.model.repositories;

import ch.bfh.bti7081.model.seminar.Seminar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author siegn2
 */
@Repository
public interface SeminarRepository extends JpaRepository<Seminar, Long> {
    List<Seminar> findByDateGreaterThanEqual(LocalDateTime startDate);

    @Query(value = "from Seminar seminar where date BETWEEN :startDate AND :endDate")
    List<Seminar> getAllBetweenDates(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    List<Seminar> findByDateLessThanEqual(LocalDateTime endDate);
}
