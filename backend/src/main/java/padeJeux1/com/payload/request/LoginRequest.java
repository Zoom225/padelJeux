package padeJeux1.com.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Représente la requête de connexion d'un utilisateur.
 */
@Data
public class LoginRequest {
    @NotBlank(message = "Le nom d'utilisateur ne peut pas être vide")
    private String username;

    @NotBlank(message = "Le mot de passe ne peut pas être vide")
    private String password;
}
