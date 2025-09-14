# Wordmate Selenium Automation

Proyecto de automatización de pruebas para la aplicación web Wordmate.es usando Selenium WebDriver, Cucumber BDD y Java.

## Tecnologías

- **Selenium WebDriver 4.15.0** para automatización web
- **Cucumber 7.14.0** con Gherkin para BDD
- **TestNG** como framework de pruebas
- **Page Object Model** para mantenibilidad
- **Allure Reports** para reportes detallados
- **GitHub Actions** para CI/CD
- **WebDriverManager** para gestión automática de drivers

## Casos de Prueba

### Registro de Usuario
- Registro exitoso con todos los campos
- Registro con foto de perfil
- Validación de email inválido
- Validación de contraseña débil
- Validación de campos vacíos
- Navegación entre páginas

### Login de Usuario
- Login exitoso con credenciales válidas
- Login fallido con usuario inválido
- Login fallido con contraseña incorrecta
- Validación de campos vacíos
- Navegación entre páginas

### Gestión de Perfil
- Acceso al perfil estando logueado
- Acceso al perfil sin estar logueado
- Logout exitoso
- Borrado de cuenta exitoso
- Borrado de cuenta con contraseña incorrecta
- Cancelación de borrado de cuenta

## Estructura del Proyecto

```
selenium-automation-project/
├── src/
│   ├── main/java/
│   │   ├── pages/              # Page Objects
│   │   └── utils/              # Utilidades (DriverManager, TestData)
│   └── test/
│       ├── java/
│       │   ├── runners/        # Ejecutores de pruebas
│       │   └── stepDefinitions/ # Steps de Cucumber
│       └── resources/
│           ├── features/       # Archivos .feature
│           └── testng.xml      # Configuración TestNG
├── .github/workflows/          # Pipeline CI/CD
├── run-tests.sh               # Script de ejecución
├── validate-project.sh        # Script de validación
└── pom.xml                    # Configuración Maven
```

## Requisitos

- Java 11 o superior
- Maven 3.6 o superior
- Chrome o Firefox instalado
- Git para control de versiones

## Ejecución

### Instalar dependencias
```bash
mvn clean compile
```

### Ejecutar todas las pruebas
```bash
mvn clean test
```

### Ejecutar solo smoke tests
```bash
mvn clean test -Dtestng.suitename="Smoke Tests"
```

### Opciones adicionales
```bash
# Firefox
mvn clean test -Dbrowser=firefox

# Modo headless
mvn clean test -Dheadless=true
```

## Reportes

Los reportes se generan automáticamente en:
- **Cucumber HTML**: `target/cucumber-reports/cucumber-html-report/`
- **Allure**: Ejecutar `mvn allure:serve` para ver el reporte

## Scripts de utilidad

```bash
# Ejecutar smoke tests
./run-tests.sh smoke

# Ejecutar tests específicos
./run-tests.sh login
./run-tests.sh register
./run-tests.sh profile

# Ejecutar en headless
./run-tests.sh headless

# Generar reporte Allure
./run-tests.sh report

# Validar proyecto
./validate-project.sh
```

## Tags de Cucumber

- `@regression` - Todas las pruebas de regresión
- `@smoke` - Pruebas críticas básicas
- `@positive` - Casos de prueba positivos
- `@negative` - Casos de prueba negativos
- `@login` - Pruebas de login
- `@register` - Pruebas de registro
- `@profile` - Pruebas de perfil
- `@destructive` - Pruebas que modifican/eliminan datos

### Ejemplos de ejecución por tags

```bash
# Solo smoke tests
mvn clean test -Dcucumber.filter.tags="@smoke"

# Solo pruebas positivas
mvn clean test -Dcucumber.filter.tags="@positive"

# Excluir pruebas destructivas
mvn clean test -Dcucumber.filter.tags="@regression and not @destructive"
```

## CI/CD

La pipeline se ejecuta automáticamente:
- En push a `main` o `develop`
- En Pull Requests hacia `main`
- Diariamente a las 6:00 AM UTC

Los artifacts incluyen reportes Cucumber HTML, Allure, JUnit XML y screenshots de fallos.

## Configuración

### Variables de sistema
- `browser` - Navegador (chrome/firefox). Default: chrome
- `headless` - Modo sin cabeza (true/false). Default: false
- `cucumber.filter.tags` - Tags de Cucumber a ejecutar

### Ejemplo personalizado
```bash
mvn clean test -Dbrowser=firefox -Dheadless=true -Dcucumber.filter.tags="@smoke"
```

## Buenas Prácticas Implementadas

1. **Page Object Model** - Separación entre lógica de prueba y elementos de página
2. **Data-Driven Testing** - Datos centralizados y reutilizables
3. **Gherkin BDD** - Casos de prueba legibles para stakeholders
4. **Thread-Safe WebDriver** - Gestión segura del driver
5. **Explicit Waits** - Esperas inteligentes para elementos dinámicos
6. **Screenshot on Failure** - Captura automática de errores
7. **Multiple Report Formats** - Varios formatos de reporte

## URLs Configuradas

- Registro: https://www.wordmate.es/public/register.html
- Login: https://www.wordmate.es/public/login.html
- Perfil: https://www.wordmate.es/public/profile.html
- Home: https://www.wordmate.es/index.html

## Contribución

1. Fork el proyecto
2. Crear feature branch (`git checkout -b feature/nueva-funcionalidad`)
3. Crear commit (`git commit -am 'Add nueva funcionalidad'`)
4. Push a la rama (`git push origin feature/nueva-funcionalidad`)
5. Crear Pull Request

## Licencia

Este proyecto está bajo la Licencia MIT.
