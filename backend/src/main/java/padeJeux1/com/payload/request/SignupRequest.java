package padeJeux1.com.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

/**
 * Représente la requête d'inscription d'un nouvel utilisateur.
 */
@Data
public class SignupRequest {
    @NotBlank(message = "Le nom d'utilisateur ne peut pas être vide")
    @Size(min = 3, max = 20, message = "Le nom d'utilisateur doit contenir entre 3 et 20 caractères")
    private String username;

    @NotBlank(message = "L'email ne peut pas être vide")
    @Size(max = 50, message = "L'email ne peut pas dépasser 50 caractères")
    @Email(message = "L'email doit être une adresse email valide")
    private String email;

    @NotBlank(message = "Le mot de passe ne peut pas être vide")
    @Size(min = 6, max = 40, message = "Le mot de passe doit contenir entre 6 et 40 caractères")
    private String password;

    private Set<String> role; // Pour spécifier les rôles lors de l'inscription (ex: ["admin", "user"])
}
