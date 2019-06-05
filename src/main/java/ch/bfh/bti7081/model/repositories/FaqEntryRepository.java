package ch.bfh.bti7081.model.repositories;

import ch.bfh.bti7081.model.faq.FaqEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author siegn2
 */
@Repository
public interface FaqEntryRepository extends JpaRepository<FaqEntry, Long> {
}
