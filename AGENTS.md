# SauceDemo Automation Suite — Convenciones del Proyecto

## Stack tecnológico

| Tecnología | Versión | Rol |
|------------|---------|-----|
| Java | 17 | Lenguaje |
| Selenium | 4.18.1 | Automatización de navegador |
| Cucumber | 7.15.0 | Framework BDD |
| JUnit | 4.13.2 | Runner de tests |
| Maven | 3.8+ | Build y dependencias |

> **Nota:** Se usa **Selenium Manager** (integrado en Selenium 4.6+) para gestión del WebDriver.  
> No se usa WebDriverManager externo.

---

## Arquitectura

```
Page Object Model (POM)
    ↓
Step Definitions  →  Hooks (ciclo de vida del driver)
    ↓
DriverManager (Singleton ThreadLocal)
```

---

## Convenciones por capa

### Page Objects (`src/test/java/pages/`)
- Un archivo por página de la aplicación.
- Los locators se declaran con `@FindBy` e inicializan con `PageFactory.initElements`.
- Los métodos públicos representan **acciones del usuario** (`login()`, `addFirstProductToCart()`).
- Usar `WebDriverWait` (10s por defecto) — **nunca** `Thread.sleep()`.
- Los Page Objects no hacen assertions: solo interactúan con la UI y retornan datos.

### Step Definitions (`src/test/java/steps/`)
- Un archivo por feature (LoginSteps ↔ login.feature, CartSteps ↔ cart.feature).
- Anotaciones en español: `@Dado`, `@Cuando`, `@Entonces`, `@Y` desde `io.cucumber.java.es`.
- **Nunca** instanciar `WebDriver` directamente; usar `DriverManager.getDriver()`.
- Las assertions van aquí, usando `org.junit.Assert`.

### DriverManager (`src/test/java/utils/DriverManager.java`)
- Patrón Singleton via `ThreadLocal<WebDriver>` (thread-safe, soporte paralelo).
- Inicializa Chrome en modo headless con `--headless=new`.
- Siempre llamar `DriverManager.quitDriver()` al finalizar — lo gestiona `Hooks.java`.

### Hooks (`src/test/java/hooks/Hooks.java`)
- `@Before` → inicializa el driver llamando a `DriverManager.getDriver()`.
- `@After` → captura screenshot (adjunto al reporte Cucumber) si el escenario falló; cierra el driver.

### Features (`src/test/resources/features/`)
- Escritas en **español** con `# language: es` al inicio.
- Tags disponibles:

| Tag | Alcance |
|-----|---------|
| `@smoke` | Pruebas críticas del flujo principal (login) |
| `@regression` | Suite completa de regresión (carrito) |

---

## Comandos de uso frecuente

```bash
# Pruebas smoke (por defecto en CI en PR)
mvn test -Dcucumber.filter.tags="@smoke"

# Pruebas de regresión (por defecto en CI schedule)
mvn test -Dcucumber.filter.tags="@regression"

# Todas las pruebas + generar reporte HTML
mvn verify -Dcucumber.filter.tags="@smoke or @regression"

# Múltiples tags
mvn test -Dcucumber.filter.tags="@smoke and @regression"
```

---

## Reportes

Después de `mvn verify`:

- `target/cucumber-reports/cucumber.html` — reporte básico de Cucumber
- `target/cucumber-reports/cucumber-html-reports/overview-features.html` — reporte enriquecido (masterthought)
- `target/cucumber-reports/cucumber.json` — usado por CI para el comentario en PR

---

## Agregar nuevos tests

1. Crear el archivo `.feature` en `src/test/resources/features/` con el tag correspondiente.
2. Crear el Page Object en `src/test/java/pages/` si es una página nueva.
3. Crear los step definitions en `src/test/java/steps/`.
4. Registrar el paquete del step en el `glue` del `TestRunner` si se crea un nuevo paquete.
