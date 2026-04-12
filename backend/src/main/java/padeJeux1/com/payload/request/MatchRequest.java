package padeJeux1.com.payload.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    // Remplacement de "playerIds" par deux listes distinctes
    @NotNull
    @Size(min = 1, max = 2, message = "L'équipe A doit contenir 1 ou 2 joueurs")
    private Set<Long> teamAPlayerIds;

    @NotNull
    @Size(min = 1, max = 2, message = "L'équipe B doit contenir 1 ou 2 joueurs")
    private Set<Long> teamBPlayerIds;
}
