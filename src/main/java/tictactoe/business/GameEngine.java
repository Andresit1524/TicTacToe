package tictactoe.business;

import java.util.Scanner;

import tictactoe.ui.InputChecker;
import tictactoe.ui.UIUtilities;

/**
 * Programa principal. Controla todo el flujo del juego.
 * 
 * @author Andrés López
 * @version 1
 */
public class GameEngine {
    public static void main(String[] args) {
        // Se crea únicas instancias para toda la aplicación
        Scanner s = new Scanner(System.in);
        InputChecker ic = new InputChecker(s);
        UIUtilities uii = new UIUtilities();

        // Imprime el encabezado e inicio del juego
        uii.printHeader();
        uii.printInstructions();
        uii.printModesMenu();

        // Elige el modo de juego
        int choice;

        System.out.println("| Ingresa una opción");
        choice = ic.getInteger(1, 4);

        // Si elegimos salir
        if (choice == 4) {
            System.exit(0);
        }

        // Inicia la partida
        Game game = new Game(choice - 1, s);
        game.start();
    }
}
