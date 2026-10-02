package com.muhammadaqeelamalikazis.backend.controller;

import com.muhammadaqeelamalikazis.backend.model.Score;
import com.muhammadaqeelamalikazis.backend.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

// TODO: add an annotation that will mark this class as REST API Controller
// TODO: add an annotation to map the API to "api/scores"
@RestController
@RequestMapping("/api/scores")
@CrossOrigin(origins = "*")
public class ScoreController {
    // TODO: Add an annotation to do Dependency Injection from the existing instance (ScoreService)
    // TODO: Add a private field for ScoreService
    @Autowired
    private ScoreService scoreService;

    // GET /api/scores/{scoreId}
    // TODO: add an annotation to map HTTP GET to this method with "/{scoreId}" as the path
    @GetMapping("/{scoreId}")
    public ResponseEntity<?> getScoreById(@PathVariable UUID scoreId) {
        // TODO: create a score variable to store the score given by scoreService
        // hint: use Optional data type
        // hint: use getScoreById method from scoreService using the correct parameter
        Optional<Score> score = scoreService.getScoreByID(scoreId);

        // check whether the score variable is present or not using isPresent()
        // if yes, return the posted score (hint: return `ResponseEntity.ok(score.get())`)
        // if no, return NOT_FOUND status with the matching error body
        if (score.isPresent()) {
            return ResponseEntity.ok(score.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\": \"Score not found\"}");
        }
    }

    //POST /api/scores
    // TODO: add an annotation to map HTTP POST to this method
    @PostMapping
    public ResponseEntity<?> createScore(@RequestBody Score score){
        try{
            // TODO: Create a new score instance using scoreService with the data available from the parameter
            Score newScore = scoreService.createScore(score);
            // TODO: return the new score response data with CREATED status
            return ResponseEntity.status(HttpStatus.CREATED).body(newScore);
        } catch (RuntimeException e){
            // TODO: return error response with BAD_REQUEST status and a matching error body
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
    // TODO:
    // 1. Add the appropriate annotation for a GET endpoint along with the appropriate endpoint
    public ResponseEntity<List<Score>> getAllScores() {
        // 2. Use scoreService to call getAllScores() and store those scores in a variable using List
        // 3. Return the variable containing those scores
        List<Score> scores = scoreService.getAllScores();
        return ResponseEntity.ok(scores);
    }
    // TODO:
// 1. Add the appropriate annotation for a GET endpoint along with the appropriate endpoint
    @GetMapping
    public  ResponseEntity<List<Score>> getLeaderboardByPoint(
            @RequestParam(defaultValue = "10") Integer limit) {
        List<Score> scores = scoreService.getLeaderboard(limit);
        return ResponseEntity.ok(scores);
	/* 2. add the '@RequestParam' parameter with defaultValue 10
	   3. as well as Integer limit */
        // 4. Use scoreService to call getLeaderboard() with the appropriate parameter
        //    and store those scores in a variable using List
        // 5. Return the variable containing those score
    }
    // TODO:
    // 1. Add the appropriate annotation for a GET endpoint along with the appropriate endpoint
    @GetMapping ("/above/{minValue}")
    public ResponseEntity<List<Score>> getScoresAboveValue(
            @PathVariable Integer minValue) {
            List<Score> scores = scoreService.getScoreAboveValue(minValue);
        return ResponseEntity.ok(scores);
            /* 2. add '@PathVariable' for Integer minValue*/
        // 3. Use scoreService to call getScoreAboveValue() with the appropriate parameter
        //    and store those scores in a variable using List
        // 4. Return the variable containing those scores
    }
    // TODO:
// 1. Add the appropriate annotation for a GET endpoint along with the appropriate endpoint
    @GetMapping("/recent")
    public ResponseEntity<List<Score>> getRecentScores(){
                List<Score> scores = scoreService.getRecentScores();
                return ResponseEntity.ok(scores);
        // 2. Use scoreService to call getRecentScores() with the appropriate parameter
        //    and store those scores in a variable using List
        // 3. Return the variable containing those scores
    }
    // TODO:
    // 1. Add the appropriate annotation for a DELETE endpoint along with the appropriate endpoint
    @DeleteMapping("/{scoreID}")
    public  ResponseEntity<?> deleteScore(
            @PathVariable UUID scoreID){
                try {
                    scoreService.deleteScore(scoreID);
                    return ResponseEntity.ok("Score with ID " + scoreID + " was deleted");
                } catch (RuntimeException e){
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
                }
            /* 2. add '@PathVariable' for scoreId*/
        // 3. create a try-catch block
        // in the try block:
        //  use scoreService to call deleteScore() with the appropriate parameter
        //  return a response indicating the score was successfully deleted
        // in the catch block:
        //  return an error response with status NOT_FOUND along with an appropriate error body
    }

}
