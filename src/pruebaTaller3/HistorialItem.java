package pruebaTaller3;

// Clase auxiliar para guardar los registros individuales de cada minijuego jugado en la sesión
public class HistorialItem {
    private String minijuego;
    private String tipoMonstruo;
    private String habilidad;
    private int puntaje;

    public HistorialItem(String minijuego, String tipoMonstruo, String habilidad, int puntaje) {
        this.minijuego = minijuego;
        this.tipoMonstruo = tipoMonstruo;
        this.habilidad = habilidad;
        this.puntaje = puntaje;
    }

    @Override
    public String toString() {
        // Da formato al texto que se imprime al mostrar el historial de la sesión
        return minijuego + " | " + tipoMonstruo + " | Habilidad: " + habilidad + " | " + puntaje + " pts";
    }
}