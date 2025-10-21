package tictactoe.ui;

import java.util.Scanner;

import tictactoe.data.Board;

/**
 * Utilidades de impresión para mejorar la consistencia en la consola del juego.
 * 
 * @author Andrés López
 * @version 1
 */
public class UIUtilities {
    private final Scanner s;

    public UIUtilities(Scanner scanner) {
        this.s = scanner;
    }

    /**
     * Imprime una línea vacía con el marco que tendrá en consola.
     */
    public void printBlankLine() {
        System.out.println("| ");
    }

    /**
     * Imprime una barra horizontal.
     */
    public void printBar() {
        System.out.println("+------------------------------------------------------------+");
    }

    /**
     * Imprime el encabezado del juego.
     */
    public void printHeader() {
        System.out.println("+------------------------------------------------------------+");
        System.out.println("|                     TicTacToe (v1.1.5)                     |");
        System.out.println("+------------------------------------------------------------+");
        System.out.println("|              por Hayran Andrés López González              |");
        System.out.println("+------------------------------------------------------------+");
    }

    /**
     * Imprime las instrucciones del juego.
     */
    public void printInstructions() {
        System.out.println("|                       Instrucciones:                       |");
        System.out.println("|                                                            |");
        System.out.println("| 1. Elige el modo de juego (1-4)                            |");
        System.out.println("| 2. Ingresa el nombre de los jugadores humanos (si los hay) |");
        System.out.println("| 3. El primer turno se elige el azar                        |");
        System.out.println("| 4. Juega indicando la casilla a jugar (1-9)                |");
        System.out.println("| 5. Gana el primero en hacer 3 en raya                      |");
        System.out.println("+------------------------------------------------------------+");
    }

    /**
     * Imprime los modos de juego a elegir.
     */
    public void printModesMenu() {
        System.out.println("|          Selecciona el modo de juego que prefieras         |");
        System.out.println("|                                                            |");
        System.out.println("| 1. Humano vs Humano                                        |");
        System.out.println("| 2. Humano vs Bot                                           |");
        System.out.println("| 3. Bot vs Bot                                              |");
        System.out.println("| 4. Salir                                                   |");
        System.out.println("+------------------------------------------------------------+");
    }

    /**
     * Función auxiliar que le pide un nombre al usuario
     */
    public String getPlayerName(int n) {
        String name;
        do {
            System.out.println("| Ingresa el nombre del jugador " + n);
            System.out.printf("| > ");
            name = s.nextLine();

            // Evita que ingrese una línea vacía
            if (name.trim().isEmpty()) {
                System.out.println("| El nombre no puede estar vacío. Intenta de nuevo");
                printBlankLine();
            }
        } while (name.trim().isEmpty());

        return name;
    }

    /**
     * Convierte el tablero en una representación de string que pueda ser impresa.
     * También hace conversión del signo a O (jugador 1) o X (jugador 2).
     */
    public String getBoardString(Board board) {
        String boardString = "";
        int[][] boardData = board.getBoard();

        // Recorre cada fila
        for (int row = 0; row < boardData.length; row++) {
            boardString += "| | ";

            // Recorre cada elemento de la fila
            for (int square : boardData[row]) {
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

                // Espacio entre elementos (incluyendo el último, como borde)
                boardString += " | ";
            }

            // Salto de línea entre filas (excepto en la última)
            if (row != boardData.length - 1) {
                boardString += "\n";
            }
        }

        return boardString;
    }
}