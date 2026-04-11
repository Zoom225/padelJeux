# Instructions - Prochaine Étape

**Objectif :** Ajouter la possibilité de gérer le score d'un match.

---

### **Contexte**

Maintenant que nous pouvons créer des matchs, nous devons pouvoir enregistrer et mettre à jour leur score.

### **Prochaines Actions (réalisées par l'assistant)**

1.  **Mettre à jour l'entité `Match.java`** pour inclure des champs pour le score (par exemple, `scoreTeamA` et `scoreTeamB`).
2.  **Créer un DTO `ScoreRequest.java`** pour transporter les informations du nouveau score.
3.  **Mettre à jour le `MatchController.java`** en ajoutant un nouvel endpoint `PUT /api/matches/{id}/score` pour permettre la mise à jour du score d'un match spécifique.
