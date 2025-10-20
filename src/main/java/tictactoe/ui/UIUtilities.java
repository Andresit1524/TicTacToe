package tictactoe.ui;

/**
 * Utilidades de impresión para mejorar la consistencia en la consola del juego.
 * 
 * @author Andrés López
 * @version 1
 */
public class UIUtilities {
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
}
