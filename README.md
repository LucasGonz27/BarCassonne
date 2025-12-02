# 🏰 La Défense de Barcassone

**Jeu de tower defense stratégique développé en Java avec LibGDX**

La Défense de Barcassone est un jeu de tower defense où vous devez défendre votre territoire contre des vagues d'ennemis en plaçant stratégiquement différents types de tours. Chaque type de tour possède des comportements, des forces et des faiblesses uniques. Le gameplay favorise la gestion des ressources, le positionnement stratégique et l'adaptation aux vagues ennemies progressives.

Le jeu propose un système d'économie sophistiqué, d'amélioration de tours jusqu'au niveau 4, et de gestion stratégique des ressources pour offrir une expérience de tower defense immersive et tactique.

---

## 🌟 Fonctionnalités principales

### **Gameplay**
- 🎮 **Placement stratégique** : Placez vos tours sur le terrain pour maximiser leur efficacité
- 🔧 **Système d'amélioration** : Améliorez vos tours jusqu'au niveau 4 pour augmenter leurs dégâts et leur portée
- 💰 **Gestion des ressources** : Gagnez des lingots en éliminant des ennemis et gérez votre économie
- 🌊 **Vagues progressives** : Affrontez des vagues d'ennemis de difficulté croissante
- 🎯 **Système de résistances** : Les ennemis ont des faiblesses et résistances selon le type d'attaque
- 🔊 **Audio immersif** : Musiques et effets sonores pour une expérience complète
- 🎨 **Design unique** : Des graphismes travaillés et une ambiance médiévale
- 🆓 **100% gratuit** : Un projet open source accessible à tous

### **Système de jeu**
- **4 niveaux d'amélioration** : Faites évoluer chaque tour pour devenir plus puissant
- **9 types d'ennemis** : Des gobelins faibles aux boss redoutables
- **Interface intuitive** : Contrôles simples à la souris et au clavier

---

## 🛠️ Technologies utilisées

### **Développement**
- **Java 21** - Langage de programmation principal
- **LibGDX** - Framework graphique multiplateforme
- **LWJGL3** - Backend OpenGL pour le rendu graphique
- **Gradle** - Système de build et gestion de dépendances

### **Outils**
- **IntelliJ IDEA** - IDE recommandé pour le développement
- **Piskel** - Création et édition de sprites
- **Gradle Wrapper** - Build automatisé sans installation préalable

### **Architecture**
- **Architecture modulaire** : Séparation entre la logique de jeu (core) et l'implémentation (lwjgl3)
- **Système de ressources** : Gestion automatique des assets (sprites, sons, polices)
- **Tests unitaires** : Suite de tests pour valider le fonctionnement du jeu

---

## 📁 Structure du projet

```
BarCassonne/
├── 📁 core/                    # Code principal portable
│   ├── 📁 src/
│   │   ├── 📁 main/java/       # Code source principal
│   │   └── 📁 test/java/       # Tests unitaires
│   └── 📄 build.gradle         # Configuration Gradle du module core
├── 📁 lwjgl3/                  # Module desktop (exécution)
│   ├── 📁 src/main/
│   │   ├── 📁 java/            # Point d'entrée du jeu
│   │   └── 📁 resources/       # Ressources spécifiques au module
│   └── 📄 build.gradle         # Configuration Gradle du module lwjgl3
├── 📁 assets/                  # Ressources du jeu
│   ├── 📁 backgrounds/         # Arrière-plans (menu, jeu, game over)
│   ├── 📁 sprites/             # Sprites des tours et ennemis
│   ├── 📁 sounds/              # Musiques et effets sonores
│   ├── 📁 fonts/               # Polices de caractères
│   └── 📁 HUD/                 # Éléments d'interface utilisateur
├── 📄 build.gradle             # Configuration Gradle principale
├── 📄 settings.gradle          # Configuration des modules
├── 📄 gradlew.bat              # Script Gradle pour Windows
├── 📄 gradlew                  # Script Gradle pour Linux/macOS
└── 📄 README.md                # Documentation du projet
```

---

## 💻 Installation & Utilisation

### **Prérequis**
- Java JDK 21 ou supérieur
- Git (pour cloner le projet)
- Gradle (inclus via wrapper, pas besoin d'installation séparée)

### **1. Cloner le projet**
```bash
git clone https://github.com/LucasGonz27/BarCassonne.git
cd BarCassonne
```

### **2. Vérifier l'installation**
```powershell
# Vérifier la version de Java
java -version

# Vérifier que Gradle fonctionne
.\gradlew.bat --version
```

### **3. Compiler le projet**
```powershell
# Compiler tout le projet
.\gradlew.bat build
```

### **4. Lancer le jeu**

**Windows :**
```powershell
.\gradlew.bat :lwjgl3:run
```

**Linux / macOS :**
```bash
./gradlew :lwjgl3:run
```

### **5. Créer un JAR exécutable**
```powershell
# Créer la distribution
.\gradlew.bat :lwjgl3:dist

# Le JAR sera créé dans : lwjgl3/build/distributions/
```

### **6. Lancer les tests**
```powershell
# Lancer tous les tests
.\gradlew.bat test

# Ou utiliser le script dédié
.\run-tests.bat
```

---

## 🎮 Contrôles

- **Placer une tour** : Clic gauche sur une tour dans le menu, puis clic sur le terrain
- **Améliorer une tour** : Clic gauche sur une tour déjà placée
- **Supprimer une tour** : Clic droit sur une tour placée
- **Menu / Pause** : `Échap` (Esc)
- **Quitter** : `Échap` dans le menu principal

---

## 📚 Guide de jeu

### **Comment jouer**

1. **Placement de tours** : Cliquez sur une tour dans le menu latéral et placez-la sur le terrain
2. **Gestion des ressources** : Gagnez des lingots en éliminant des ennemis
3. **Amélioration** : Cliquez sur une tour placée pour l'améliorer (jusqu'au niveau 4) ou la supprimer
4. **Stratégie** : Positionnez vos tours pour maximiser leur efficacité contre les vagues d'ennemis
5. **Système de résistances** : Certains ennemis sont résistants ou vulnérables à certains types de tours

### **Types de tours**

#### 🏹 Tour Archer
- **Prix** : 100 lingots
- **Portée** : 200 pixels
- **Cadence** : Rapide
- **Dégâts** : Modérés
- **Idéale pour** : Éliminer rapidement les ennemis faibles et moyens

#### ✨ Tour Magique
- **Prix** : 1000 lingots
- **Portée** : 150 pixels
- **Cadence** : Très rapide
- **Dégâts** : Magiques élevés
- **Idéale pour** : Ennemis résistants aux attaques physiques

#### 🪨 Tour Canon
- **Prix** : 600 lingots
- **Portée** : 110 pixels
- **Cadence** : Lente
- **Dégâts** : Très élevés
- **Idéale pour** : Éliminer les ennemis robustes et les boss

#### 🔨 Tour Forgeron
- **Prix** : 1500 lingots
- **Portée** : 0 (ne combat pas)
- **Fonction** : Génère des lingots passivement au fil du temps
- **Idéale pour** : Augmenter votre économie à long terme

> **Note** : Chaque tour peut être améliorée jusqu'au niveau 4 pour des dégâts accrus, une meilleure portée et un design amélioré !

### **Types d'ennemis**

| Ennemi | Points de vie | Vitesse | Résistances | Vulnérabilités |
|--------|---------------|---------|-------------|----------------|
| **PaysanGoblin** | Faible | Normale | - | Tous types |
| **GuerrierGoblin** | Moyen | Normale | Physique | Magie |
| **GoblinGuerrisseur** | Faible | Lente | - | Soigne les autres |
| **GoblinBomb** | Faible | Rapide | - | Explosif ⚠️ |
| **Cochon** | Faible | Très rapide | - | Tous types |
| **Chevalier** | Élevé | Lente | Physique | Magie |
| **BossChevalier** | Très élevé | Lente | Physique | Magie |
| **Golem** | Très élevé | Très lente | Physique | Magie |
| **RoiGoblin** | Extrêmement élevé | Normale | Tous | - |

> **Conseil stratégique** : Utilisez des tours magiques contre les ennemis résistants aux attaques physiques !

---

## 🔧 Dépannage

### **Erreur : "Java not found"**
```powershell
# Vérifier que Java est installé
java -version

# Si non installé, télécharger Java 21 depuis :
# https://www.oracle.com/java/technologies/downloads/
```

### **Erreur : "Gradle build failed"**
```powershell
# Nettoyer le projet et réessayer
.\gradlew.bat clean build
```

### **Le jeu ne se lance pas**
```powershell
# Vérifier que tous les assets sont présents
# Les assets doivent être dans le dossier assets/
# Exécuter la génération de la liste des assets
.\gradlew.bat generateAssetList
```

### **Problème de permissions (Linux/macOS)**
```bash
# Rendre le script gradlew exécutable
chmod +x gradlew
```

---

## 📦 Ressources et crédits

### **Assets & Sprites**
- [CraftPix.net](https://craftpix.net/) - Ressources graphiques pour les tours et environnements
- [OpenGameArt.org](https://opengameart.org/) - Sprites d'ennemis et éléments de jeu
- [Piskel](https://www.piskelapp.com/p/create/sprite/) - Création et édition de sprites personnalisés

### **Code & Tutoriels**
- [JVM Gaming](https://jvm-gaming.org/) - Communauté et ressources pour le développement de jeux en Java
- [JavaFX Tower Defense Tutorial](https://www.javacodegeeks.com/2013/10/tower-defense-in-javafx.html) - Base technique et concepts pour tower defense en Java

Un grand merci à ces communautés et créateurs pour leurs ressources précieuses !

---

## 👨‍💻 Auteurs

- **Théo Baixeras**
  - GitHub : [@Baixerastheo](https://github.com/Baixerastheo)

- **Lucas Gonzalez**
  - GitHub : [@LucasGonz27](https://github.com/LucasGonz27)

- **Samuel Baptista**
  - GitHub : [@Samuel-Baptista](https://github.com/Samuel-Baptista)

---

## 🏁 Licence

Vous êtes libre de :
- ✅ Utiliser ce code pour des projets personnels ou commerciaux
- ✅ Modifier et distribuer le code
- ✅ Contribuer au projet via des pull requests

---

## 🤝 Contributions

Les contributions sont les bienvenues ! N'hésitez pas à :
- 🐛 Signaler des bugs
- 💡 Proposer de nouvelles fonctionnalités
- 🔧 Soumettre des pull requests
- 📝 Améliorer la documentation

### **Comment contribuer**
```bash
# 1. Fork le projet
# 2. Créer une branche pour votre fonctionnalité
git checkout -b ma-nouvelle-fonctionnalite

# 3. Faire vos modifications
# 4. Commiter vos changements
git commit -m "Ajout d'une nouvelle fonctionnalité"

# 5. Pousser vers la branche
git push origin ma-nouvelle-fonctionnalite

# 6. Ouvrir une Pull Request sur GitHub
```


**La Défense de Barcassone** - *Défendez votre territoire avec stratégie* 🏰⚔️
