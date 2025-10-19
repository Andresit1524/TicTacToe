package tictactoe.data;

/**
 * Almacena los elementos del tablero del juego como una lista de números
 * enteros:
 * 
 * - 0: vacío
 * - 1: jugador 1
 * - -1: jugador 2
 * 
 * @author Andrés López
 * @version 1
 */
public class Board {
    // Arreglo que almacena el tablero
    private int[][] board;
    private boolean isFull;

    public Board() {
        board = new int[3][3];
        isFull = false;
    }

    /**
     * Evalúa si el tablero esta lleno
     */
    public boolean isFull() {
        // Empieza asumiendo que esta lleno
        isFull = true;

        // Si hay un cero, entonces no está lleno
        for (int[] row : board) {
            for (int square : row) {
                if (square == 0) {
                    isFull = false;

                    // Retorna de inmediato
                    return isFull;
                }
            }
        }

        return isFull;
    }

    /**
     * Evalúa si el recuadro seleccionado está disponible
     */
    public boolean isAvailableSquare(int row, int col) {
        // Si está lleno, no hay recuadro disponible
        if (isFull) {
            return false;
        }

        // Si no está lleno, evalúa el recuadro seleccionado
        return board[row][col] == 0;
    }

    /**
     * Establece el recuadro seleccionado del tablero con el valor indicado, si es
     * movimiento válido. Retorna false si el movimiento no es válido.
     */
    public boolean setSquare(int movement, int row, int col) {
        // Retorna si el recuadro no está disponible
        if (!isAvailableSquare(row, col)) {
            return false;
        }

        // Escribe en el recuadro y retorna true
        board[row][col] = movement;

        return true;
    }

    /**
     * Convierte el tablero en una representación de string que pueda ser impresa.
     * También hace conversión del signo a O (jugador 1) o X (jugador 2).
     */
    public String getBoardString() {
        String boardString = "";

        // Recorre cada fila
        for (int[] row : board) {
            boardString += "| ";

            // Recorre cada elemento de la fila
            for (int square : row) {
                switch (square) {
                    case 0:
                        boardString += " ";
                        break;
                    case 1:
                        boardString += "O";
                        break;
                    case -1:
                        boardString += "X";
                        break;
                    default:
                        break;
                }

                // Espacio entre elementos
                boardString += " | ";
            }

            // Salto de línea entre filas
            boardString += "\n";
        }

        return boardString;
    }

    /**
     * Accede al tablero con sus valores numéricos
     */
    public int[][] getBoard() {
        return board;
    }

    /**
     * Extrae la casilla seleccionada en forma de número
     */
    public int getSquare(int row, int col) {
        return board[row][col];
    }
}
