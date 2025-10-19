package tictactoe.business;

import tictactoe.data.Board;
import tictactoe.data.GameState;
import tictactoe.ui.UIManager;
import tictactoe.ui.GetImputs;

/**
 * Programa principal. Controla todo el flujo del juego
 * 
 * @author Andrés López
 * @version 1
 */
public class GameEngine {
    private static UIManager uim = new UIManager();
    private static GetImputs gim = new GetImputs();
    private static GameState gs;
    private static Board b;
    private static WinChecker wc;

    public static void main(String[] args) {
        uim.printHeader();
    }
}
