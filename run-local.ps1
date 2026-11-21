$ErrorActionPreference = "Stop"

$root = Split-Path -Parent $MyInvocation.MyCommand.Path
$backend = Join-Path $root "Library-Management-System-backend"
$frontend = Join-Path $root "library-frontend"

function Test-ListeningPort {
    param([int]$Port)

    return [bool](Get-NetTCPConnection -State Listen -LocalPort $Port -ErrorAction SilentlyContinue)
}

if (-not (Test-ListeningPort 8080)) {
    Start-Process -FilePath "powershell.exe" `
        -WorkingDirectory $backend `
        -ArgumentList @("-NoExit", "-Command", ".\mvnw.cmd spring-boot:run")
    Write-Host "Starting backend at http://localhost:8080"
} else {
    Write-Host "Backend is already listening on port 8080"
}

if (-not (Test-ListeningPort 5173)) {
    Start-Process -FilePath "powershell.exe" `
        -WorkingDirectory $frontend `
        -ArgumentList @(
            "-NoExit",
            "-Command",
            "if (-not (Test-Path .\node_modules)) { npm ci; if (`$LASTEXITCODE -ne 0) { exit `$LASTEXITCODE } }; npm run dev -- --host 127.0.0.1"
        )
    Write-Host "Starting frontend at http://localhost:5173"
} else {
    Write-Host "Frontend is already listening on port 5173"
}

for ($attempt = 0; $attempt -lt 60; $attempt++) {
    try {
        $backendReady = (Invoke-WebRequest -UseBasicParsing "http://localhost:8080/health" -TimeoutSec 2).StatusCode -eq 200
    } catch {
        $backendReady = $false
    }

    try {
        $frontendReady = (Invoke-WebRequest -UseBasicParsing "http://127.0.0.1:5173/" -TimeoutSec 2).StatusCode -eq 200
    } catch {
        $frontendReady = $false
    }

    if ($backendReady -and $frontendReady) {
        Start-Process "http://localhost:5173"
        exit 0
    }

    Start-Sleep -Seconds 2
}

throw "Local services did not become ready. Check the backend and frontend PowerShell windows for errors."
