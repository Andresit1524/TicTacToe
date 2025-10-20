package tictactoe.business;

import java.util.Scanner;

import tictactoe.ui.InputChecker;

/**
 * Programa principal. Controla todo el flujo del juego.
 * 
 * @author Andrés López
 * @version 1
 */
public class GameEngine {
    private static void printHeader() {
        System.out.println("+------------------------------------------------------------+");
        System.out.println("|                       TicTacToe (v1)                       |");
        System.out.println("+------------------------------------------------------------+");
        System.out.println("|              por Hayran Andrés López González              |");
        System.out.println("+------------------------------------------------------------+");
        System.out.println("| Selecciona el modo de juego que prefieras                  |");
        System.out.println("|                                                            |");
        System.out.println("| 1. Humano vs Humano                                        |");
        System.out.println("| 2. Humano vs Bot                                           |");
        System.out.println("| 3. Bot vs Bot                                              |");
        System.out.println("| 4. Salir                                                   |");
        System.out.println("+------------------------------------------------------------+");
    }

    public static void main(String[] args) {
        // Se crea una única instancia de Scanner para toda la aplicación
        Scanner scanner = new Scanner(System.in);
        InputChecker ic = new InputChecker(scanner);

        printHeader();

        // Elige el modo de juego
        int choice;

        System.out.println("| Ingresa una opción");
        choice = ic.getInteger(1, 4);

        // Si elegimos salir
        if (choice == 4) {
            System.exit(0);
        }

        // Inicia la partida
        Game game = new Game(choice - 1, scanner);
        game.start();
    }
}
