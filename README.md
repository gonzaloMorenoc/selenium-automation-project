# Wordmate Selenium Automation

Proyecto de automatización de pruebas para la aplicación web Wordmate.es utilizando Selenium WebDriver, Cucumber BDD y Java.

## 🚀 Características

- **Selenium WebDriver 4.15.0** para automatización web
- **Cucumber 7.14.0** con Gherkin para BDD (Behavior Driven Development)
- **TestNG** como framework de pruebas
- **Page Object Model** para mantenibilidad del código
- **Allure Reports** para reportes detallados
- **GitHub Actions** para CI/CD
- **WebDriverManager** para gestión automática de drivers

## 📋 Casos de Prueba Cubiertos

### ✅ Registro de Usuario
- Registro exitoso con todos los campos
- Registro exitoso con foto de perfil
- Validación de email inválido
- Validación de contraseña débil
- Validación de campos vacíos
- Navegación entre páginas

### 🔐 Login de Usuario
- Login exitoso con credenciales válidas
- Login fallido con usuario inválido
- Login fallido con contraseña incorrecta
- Validación de campos vacíos
- Navegación entre páginas

### 👤 Gestión de Perfil
- Acceso al perfil estando logueado
- Acceso al perfil sin estar logueado
- Logout exitoso
- Borrado de cuenta exitoso
- Borrado de cuenta con contraseña incorrecta
- Cancelación de borrado de cuenta

## 📁 Estructura del Proyecto

```
selenium-automation-project/
├── src/
│   ├── main/java/
│   │   ├── pages/
│   │   │   ├── BasePage.java           # Clase base para Page Objects
│   │   │   ├── LoginPage.java          # Page Object para Login
│   │   │   ├── RegisterPage.java       # Page Object para Registro
│   │   │   └── ProfilePage.java        # Page Object para Perfil
│   │   └── utils/
│   │       ├── DriverManager.java      # Gestión del WebDriver
│   │       └── TestData.java           # Datos de prueba
│   └── test/
│       ├── java/
│       │   ├── runners/
│       │   │   ├── TestRunner.java     # Ejecutor principal
│       │   │   └── SmokeTestRunner.java # Ejecutor de smoke tests
│       │   └── stepDefinitions/
│       │       ├── CommonSteps.java    # Steps compartidos
│       │       ├── Hooks.java          # Configuración antes/después
│       │       ├── LoginSteps.java     # Steps para Login
│       │       ├── RegisterSteps.java  # Steps para Registro
│       │       └── ProfileSteps.java   # Steps para Perfil
│       └── resources/
│           ├── features/
│           │   ├── login.feature       # Casos de prueba de Login
│           │   ├── register.feature    # Casos de prueba de Registro
│           │   └── profile.feature     # Casos de prueba de Perfil
│           └── testng.xml              # Configuración TestNG
├── .github/
│   └── workflows/
│       └── test.yml                    # Pipeline de GitHub Actions
├── validate-project.sh                 # Script de validación
├── run-tests.sh                        # Script de ejecución
├── pom.xml                             # Configuración Maven
└── README.md                           # Documentación
```

## 🛠️ Requisitos Previos

- **Java 11** o superior
- **Maven 3.6** o superior
- **Chrome** o **Firefox** instalado
- **Git** para control de versiones

## 🏃‍♂️ Ejecución Local

### 1. Clonar el repositorio
```bash
git clone <url-del-repositorio>
cd selenium-automation-project
```

### 2. Instalar dependencias
```bash
mvn clean compile
```

### 3. Ejecutar todas las pruebas
```bash
mvn clean test
```

### 4. Ejecutar solo smoke tests
```bash
mvn clean test -Dtestng.suitename="Smoke Tests"
```

### 5. Ejecutar con diferentes navegadores
```bash
# Chrome (por defecto)
mvn clean test -Dbrowser=chrome

# Firefox
mvn clean test -Dbrowser=firefox

# Modo headless
mvn clean test -Dheadless=true
```

## 📊 Reportes

### Cucumber HTML Report
```bash
# Se genera automáticamente en:
target/cucumber-reports/cucumber-html-report/
```

### Allure Report
```bash
# Generar reporte Allure
mvn allure:report

# Servir reporte localmente
mvn allure:serve
```

## 🔄 CI/CD con GitHub Actions

La pipeline se ejecuta automáticamente en:
- **Push** a ramas `main` o `develop`
- **Pull Requests** hacia `main`
- **Diariamente** a las 6:00 AM UTC

### Artifacts generados:
- 📊 Reportes Cucumber HTML
- 📈 Reportes Allure
- 📄 Reportes JUnit XML
- 📸 Screenshots de fallos

## 🏷️ Tags de Cucumber

- `@regression` - Todas las pruebas de regresión
- `@smoke` - Pruebas críticas básicas
- `@positive` - Casos de prueba positivos
- `@negative` - Casos de prueba negativos
- `@login` - Pruebas específicas de login
- `@register` - Pruebas específicas de registro
- `@profile` - Pruebas específicas de perfil
- `@destructive` - Pruebas que modifican/eliminan datos

## 🎯 Ejemplos de Ejecución por Tags

```bash
# Solo smoke tests
mvn clean test -Dcucumber.filter.tags="@smoke"

# Solo pruebas positivas
mvn clean test -Dcucumber.filter.tags="@positive"

# Solo pruebas de login
mvn clean test -Dcucumber.filter.tags="@login"

# Excluir pruebas destructivas
mvn clean test -Dcucumber.filter.tags="@regression and not @destructive"
```

## 🔧 Configuración

### Variables de Sistema
- `browser` - Navegador a usar (chrome/firefox). Default: chrome
- `headless` - Modo sin cabeza (true/false). Default: false
- `cucumber.filter.tags` - Tags de Cucumber a ejecutar

### Ejemplo de configuración personalizada:
```bash
mvn clean test \
  -Dbrowser=firefox \
  -Dheadless=true \
  -Dcucumber.filter.tags="@smoke"
```

## 🐛 Depuración

### Screenshots automáticos
Los screenshots se toman automáticamente cuando una prueba falla y se guardan en:
- Local: Se adjuntan al reporte Cucumber
- CI/CD: Se suben como artifacts de GitHub

### Logs
Los logs detallados se muestran en la consola durante la ejecución.

### Solución a Steps Duplicados
Si encuentras el error `DuplicateStepDefinition`, significa que tienes el mismo step definition en múltiples clases. Este proyecto incluye:
- **CommonSteps.java** - Para steps compartidos entre features
- Steps específicos en cada clase (RegisterSteps, LoginSteps, ProfileSteps)

### Script de Validación
```bash
# Validar proyecto antes de ejecutar tests
chmod +x validate-project.sh
./validate-project.sh
```

## 📈 Mejores Prácticas Implementadas

1. **Page Object Model** - Separación clara entre lógica de prueba y elementos de página
2. **Data-Driven Testing** - Datos de prueba centralizados y reutilizables
3. **Gherkin BDD** - Casos de prueba legibles para stakeholders no técnicos
4. **Thread-Safe WebDriver** - Gestión segura del driver para ejecución paralela futura
5. **Explicit Waits** - Esperas inteligentes para elementos dinámicos
6. **Screenshot on Failure** - Captura automática de errores
7. **Comprehensive Reporting** - Múltiples formatos de reporte
8. **CI/CD Integration** - Integración completa con GitHub Actions

## 🤝 Contribución

1. Fork el proyecto
2. Crear feature branch (`git checkout -b feature/nueva-funcionalidad`)
3. Commit cambios (`git commit -am 'Add nueva funcionalidad'`)
4. Push a la rama (`git push origin feature/nueva-funcionalidad`)
5. Crear Pull Request

## 📄 Licencia

Este proyecto está licenciado bajo la Licencia MIT.