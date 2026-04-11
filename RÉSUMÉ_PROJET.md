# Récapitulatif des Actions Menées sur le Projet Padel

### 1. Réorganisation du Projet

*   **Objectif :** Structurer le projet pour séparer le backend et le frontend.
*   **Actions :**
    *   Création des dossiers `/backend` et `/frontend` à la racine.
    *   Déplacement de tous les fichiers du projet Spring Boot initial dans le dossier `/backend`.
    *   Création d'un fichier `pom.xml` à la racine pour transformer le projet en un **projet multi-modules**, aidant l'IDE à comprendre la nouvelle structure.
    *   Création des fichiers `docker-compose.yml` et `README.md` à la racine.

### 2. Configuration du Backend

*   **Objectif :** Mettre en place les dépendances et la configuration de base de l'application.
*   **Actions :**
    *   **Dépendances :** Ajout de `Spring Security` (pour la gestion des utilisateurs) et `Spring Validation` (pour valider les données des formulaires) au `pom.xml` du backend.
    *   **Base de Données :** Configuration du fichier `application.properties` pour connecter l'application à votre base de données MySQL `padelplus`.
    *   **Création Automatique des Tables :** Activation de l'option `spring.jpa.hibernate.ddl-auto=update` pour que les tables de la base de données soient créées ou mises à jour automatiquement à partir des classes Java.

### 3. Création du Modèle de Données (Entités)

*   **Objectif :** Définir la structure des données de l'application en utilisant des entités JPA.
*   **Actions :**
    *   Création du package `com.model`.
    *   **Entité `User` :** Création d'une classe `User` pour représenter la table `users` dans la base de données (avec les champs `id`, `username`, `email`, `password`).
    *   **Entité `Role` :** Création d'une classe `Role` et d'une énumération `ERole` pour gérer les permissions des utilisateurs (`ROLE_USER`, `ROLE_ADMIN`).
    *   **Repositories :** Création du package `com.repository` avec les interfaces `UserRepository` et `RoleRepository`. Ces interfaces permettent de communiquer avec la base de données (sauvegarder, rechercher, etc.) sans écrire de code SQL.

### 4. Implémentation de la Sécurité

*   **Objectif :** Remplacer la sécurité par défaut de Spring par notre propre logique d'authentification.
*   **Actions :**
    *   Création du package `com.security`.
    *   **`UserDetailsServiceImpl` :** Création d'un service qui explique à Spring Security comment trouver un utilisateur dans notre base de données via le `UserRepository`.
    *   **`WebSecurityConfig` :** Création de la classe de configuration principale de la sécurité pour :
        *   Définir un encodeur de mot de passe (`BCryptPasswordEncoder`) pour ne jamais stocker les mots de passe en clair.
        *   Définir les règles d'accès : autoriser l'accès public aux pages d'authentification (`/api/auth/**`) et à Swagger UI, tout en protégeant toutes les autres pages.
    *   **`DataInitializer` :** Création d'un composant qui s'exécute au démarrage pour créer automatiquement les rôles `ROLE_USER` et `ROLE_ADMIN` dans la base de données, évitant ainsi des erreurs lors de la première inscription.

### 5. Création et Test des Endpoints d'Authentification

*   **Objectif :** Créer les "portes d'entrée" de l'API pour que les utilisateurs puissent s'inscrire et se connecter.
*   **Actions :**
    *   Création du package `com.controller`.
    *   **`AuthController` :** Création d'un contrôleur qui expose deux endpoints principaux :
        *   `POST /api/auth/signup` : Pour enregistrer un nouvel utilisateur.
        *   `POST /api/auth/signin` : Pour connecter un utilisateur existant.
    *   **DTOs (Payloads) :** Création des classes `LoginRequest` et `SignupRequest` pour transporter proprement les données entre le client et le serveur.
*   **Tests :** Utilisation de l'interface **Swagger UI** (accessible via `http://localhost:8080/swagger-ui.html`) pour tester avec succès les endpoints d'inscription et de connexion, confirmant que tout le système est fonctionnel.

### 6. Implémentation de l'Authentification par Token JWT

*   **Objectif :** Remplacer l'authentification de base par un système de token JWT. Après une connexion réussie, le serveur fournira un token que le client devra présenter à chaque requête pour prouver son identité.
*   **Actions :**
    *   Ajout des dépendances `jjwt` au `pom.xml`.
    *   Création de la classe `JwtUtils` pour générer et valider les tokens.
    *   Création de la classe `JwtResponse` pour la réponse de connexion.
    *   Mise à jour de `AuthController` pour renvoyer un token JWT.
    *   Création du filtre `AuthTokenFilter` pour intercepter et valider les tokens à chaque requête.
    *   Création du point d'entrée `AuthEntryPointJwt` pour gérer les erreurs d'authentification.
    *   Mise à jour de `WebSecurityConfig` pour intégrer le filtre JWT.
    *   Création d'un `TestController` pour vérifier l'accès aux ressources sécurisées.
*   **Tests :** Utilisation de Swagger UI pour obtenir un token et l'utiliser avec succès pour accéder à un endpoint protégé (`/api/test/user`). **Système validé.**

### 7. Création de la Gestion des Matchs

*   **Objectif :** Construire les fonctionnalités de base pour la gestion des matchs de padel.
*   **Actions :**
    *   Création de l'entité `Match.java` pour représenter un match.
    *   Création du `MatchRepository.java` pour l'accès aux données des matchs.
    *   Création du `MatchController.java` pour exposer les endpoints `/api/matches`.
    *   Création du DTO `MatchRequest.java` pour les requêtes de création de match.
*   **Tests :** Utilisation de Swagger UI pour créer et lister des matchs avec succès. **Système validé.**
