@echo off
setlocal enabledelayedexpansion

REM Guardar el directorio actual
set CURRENT_DIR=%cd%

REM Verificar si Java 17 está instalado
echo Verificando la versión de Java...
java -version 2>&1 | find "17" >nul
if %ERRORLEVEL% neq 0 (
    echo Java 17 no está instalado. Por favor, instala Java 17 y vuelve a intentar.
    exit /b 1
)
echo Java 17 está instalado

REM Paso 1: Cambiar al directorio del proyecto y construir el JAR usando Maven
cd romanos
echo Cambiado al directorio del proyecto: %cd%
echo Construyendo el JAR con Maven...
call mvn clean package -DskipTests
if %ERRORLEVEL% neq 0 (
    echo Error al construir el JAR
    cd %CURRENT_DIR%
    exit /b 1
)
echo Maven build terminado

REM Volver al directorio original
cd %CURRENT_DIR%
echo Volviendo al directorio original: %cd%

REM Verificar si el archivo JAR existe
echo Verificando la existencia del archivo JAR...
if not exist romanos\target\romanos-0.0.1-SNAPSHOT.jar (
    echo El archivo JAR no se encontró en la ruta especificada: romanos\target\romanos-0.0.1-SNAPSHOT.jar
    exit /b 1
)
echo Archivo JAR encontrado

REM Paso 2: Ejecutar el archivo JAR
echo Ejecutando el archivo JAR...
java -jar romanos\target\romanos-0.0.1-SNAPSHOT.jar
if %ERRORLEVEL% neq 0 (
    echo Error al ejecutar el archivo JAR
    exit /b 1
)

echo Ejecución del archivo JAR terminada
