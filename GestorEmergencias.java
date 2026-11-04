import java.util.ArrayList;
import java.util.List;

public class GestorEmergencias {

    private List<Emergencia> emergencias;

    public GestorEmergencias() {
        emergencias = new ArrayList<>();
    }

    public synchronized void registrarEmergencia(Emergencia emergencia) {
        emergencias.add(emergencia);
        System.out.println("Emergencia registrada: " + emergencia);
    }

    public synchronized List<Emergencia> obtenerEmergencias() {
        return new ArrayList<>(emergencias);
    }
}