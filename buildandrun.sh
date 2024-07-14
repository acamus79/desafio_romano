#!/bin/bash

# Función para comprobar si el script se está ejecutando como root
function check_root {
    if [ "$EUID" -ne 0 ]; then
        echo "Este script necesita ser ejecutado como root."
        exec sudo "$0" "$@"
        exit
    fi
}

check_root "$@"

# Paso 1: Cambiar al directorio del proyecto y construir el JAR usando Maven
projectDir="romanos"
echo "Cambiando al directorio del proyecto: $projectDir"
cd "$projectDir" || exit

# Paso 2: Construir el JAR usando Maven
echo "Construyendo el JAR con Maven..."
mvnResult=$(mvn clean package -DskipTests)
if [ $? -ne 0 ]; then
    echo "Error al construir el JAR"
    exit $?
fi

# Volver al directorio original
cd - || exit

# Verificar si el archivo JAR existe
jarPath="romanos/target/romanosAPI-0.0.1-SNAPSHOT.jar"
if [ ! -f "$jarPath" ]; then
    echo "El archivo JAR no se encontró en la ruta especificada: $jarPath"
    exit 1
fi

# Paso 3: Construir la imagen Docker
echo "Construyendo la imagen Docker..."
docker build -t acamus79/romanos-api:latest -f Dockerfile .
if [ $? -ne 0 ]; then
    echo "Error al construir la imagen Docker"
    exit $?
fi

# Paso 4: Ejecutar Docker Compose
echo "Levantando los servicios con Docker Compose..."
docker-compose -f docker-compose.yml up --build
if [ $? -ne 0 ]; then
    echo "Error al levantar los servicios con Docker Compose"
    exit $?
fi
