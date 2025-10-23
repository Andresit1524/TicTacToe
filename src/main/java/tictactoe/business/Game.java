package tictactoe.business;

import java.util.Random;
import java.util.Scanner;

import tictactoe.data.Board;
import tictactoe.data.Player;
import tictactoe.ui.InputChecker;
import tictactoe.ui.UIUtilities;

/**
 * Controla una única partida del juego.
 * 
 * @author Andrés López
 * @version 2
 */
public class Game {
    // Utilidades (se inicializan en el constructor para usar la misma instancia)
    public final Scanner s;
    public final Random r;
    private final InputChecker ic;
    private final BotHeuristics bh;
    private final WinChecker wc;
    private final UIUtilities uii;

    // Tablero, modo de juego y jugadores
    private final Board board;
    private final int gameMode;
    private Player player1;
    private Player player2;

    public Game(int gameMode, Scanner scanner, Random random) {
        this.gameMode = gameMode;
        this.board = new Board();

        // Utilidades
        this.s = scanner;
        this.r = random;
        this.ic = new InputChecker(scanner);
        this.bh = new BotHeuristics(r);
        this.wc = new WinChecker(this.board);
        this.uii = new UIUtilities();
    }

    /**
     * Crea los dos nombres de los jugadores para la partida.
     */
    private String[] setPlayersNames() {
        String[] playerNames = new String[2];
        uii.printBar();

        switch (gameMode) {
            case 0: // Humano vs Humano
                playerNames[0] = uii.getPlayerName(this, 1);
                uii.printBlankLine();
                playerNames[1] = uii.getPlayerName(this, 2);
                break;
            case 1: // Humano vs Bot
                playerNames[0] = uii.getPlayerName(this, 1);
                playerNames[1] = uii.getBotName(this, playerNames[0]);
                break;
            case 2: // Bot vs Bot
                playerNames[0] = uii.getBotName(this, null);
                playerNames[1] = uii.getBotName(this, playerNames[0]);
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
    private void createPlayers(String[] playerNames) {
        // Determina si cada jugador es un bot basándose en el gameMode
        boolean isPlayer1Bot = (gameMode == 2); // El jugador 2 es bot solo en modo "Bot vs Bot"
        boolean isPlayer2Bot = (gameMode == 1 || gameMode == 2); // Bot en modos "Humano vs Bot" o "Bot vs Bot"

        // Crea a los jugadores con sus modos
        player1 = new Player(playerNames[0], isPlayer1Bot);
        player2 = new Player(playerNames[1], isPlayer2Bot);
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
            uii.printBlankLine();

            // Coloca el cuadrado
            settedSquare = board.setSquare(currTurn ? Board.PLAYER_1 : Board.PLAYER_2, square - 1);

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

        // Hacemos una pausa de 1500 +- 1000 ms para que el bot no sea instantáneo
        try {
            int baseTime = 1500;
            int variance = 1000;
            Thread.sleep(baseTime + r.nextInt(-variance, variance));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("La pausa del juego fue interrumpida.");
        }

        // Repetimos hasta que haya movimiento o el tablero se llene
        while (!settedSquare && !board.isFull()) {
            // Anotamos el valor del oponente para elegir la jugada del bot
            int playerValue = currTurn ? Board.PLAYER_1 : Board.PLAYER_2;

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
        createPlayers(playerNames);

        // Si el modo de juego involucra a un humano, ponemos línea en blanco
        if (gameMode == 0 || gameMode == 1) {
            uii.printBlankLine();
        }

        // Imprime los nombres del enfrentamiento
        System.out.printf("| %s vs %s\n", playerNames[0], playerNames[1]);

        // Alternamos los turnos con un booleano. El primer turno se asigna al azar
        boolean currTurn = r.nextBoolean();

        while (true) {
            // Imprime el tablero y el turno
            uii.printBar();
            System.out.println("| Turno de " + (playerNames[currTurn ? 0 : 1]));
            uii.printBlankLine();

            // Juega el turno dependiendo de si le toca a un humano o a un bot
            if (!(currTurn ? player1 : player2).isBot()) {
                humanTurn(currTurn);
            } else {
                botTurn(currTurn);
            }

            // Imprimimos el tablero
            System.out.println(board.getBoardString());

            // Verifica si el tablero es ganador
            int winner = wc.winner();

            // Primero, revisa si alguien ganó. Si nadie ganó, revisa si hay empate
            if (winner != Board.EMPTY) {
                String winnerName = (winner == Board.PLAYER_1) ? playerNames[0] : playerNames[1];
                uii.printBar();
                System.out.println("| ¡Fin del juego! el ganador es " + winnerName);
                uii.printBar();
                return;
            } else if (board.isFull()) {
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
