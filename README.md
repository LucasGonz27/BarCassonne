# BarCassonne

A [libGDX](https://libgdx.com/) project generated with [gdx-liftoff](https://github.com/libgdx/gdx-liftoff).

This project was generated with a template including simple application launchers and an `ApplicationAdapter` extension that draws libGDX logo.

## Platforms

- `core`: Main module with the application logic shared by all platforms.
- `lwjgl3`: Primary desktop platform using LWJGL3; was called 'desktop' in older docs.

## Gradle

This project uses [Gradle](https://gradle.org/) to manage dependencies.
The Gradle wrapper was included, so you can run Gradle tasks using `gradlew.bat` or `./gradlew` commands.
Useful Gradle tasks and flags:

- `--continue`: when using this flag, errors will not stop the tasks from running.
- `--daemon`: thanks to this flag, Gradle daemon will be used to run chosen tasks.
- `--offline`: when using this flag, cached dependency archives will be used.
- `--refresh-dependencies`: this flag forces validation of all dependencies. Useful for snapshot versions.
- `build`: builds sources and archives of every project.
- `cleanEclipse`: removes Eclipse project data.
- `cleanIdea`: removes IntelliJ project data.
- `clean`: removes `build` folders, which store compiled classes and built archives.
- `eclipse`: generates Eclipse project data.
- `idea`: generates IntelliJ project data.
- `lwjgl3:jar`: builds application's runnable jar, which can be found at `lwjgl3/build/libs`.
- `lwjgl3:run`: starts the application.
- `test`: runs unit tests (if any).

Note that most tasks that are not specific to a single project can be run with `name:` prefix, where the `name` should be replaced with the ID of a specific project.
For example, `core:clean` removes `build` folder only from the `core` project.

## Tests Unitaires

Le projet contient une suite complète de tests unitaires pour valider le fonctionnement du code.

### Exécution des tests

**Script d'automatisation :**
```bash
run-tests.bat
```

**Manuellement avec Gradle :**
```bash
# Exécuter les tests
./gradlew test

# Générer le rapport de couverture
./gradlew jacocoTestReport
```

### Tests disponibles

Le projet contient **10 fichiers de tests unitaires** couvrant :
- **Managers** : `TowerManagerTest`, `TowerDataManagerTest`, `GameStateTest`, `CheminManaTest`, `VagueManaTest`
- **Entities - Towers** : `TowerArcherTest`, `TowerMagieTest`, `TowerCanonTest`, `TowerForgeronTest`
- **Entities - Mechants** : `MechantTest`

### Couverture de code

Le projet utilise **JaCoCo** pour mesurer la couverture de code. Après l'exécution des tests, le rapport de couverture est généré automatiquement.

**Statistiques actuelles :**
- Instructions : 28%
- Branches : 10%
- Lignes : 43%
- Méthodes : 58%
- Classes : 50%

**Rapport de couverture :**
- HTML : `core/build/reports/jacoco/test/html/index.html`
- XML : `core/build/reports/jacoco/test/jacocoTestReport.xml`
- CSV : `core/build/reports/jacoco/test/jacocoTestReport.csv`

**Rapport de tests :**
- HTML : `core/build/reports/tests/test/index.html`

### Automatisation des tests

Les tests peuvent être exécutés automatiquement via le script :
- `run-tests.bat` - Exécute les tests et génère le rapport de couverture
