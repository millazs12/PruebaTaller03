package pruebaTaller3;

// Clase abstracta principal que agrupa los atributos comunes de todos los monstruos
public abstract class Monstruo {
    protected String tipo;
    protected int vida;
    protected int velocidad;
    protected int fuerza;
    protected int resistencia;
    protected boolean usado; // Sirve para saber si ya fue utilizado en la sesión actual

    // Constructor para inicializar los atributos base del monstruo
    public Monstruo(String tipo, int vida, int velocidad, int fuerza, int resistencia) {
        this.tipo = tipo;
        this.vida = vida;
        this.velocidad = velocidad;
        this.fuerza = fuerza;
        this.resistencia = resistencia;
        this.usado = false; // Al iniciar la sesión, ningún monstruo está usado
    }

    // Métodos Getters y Setters tradicionales
    public String getTipo() { 
        return tipo; 
    }
    
    public int getVida() { 
        return vida; 
    }
    
    public int getVelocidad() { 
        return velocidad; 
    }
    
    public int getFuerza() { 
        return fuerza; 
    }
    
    public int getResistencia() { 
        return resistencia; 
    }
    
    public boolean isUsado() { 
        return usado; 
    }
    
    public void setUsado(boolean usado) { 
        this.usado = usado; 
    }

    // Métodos abstractos que cada subclase implementará para calcular su potencial en cada minijuego
    public abstract double calcularPotencialCarrera();
    public abstract double calcularPotencialCalabazas();
    public abstract double calcularPotencialLapidas();
    public abstract double calcularPotencialAmanecer();
    
    // Método abstracto para mostrar los atributos específicos por pantalla
    public abstract void mostrarAtributos();
}