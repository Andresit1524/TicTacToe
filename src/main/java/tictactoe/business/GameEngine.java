package tictactoe.business;

import java.util.Scanner;

import tictactoe.data.Player;
import tictactoe.ui.InputChecker;
import tictactoe.ui.UIUtilities;

/**
 * Programa principal. Controla todo el flujo del juego.
 * 
 * @author Andrés López
 * @version 2.0
 */
public class GameEngine {
    public static void main(String[] args) {
        // 1. Inicialización de componentes
        Scanner s = new Scanner(System.in);
        InputChecker ic = new InputChecker(s);
        UIUtilities uii = new UIUtilities(s);
        BotHeuristics bh = new BotHeuristics();

        // 2. Menú principal y configuración del juego
        uii.printHeader();
        uii.printInstructions();
        uii.printModesMenu();

        System.out.println("| Ingresa una opción");
        int choice = ic.getInteger(1, 4);

        if (choice == 4) {
            System.exit(0);
        }

        Game game = new Game(choice - 1);

        // 3. Creación de jugadores
        String name1, name2;
        int gameMode = game.getGameMode();
        uii.printBar();

        switch (gameMode) {
            case 0: // Humano vs Humano
                name1 = uii.getPlayerName(1);
                uii.printBlankLine();
                name2 = uii.getPlayerName(2);
                break;
            case 1: // Humano vs Bot
                name1 = uii.getPlayerName(1);
                name2 = game.generateBotName(name1);
                break;
            case 2: // Bot vs Bot
            default:
                name1 = game.generateBotName(null);
                name2 = game.generateBotName(name1);
                break;
        }
        game.createPlayers(name1, name2);
        game.decideFirstTurn();

        System.out.printf("| %s (O) vs %s (X)\n", game.getPlayer1().getName(), game.getPlayer2().getName());
        uii.printBlankLine();

        // 4. Bucle principal del juego
        while (true) {
            Player currentPlayer = game.getCurrentPlayer();

            // Imprime tablero y turno
            System.out.println(uii.getBoardString(game.getBoard()));
            uii.printBar();
            System.out.println("| Turno de " + currentPlayer.getName());
            uii.printBlankLine();

            int square;
            // Decide si el turno es de un humano o un bot
            if (currentPlayer.isBot()) {
                // Pausa para simular que el bot "piensa"
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                int playerValue = currentPlayer == game.getPlayer1() ? 1 : -1;
                square = bh.getBestMove(game.getBoard(), playerValue);
                System.out.println("| El bot juega en la casilla " + square);

            } else {
                System.out.println("| Ingresa la casilla a jugar (1-9)");
                square = ic.getInteger(1, 9);
            }

            // Intenta hacer el movimiento
            if (!game.makeMove(square)) {
                System.out.println("| Casilla ocupada. Intenta de nuevo");
                uii.printBlankLine();
                continue; // Repite el turno del mismo jugador
            }

            // Revisa si el juego ha terminado
            if (game.isOver()) {
                System.out.println(uii.getBoardString(game.getBoard()));
                uii.printBar();
                int winner = game.getWinner();
                if (winner != 0) {
                    String winnerName = (winner == 1) ? game.getPlayer1().getName() : game.getPlayer2().getName();
                    System.out.println("| ¡Fin del juego! El ganador es " + winnerName);
                } else {
                    System.out.println("| ¡Fin del juego! Empate");
                }
                uii.printBar();
                break; // Sale del bucle while
            }

            // Pasa al siguiente turno
            game.nextTurn();
        }
    }
}