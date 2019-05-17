package ch.bfh.bti7081.model.repositories;

import ch.bfh.bti7081.model.seminar.SeminarCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeminarCategoryRepository extends JpaRepository<SeminarCategory, Long> {
}
