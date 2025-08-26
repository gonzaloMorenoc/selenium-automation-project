#!/bin/bash

# Script de validación del proyecto Wordmate Selenium Automation
# Verifica que todas las URLs estén correctamente configuradas

set -e

echo "🔍 Validando Proyecto Wordmate Selenium Automation"
echo "=================================================="

# Colores para output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Contadores
ERRORS=0
WARNINGS=0

# Función para mostrar errores
show_error() {
    echo -e "${RED}❌ ERROR: $1${NC}"
    ((ERRORS++))
}

# Función para mostrar advertencias
show_warning() {
    echo -e "${YELLOW}⚠️  WARNING: $1${NC}"
    ((WARNINGS++))
}

# Función para mostrar éxito
show_success() {
    echo -e "${GREEN}✅ $1${NC}"
}

# Función para mostrar información
show_info() {
    echo -e "${BLUE}ℹ️  $1${NC}"
}

# 1. Verificar estructura del proyecto
echo ""
echo "1. Verificando estructura del proyecto..."

required_dirs=(
    "src/main/java/pages"
    "src/main/java/utils"
    "src/test/java/runners"
    "src/test/java/stepDefinitions"
    "src/test/resources/features"
)

for dir in "${required_dirs[@]}"; do
    if [ -d "$dir" ]; then
        show_success "Directorio encontrado: $dir"
    else
        show_error "Directorio faltante: $dir"
    fi
done

# 2. Verificar archivos principales
echo ""
echo "2. Verificando archivos principales..."

required_files=(
    "pom.xml"
    "src/main/java/utils/TestData.java"
    "src/main/java/pages/BasePage.java"
    "src/main/java/pages/RegisterPage.java"
    "src/main/java/pages/LoginPage.java"
    "src/main/java/pages/ProfilePage.java"
    "src/test/java/stepDefinitions/RegisterSteps.java"
    "src/test/java/stepDefinitions/LoginSteps.java"
    "src/test/java/stepDefinitions/ProfileSteps.java"
    "src/test/java/stepDefinitions/CommonSteps.java"
    "src/test/resources/features/register.feature"
    "src/test/resources/features/login.feature"
    "src/test/resources/features/profile.feature"
)

for file in "${required_files[@]}"; do
    if [ -f "$file" ]; then
        show_success "Archivo encontrado: $file"
    else
        show_error "Archivo faltante: $file"
    fi
done

# 3. Verificar URLs en los archivos
echo ""
echo "3. Verificando configuración de URLs..."

# Verificar TestData.java
if [ -f "src/main/java/utils/TestData.java" ]; then
    if grep -q "/public/register.html" "src/main/java/utils/TestData.java"; then
        show_success "URL de registro correcta en TestData.java"
    else
        show_error "URL de registro incorrecta en TestData.java"
    fi
    
    if grep -q "/public/login.html" "src/main/java/utils/TestData.java"; then
        show_success "URL de login correcta en TestData.java"
    else
        show_error "URL de login incorrecta en TestData.java"
    fi
    
    if grep -q "/public/profile.html" "src/main/java/utils/TestData.java"; then
        show_success "URL de perfil correcta en TestData.java"
    else
        show_error "URL de perfil incorrecta en TestData.java"
    fi
    
    if grep -q "/index.html" "src/main/java/utils/TestData.java" && ! grep -q "/public/index.html" "src/main/java/utils/TestData.java"; then
        show_success "URL de home correcta en TestData.java (sin /public/)"
    else
        show_error "URL de home incorrecta en TestData.java"
    fi
fi

# Verificar Page Objects
page_files=(
    "src/main/java/pages/RegisterPage.java"
    "src/main/java/pages/LoginPage.java" 
    "src/main/java/pages/ProfilePage.java"
)

for file in "${page_files[@]}"; do
    if [ -f "$file" ]; then
        if grep -q "TestData.URLs" "$file"; then
            show_success "$(basename $file) usa URLs centralizadas"
        else
            show_warning "$(basename $file) podría no usar URLs centralizadas"
        fi
    fi
done

# 4. Verificar dependencias de Maven
echo ""
echo "4. Verificando configuración de Maven..."

if [ -f "pom.xml" ]; then
    if grep -q "selenium-java" "pom.xml"; then
        show_success "Dependencia Selenium encontrada"
    else
        show_error "Dependencia Selenium faltante"
    fi
    
    if grep -q "cucumber-java" "pom.xml"; then
        show_success "Dependencia Cucumber encontrada"
    else
        show_error "Dependencia Cucumber faltante"
    fi
    
    if grep -q "testng" "pom.xml"; then
        show_success "Dependencia TestNG encontrada"
    else
        show_error "Dependencia TestNG faltante"
    fi
    
    if grep -q "webdrivermanager" "pom.xml"; then
        show_success "Dependencia WebDriverManager encontrada"
    else
        show_error "Dependencia WebDriverManager faltante"
    fi
fi

# 5. Verificar features de Cucumber
echo ""
echo "5. Verificando archivos .feature..."

feature_files=(
    "src/test/resources/features/register.feature"
    "src/test/resources/features/login.feature"
    "src/test/resources/features/profile.feature"
)

for file in "${feature_files[@]}"; do
    if [ -f "$file" ]; then
        scenario_count=$(grep -c "Scenario:" "$file" 2>/dev/null || echo "0")
        show_success "$(basename $file): $scenario_count escenarios encontrados"
    fi
done

# 6. Verificar configuración de TestNG
echo ""
echo "6. Verificando configuración de TestNG..."

if [ -f "src/test/resources/testng.xml" ]; then
    show_success "Archivo testng.xml encontrado"
    if grep -q "TestRunner" "src/test/resources/testng.xml"; then
        show_success "TestRunner configurado en testng.xml"
    fi
else
    show_warning "Archivo testng.xml faltante"
fi

# 7. Verificar que Java y Maven estén disponibles
echo ""
echo "7. Verificando herramientas requeridas..."

if command -v java &> /dev/null; then
    java_version=$(java -version 2>&1 | head -n 1)
    show_success "Java disponible: $java_version"
else
    show_error "Java no está instalado"
fi

if command -v mvn &> /dev/null; then
    mvn_version=$(mvn -version 2>&1 | head -n 1)
    show_success "Maven disponible: $mvn_version"
else
    show_error "Maven no está instalado"
fi

# 8. Verificar URLs problemáticas
echo ""
echo "8. Buscando URLs problemáticas..."

# Buscar URLs sin /public/ en archivos Java (excluyendo comentarios)
problematic_patterns=(
    "wordmate\.es/register\.html"
    "wordmate\.es/login\.html" 
    "wordmate\.es/profile\.html"
)

for pattern in "${problematic_patterns[@]}"; do
    if grep -r --include="*.java" "$pattern" src/ 2>/dev/null | grep -v "^[[:space:]]*//"; then
        show_warning "Posible URL problemática encontrada: $pattern"
    fi
done

# Resumen
echo ""
echo "=================================================="
echo "🏁 RESUMEN DE VALIDACIÓN"
echo "=================================================="

if [ $ERRORS -eq 0 ] && [ $WARNINGS -eq 0 ]; then
    show_success "✨ Validación completada sin errores ni advertencias!"
    echo ""
    echo "✅ Tu proyecto está listo para ejecutar tests"
    echo "💡 Para ejecutar tests: ./run-tests.sh smoke"
elif [ $ERRORS -eq 0 ]; then
    show_success "✅ Validación completada sin errores"
    echo -e "${YELLOW}⚠️  Advertencias encontradas: $WARNINGS${NC}"
    echo ""
    echo "✅ Tu proyecto debería funcionar correctamente"
    echo "💡 Revisa las advertencias para optimizar el proyecto"
else
    show_error "❌ Validación completada con errores"
    echo -e "${RED}❌ Errores encontrados: $ERRORS${NC}"
    echo -e "${YELLOW}⚠️  Advertencias encontradas: $WARNINGS${NC}"
    echo ""
    echo "❌ Debes corregir los errores antes de ejecutar tests"
    exit 1
fi

echo ""
echo "📋 URLs Configuradas Correctamente:"
echo "   • Registro: https://www.wordmate.es/public/register.html"
echo "   • Login: https://www.wordmate.es/public/login.html"
echo "   • Perfil: https://www.wordmate.es/public/profile.html"  
echo "   • Home: https://www.wordmate.es/index.html"

exit 0