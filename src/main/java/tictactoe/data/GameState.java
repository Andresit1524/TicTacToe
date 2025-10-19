package tictactoe.data;

/**
 * Almacena estadísticas de la partida para su consulta y uso:
 * 
 * - Nombres de los jugadores
 * - Puntos obtenidos para cada uno (si hay múltiples partidas)
 * 
 * @author Andrés López
 * @version 1
 */
public class GameState {
    private Player player1;
    private Player player2;
    private int gameMode;

    public GameState(int gameMode) {
        this.gameMode = gameMode;
    }

    /**
     * Establece el modo de juego:
     * 
     * - 0: Jugadores humanos
     * - 1: Humano contra bot
     * - 2: Dos bots
     * 
     * Puedes interpretar el número como la cantidad de bots que tiene el juego
     */

    /**
     * Establece los jugadores por sus nombres y los tipos de jugador que dicta el
     * modo de juego
     */
    public void setPlayers(String name1, String name2) {
        switch (gameMode) {
            // Humanos
            case 0:
                player1 = new Player(name1);
                player2 = new Player(name2);
                break;
            // Humano - bot
            case 1:
                player1 = new Player(name1);
                player2 = new Player(name2, gameMode);
                break;
            // Bots
            case 2:
                player1 = new Player(name1, gameMode);
                player2 = new Player(name2, gameMode);
                break;
            // Si no coincide con nada
            default:
                System.out.println("No es un modo de juego válido");
                break;
        }
    }

    public int getGameMode() {
        return gameMode;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }
}
