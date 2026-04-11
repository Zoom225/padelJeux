package padeJeux1.com.repository;

import padeJeux1.com.model.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository pour l'entité Match.
 */
@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
}
