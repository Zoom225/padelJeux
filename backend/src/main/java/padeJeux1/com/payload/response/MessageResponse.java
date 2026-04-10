package padeJeux1.com.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Représente une réponse de message simple (succès/erreur).
 */
@Data
@AllArgsConstructor
public class MessageResponse {
    private String message;
}
