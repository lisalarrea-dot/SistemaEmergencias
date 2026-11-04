public class RecursoEmergencia {

    private int id;
    private String tipo;
    private boolean disponible;

    public RecursoEmergencia(int id, String tipo) {
        this.id = id;
        this.tipo = tipo;
        this.disponible = true;
    }

    public int getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public synchronized boolean asignar() {
        if (disponible) {
            disponible = false;
            return true;
        }
        return false;
    }

    public synchronized void liberar() {
        disponible = true;
    }

    @Override
    public String toString() {
        return "Recurso " + id + " | Tipo: " + tipo
                + " | Disponible: " + disponible;
    }
}
