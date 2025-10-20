package tictactoe.business;

import java.util.Random;
import java.util.Scanner;

import tictactoe.data.Board;
import tictactoe.data.Player;
import tictactoe.ui.InputChecker;
import tictactoe.ui.UIUtilities;

/**
 * Controla una única partida del juego
 * 
 * @author Andrés López
 * @version 1.2
 */
public class Game {
    // Utilidades (se inicializan en el constructor para usar la misma instancia)
    private final Scanner s;
    private final Random r;
    private final InputChecker ic;
    private final BotHeuristics bh;
    private final WinChecker wc;
    private final UIUtilities uii;

    // Tablero, modo de juego y jugadores
    private final Board board;
    private final int gameMode;
    private Player player1;
    private Player player2;

    // Lista de nombres para bots
    private String[] botsNameList = { "Wall-E", "R2-D2", "Optimus Prime", "Bumblebee", "HAL 9000", "Ultron", "Skynet",
            "Deep Blue", "Watson", "Robocop"
    };

    public Game(int gameMode, Scanner scanner) {
        this.gameMode = gameMode;
        this.board = new Board();

        // Utilidades
        this.s = scanner;
        this.r = new Random();
        this.ic = new InputChecker(scanner);
        this.bh = new BotHeuristics();
        this.wc = new WinChecker(this.board);
        this.uii = new UIUtilities();
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

            // Evita que ingrese una línea vacía
            if (name.trim().isEmpty()) {
                System.out.println("| El nombre no puede estar vacío. Intenta de nuevo");
                uii.printBlankLine();
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
            botName = botsNameList[r.nextInt(botsNameList.length)];
        } while (botName.equals(existingName));

        return botName;
    }

    /**
     * Crea los dos nombres de los jugadores para la partida.
     */
    private String[] setPlayersNames() {
        String[] playerNames = new String[2];
        uii.printBar();

        switch (gameMode) {
            case 0: // Humano vs Humano
                playerNames[0] = getPlayerName(1);
                uii.printBlankLine();
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
     * Crea los jugadores a partir de los nombres.
     */
    private Player[] createPlayers(String[] playerNames) {
        // Determina si cada jugador es un bot basándose en el gameMode
        boolean isPlayer1Bot = (gameMode == 2); // El jugador 2 es bot solo en modo "Bot vs Bot"
        boolean isPlayer2Bot = (gameMode == 1 || gameMode == 2); // Bot en modos "Humano vs Bot" o "Bot vs Bot"

        // Crea a los jugadores con sus modos
        player1 = new Player(playerNames[0], isPlayer1Bot);
        player2 = new Player(playerNames[1], isPlayer2Bot);

        return new Player[] { player1, player2 };
    }

    /**
     * Juega el turno de un humano.
     */
    private void humanTurn(boolean currTurn) {
        int square;
        boolean settedSquare = false;

        // Repetimos hasta que haya movimiento o el tablero se llene
        while (!settedSquare && !board.isFull()) {
            // Ingresa la casilla desde la consola
            System.out.println("| Ingresa la casilla a jugar (1-9)");
            square = ic.getInteger(1, 9);

            // Coloca el cuadrado
            settedSquare = board.setSquare(currTurn ? 1 : -1, square - 1);

            if (!settedSquare) {
                System.out.println("| Casilla ocupada. Intenta de nuevo");
                uii.printBlankLine();
            }
        }
    }

    /**
     * Juega el turno de un bot.
     */
    private void botTurn(boolean currTurn) {
        int square;
        boolean settedSquare = false;

        // // Hacemos una pausa de 2000 +- 1000 ms para que no sea instantáneo
        // try {
        // int baseTime = 1500;
        // int variance = 1000;
        // Thread.sleep(baseTime + r.nextInt(-variance, variance));
        // } catch (InterruptedException e) {
        // Thread.currentThread().interrupt();
        // System.err.println("La pausa del juego fue interrumpida.");
        // }

        // Repetimos hasta que haya movimiento o el tablero se llene
        while (!settedSquare && !board.isFull()) {
            // Anotamos el valor del oponente para elegir la jugada del bot
            int playerValue = currTurn ? 1 : -1;

            // No validamos si la jugada se hizo porque bh.getBestMove() lo hace
            square = bh.getBestMove(board, playerValue);
            settedSquare = board.setSquare(playerValue, square - 1);
        }
    }

    /**
     * Le da inicio a la partida.
     */
    public void start() {
        String[] playerNames = setPlayersNames();
        Player[] players = createPlayers(playerNames);

        // Imprime los nombres del enfrentamiento
        System.out.printf("| %s vs %s\n", playerNames[0], playerNames[1]);
        uii.printBlankLine();

        // Alternamos los turnos con un booleano. El primer turno se asigna al azar
        boolean currTurn = r.nextBoolean();

        while (true) {
            // Imprime el tablero y el turno
            System.out.println(board.getBoardString());
            uii.printBar();
            System.out.println("| Turno de " + (playerNames[currTurn ? 0 : 1]));
            uii.printBlankLine();

            // Juega el turno dependiendo de si le toca a un humano o a un bot
            if (!players[currTurn ? 0 : 1].isBot()) {
                humanTurn(currTurn);
            } else {
                botTurn(currTurn);
            }

            // Verifica si el tablero es ganador
            int winner = wc.winner();

            // Primero, revisa si alguien ganó. Si nadie ganó, revisa si hay empate
            if (winner != 0) {
                System.out.println(board.getBoardString());
                uii.printBar();
                String winnerName = (winner == 1) ? playerNames[0] : playerNames[1];
                System.out.println("| ¡Fin del juego! el ganador es " + winnerName);
                uii.printBar();
                return;
            } else if (board.isFull()) {
                System.out.println(board.getBoardString());
                uii.printBar();
                System.out.println("| ¡Fin del juego! empate");
                uii.printBar();
                return;
            }

            // Alternamos el turno
            currTurn = !currTurn;
        }
    }
}
