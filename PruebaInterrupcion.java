
public class PruebaInterrupcion {

    public static void main(String[] args) {

        System.out.println("================================");
        System.out.println(" PRUEBA DE RECUPERACION AUTOMATICA");
        System.out.println("================================");

        Emergencia emergencia = new Emergencia(
                100,
                "Emergencia medica de prueba",
                "Critica",
                5,
                2.0
        );

        GestorRecursos gestorRecursos = new GestorRecursos();

        RecursoEmergencia recurso =
        gestorRecursos.asignarRecurso(emergencia);

        AtencionEmergencia atencion =
                new AtencionEmergencia(
                        emergencia,
                        gestorRecursos,
                        recurso
                );

        Thread hilo = new Thread(atencion);
        hilo.start();

        try {

            // Esperar para permitir que comience la atencion
            Thread.sleep(1000);

            System.out.println("\nSimulando una falla...");
            hilo.interrupt();

            // Esperar a que termine el primer intento
            // y se inicie la recuperacion automatica
            hilo.join();

            // Esperar a que el reintento finalice
            // El segundo intento tarda aproximadamente 3 segundos
            Thread.sleep(4000);

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();
        }

        System.out.println("\nESTADO FINAL DE LA EMERGENCIA:");
        System.out.println(emergencia);

        System.out.println("\nESTADO DE LOS RECURSOS:");
        gestorRecursos.mostrarRecursos();

        System.out.println("\nPrueba finalizada.");
    }
}
