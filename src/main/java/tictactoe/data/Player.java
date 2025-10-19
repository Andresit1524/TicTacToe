package tictactoe.data;

/**
 * Almacena los datos del jugador y actúa como instancia del mismo para el
 * control del juego.
 * 
 * El modo indica si el jugador es humano o no:
 * 
 * - 0: humano
 * - 1: bot
 * 
 * @author Andrés López
 * @version 1
 */
public class Player {
    private String name;
    private int points;
    private int mode;

    /**
     * Jugador con nombre y modo (para bots)
     */
    public Player(String name, int mode) {
        this.name = name;
        this.mode = mode;
        points = 0;
    }

    /**
     * Jugador solo con nombre (para humanos)
     */
    public Player(String name) {
        this.name = name;
        mode = 0;
        points = 0;
    }

    /**
     * Añade un punto al jugador
     */
    public void addPoint() {
        points++;
    }

    /**
     * Obtiene el puntaje del jugador
     */
    public int getPoints() {
        return points;
    }

    /**
     * Establece el nombre del jugador
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Obtiene el nombre del jugador
     */
    public String getName() {
        return name;
    }

    /**
     * Establece el modo del jugador (humano o bot):
     * 
     * - 0: humano
     * - 1: bot
     */
    public void setMode(int mode) {
        this.mode = mode;
    }

    /**
     * Obtiene el modo del jugador:
     * 
     * - 0: humano
     * - 1: bot
     */
    public int getMode() {
        return mode;
    }
}
