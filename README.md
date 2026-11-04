# Sistema de Emergencias Médicas

## Descripción
Proyecto desarrollado en Java que simula la gestión de emergencias mediante programación concurrente. Permite registrar llamadas, clasificar emergencias por prioridad, asignar recursos y monitorear el estado de las atenciones.

## Funcionalidades
- Recepción simultánea de llamadas mediante hilos.
- Clasificación por gravedad, tiempo de espera y distancia.
- Asignación de ambulancias, patrullas y camiones de bomberos.
- Sincronización para administrar recursos compartidos.
- Monitoreo de emergencias en tiempo real desde consola.
- Registro de tiempos de respuesta y atención.
- Recuperación automática ante interrupciones.

## Tecnologías
- Java JDK 21.
- Visual Studio Code.
- Programación orientada a objetos.
- Programación concurrente y sincronización.

## Estructura del proyecto
```text
SistemaEmergencias/
└── src/
    ├── AtencionEmergencia.java
    ├── CentroAtencion.java
    ├── Emergencia.java
    ├── GestorEmergencias.java
    ├── GestorRecursos.java
    ├── Main.java
    ├── MonitorEmergencias.java
    ├── PrioridadEmergencia.java
    ├── RecursoEmergencia.java
    └── PruebaInterrupcion.java
```

## Ejecución

Desde la carpeta principal del proyecto, ejecutar:

```powershell
javac -d bin src/*.java
java -cp bin Main
```

Para ejecutar la prueba de recuperación:

```powershell
java -cp bin PruebaInterrupcion
```

## Pruebas
Se realizaron dos pruebas:
1. Ejecución principal con cinco emergencias. Todas finalizaron como ATENDIDA y los recursos quedaron disponibles.
2. Prueba de interrupción. El sistema liberó el recurso y realizó un reintento automático hasta completar la atención.

## Autor
Nombre: Lisa Mariana Larrea Rivera

Institución: IU Digital de Antioquia

Programa académico: Tecnologia en Desarrollos de Software
