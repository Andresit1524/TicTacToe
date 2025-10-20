package tictactoe.business;

import java.util.ArrayList;
import tictactoe.data.Board;

/**
 * Verifica si el tablero ya dió un ganador. El código está basado en un juego
 * n*n con n en línea, lo cual es una medida de flexibilidad para minimizar
 * errores y evitar el hardcoding.
 * 
 * @author Andrés López
 * @version 1
 */
public class WinChecker {
    private Board board;
    private int boardSize;

    public WinChecker(Board board) {
        this.board = board;
        this.boardSize = board.getBoard().length;
    }

    /**
     * Función auxiliar que suma las filas, columnas y diagonales del tablero
     */
    private ArrayList<Integer> getSums() {
        // Lista final de sumas
        ArrayList<Integer> sumsList = new ArrayList<>();

        // 1. Suma de filas
        for (int[] row : board.getBoard()) {
            int rowSum = 0;

            for (int square : row) {
                rowSum += square;
            }

            sumsList.add(rowSum);
        }

        // 2. Suma de columnas
        for (int i = 0; i < boardSize; i++) {
            int colSum = 0;

            for (int j = 0; j < boardSize; j++) {
                colSum += board.getBoard()[j][i];
            }

            sumsList.add(colSum);
        }

        // 3. Suma de la diagonal principal
        int diagonalSum = 0;
        for (int i = 0; i < boardSize; i++) {
            diagonalSum += board.getBoard()[i][i];
        }

        sumsList.add(diagonalSum);

        // 4. Suma de la diagonal secundaria
        diagonalSum = 0;
        for (int i = 0; i < boardSize; i++) {
            diagonalSum += board.getBoard()[i][boardSize - i - 1];
        }

        sumsList.add(diagonalSum);

        return sumsList;
    }

    /**
     * Verifica si el tablero ya tiene un ganador e indica quién:
     * 
     * - Retorna 1 o -1 si gano el primer o segundo jugador
     * - Retorna 0 si es empate o no hay ganador
     */
    public int winner() {
        // Si dentro de las sumas hay un 3, quiere decir que hubo tres 1s en fila
        // Entonces es victoria del jugador 1
        if (getSums().contains(boardSize)) {
            return 1;
        }

        // Si dentro de las sumas hay un -3, quiere decir que hubo tres -1s en fila
        // Entonces es victoria del jugador 2
        if (getSums().contains(-boardSize)) {
            return -1;
        }

        // Si no es nada de lo anterior, entonces es empate o partida en curso
        return 0;
    }
}
