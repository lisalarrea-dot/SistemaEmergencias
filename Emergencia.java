
public class Emergencia {

    private int id;
    private String descripcion;
    private String gravedad;
    private int tiempoEspera;
    private double distancia;
    private volatile String estado;

    // Registro de tiempos
    private long fechaRegistro;
    private long inicioPrimeraAtencion;
    private long inicioAtencionActual;
    private long tiempoRespuesta;
    private long tiempoAtencion;

    public Emergencia(int id, String descripcion, String gravedad,
                      int tiempoEspera, double distancia) {
        this.id = id;
        this.descripcion = descripcion;
        this.gravedad = gravedad;
        this.tiempoEspera = tiempoEspera;
        this.distancia = distancia;
        this.estado = "PENDIENTE";

        this.fechaRegistro = System.currentTimeMillis();
    }

    public int getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getGravedad() {
        return gravedad;
    }

    public int getTiempoEspera() {
        return tiempoEspera;
    }

    public double getDistancia() {
        return distancia;
    }

    public String getEstado() {
        return estado;
    }

    // Registra el inicio de la atención
    public synchronized void iniciarAtencion() {
        long ahora = System.currentTimeMillis();

        if (inicioPrimeraAtencion == 0) {
            inicioPrimeraAtencion = ahora;
            tiempoRespuesta = ahora - fechaRegistro;
        }

        inicioAtencionActual = ahora;
    }

    // Registra la finalización de la atención
    public synchronized void finalizarAtencion() {
        if (inicioAtencionActual > 0) {
            tiempoAtencion =
                    System.currentTimeMillis() - inicioAtencionActual;
        }
    }

    public synchronized long getTiempoRespuesta() {
        return tiempoRespuesta;
    }

    public synchronized long getTiempoAtencion() {
        return tiempoAtencion;
    }

    public synchronized void setEstado(String estado) {
        this.estado = estado;
        System.out.println("Emergencia " + id
                + " cambio de estado: " + estado);
    }

    @Override
    public String toString() {
        return "Emergencia " + id
                + " | " + descripcion
                + " | Gravedad: " + gravedad
                + " | Espera: " + tiempoEspera + " min"
                + " | Distancia: " + distancia + " km"
                + " | Estado: " + estado;
    }
}
