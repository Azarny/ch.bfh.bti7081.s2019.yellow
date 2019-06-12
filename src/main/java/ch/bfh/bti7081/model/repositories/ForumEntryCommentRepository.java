package ch.bfh.bti7081.model.repositories;

import ch.bfh.bti7081.model.forum.ForumEntryComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author siegn2
 */
@Repository
public interface ForumEntryCommentRepository extends JpaRepository<ForumEntryComment, Long> {
}
