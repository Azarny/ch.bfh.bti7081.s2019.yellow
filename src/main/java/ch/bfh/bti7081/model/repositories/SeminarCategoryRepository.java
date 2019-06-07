package ch.bfh.bti7081.model.repositories;

import ch.bfh.bti7081.model.seminar.SeminarCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author siegn2
 */
@Repository
public interface SeminarCategoryRepository extends JpaRepository<SeminarCategory, Long> {
    SeminarCategory findByName(String name);
}
