package tictactoe.business;

import java.util.ArrayList;
import java.util.Random;

import tictactoe.data.Board;

/**
 * Almacena y aplica la heurística del juego de un bot. Los datos de los
 * jugadores no humanos se almacenan como parte de la clase Player.
 * 
 * @author Andrés López
 * @version 2
 */
public class BotHeuristics {
    private final Random r;

    // Constantes para la heurística del bot
    private static final int NO_MOVE_FOUND = -1;
    private static final int TOTAL_SQUARES = 9;
    private static final int CENTER_SQUARE = 5;
    private static final int[] CORNER_SQUARES = { 1, 3, 7, 9 };
    private static final int[] SIDE_SQUARES = { 2, 4, 6, 8 };

    public BotHeuristics(Random random) {
        this.r = random;
    }

    /**
     * Obtiene el mejor movimiento dado el tablero actual.
     */
    public int getBestMove(Board board, int botPlayerValue) {
        int opponentPlayerValue = botPlayerValue * -1; // El oponente es el valor inverso

        // Prioridad 1: Buscar un movimiento para ganar
        int move = findWinningOrBlockingMove(board, botPlayerValue);
        if (move != NO_MOVE_FOUND) {
            return move;
        }

        // Prioridad 2: Buscar un movimiento para bloquear al oponente
        move = findWinningOrBlockingMove(board, opponentPlayerValue);
        if (move != NO_MOVE_FOUND) {
            return move;
        }

        // Prioridad 3: Tomar el centro si está disponible
        if (board.isAvailableSquare(CENTER_SQUARE)) {
            return CENTER_SQUARE;
        }

        // Prioridad 4: Tomar una esquina vacía
        ArrayList<Integer> availableCorners = new ArrayList<>();
        for (int corner : CORNER_SQUARES) {
            if (board.isAvailableSquare(corner)) {
                availableCorners.add(corner);
            }
        }

        // Elige una esquina al azar
        if (!availableCorners.isEmpty()) {
            return availableCorners.get(r.nextInt(availableCorners.size()));
        }

        // Prioridad 5: Tomar un lado vacío
        ArrayList<Integer> availableSides = new ArrayList<>();
        for (int side : SIDE_SQUARES) {
            if (board.isAvailableSquare(side)) {
                availableSides.add(side);
            }
        }

        if (!availableSides.isEmpty()) {
            return availableSides.get(r.nextInt(availableSides.size()));
        }

        // Si todo lo demás falla (no debería ocurrir), mueve al azar
        return r.nextInt(TOTAL_SQUARES) + 1;
    }

    /**
     * Busca un movimiento que complete una línea de tres para un jugador.
     * 
     * Esta función es la heurística principal para que el bot decida un movimiento
     * ganador (si se usa con su propio ID) o un movimiento de bloqueo (si se usa
     * con el ID del oponente).
     */
    private int findWinningOrBlockingMove(Board board, int player) {
        // Recorremos todas las casillas del tablero
        for (int i = 0; i < TOTAL_SQUARES; i++) {
            // Crea una copia del tablero para simular el movimiento
            Board boardCopy = new Board(board);

            // Si la casilla está disponible, haz el movimiento en la copia
            if (boardCopy.setSquare(player, i)) {
                // Comprueba si ese movimiento gana en la copia
                if (new WinChecker(boardCopy).winner() == player) {
                    return i + 1; // Devuelve la casilla (1-9)
                }
            }
        }

        return NO_MOVE_FOUND; // No se encontró movimiento
    }
}
