
import java.util.Comparator;

public class PrioridadEmergencia {

    public static int obtenerNivel(String gravedad) {

        switch (gravedad.toLowerCase()) {
            case "critica":
                return 1;
            case "alta":
                return 2;
            case "media":
                return 3;
            case "baja":
                return 4;
            default:
                return 5;
        }
    }

    public static void ordenarEmergencias(
            java.util.List<Emergencia> emergencias) {

        emergencias.sort(
            Comparator
                .comparingInt((Emergencia e) ->
                    obtenerNivel(e.getGravedad()))
                .thenComparing(
                    Comparator.comparingInt(
                        Emergencia::getTiempoEspera
                    ).reversed()
                )
                .thenComparingDouble(Emergencia::getDistancia)
        );
    }
}
