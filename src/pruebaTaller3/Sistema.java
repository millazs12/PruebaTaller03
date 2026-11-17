package pruebaTaller3;

import java.util.ArrayList;
import java.util.LinkedList;

// Clase Sistema encargada de toda la lógica de negocio, manejo de listas y validaciones
public class Sistema {
    private ArrayList<Monstruo> catalogoMonstruos; // Guarda todos los monstruos leídos del archivo
    private ArrayList<Jugador> listaJugadores;     // Guarda los jugadores leídos de los registros
    private Jugador jugadorActual;                 // Almacena el jugador que inició sesión actualmente
    
    private boolean[] minijuegosJugados;           // Controla cuáles de los 4 minijuegos ya se jugaron
    private int[] puntajesMinijuegos;              // Guarda los puntajes obtenidos en cada minijuego de la sesión
    private LinkedList<HistorialItem> historialSesion; // Historial ordenado con LinkedList
    private int puntajeAcumuladoSesion;            // Suma total de puntos en la sesión actual

    // Constructor para inicializar las estructuras de datos
    public Sistema() {
        this.catalogoMonstruos = new ArrayList<>();
        this.listaJugadores = new ArrayList<>();
        this.minijuegosJugados = new boolean[4];
        this.puntajesMinijuegos = new int[4];
        this.historialSesion = new LinkedList<>();
        this.puntajeAcumuladoSesion = 0;
    }

    // Método para validar e instanciar un monstruo a partir de las líneas del archivo
    public boolean agregarMonstruo(String[] partes) {
        try {
            String tipo = partes[0];
            int vida = Integer.parseInt(partes[1]);
            int vel = Integer.parseInt(partes[2]);
            int fue = Integer.parseInt(partes[3]);
            int res = Integer.parseInt(partes[4]);

            // Validar que los atributos base estén dentro de sus rangos permitidos
            if (vida < 1 || vida > 200 || vel < 1 || vel > 100 || fue < 1 || fue > 100 || res < 1 || res > 100) {
                return false;
            }

            Monstruo m = null;
            // Validar creación según el tipo de monstruo y cantidad exacta de parámetros
            if (tipo.equals("Zombi") && partes.length == 6) {
                int mordida = Integer.parseInt(partes[5]);
                if (mordida >= 1 && mordida <= 100) {
                    m = new Zombi(vida, vel, fue, res, mordida);
                }
            } else {
                if (tipo.equals("Vampiro") && partes.length == 7) {
                    int vuelo = Integer.parseInt(partes[5]);
                    int sol = Integer.parseInt(partes[6]);
                    if (vuelo >= 1 && vuelo <= 100 && sol >= 1 && sol <= 100) {
                        m = new Vampiro(vida, vel, fue, res, vuelo, sol);
                    }
                } else {
                    if (tipo.equals("Momia") && partes.length == 6) {
                        int vendajes = Integer.parseInt(partes[5]);
                        if (vendajes >= 1 && vendajes <= 300) {
                            m = new Momia(vida, vel, fue, res, vendajes);
                        }
                    } else {
                        if (tipo.equals("HombreLobo") && partes.length == 7) {
                            int fero = Integer.parseInt(partes[5]);
                            int luna = Integer.parseInt(partes[6]);
                            if (fero >= 1 && fero <= 100 && luna >= 1 && luna <= 4) {
                                m = new HombreLobo(vida, vel, fue, res, fero, luna);
                            }
                        }
                    }
                }
            }

            if (m != null) {
                catalogoMonstruos.add(m);
                return true;
            }
            return false;
        } catch (Exception e) {
            return false; // Retorna falso si ocurre cualquier error de conversión numérica
        }
    }

    // Método para agregar un jugador desde el archivo de registros
    public boolean agregarJugadorRegistro(String nombre, int puntaje) {
        if (nombre == null || nombre.trim().isEmpty() || nombre.contains(";") || puntaje < 0) {
            return false;
        }
        listaJugadores.add(new Jugador(nombre, puntaje));
        return true;
    }

    // Método que gestiona el inicio de sesión del usuario
    public int iniciarSesion(String nombre) {
        if (nombre == null || nombre.trim().isEmpty() || nombre.contains(";")) {
            return -1; // Nombre inválido según los requisitos
        }
        
        jugadorActual = null;
        for (int i = 0; i < listaJugadores.size(); i++) {
            if (listaJugadores.get(i).getNombre().equalsIgnoreCase(nombre)) {
                jugadorActual = listaJugadores.get(i);
            }
        }
        
        int estado = 1; // 1 significa jugador nuevo
        if (jugadorActual == null) {
            jugadorActual = new Jugador(nombre, 0);
        } else {
            estado = 2; // 2 significa jugador ya existente en los registros
        }
        
        // Reiniciar variables de la sesión de juego actual
        minijuegosJugados = new boolean[4];
        puntajesMinijuegos = new int[4];
        historialSesion.clear();
        puntajeAcumuladoSesion = 0;
        
        // Dejar todos los monstruos disponibles nuevamente
        for (int i = 0; i < catalogoMonstruos.size(); i++) {
            catalogoMonstruos.get(i).setUsado(false);
        }
        return estado;
    }

    public Jugador getJugadorActual() { 
        return jugadorActual; 
    }
    
    public ArrayList<Monstruo> getCatalogoMonstruos() { 
        return catalogoMonstruos; 
    }
    
    public boolean[] getMinijuegosJugados() { 
        return minijuegosJugados; 
    }
    
    public int[] getPuntajesMinijuegos() { 
        return puntajesMinijuegos; 
    }

    // Método para calcular el puntaje de un minijuego aplicando la habilidad y multiplicador
    public int calcularYRegistrarPuntaje(int indexMinijuego, Monstruo monstruo, String habilidadUsada, String habilidadPremiada, String nombreMinijuego, double potencial) {
        double multiplicador = 1.0;
        if (habilidadUsada.equals(habilidadPremiada)) {
            multiplicador = 1.5; // Multiplicador de bonificación si la habilidad coincide
        }
        
        int puntaje = (int) Math.round(potencial * 10.0 * multiplicador);
        minijuegosJugados[indexMinijuego] = true;
        puntajesMinijuegos[indexMinijuego] = puntaje;
        monstruo.setUsado(true); // El monstruo queda marcado como usado en esta sesión
        puntajeAcumuladoSesion += puntaje;
        historialSesion.add(new HistorialItem(nombreMinijuego, monstruo.getTipo(), habilidadUsada, puntaje));
        return puntaje;
    }

    public int getPuntajeAcumuladoSesion() { 
        return puntajeAcumuladoSesion; 
    }
    
    public LinkedList<HistorialItem> getHistorialSesion() { 
        return historialSesion; 
    }

    // Verifica si el usuario ha jugado al menos un minijuego en la sesión
    public boolean haJugadoAlMenosUno() {
        for (int i = 0; i < minijuegosJugados.length; i++) {
            if (minijuegosJugados[i]) {
                return true;
            }
        }
        return false;
    }

    // Método que evalúa el término de sesión y actualiza récords si corresponde
    public String finalizarSesion() {
        if (!haJugadoAlMenosUno()) {
            return "No jugaste ningún minijuego, no se guardó registro.";
        }
        
        boolean existeEnLista = false;
        for (int i = 0; i < listaJugadores.size(); i++) {
            if (listaJugadores.get(i) == jugadorActual) {
                existeEnLista = true;
            }
        }
        
        if (!existeEnLista) {
            jugadorActual.setPuntajeMaximo(puntajeAcumuladoSesion);
            listaJugadores.add(jugadorActual);
            return "Sesión terminada. Puntaje total: " + puntajeAcumuladoSesion + " puntos.\n¡Nuevo registro guardado!";
        } else {
            int recordAnterior = jugadorActual.getPuntajeMaximo();
            if (puntajeAcumuladoSesion > recordAnterior) {
                jugadorActual.setPuntajeMaximo(puntajeAcumuladoSesion);
                return "Sesión terminada. Puntaje total: " + puntajeAcumuladoSesion + " puntos.\n¡Felicidades, superaste tu récord anterior (" + recordAnterior + ")!";
            } else {
                return "Sesión terminada. Puntaje total: " + puntajeAcumuladoSesion + " puntos.\nNo superaste tu récord (" + recordAnterior + "). ¡Suerte para la próxima!";
            }
        }
    }

    // Algoritmo de ordenamiento manual para el ranking (R7) de mayor a menor puntaje y alfabético por nombre
    public void ordenarRanking() {
        for (int i = 0; i < listaJugadores.size() - 1; i++) {
            for (int j = 0; j < listaJugadores.size() - i - 1; j++) {
                Jugador j1 = listaJugadores.get(j);
                Jugador j2 = listaJugadores.get(j + 1);
                
                boolean cambiar = false;
                if (j2.getPuntajeMaximo() > j1.getPuntajeMaximo()) {
                    cambiar = true;
                } else {
                    if (j2.getPuntajeMaximo() == j1.getPuntajeMaximo()) {
                        if (j2.getNombre().compareToIgnoreCase(j1.getNombre()) < 0) {
                            cambiar = true;
                        }
                    }
                }
                
                if (cambiar) {
                    listaJugadores.set(j, j2);
                    listaJugadores.set(j + 1, j1);
                }
            }
        }
    }

    public ArrayList<Jugador> getListaJugadores() {
        ordenarRanking();
        return listaJugadores;
    }
}