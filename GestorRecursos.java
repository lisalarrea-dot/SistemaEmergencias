
import java.util.ArrayList;
import java.util.List;

public class GestorRecursos {

    private final List<RecursoEmergencia> recursos = new ArrayList<>();

    public GestorRecursos() {
        recursos.add(new RecursoEmergencia(1, "Ambulancia"));
        recursos.add(new RecursoEmergencia(2, "Patrulla"));
        recursos.add(new RecursoEmergencia(3, "Camion de bomberos"));
    }

    // Determinar el recurso segun el tipo de emergencia
    private String determinarRecurso(Emergencia emergencia) {

        String descripcion =
                emergencia.getDescripcion().toLowerCase();

        if (descripcion.contains("incendio")) {
            return "Camion de bomberos";
        }

        if (descripcion.contains("accidente")
                || descripcion.contains("medica")) {
            return "Ambulancia";
        }

        return "Patrulla";
    }

    // Asignar el recurso adecuado para cada emergencia
    public synchronized RecursoEmergencia asignarRecurso(
            Emergencia emergencia) {

        String tipoRequerido = determinarRecurso(emergencia);

        while (true) {

            for (RecursoEmergencia recurso : recursos) {

                if (recurso.getTipo().equals(tipoRequerido)
                        && recurso.asignar()) {

                    System.out.println("Recurso seleccionado: "
                            + tipoRequerido);

                    return recurso;
                }
            }

            try {

                System.out.println("El recurso "
                        + tipoRequerido
                        + " esta ocupado. Esperando disponibilidad...");

                wait();

            } catch (InterruptedException e) {

                Thread.currentThread().interrupt();
                return null;
            }
        }
    }

    // Mantener el metodo anterior para pruebas generales
    public synchronized RecursoEmergencia asignarRecurso() {

        while (true) {

            for (RecursoEmergencia recurso : recursos) {

                if (recurso.asignar()) {
                    return recurso;
                }
            }

            try {

                System.out.println("Todos los recursos estan ocupados. "
                        + "Esperando disponibilidad...");

                wait();

            } catch (InterruptedException e) {

                Thread.currentThread().interrupt();
                return null;
            }
        }
    }

    public synchronized void liberarRecurso(
            RecursoEmergencia recurso) {

        if (recurso != null) {

            recurso.liberar();

            System.out.println("Recurso disponible nuevamente: "
                    + recurso.getTipo());

            notifyAll();
        }
    }

    public synchronized void mostrarRecursos() {

        System.out.println("\nESTADO DE LOS RECURSOS:");

        for (RecursoEmergencia recurso : recursos) {
            System.out.println(recurso);
        }
    }
}
