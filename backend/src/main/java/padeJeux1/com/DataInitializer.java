package padeJeux1.com;

import padeJeux1.com.model.ERole;
import padeJeux1.com.model.Role;
import padeJeux1.com.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Ce composant s'exécute au démarrage de l'application
 * pour initialiser les données de base, comme les rôles.
 */
@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        // Vérifie si le rôle USER existe, sinon le crée
        if (roleRepository.findByName(ERole.ROLE_USER).isEmpty()) {
            roleRepository.save(new Role(null, ERole.ROLE_USER));
            System.out.println("Rôle ROLE_USER initialisé.");
        }

        // Vérifie si le rôle ADMIN existe, sinon le crée
        if (roleRepository.findByName(ERole.ROLE_ADMIN).isEmpty()) {
            roleRepository.save(new Role(null, ERole.ROLE_ADMIN));
            System.out.println("Rôle ROLE_ADMIN initialisé.");
        }
    }
}
