package tictactoe.ui;

/**
 * Gestiona la interfaz del juego y las entradas del usuario
 * 
 * @author Andrés López
 * @version 1
 */
public class UIManager {
    GetImputs gi = new GetImputs();

    /**
     * Función auxiliar que imprime una barra vertical
     */
    public void printBar() {
        System.out.println("> ----------------------------------------------------------- <");
    }

    /**
     * Imprime el ancabezado del juego
     */
    public void printHeader() {
        System.out.println("> ----------------------------------------------------------- <");
        System.out.println(">                    TicTacToe - Taller 2                     <");
        System.out.println("> ----------------------------------------------------------- <");
        System.out.println(">                 Hayran Andrés López González                <");
        System.out.println(">                          Versión 1                          <");
        System.out.println("> ----------------------------------------------------------- <");
    }

    /**
     * Elige el modo de juego desde la entrada del usuario
     */
    public int chooseGameMode() {
        System.out.println("> Elige el modo de juego:");
        System.out.println("> ");
        System.out.println("> 1. Jugadores humanos");
        System.out.println("> 2. Humano contra bot");
        System.out.println("> 3. Dos bots");
        System.out.println("> ");

        // Resta 1 para corregir el desfase (los modos de juego con 0-based)
        return gi.getInteger(1, 3) - 1;
    }
}
