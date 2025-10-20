package tictactoe.business;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import tictactoe.data.Board;

/**
 * Almacena y aplica la heurística del juego de un bot. Los datos de los
 * jugadores no humanos se almacenan como parte de la clase Player.
 * 
 * @author Andrés López
 * @version 1.1
 */
public class BotHeuristics {
    Random r = new Random();

    /**
     * Obtiene el mejor movimiento dado el tablero actual.
     */
    public int getBestMove(Board board, int botPlayerValue) {
        int opponentPlayerValue = botPlayerValue * -1; // El oponente es el valor inverso

        // Prioridad 1: Buscar un movimiento para ganar
        int move = findWinningOrBlockingMove(board, botPlayerValue);
        if (move != -1) {
            return move;
        }

        // Prioridad 2: Buscar un movimiento para bloquear al oponente
        move = findWinningOrBlockingMove(board, opponentPlayerValue);
        if (move != -1) {
            return move;
        }

        // Prioridad 3: Tomar el centro si está disponible
        if (board.isAvailableSquare(1, 1)) {
            return 5; // La casilla 5 es el centro (índice 4)
        }

        // Prioridad 4: Tomar una esquina vacía
        ArrayList<Integer> corners = new ArrayList<>();
        if (board.isAvailableSquare(0, 0))
            corners.add(1);
        if (board.isAvailableSquare(0, 2))
            corners.add(3);
        if (board.isAvailableSquare(2, 0))
            corners.add(7);
        if (board.isAvailableSquare(2, 2))
            corners.add(9);
        if (!corners.isEmpty()) {
            Collections.shuffle(corners); // Elige una esquina al azar
            return corners.get(0);
        }

        // Prioridad 5: Tomar un lado vacío
        ArrayList<Integer> sides = new ArrayList<>();
        if (board.isAvailableSquare(0, 1))
            sides.add(2);
        if (board.isAvailableSquare(1, 0))
            sides.add(4);
        if (board.isAvailableSquare(1, 2))
            sides.add(6);
        if (board.isAvailableSquare(2, 1))
            sides.add(8);
        if (!sides.isEmpty()) {
            Collections.shuffle(sides); // Elige un lado al azar
            return sides.get(0);
        }

        // Si todo lo demás falla (no debería ocurrir en un juego normal), movimiento
        // aleatorio
        return r.nextInt(9) + 1;
    }

    /**
     * Busca una línea con dos fichas de un jugador y una casilla vacía.
     */
    private int findWinningOrBlockingMove(Board board, int player) {
        // Recorremos las 9 casillas
        for (int i = 0; i < 9; i++) {
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
        return -1; // No se encontró movimiento
    }
}
