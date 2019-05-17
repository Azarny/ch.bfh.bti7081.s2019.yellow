package ch.bfh.bti7081.model.repositories;

import ch.bfh.bti7081.model.seminar.Seminar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeminarRepository extends JpaRepository<Seminar, Long> {
}
