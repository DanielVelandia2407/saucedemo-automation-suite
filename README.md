# SauceDemo Automation Suite

Suite de pruebas automatizadas para [SauceDemo](https://www.saucedemo.com) usando Java, Selenium 4, Cucumber 7 y JUnit 4 con arquitectura Page Object Model.

---

## Requisitos previos

| Herramienta | Versión mínima |
|-------------|---------------|
| Java JDK | 17 |
| Maven | 3.8+ |
| Google Chrome | cualquier versión reciente |

> **No se requiere descargar ChromeDriver manualmente.**  
> Selenium Manager (incluido en Selenium 4.6+) lo gestiona automáticamente.

---

## Cómo correr localmente

### Por tag

```bash
# Pruebas de humo — verifica el flujo crítico de login
mvn test -Dcucumber.filter.tags="@smoke"

# Suite completa de regresión — verifica el carrito
mvn test -Dcucumber.filter.tags="@regression"

# Ejecutar ambas suites
mvn test -Dcucumber.filter.tags="@smoke or @regression"
```

### Generar reporte HTML

```bash
# Ejecuta tests Y genera el reporte enriquecido
mvn verify -Dcucumber.filter.tags="@smoke or @regression"
```

### Modo no headless (debug visual)

Editar `src/test/java/utils/DriverManager.java` y comentar la línea:
```java
// options.addArguments("--headless=new");
```

---

## Cómo ver los reportes

Después de ejecutar `mvn verify`:

```
target/cucumber-reports/
├── cucumber.html                              ← Reporte básico Cucumber
├── cucumber.json                              ← JSON para CI
├── cucumber.xml                               ← JUnit XML para CI
└── cucumber-html-reports/
    └── overview-features.html                 ← Reporte enriquecido (masterthought)
```

Abrir en el navegador:
```
target/cucumber-reports/cucumber-html-reports/overview-features.html
```

---

## Estructura del proyecto

```
saucedemo-automation-suite/
├── .github/
│   └── workflows/
│       └── test.yml              # Pipeline CI/CD con GitHub Actions
├── src/
│   └── test/
│       ├── java/
│       │   ├── hooks/
│       │   │   └── Hooks.java            # @Before/@After + screenshot en fallo
│       │   ├── pages/
│       │   │   ├── LoginPage.java        # POM: página de login
│       │   │   ├── InventoryPage.java    # POM: catálogo de productos
│       │   │   └── CartPage.java         # POM: carrito de compras
│       │   ├── runners/
│       │   │   └── TestRunner.java       # Runner JUnit 4 + CucumberOptions
│       │   ├── steps/
│       │   │   ├── LoginSteps.java       # Steps de login.feature
│       │   │   └── CartSteps.java        # Steps de cart.feature
│       │   └── utils/
│       │       └── DriverManager.java    # Singleton ThreadLocal del WebDriver
│       └── resources/
│           └── features/
│               ├── login.feature         # Escenarios @smoke de login
│               └── cart.feature          # Escenarios @regression del carrito
├── .gitignore
├── CLAUDE.md                             # Convenciones del proyecto
├── pom.xml                               # Dependencias y plugins Maven
└── README.md
```

---

## Credenciales de prueba

| Usuario | Contraseña | Estado |
|---------|------------|--------|
| `standard_user` | `secret_sauce` | Activo — acceso completo |
| `locked_out_user` | `secret_sauce` | Bloqueado — error en login |

---

## Pipeline CI/CD

El workflow `.github/workflows/test.yml` se ejecuta en tres situaciones:

| Evento | Tags ejecutados | Descripción |
|--------|----------------|-------------|
| `push` a `main` | `@smoke` | Verifica que el merge no rompe el flujo crítico |
| `pull_request` a `main` | `@smoke` | Verifica PR + publica resumen en el PR |
| Schedule (L–V 7am UTC) | `@regression` | Regresión diaria completa |

Los reportes HTML se suben como **artifact** en cada ejecución (retención: 30 días).
