package com.muhammadaqeelamalikazis.backend.repository;

import com.muhammadaqeelamalikazis.backend.model.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

// TODO: Add an annotation that will mark this interface as a repository that will interact with the database
@Repository
public interface ScoreRepository extends JpaRepository<Score, UUID> {
    // TODO: create a "findPointGreaterThan" method with [Integer minValue] as the parameter, this method will return List<Score>
    List<Score> findAllByPointGreaterThan(Integer minValue);
    // TODO: create a "findAllByOrderByCreatedAtDesc" method that will return List<Score>
    List<Score> findAllByOrderByCreatedAtDesc();

    @Query(value = "SELECT * FROM score ORDER BY point DESC LIMIT ?1", nativeQuery = true)
    // TODO: create a "findTopScores" method with Integer limit as the parameter, this method will return List<Score>
    List<Score> findTopScores(Integer limit);
}
