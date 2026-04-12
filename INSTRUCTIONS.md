# Instructions - Prochaine Étape

**Objectif :** Améliorer la structure des matchs pour différencier les joueurs de chaque équipe.

---

### **Contexte**

Actuellement, un match a une seule liste de "joueurs". Pour gérer correctement un match de padel, nous devons savoir qui est dans l'équipe A et qui est dans l'équipe B.

### **Prochaines Actions (réalisées par l'assistant)**

1.  **Mettre à jour l'entité `Match.java`** : Remplacer la liste `players` par deux nouvelles listes, `teamA` et `teamB`.
2.  **Mettre à jour le DTO `MatchRequest.java`** : Pour permettre de spécifier les IDs des joueurs de chaque équipe lors de la création.
3.  **Mettre à jour la logique du `MatchController.java`** pour gérer ces deux nouvelles listes d'équipes.
