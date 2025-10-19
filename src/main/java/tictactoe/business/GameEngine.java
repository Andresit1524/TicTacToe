package tictactoe.business;

import tictactoe.data.GameState;
import tictactoe.ui.UIManager;

/**
 * Programa principal. Controla todo el flujo del juego
 * 
 * @author Andrés López
 * @version 1
 */
public class GameEngine {
    private static UIManager uim = new UIManager();
    private static GameState gs;

    public static void main(String[] args) {
        // Imprime el encabezado del juego
        uim.printHeader();

        // Elige el modo de juego
        int gameMode = uim.chooseGameMode();

        // Crea la instancia de estado del juego
        gs = new GameState(gameMode);

        // Instancia a los nombres de los dos jugadores
        uim.printBar();

    }
}
