
public class AtencionEmergencia implements Runnable {

    private Emergencia emergencia;
    private GestorRecursos gestorRecursos;
    private RecursoEmergencia recurso;
    private int intentos;

    // Maximo de reintentos automaticos
    private static final int MAX_REINTENTOS = 1;

    public AtencionEmergencia(
            Emergencia emergencia,
            GestorRecursos gestorRecursos,
            RecursoEmergencia recurso) {

        this(emergencia, gestorRecursos, recurso, 0);
    }

    public AtencionEmergencia(
            Emergencia emergencia,
            GestorRecursos gestorRecursos,
            RecursoEmergencia recurso,
            int intentos) {

        this.emergencia = emergencia;
        this.gestorRecursos = gestorRecursos;
        this.recurso = recurso;
        this.intentos = intentos;
    }

    @Override
    public void run() {

        boolean reintentar = false;

        try {

            System.out.println("\nCENTRO DE EMERGENCIAS:");
            System.out.println("Despachando " + recurso.getTipo()
                    + " para la emergencia "
                    + emergencia.getId());

            System.out.println("EQUIPO DE ATENCION:");
            System.out.println(recurso.getTipo()
                    + " confirma la recepcion del servicio.");

            // Registrar inicio de atencion y tiempo de respuesta
            emergencia.iniciarAtencion();

            emergencia.setEstado("EN ATENCIÓN");

            System.out.println(recurso.getTipo()
                    + " inicia la atencion de la emergencia "
                    + emergencia.getId() + "...");

            Thread.sleep(3000);

            // Registrar finalizacion y duracion de la atencion
            emergencia.finalizarAtencion();

            System.out.println("EQUIPO DE ATENCION:");
            System.out.println(recurso.getTipo()
                    + " informa que finalizo el servicio.");

            emergencia.setEstado("ATENDIDA");

            System.out.println("CENTRO DE EMERGENCIAS:");
            System.out.println("Servicio de la emergencia "
                    + emergencia.getId()
                    + " finalizado correctamente.");

        } catch (InterruptedException e) {

            emergencia.setEstado("PENDIENTE");

            System.out.println("\nCOMUNICACION DE FALLA:");
            System.out.println("La atencion de la emergencia "
                    + emergencia.getId()
                    + " fue interrumpida.");

            if (intentos < MAX_REINTENTOS) {

                System.out.println("Se programara un reintento automatico.");

                reintentar = true;

            } else {

                System.out.println("Se alcanzo el limite de reintentos.");
                System.out.println("La emergencia queda pendiente.");
            }

        } finally {

            gestorRecursos.liberarRecurso(recurso);

            System.out.println("CENTRO DE EMERGENCIAS:");
            System.out.println("Recurso liberado: "
                    + recurso.getTipo());
        }

        // Realizar un nuevo intento despues de liberar el recurso
        if (reintentar) {

            System.out.println("\nRECUPERACION AUTOMATICA:");

            RecursoEmergencia nuevoRecurso =
                    gestorRecursos.asignarRecurso(emergencia);

            System.out.println("Nuevo recurso asignado a la emergencia "
                    + emergencia.getId() + ": "
                    + nuevoRecurso.getTipo());

            AtencionEmergencia nuevaAtencion =
                    new AtencionEmergencia(
                            emergencia,
                            gestorRecursos,
                            nuevoRecurso,
                            intentos + 1
                    );

            Thread nuevoHilo = new Thread(nuevaAtencion);
            nuevoHilo.start();
            
            try {
                nuevoHilo.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
}
        }
    }
}
