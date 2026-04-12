# Récapitulatif des Actions Menées sur le Projet Padel

### 1. Réorganisation du Projet

*   **Objectif :** Structurer le projet pour séparer le backend et le frontend.
*   **Actions :**
    *   Création des dossiers `/backend` et `/frontend` à la racine.
    *   Déplacement de tous les fichiers du projet Spring Boot initial dans le dossier `/backend`.
    *   Création d'un fichier `pom.xml` à la racine pour transformer le projet en un **projet multi-modules**.
    *   Création des fichiers `docker-compose.yml` et `README.md` à la racine.

### 2. Configuration du Backend

*   **Objectif :** Mettre en place les dépendances et la configuration de base de l'application.
*   **Actions :**
    *   **Dépendances :** Ajout de `Spring Security` et `Spring Validation` au `pom.xml`.
    *   **Base de Données :** Configuration du fichier `application.properties` pour connecter l'application à la base de données MySQL `padelplus`.
    *   **Création Automatique des Tables :** Activation de `spring.jpa.hibernate.ddl-auto=update`.

### 3. Création du Modèle de Données (Entités)

*   **Objectif :** Définir la structure des données de l'application.
*   **Actions :**
    *   Création de l'entité `User` pour représenter un utilisateur.
    *   Création de l'entité `Role` pour gérer les permissions.
    *   Création des interfaces `UserRepository` et `RoleRepository` pour l'accès aux données.

### 4. Implémentation de la Sécurité

*   **Objectif :** Remplacer la sécurité par défaut par notre propre logique d'authentification.
*   **Actions :**
    *   Création du service `UserDetailsServiceImpl` pour charger les utilisateurs.
    *   Création de la classe `WebSecurityConfig` pour définir les règles d'accès et l'encodeur de mot de passe.
    *   Création d'un `DataInitializer` pour s'assurer que les rôles de base existent au démarrage.

### 5. Création et Test des Endpoints d'Authentification

*   **Objectif :** Créer les "portes d'entrée" de l'API pour l'inscription et la connexion.
*   **Actions :**
    *   Création du `AuthController` avec les endpoints `POST /api/auth/signup` et `POST /api/auth/signin`.
    *   Création des DTOs `LoginRequest` et `SignupRequest`.
*   **Procédure de Test Détaillée :**
    1.  **Lancement :** Démarrage de l'application et ouverture de l'interface **Swagger UI** (`http://localhost:8080/swagger-ui.html`).
    2.  **Inscription :** Utilisation de l'endpoint `POST /api/auth/signup`. Dans le "Request body", envoi d'un JSON avec un `username`, un `email` et un `password` valides. Vérification de la réception d'une réponse `200 OK`.
    3.  **Connexion :** Utilisation de l'endpoint `POST /api/auth/signin` avec le `username` et le `password` de l'utilisateur juste créé. Vérification de la réception d'une réponse `200 OK`.
    4.  **Résultat :** Le système a été validé avec succès.

### 6. Implémentation de l'Authentification par Token JWT

*   **Objectif :** Mettre en place une authentification "stateless" via des tokens.
*   **Actions :**
    *   Ajout des dépendances `jjwt` au `pom.xml`.
    *   Création de la classe `JwtUtils` pour générer et valider les tokens.
    *   Création de la classe `JwtResponse` pour la réponse de connexion.
    *   Mise à jour de `AuthController` pour renvoyer un token JWT.
    *   Création du filtre `AuthTokenFilter` pour intercepter et valider les tokens à chaque requête.
    *   Mise à jour de `WebSecurityConfig` pour intégrer le filtre JWT.
*   **Procédure de Test Détaillée :**
    1.  **Obtention du Token :** Utilisation de l'endpoint `POST /api/auth/signin` pour se connecter. Copie de la valeur du champ `"token"` dans la réponse.
    2.  **Autorisation :** Clic sur le bouton **`Authorize`** dans Swagger UI. Dans la fenêtre, collage du token (précédé de `Bearer `). Fermeture de la fenêtre et vérification que le cadenas est fermé.
    3.  **Accès Protégé :** Appel de l'endpoint `GET /api/test/user`. Vérification de la réception d'une réponse `200 OK`, confirmant que le token a bien été utilisé pour l'authentification.
    4.  **Résultat :** Le système a été validé avec succès.

### 7. Création de la Gestion des Matchs

*   **Objectif :** Construire les fonctionnalités de base pour la gestion des matchs.
*   **Actions :**
    *   Création de l'entité `Match.java`.
    *   Création du `MatchRepository.java`.
    *   Création du `MatchController.java` avec les endpoints `POST` et `GET` pour `/api/matches`.
    *   Création du DTO `MatchRequest.java`.
*   **Procédure de Test Détaillée :**
    1.  **Authentification :** Répétition des étapes 1 et 2 de la procédure de test JWT pour s'authentifier dans Swagger.
    2.  **Création de Match :** Utilisation de l'endpoint `POST /api/matches` avec un JSON valide (date, lieu, et un tableau d'IDs de joueurs). Vérification de la réception d'une réponse `200 OK`.
    3.  **Vérification :** Utilisation de l'endpoint `GET /api/matches` pour vérifier que le match créé apparaît bien dans la liste.
    4.  **Résultat :** Le système a été validé avec succès.

### 8. Ajout de la Gestion des Scores

*   **Objectif :** Permettre la mise à jour du score d'un match.
*   **Actions :**
    *   Mise à jour de l'entité `Match` pour inclure les champs `scoreTeamA` et `scoreTeamB`.
    *   Création du DTO `ScoreRequest.java`.
    *   Ajout de l'endpoint `PUT /api/matches/{id}/score` au `MatchController`.
*   **Procédure de Test Détaillée :**
    1.  **Authentification :** Répétition de la procédure d'authentification JWT.
    2.  **Création d'un Match :** Utilisation de `POST /api/matches` pour créer un match de test. Noter l'ID du match dans la réponse.
    3.  **Mise à Jour du Score :** Utilisation de l'endpoint `PUT /api/matches/{id}/score`, en passant l'ID du match dans l'URL et un JSON avec les scores dans le "Request body". Vérification de la réception d'une réponse `200 OK` avec le match mis à jour.
    4.  **Résultat :** Le système a été validé avec succès.

### 9. Amélioration de la Structure des Matchs (Équipes)

*   **Objectif :** Différencier les joueurs de chaque équipe au sein d'un match.
*   **Actions :**
    *   Mise à jour de l'entité `Match` pour remplacer la liste `players` par deux listes : `teamA` et `teamB`.
    *   Mise à jour du DTO `MatchRequest` pour accepter les IDs des joueurs de chaque équipe.
    *   Mise à jour de la logique du `MatchController` pour gérer la création des équipes.
*   **Procédure de Test Détaillée :**
    1.  **Authentification :** Répétition de la procédure d'authentification JWT.
    2.  **Création de plusieurs utilisateurs** (`player2`, `player3`, etc.) via l'endpoint de `signup`.
    3.  **Récupération des IDs :** Connexion successive à chaque utilisateur via `signin` pour noter leurs IDs respectifs.
    4.  **Création de Match :** Utilisation de `POST /api/matches` avec un JSON spécifiant les IDs des joueurs dans les champs `teamAPlayerIds` et `teamBPlayerIds`.
    5.  **Vérification :** Utilisation de `GET /api/matches` pour confirmer que le match a été créé avec les bonnes équipes.
    6.  **Résultat :** Le système a été validé avec succès.

### 10. Finalisation du Backend

*   **Objectif :** Nettoyer le code avant de passer au développement du frontend.
*   **Actions :**
    *   Suppression du `TestController` qui servait au débogage.
    *   Nettoyage de la configuration de sécurité (`WebSecurityConfig`).
*   **Résultat :** Le backend est propre et prêt pour la suite.
