public class MonitorEmergencias implements Runnable {

    private GestorEmergencias gestorEmergencias;
    private volatile boolean activo = true;

    public MonitorEmergencias(GestorEmergencias gestorEmergencias) {
        this.gestorEmergencias = gestorEmergencias;
    }

    @Override
    public void run() {

        while (activo) {

            int pendientes = 0;
            int enAtencion = 0;
            int atendidas = 0;

            System.out.println("\n========== MONITOREO EN TIEMPO REAL ==========");

            for (Emergencia emergencia :
                    gestorEmergencias.obtenerEmergencias()) {

                String estado = emergencia.getEstado();

                if (estado.equals("PENDIENTE")) {
                    pendientes++;
                } else if (estado.equals("EN ATENCIÓN")) {
                    enAtencion++;
                } else if (estado.equals("ATENDIDA")) {
                    atendidas++;
                }

                System.out.println(
                        "Emergencia " + emergencia.getId()
                        + " - " + emergencia.getDescripcion()
                        + " - Estado: " + estado
                );
            }

            System.out.println("----------------------------------------------");
            System.out.println("Pendientes: " + pendientes);
            System.out.println("En atención: " + enAtencion);
            System.out.println("Atendidas: " + atendidas);
            System.out.println("==============================================");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        System.out.println("\nMonitor de emergencias detenido.");
    }

    public void detener() {
        activo = false;
    }
}