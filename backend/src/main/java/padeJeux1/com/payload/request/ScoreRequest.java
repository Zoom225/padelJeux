package padeJeux1.com.payload.request;

import jakarta.validation.constraints.Min;
import lombok.Data;

/**
 * Représente la requête pour mettre à jour le score d'un match.
 */
@Data
public class ScoreRequest {

    @Min(value = 0, message = "Le score ne peut pas être négatif")
    private int scoreTeamA;

    @Min(value = 0, message = "Le score ne peut pas être négatif")
    private int scoreTeamB;
}
