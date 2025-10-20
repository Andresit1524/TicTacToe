package tictactoe.business;

import java.util.ArrayList;

import tictactoe.data.Board;

/**
 * Verifica si el tablero ya dió un ganador. El código está basado en un juego
 * n * n con n en línea, lo cual es una medida de flexibilidad para minimizar
 * errores y evitar el hardcoding.
 * 
 * @author Andrés López
 * @version 1.2
 */
public class WinChecker {
    private Board board;
    private int boardSize;

    public WinChecker(Board board) {
        this.board = board;
        this.boardSize = board.getBoard().length;
    }

    /**
     * Función auxiliar que suma las filas, columnas y diagonales del tablero.
     */
    private ArrayList<Integer> getSums() {
        // Lista final de sumas
        ArrayList<Integer> sumsList = new ArrayList<>();

        // Sumas de cada fila
        for (int[] row : board.getBoard()) {
            // Reiniciar la suma para cada fila
            int sum = 0;

            for (int square : row) {
                sum += square;
            }

            sumsList.add(sum);
        }

        // Sumas de cada columna
        for (int i = 0; i < boardSize; i++) {
            // Reinicia la suma para cada columna
            int sum = 0;

            for (int j = 0; j < boardSize; j++) {
                sum += board.getBoard()[j][i];
            }

            sumsList.add(sum);
        }

        // Reinicia primero
        int sum = 0;

        // Suma de la diagonal principal
        for (int i = 0; i < boardSize; i++) {
            sum += board.getBoard()[i][i];
        }

        sumsList.add(sum);

        // Suma de la diagonal secundaria
        sum = 0;
        for (int i = 0; i < boardSize; i++) {
            sum += board.getBoard()[i][boardSize - i - 1];
        }

        sumsList.add(sum);

        return sumsList;
    }

    /**
     * Verifica si el tablero ya tiene un ganador e indica quién:
     * 
     * - Retorna 1 o -1 si gano el primer o segundo jugador
     * - Retorna 0 si es empate o no hay ganador
     */
    public int winner() {
        // Lista de sumas para consultar
        ArrayList<Integer> sums = getSums();

        // Si en las sumas hay un valor de n, quiere decir que hubo n 1s en fila
        // Entonces es victoria del jugador 1
        if (sums.contains(boardSize)) {
            return 1;
        }

        // Si en las sumas hay un valor de -n, quiere decir que hubo n -1s en fila
        // Entonces es victoria del jugador 2
        if (sums.contains(-boardSize)) {
            return -1;
        }

        // Si no es nada de lo anterior, entonces es empate o partida en curso
        return 0;
    }
}
