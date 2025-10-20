package tictactoe.business;

import java.util.Scanner;
import java.util.Random;
import tictactoe.data.*;
import tictactoe.ui.*;

/**
 * Controla una única partida del juego
 * 
 * @author Andrés López
 * @version 1
 */

public class Game {
    private final Scanner s;
    private final Random r;
    private final InputChecker ic;
    private final BotHeuristics bh;
    private final WinChecker wc;

    private Player player1;
    private Player player2;
    private Board board;
    private int gameMode;

    // Lista de nombres para bots
    private String[] botsNameList = { "Manuel", "Alfredo", "Roberto", "Carlos", "Luis", "Sofía", "Ana", "María",
            "Lucía", "Pedro", "Diego", "Elena", "Paula", "Jorge", "Miguel" };

    public Game(int gameMode, Scanner scanner) {
        this.gameMode = gameMode;
        this.board = new Board();

        // Inicializa todos los componentes para esta instancia del juego
        this.s = scanner;
        this.r = new Random();
        this.ic = new InputChecker(scanner);
        this.bh = new BotHeuristics();
        this.wc = new WinChecker(this.board);
    }

    /**
     * Función auxiliar que imprime una línea vacía con el marco que tendrá en
     * consola
     */
    private void printBlankLine() {
        System.out.println("| ");
    }

    /**
     * Función auxiliar que imprime la barra vertical
     */
    private void printBar() {
        System.out.println("+------------------------------------------------------------+");
    }

    /**
     * Función auxiliar que le pide un nombre al usuario
     */
    private String getPlayerName(int n) {
        String name;
        do {
            System.out.println("| Ingresa el nombre del jugador " + n);
            System.out.printf("| > ");
            name = s.nextLine();

            if (name.trim().isEmpty()) {
                System.out.println("| El nombre no puede estar vacío. Intenta de nuevo");
                printBlankLine();
            }
        } while (name.trim().isEmpty());

        return name;
    }

    /**
     * Función auxiliar para obtener un nombre de bot aleatorio, asegurándose de que
     * no se repita con un nombre dado.
     */
    private String getBotName(String existingName) {
        String botName;

        // Repite hasta obtener una versión válida
        do {
            botName = botsNameList[r.nextInt(botsNameList.length)] + " " + botsNameList[r.nextInt(botsNameList.length)];
        } while (botName.equals(existingName));

        return botName;
    }

    /**
     * Crea los dos nombres de los jugadores para la partida
     */
    private String[] setPlayersNames() {
        String[] playerNames = new String[2];
        printBar();

        switch (gameMode) {
            case 0: // Humano vs Humano
                playerNames[0] = getPlayerName(1);
                printBlankLine();
                playerNames[1] = getPlayerName(2);
                break;
            case 1: // Humano vs Bot
                playerNames[0] = getPlayerName(1);
                playerNames[1] = getBotName(playerNames[0]);
                break;
            case 2: // Bot vs Bot
                playerNames[0] = getBotName(null);
                playerNames[1] = getBotName(playerNames[0]);
                break;
            default:
                // Esto no debería ocurrir, pero evitará erorres
                System.err.println("Modo de juego inválido: " + gameMode);
                playerNames[0] = "Jugador1";
                playerNames[1] = "Jugador2";
                break;
        }
        return playerNames;
    }

    /**
     * Crea los jugadores a partir de los nombres
     */
    private Player[] createPlayers(String[] playerNames) {
        // Determina si cada jugador es un bot basándose en el gameMode
        boolean isPlayer1Bot = (gameMode == 2); // Solo bot en modo Bot vs Bot
        boolean isPlayer2Bot = (gameMode == 1 || gameMode == 2); // Bot en modo Humano vs Bot o Bot vs Bot

        player1 = new Player(playerNames[0], isPlayer1Bot);
        player2 = new Player(playerNames[1], isPlayer2Bot);
        return new Player[] { player1, player2 };
    }

    /**
     * Le da inicio a la partida
     */
    public void start() {
        String[] playerNames = setPlayersNames();
        Player[] players = createPlayers(playerNames);

        // Alternamos los turnos con un valor booleano
        boolean currTurn = true;
        while (true) {
            System.out.println(board.getBoardString());
            printBar();
            System.out.println("| Turno de " + (playerNames[currTurn ? 0 : 1]));
            printBlankLine();

            // Turno del jugador humano o bot (1: jugador 1, -1, jugador 2)
            // El turno se repite hasta que sea válido y mientras el tablero no esté lleno
            int square;
            boolean settedSquare;
            if (!players[currTurn ? 0 : 1].isBot()) {
                // Turno del jugador humano
                while (!board.isFull()) {
                    System.out.println("| Ingresa la casilla a jugar (1-9)");
                    square = ic.getInteger(1, 9);
                    settedSquare = board.setSquare(currTurn ? 1 : -1, square - 1);

                    if (settedSquare) {
                        break;
                    } else {
                        System.out.println("| Casilla ocupada. Intenta de nuevo");
                        printBlankLine();
                    }
                }
            } else {
                // Turno del jugador bot
                while (!board.isFull()) {
                    square = bh.getBestMove(board);
                    settedSquare = board.setSquare(currTurn ? 1 : -1, square - 1);

                    if (settedSquare) {
                        break;
                    }
                }
            }

            // Verifica si el tablero es ganador
            int winner = wc.winner();

            // Primero, revisa si alguien ganó. Si nadie ganó, revisa si hay empate
            if (winner != 0) {
                System.out.println(board.getBoardString());
                printBar();
                String winnerName = (winner == 1) ? playerNames[0] : playerNames[1];
                System.out.println("| ¡Fin del juego! el ganador es " + winnerName);
                printBar();
                return;
            } else if (board.isFull()) {
                System.out.println(board.getBoardString());
                printBar();
                System.out.println("| ¡Fin del juego! empate");
                printBar();
                return;
            }

            // Alternamos el turno
            currTurn = !currTurn;
        }
    }
}
