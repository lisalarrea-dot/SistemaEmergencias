
public class CentroAtencion implements Runnable {

    private GestorEmergencias gestor;
    private int id;
    private String descripcion;
    private String gravedad;
    private int tiempoEspera;
    private double distancia;

    public CentroAtencion(GestorEmergencias gestor, int id,
            String descripcion, String gravedad,
            int tiempoEspera, double distancia) {

        this.gestor = gestor;
        this.id = id;
        this.descripcion = descripcion;
        this.gravedad = gravedad;
        this.tiempoEspera = tiempoEspera;
        this.distancia = distancia;
    }

    @Override
    public void run() {
        System.out.println("Recibiendo llamada de emergencia " + id);

        try {
            Thread.sleep(2000);

            Emergencia emergencia = new Emergencia(
                id,
                descripcion,
                gravedad,
                tiempoEspera,
                distancia
            );

            gestor.registrarEmergencia(emergencia);

        } catch (InterruptedException e) {
            System.out.println("La llamada fue interrumpida.");
            Thread.currentThread().interrupt();
        }
    }
}
