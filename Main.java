
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        System.out.println("================================");
        System.out.println(" SISTEMA DE EMERGENCIAS");
        System.out.println("================================");

        GestorEmergencias gestor = new GestorEmergencias();

        // Registrar cinco emergencias
        CentroAtencion llamada1 = new CentroAtencion(
                gestor, 1, "Accidente de transito",
                "Alta", 5, 3.0);

        CentroAtencion llamada2 = new CentroAtencion(
                gestor, 2, "Emergencia medica",
                "Critica", 2, 1.5);

        CentroAtencion llamada3 = new CentroAtencion(
                gestor, 3, "Incendio estructural",
                "Alta", 8, 4.0);

        CentroAtencion llamada4 = new CentroAtencion(
                gestor, 4, "Accidente de motocicleta",
                "Media", 10, 5.0);

        CentroAtencion llamada5 = new CentroAtencion(
                gestor, 5, "Incendio forestal",
                "Critica", 3, 2.0);

        // Ejecutar las llamadas simultaneamente
        Thread[] llamadas = {
            new Thread(llamada1),
            new Thread(llamada2),
            new Thread(llamada3),
            new Thread(llamada4),
            new Thread(llamada5)
        };

        for (Thread hilo : llamadas) {
            hilo.start();
        }

        for (Thread hilo : llamadas) {
            try {
                hilo.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }

        // Obtener las emergencias registradas
        List<Emergencia> lista = gestor.obtenerEmergencias();

        System.out.println("\nEMERGENCIAS REGISTRADAS:");

        for (Emergencia emergencia : lista) {
            System.out.println(emergencia);
        }

        // Ordenar por prioridad
        PrioridadEmergencia.ordenarEmergencias(lista);

        System.out.println("\nEMERGENCIAS ORDENADAS POR PRIORIDAD:");

        for (Emergencia emergencia : lista) {
            System.out.println(emergencia);
        }

        // Gestionar recursos
        GestorRecursos gestorRecursos = new GestorRecursos();

        // Iniciar monitor en tiempo real
        MonitorEmergencias monitor = new MonitorEmergencias(gestor);
        Thread hiloMonitor = new Thread(monitor);

        System.out.println("\nINICIANDO MONITOREO DE EMERGENCIAS...");
        hiloMonitor.start();

        System.out.println("\nINICIO DE ATENCION POR PRIORIDAD:");

        List<Thread> hilosAtencion = new ArrayList<>();

        // Asignar recursos respetando el orden de prioridad
        for (Emergencia emergencia : lista) {

            RecursoEmergencia recurso =
                    gestorRecursos.asignarRecurso(emergencia);

            System.out.println("Recurso asignado a la emergencia "
                    + emergencia.getId() + ": " + recurso.getTipo());

            Thread hilo = new Thread(
                    new AtencionEmergencia(
                            emergencia,
                            gestorRecursos,
                            recurso
                    )
            );

            hilosAtencion.add(hilo);
            hilo.start();
        }

        // Esperar la finalizacion de todas las atenciones
        for (Thread hilo : hilosAtencion) {
            try {
                hilo.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }

        // Detener el monitor
        monitor.detener();

        try {
            hiloMonitor.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }

        // Mostrar resumen final de emergencias
        System.out.println("\n================================");
        System.out.println(" RESUMEN FINAL DE EMERGENCIAS");
        System.out.println("================================");

        long sumaRespuesta = 0;
        long sumaAtencion = 0;
        int emergenciasAtendidas = 0;

        for (Emergencia emergencia : lista) {

            System.out.println(emergencia);

            if ("ATENDIDA".equals(emergencia.getEstado())) {

                long respuesta = emergencia.getTiempoRespuesta();
                long atencion = emergencia.getTiempoAtencion();

                System.out.println("Tiempo de respuesta: "
                        + respuesta + " ms");

                System.out.println("Tiempo de atencion: "
                        + atencion + " ms");

                sumaRespuesta += respuesta;
                sumaAtencion += atencion;
                emergenciasAtendidas++;

            } else {
                System.out.println("No se registraron tiempos completos "
                        + "porque la emergencia sigue pendiente.");
            }

            System.out.println("--------------------------------");
        }

        // Calcular promedios
        System.out.println("\n================================");
        System.out.println(" ESTADISTICAS DEL SISTEMA");
        System.out.println("================================");

        System.out.println("Total de emergencias registradas: "
                + lista.size());

        System.out.println("Emergencias atendidas: "
                + emergenciasAtendidas);

        if (emergenciasAtendidas > 0) {

            double promedioRespuesta =
                    (double) sumaRespuesta / emergenciasAtendidas;

            double promedioAtencion =
                    (double) sumaAtencion / emergenciasAtendidas;

            System.out.printf("Promedio de respuesta: %.2f ms%n",
                    promedioRespuesta);

            System.out.printf("Promedio de atencion: %.2f ms%n",
                    promedioAtencion);

        } else {
            System.out.println("No hay datos suficientes para calcular "
                    + "los promedios.");
        }

        // Mostrar estado final de los recursos
        System.out.println("\nESTADO FINAL DE LOS RECURSOS:");
        gestorRecursos.mostrarRecursos();

        System.out.println("\nSistema finalizado.");
    }
}
