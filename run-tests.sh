#!/bin/bash

# Wordmate Automation Test Runner
# Este script facilita la ejecución de diferentes tipos de tests

set -e

echo "🚀 Wordmate Selenium Automation Test Runner"
echo "============================================="

# Función para mostrar ayuda
show_help() {
    echo "Uso: $0 [OPCIÓN]"
    echo ""
    echo "Opciones:"
    echo "  smoke       Ejecutar solo smoke tests"
    echo "  regression  Ejecutar todos los tests de regresión"
    echo "  login       Ejecutar solo tests de login"
    echo "  register    Ejecutar solo tests de registro"
    echo "  profile     Ejecutar solo tests de perfil"
    echo "  headless    Ejecutar tests en modo headless"
    echo "  firefox     Ejecutar tests con Firefox"
    echo "  report      Generar y servir reporte Allure"
    echo "  clean       Limpiar reports anteriores"
    echo "  help        Mostrar esta ayuda"
    echo ""
    echo "Ejemplos:"
    echo "  $0 smoke"
    echo "  $0 headless"
    echo "  $0 firefox"
    echo ""
}

# Función para limpiar reports anteriores
clean_reports() {
    echo "🧹 Limpiando reports anteriores..."
    rm -rf target/cucumber-reports/
    rm -rf target/allure-results/
    rm -rf target/site/
    echo "✅ Reports limpiados"
}

# Función para generar y servir reporte Allure
serve_allure_report() {
    echo "📊 Generando reporte Allure..."
    mvn allure:report
    echo "🌐 Sirviendo reporte Allure..."
    mvn allure:serve
}

# Validar que Maven esté instalado
if ! command -v mvn &> /dev/null; then
    echo "❌ Maven no está instalado o no está en el PATH"
    exit 1
fi

# Validar que Java esté instalado
if ! command -v java &> /dev/null; then
    echo "❌ Java no está instalado o no está en el PATH"
    exit 1
fi

# Verificar versión de Java
java_version=$(java -version 2>&1 | awk -F '"' '/version/ {print $2}' | awk -F. '{print $1}')
if [ "$java_version" -lt 11 ]; then
    echo "❌ Se requiere Java 11 o superior. Versión actual: $(java -version 2>&1 | head -n 1)"
    exit 1
fi

# Procesar argumentos
case "${1:-help}" in
    smoke)
        echo "🧪 Ejecutando Smoke Tests..."
        mvn clean test -Dcucumber.filter.tags="@smoke"
        ;;
    regression)
        echo "🔄 Ejecutando Regression Tests..."
        mvn clean test -Dcucumber.filter.tags="@regression"
        ;;
    login)
        echo "🔐 Ejecutando Login Tests..."
        mvn clean test -Dcucumber.filter.tags="@login"
        ;;
    register)
        echo "📝 Ejecutando Register Tests..."
        mvn clean test -Dcucumber.filter.tags="@register"
        ;;
    profile)
        echo "👤 Ejecutando Profile Tests..."
        mvn clean test -Dcucumber.filter.tags="@profile"
        ;;
    headless)
        echo "👻 Ejecutando tests en modo headless..."
        mvn clean test -Dheadless=true
        ;;
    firefox)
        echo "🦊 Ejecutando tests con Firefox..."
        mvn clean test -Dbrowser=firefox
        ;;
    report)
        serve_allure_report
        ;;
    clean)
        clean_reports
        ;;
    help|--help|-h)
        show_help
        ;;
    *)
        echo "❌ Opción no válida: $1"
        echo ""
        show_help
        exit 1
        ;;
esac

# Mostrar resumen al finalizar (excepto para comandos especiales)
if [[ "$1" != "report" && "$1" != "clean" && "$1" != "help" && "$1" != "--help" && "$1" != "-h" ]]; then
    echo ""
    echo "✅ Ejecución completada!"
    echo "📁 Reports disponibles en:"
    echo "   - Cucumber HTML: target/cucumber-reports/cucumber-html-report/"
    echo "   - JSON: target/cucumber-reports/cucumber-report.json"
    echo "   - JUnit XML: target/cucumber-reports/cucumber-junit-report.xml"
    echo ""
    echo "💡 Para ver el reporte Allure ejecuta:"
    echo "   $0 report"
fi