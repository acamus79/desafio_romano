# buildandrun.ps1

function Test-Admin {
    $currentUser = New-Object Security.Principal.WindowsPrincipal([Security.Principal.WindowsIdentity]::GetCurrent())
    return $currentUser.IsInRole([Security.Principal.WindowsBuiltInRole]::Administrator)
}

if (-not (Test-Admin)) {
    Write-Host "Este script necesita ser ejecutado como administrador."
    Start-Process powershell.exe "-File $PSCommandPath" -Verb RunAs
    exit
}

# Paso 1: Cambiar al directorio del proyecto y construir el JAR usando Maven
$projectDir = "romanos"
Write-Host "Cambiando al directorio del proyecto: $projectDir"
Push-Location $projectDir

# Paso 2: Construir el JAR usando Maven
Write-Host "Construyendo el JAR con Maven..."
$mvnResult = & mvn clean package -DskipTests
if ($LASTEXITCODE -ne 0) {
    Write-Host "Error al construir el JAR"
    exit $LASTEXITCODE
}

# Volver al directorio original
Pop-Location


# Verificar si el archivo JAR existe
$jarPath = "romanos/target/romanos-0.0.1-SNAPSHOT.jar"
if (-Not (Test-Path $jarPath)) {
    Write-Host "El archivo JAR no se encontró en la ruta especificada: $jarPath"
    exit 1
}

# Paso 3: Construir la imagen Docker
Write-Host "Construyendo la imagen Docker..."
$dockerBuildResult = & docker build -t acamus79/romanos-api:latest -f Dockerfile .
if ($LASTEXITCODE -ne 0) {
    Write-Host "Error al construir la imagen Docker"
    exit $LASTEXITCODE
}

# Paso 4: Ejecutar Docker Compose
Write-Host "Levantando los servicios con Docker Compose..."
$dockerComposeResult = & docker-compose -f docker-compose.yml up --build
if ($LASTEXITCODE -ne 0) {
    Write-Host "Error al levantar los servicios con Docker Compose"
    exit $LASTEXITCODE
}
