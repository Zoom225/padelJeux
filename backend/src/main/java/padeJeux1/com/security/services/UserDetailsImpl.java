package padeJeux1.com.security.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import padeJeux1.com.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Implémentation personnalisée de l'interface {@link UserDetails} de Spring Security.
 * Cette classe encapsule les informations d'un utilisateur authentifié
 * et est utilisée par Spring Security pour les contrôles d'authentification et d'autorisation.
 */
public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L; // Identifiant de version pour la sérialisation

    private Long id;
    private String username;
    private String email;

    @JsonIgnore // Empêche la sérialisation du mot de passe dans les réponses JSON
    private String password;

    // Collection des autorités (rôles) accordées à l'utilisateur
    private Collection<? extends GrantedAuthority> authorities;

    /**
     * Constructeur pour créer une instance de UserDetailsImpl.
     *
     * @param id L'identifiant unique de l'utilisateur.
     * @param username Le nom d'utilisateur.
     * @param email L'adresse email de l'utilisateur.
     * @param password Le mot de passe de l'utilisateur (haché).
     * @param authorities La collection des autorités (rôles) de l'utilisateur.
     */
    public UserDetailsImpl(Long id, String username, String email, String password,
                           Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    /**
     * Méthode statique pour construire un objet UserDetailsImpl à partir d'une entité User.
     * Elle convertit les rôles de l'utilisateur en GrantedAuthority.
     *
     * @param user L'entité User à partir de laquelle construire UserDetailsImpl.
     * @return Une instance de UserDetailsImpl.
     */
    public static UserDetailsImpl build(User user) {
        // Convertit les rôles de l'utilisateur en une liste de SimpleGrantedAuthority
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

        return new UserDetailsImpl(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                authorities);
    }

    /**
     * Retourne la collection des autorités (rôles) accordées à l'utilisateur.
     * @return Une collection de GrantedAuthority.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    // Getters spécifiques pour les propriétés de l'utilisateur
    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    /**
     * Retourne le mot de passe utilisé pour authentifier l'utilisateur.
     * @return Le mot de passe de l'utilisateur.
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Retourne le nom d'utilisateur utilisé pour authentifier l'utilisateur.
     * @return Le nom d'utilisateur.
     */
    @Override
    public String getUsername() {
        return username;
    }

    // Méthodes indiquant si le compte de l'utilisateur est valide (non expiré, non verrouillé, etc.)
    // Par défaut, nous les définissons toutes à 'true' pour un compte actif.
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * Compare deux objets UserDetailsImpl basés sur leur ID.
     * @param o L'objet à comparer.
     * @return true si les objets sont égaux, false sinon.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }
}
