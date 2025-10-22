package tictactoe.ui;

import tictactoe.business.Game;

/**
 * Utilidades de impresión para mejorar la consistencia en la consola del juego.
 * 
 * @author Andrés López
 * @version 2
 */
public class UIUtilities {
    // Lista de nombres para bots
    public String[] botsNameList = { "Wall-E", "R2-D2", "Optimus Prime", "Bumblebee", "HAL 9000", "Ultron", "Skynet",
            "Deep Blue", "Watson", "Robocop"
    };

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
     * Imprime el tablero de posiciones para que el usuario sepa como ingresar las
     * jugadas.
     */
    public void printBoardInstructions() {
        System.out.println("|              Posiciones de las casillas (1-9)              |");
        System.out.println("|                                                            |");
        System.out.println("|                       | 1 | 2 | 3 |                        |");
        System.out.println("|                       | 4 | 5 | 6 |                        |");
        System.out.println("|                       | 7 | 8 | 9 |                        |");
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
     * Función auxiliar para obtener un nombre de bot aleatorio, asegurándose de que
     * no se repita con un nombre dado.
     */
    public String getBotName(Game game, String existingName) {
        String botName;

        // Repite hasta obtener una versión válida
        do {
            botName = botsNameList[game.r.nextInt(botsNameList.length)];
        } while (botName.equals(existingName));

        return botName;
    }

    /**
     * Función auxiliar que le pide un nombre al usuario
     */
    public String getPlayerName(Game game, int n) {
        String name;
        do {
            System.out.println("| Ingresa el nombre del jugador " + n);
            System.out.printf("| > ");
            name = game.s.nextLine();

            // Evita que ingrese una línea vacía
            if (name.trim().isEmpty()) {
                System.out.println("| El nombre no puede estar vacío. Intenta de nuevo");
                printBlankLine();
            }
        } while (name.trim().isEmpty());

        return name;
    }
}
