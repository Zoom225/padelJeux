package padeJeux1.com.controller;

import padeJeux1.com.model.ERole;
import padeJeux1.com.model.Role;
import padeJeux1.com.model.User;
import padeJeux1.com.payload.request.LoginRequest;
import padeJeux1.com.payload.request.SignupRequest;
import padeJeux1.com.payload.response.MessageResponse;
import padeJeux1.com.repository.RoleRepository;
import padeJeux1.com.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Contrôleur REST pour la gestion de l'authentification (inscription et connexion).
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    /**
     * Gère la connexion d'un utilisateur.
     * @param loginRequest Les informations de connexion (username, password).
     * @return Une réponse HTTP indiquant le succès ou l'échec de la connexion.
     */
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        // Ici, nous n'avons pas encore de JWT, donc nous renvoyons juste un message de succès.
        // Plus tard, un JWT sera généré et renvoyé.
        return ResponseEntity.ok(new MessageResponse("User authenticated successfully!"));
    }

    /**
     * Gère l'inscription d'un nouvel utilisateur.
     * @param signupRequest Les informations d'inscription (username, email, password, roles).
     * @return Une réponse HTTP indiquant le succès ou l'échec de l'inscription.
     */
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Erreur: Le nom d'utilisateur est déjà pris !"));
        }

        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Erreur: L'email est déjà utilisé !"));
        }

        // Créer un nouvel utilisateur
        User user = new User(signupRequest.getUsername(),
                             signupRequest.getEmail(),
                             encoder.encode(signupRequest.getPassword()));

        Set<String> strRoles = signupRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Erreur: Le rôle USER n'est pas trouvé."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Erreur: Le rôle ADMIN n'est pas trouvé."));
                        roles.add(adminRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Erreur: Le rôle USER n'est pas trouvé."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("Utilisateur enregistré avec succès !"));
    }
}
