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
 * @version 2
 */
public class Board {
    public static final int EMPTY = 0;
    public static final int PLAYER_1 = 1;
    public static final int PLAYER_2 = -1;
    public static final int SIZE = 3;

    // Arreglo que almacena el tablero
    private int[][] board;

    public Board() {
        board = new int[SIZE][SIZE];
    }

    /**
     * Constructor de copia. Crea un nuevo tablero que es un clon exacto de otro.
     */
    public Board(Board other) {
        int[][] otherBoard = other.getBoard();
        int size = otherBoard.length;
        this.board = new int[size][size];
        for (int i = 0; i < size; i++) {
            System.arraycopy(otherBoard[i], 0, this.board[i], 0, size);
        }
    }

    /**
     * Evalúa si el tablero esta lleno.
     */
    public boolean isFull() {
        for (int[] row : board) {
            for (int square : row) {
                if (square == EMPTY) {
                    // Encontramos un espacio vacío, no está lleno
                    return false;
                }
            }
        }

        // No se encontraron espacios vacíos, está lleno
        return true;
    }

    /**
     * Evalúa si el recuadro seleccionado está disponible.
     */
    public boolean isAvailableSquare(int row, int col) {
        return board[row][col] == EMPTY;
    }

    /**
     * Evalúa si el recuadro seleccionado está disponible, usando el número de
     * casilla (1-9).
     */
    public boolean isAvailableSquare(int square) {
        // Convierte el número de casilla (1-9) a un índice (0-8)
        int index = square - 1;

        // Valida que el índice esté en el rango correcto
        if (index < 0 || index >= SIZE * SIZE) {
            return false;
        }

        // Convierte a coordenadas y reutiliza el método existente
        int row = index / SIZE;
        int col = index % SIZE;

        return isAvailableSquare(row, col);
    }

    /**
     * Establece el recuadro seleccionado del tablero con el valor indicado, si es
     * movimiento válido. Retorna false si el movimiento no es válido.
     */
    public boolean setSquare(int movement, int square) {
        // Calcula row y col en función de la casilla en cuestión
        int row = square / SIZE;
        int col = square % SIZE;

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
        StringBuilder boardString = new StringBuilder();

        // Recorre cada fila
        for (int row = 0; row < board.length; row++) {
            boardString.append("| | ");

            // Recorre cada elemento de la fila
            for (int square : board[row]) {
                switch (square) {
                    case EMPTY:
                        boardString.append(" ");
                        break;
                    case PLAYER_1:
                        boardString.append("O");
                        break;
                    case PLAYER_2:
                        boardString.append("X");
                        break;
                    default:
                        break;
                }

                // Espacio entre elementos (incluyendo el último, como borde)
                boardString.append(" | ");
            }

            // Salto de línea entre filas (excepto en la última)
            if (row != board.length - 1) {
                boardString.append("\n");
            }
        }

        return boardString.toString();
    }

    /**
     * Accede al tablero con sus valores numéricos.
     */
    public int[][] getBoard() {
        return board;
    }

    /**
     * Extrae la casilla seleccionada en forma de número.
     */
    public int getSquare(int row, int col) {
        return board[row][col];
    }
}
