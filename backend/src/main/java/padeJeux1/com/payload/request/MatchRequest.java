package padeJeux1.com.payload.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Représente la requête pour créer ou mettre à jour un match.
 */
@Data
public class MatchRequest {

    @NotNull(message = "La date du match ne peut pas être nulle")
    @Future(message = "La date du match doit être dans le futur")
    private LocalDateTime matchDate;

    @NotBlank(message = "Le lieu ne peut pas être vide")
    private String location;

    @NotNull(message = "La liste des joueurs ne peut pas être nulle")
    private Set<Long> playerIds; // On enverra les IDs des utilisateurs
}
