package padeJeux1.com.security.services;

import padeJeux1.com.model.User;
import padeJeux1.com.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implémentation du service {@link UserDetailsService} de Spring Security.
 * Ce service est responsable de charger les informations spécifiques à l'utilisateur
 * lors du processus d'authentification. Il interagit avec le UserRepository
 * pour récupérer les données de l'utilisateur depuis la base de données.
 */
@Service // Indique que cette classe est un composant de service Spring
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired // Injection de dépendance du UserRepository
    UserRepository userRepository;

    /**
     * Charge les détails de l'utilisateur par son nom d'utilisateur.
     * Cette méthode est appelée par Spring Security lors d'une tentative d'authentification.
     *
     * @param username Le nom d'utilisateur de l'utilisateur à charger.
     * @return Un objet UserDetails contenant les informations de l'utilisateur.
     * @throws UsernameNotFoundException Si aucun utilisateur n'est trouvé avec le nom d'utilisateur donné.
     */
    @Override
    @Transactional // Assure que la méthode s'exécute dans une transaction de base de données
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Recherche l'utilisateur dans la base de données par son nom d'utilisateur
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé avec le nom d'utilisateur : " + username));

        // Construit et retourne un objet UserDetailsImpl à partir de l'entité User
        return UserDetailsImpl.build(user);
    }
}
