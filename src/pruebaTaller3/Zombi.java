package pruebaTaller3;

// Subclase Zombi que hereda de Monstruo e implementa las interfaces Mordedor y Regenerable
public class Zombi extends Monstruo implements Mordedor, Regenerable {
    private int fuerzaDeMordida;

    // Constructor del Zombi que recibe atributos base y su atributo propio
    public Zombi(int vida, int velocidad, int fuerza, int resistencia, int fuerzaDeMordida) {
        super("Zombi", vida, velocidad, fuerza, resistencia);
        this.fuerzaDeMordida = fuerzaDeMordida;
    }

    @Override
    public String morder() { 
        return "¡El zombi lanza una mordida feroz!"; 
    }

    @Override
    public String regenerarse() { 
        return "¡El zombi regenera parte de su cuerpo!"; 
    }

    @Override
    public double calcularPotencialCarrera() {
        // Fórmula del potencial para la Carrera del Cementerio
        return (double) (velocidad * resistencia) / 100.0;
    }

    @Override
    public double calcularPotencialCalabazas() {
        // Fórmula del potencial para la Caza de Calabazas
        return (double) (fuerzaDeMordida * vida) / 100.0 + (double) resistencia / 2.0;
    }

    @Override
    public double calcularPotencialLapidas() {
        // Fórmula del potencial para el Lanzamiento de Lápidas
        return (double) (fuerza * resistencia) / 100.0;
    }

    @Override
    public double calcularPotencialAmanecer() {
        // Fórmula del potencial para Sobrevivir al Amanecer
        return (double) (resistencia * vida) / 100.0;
    }

    @Override
    public void mostrarAtributos() {
        // Muestra en consola todos los datos del zombi formateados
        System.out.println("Zombi      | Vida " + vida + " | Vel " + velocidad + " | Fue " + fuerza + " | Res " + resistencia + " | Mordida " + fuerzaDeMordida);
    }
}