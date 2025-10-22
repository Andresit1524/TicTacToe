package tictactoe.data;

/**
 * Almacena los datos del jugador y actúa como instancia del mismo para el
 * control del juego.
 * 
 * @author Andrés López
 * @version 2
 */
public class Player {
    private String name;
    private boolean isBot;

    /**
     * Constructor para jugadores humanos. Indica nombre solamente.
     */
    public Player(String name) {
        this.name = name;
        this.isBot = false;
    }

    /**
     * Constructor para (generalmente) bots.
     * Puedes indicar que es humano con isBot = false.
     */
    public Player(String name, boolean isBot) {
        this.name = name;
        this.isBot = isBot;
    }

    /**
     * Obtiene el nombre del jugador
     */
    public String getName() {
        return name;
    }

    /**
     * Obtiene el rol (humano o bot) del jugador
     */
    public boolean isBot() {
        return isBot;
    }
}
