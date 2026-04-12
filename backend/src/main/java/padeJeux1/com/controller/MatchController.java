package padeJeux1.com.controller;

import padeJeux1.com.model.Match;
import padeJeux1.com.model.User;
import padeJeux1.com.payload.request.MatchRequest;
import padeJeux1.com.payload.request.ScoreRequest;
import padeJeux1.com.repository.MatchRepository;
import padeJeux1.com.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Contrôleur REST pour la gestion des matchs.
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/matches")
public class MatchController {

    private final MatchRepository matchRepository;
    private final UserRepository userRepository;

    public MatchController(MatchRepository matchRepository, UserRepository userRepository) {
        this.matchRepository = matchRepository;
        this.userRepository = userRepository;
    }

    /**
     * Endpoint pour créer un nouveau match.
     * Accessible uniquement par les utilisateurs authentifiés.
     */
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> createMatch(@Valid @RequestBody MatchRequest matchRequest) {
        Match match = new Match();
        match.setMatchDate(matchRequest.getMatchDate());
        match.setLocation(matchRequest.getLocation());

        // Vérifier qu'un joueur n'est pas dans les deux équipes à la fois
        Set<Long> allPlayerIds = Stream.concat(matchRequest.getTeamAPlayerIds().stream(), matchRequest.getTeamBPlayerIds().stream()).collect(Collectors.toSet());
        if (allPlayerIds.size() != matchRequest.getTeamAPlayerIds().size() + matchRequest.getTeamBPlayerIds().size()) {
            return ResponseEntity.badRequest().body("Un joueur ne peut pas être dans les deux équipes.");
        }

        // Récupérer les joueurs de l'équipe A
        Set<User> teamAPlayers = new HashSet<>(userRepository.findAllById(matchRequest.getTeamAPlayerIds()));
        if (teamAPlayers.size() != matchRequest.getTeamAPlayerIds().size()) {
            return ResponseEntity.badRequest().body("Un ou plusieurs joueurs de l'équipe A n'ont pas été trouvés.");
        }
        match.setTeamA(teamAPlayers);

        // Récupérer les joueurs de l'équipe B
        Set<User> teamBPlayers = new HashSet<>(userRepository.findAllById(matchRequest.getTeamBPlayerIds()));
        if (teamBPlayers.size() != matchRequest.getTeamBPlayerIds().size()) {
            return ResponseEntity.badRequest().body("Un ou plusieurs joueurs de l'équipe B n'ont pas été trouvés.");
        }
        match.setTeamB(teamBPlayers);

        matchRepository.save(match);

        return ResponseEntity.ok(match);
    }

    /**
     * Endpoint pour lister tous les matchs.
     * Accessible uniquement par les utilisateurs authentifiés.
     */
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Match>> getAllMatches() {
        List<Match> matches = matchRepository.findAll();
        return ResponseEntity.ok(matches);
    }

    /**
     * Endpoint pour mettre à jour le score d'un match existant.
     * Accessible uniquement par les utilisateurs authentifiés.
     */
    @PutMapping("/{id}/score")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> updateScore(@PathVariable Long id, @Valid @RequestBody ScoreRequest scoreRequest) {
        return matchRepository.findById(id).map(match -> {
            match.setScoreTeamA(scoreRequest.getScoreTeamA());
            match.setScoreTeamB(scoreRequest.getScoreTeamB());
            matchRepository.save(match);
            return ResponseEntity.ok(match);
        }).orElse(ResponseEntity.notFound().build());
    }
}
