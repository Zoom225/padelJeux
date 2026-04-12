package padeJeux1.com.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Entité représentant un match de padel.
 */
@Entity
@Table(name = "matches")
@Data
@NoArgsConstructor
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime matchDate;

    @Column(nullable = false)
    private String location;

    // Nouveaux champs pour le score
    @Column(nullable = false)
    private int scoreTeamA = 0;

    @Column(nullable = false)
    private int scoreTeamB = 0;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "match_players",
            joinColumns = @JoinColumn(name = "match_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> players = new HashSet<>();
}
