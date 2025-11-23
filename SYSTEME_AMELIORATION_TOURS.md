# Système d'Amélioration des Tours - Documentation

## Vue d'ensemble

Un système complet d'amélioration de tours a été implémenté pour votre jeu tower defense. Ce système permet :
- D'améliorer les tours jusqu'au niveau 4 (extensible)
- De supprimer les tours avec remboursement de 50%
- D'afficher une interface graphique d'amélioration
- De multiplier les dégâts selon le niveau de la tour

## Fichiers créés

### 1. `TowerUpgradeConfig.java` (core/src/main/java/Epi/BarCassonne/game/Config/)
Configuration centralisée pour :
- Coûts d'amélioration par type de tour et par niveau
- Multiplicateurs de dégâts (Level 2: x2, Level 3: x3.5, Level 4: x5)
- Pourcentage de remboursement (50%)

**Coûts d'amélioration définis :**
- **TowerArcher** : 50 → 100 → 200 lingots
- **TowerMagie** : 250 → 500 → 1000 lingots
- **TowerCanon** : 200 → 400 → 800 lingots
- **TowerForgeron** : 300 → 600 → 1200 lingots

### 2. `TowerPanelInfo.java` (core/src/main/java/Epi/BarCassonne/game/UI/)
Interface graphique complète avec :
- Affichage des informations de la tour (type, niveau)
- Bouton "Améliorer" (si niveau < max)
- Bouton "Supprimer"
- Affichage du coût d'amélioration
- Détection des clics sur les boutons

## Fichiers modifiés

### 1. `Tower.java`
- Transmission du niveau de la tour aux projectiles

### 2. `Projectile.java`
- Ajout du champ `niveauTour`
- Calcul des dégâts avec multiplicateur selon le niveau
- Import de `TowerUpgradeConfig` pour accéder aux multiplicateurs

### 3. `TowerDataManager.java`
- Support des textures par niveau : `TowerArcher_1`, `TowerArcher_2`, etc.
- Nouvelle méthode `getTextureWithLevel(String towerType, int level)`
- Fallback sur Level 1 si texture d'un niveau n'existe pas

### 4. `TowerManager.java`
**Nouveaux champs :**
- `towerPanelInfo` : Interface d'amélioration
- `tourSelectionnee` : Tour actuellement sélectionnée

**Nouvelles méthodes :**
- `gererClicTour()` : Gère les clics sur les tours et l'interface
- `ameliorerTour()` : Améliore une tour au niveau supérieur
- `supprimerTour()` : Supprime une tour avec remboursement
- `trouverTourAPosition()` : Trouve une tour aux coordonnées cliquées

**Modifications :**
- `dessinerTours()` : Utilise la texture selon le niveau
- `render()` : Affiche l'interface d'amélioration

## Sprites à créer

**Vous devez créer les sprites Level 2 suivants dans `assets/sprites/` :**

1. `TourArcherLevel2.png`
2. `TourMagieLevel2.png`
3. `CanonLevel2.png`
4. `ForgeronLevel2.png`

⚠️ **Important** : Si ces fichiers n'existent pas, le système utilisera automatiquement les textures Level 1 (fallback).

## Intégration dans GameScreen

Pour activer le système, ajoutez dans votre `GameScreen.java` ou classe équivalente :

```java
// Dans la méthode touchDown() ou handleInput()
@Override
public boolean touchDown(int screenX, int screenY, int pointer, int button) {
    // Vérifier d'abord si on clique sur une tour ou l'interface d'amélioration
    if (towerManager.gererClicTour(screenX, screenY, screenWidth, screenHeight, mapViewport)) {
        return true; // Le clic a été traité
    }

    // Gérer le placement de tours si en mode placement
    if (towerManager.estEnModePlacement()) {
        towerManager.placerTour(screenX, screenY, screenWidth, screenHeight, mapViewport);
        return true;
    }

    return false;
}
```

## Fonctionnalités

### Amélioration de tour
1. Cliquer sur une tour placée → Affiche le panneau d'amélioration
2. Cliquer sur "Améliorer" → Vérifie l'argent et améliore la tour
3. La tour passe au niveau supérieur avec :
   - Nouvelle texture (si disponible)
   - Dégâts multipliés (x2 pour level 2)
   - Conservation de la position et du type

### Suppression de tour
1. Cliquer sur "Supprimer" → Supprime la tour
2. Rembourse 50% du coût total investi (prix initial + améliorations)
3. Exemple : TowerArcher Level 2 → Remboursement = (100 + 50) * 0.5 = 75 lingots

### Fermeture du panneau
- Cliquer en dehors du panneau
- Après une amélioration
- Après une suppression

## Extensibilité

Pour ajouter un niveau 3 ou 4 :
1. Les coûts sont déjà définis dans `TowerUpgradeConfig`
2. Les multiplicateurs de dégâts sont configurés (x3.5 et x5)
3. Créer les sprites : `TourArcherLevel3.png`, etc.
4. Modifier `maxLevel` dans les constructeurs des tours si nécessaire

## Messages d'erreur

Le système affiche des messages flottants :
- ❌ "Argent insuffisant !" (rouge)
- ❌ "Niveau maximum atteint !" (rouge)
- ✅ "Tour améliorée !" (vert)
- ✅ "+XX lingots" (vert) lors de la suppression

## Configuration de la police

Le système utilise `fonts/arial.ttf`. Assurez-vous que ce fichier existe dans votre dossier `assets/fonts/`.
Si vous n'avez pas cette police, modifiez la ligne dans `TowerPanelInfo.chargerRessources()` :

```java
FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/votre_police.ttf"));
```

## Valeurs de jeu

| Tour          | Prix initial | Level 2 | Level 3 | Level 4 | Dégâts x2 | Dégâts x3.5 | Dégâts x5 |
|---------------|--------------|---------|---------|---------|-----------|-------------|-----------|
| TowerArcher   | 100          | +50     | +100    | +200    | ✓         | ✓           | ✓         |
| TowerMagie    | 500          | +250    | +500    | +1000   | ✓         | ✓           | ✓         |
| TowerCanon    | 400          | +200    | +400    | +800    | ✓         | ✓           | ✓         |
| TowerForgeron | Variable     | +300    | +600    | +1200   | N/A       | N/A         | N/A       |

**Remboursement** : 50% du coût total (prix + améliorations)

## Remarques importantes

1. ⚠️ Le système de projectiles pour TowerForgeron n'applique pas de multiplicateur de dégâts (tour de support)
2. ✅ L'interface d'amélioration utilise l'asset `Amelioration.png` que vous avez déjà
3. ✅ Le système est compatible avec votre architecture existante (Factory pattern, Manager pattern)
4. ✅ Les tours conservent leur position exacte lors de l'amélioration
5. ✅ Le système gère automatiquement les tours de type Forgeron dans les remboursements

## Tests recommandés

- [ ] Placer une tour et cliquer dessus → Le panneau s'affiche
- [ ] Améliorer une tour → Vérifier que la texture change (si sprite Level 2 existe)
- [ ] Améliorer une tour → Vérifier que les dégâts sont x2
- [ ] Supprimer une tour → Vérifier le remboursement (50%)
- [ ] Cliquer en dehors du panneau → Le panneau se ferme
- [ ] Améliorer sans argent → Message d'erreur affiché
- [ ] Améliorer au niveau max → Bouton "Améliorer" caché, "Niveau MAX" affiché
