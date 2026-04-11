package padeJeux1.com.controller;

import padeJeux1.com.model.Match;
import padeJeux1.com.model.User;
import padeJeux1.com.payload.request.MatchRequest;
import padeJeux1.com.repository.MatchRepository;
import padeJeux1.com.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

        Set<User> players = new HashSet<>(userRepository.findAllById(matchRequest.getPlayerIds()));
        if (players.size() != matchRequest.getPlayerIds().size()) {
            return ResponseEntity.badRequest().body("Un ou plusieurs joueurs n'ont pas été trouvés.");
        }
        match.setPlayers(players);

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
}
