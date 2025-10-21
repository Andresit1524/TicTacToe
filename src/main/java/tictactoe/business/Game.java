package tictactoe.business;

import java.util.Random;

import tictactoe.data.Board;
import tictactoe.data.Player;

/**
 * Controla el estado de una única partida del juego. No tiene lógica de UI.
 * 
 * @author Andrés López
 * @version 2.0
 */
public class Game {
    private final Board board;
    private final int gameMode;
    private Player player1;
    private Player player2;
    private boolean isPlayer1Turn;
    private final Random r = new Random();
    private final WinChecker wc;

    private final String[] botsNameList = { "Wall-E", "R2-D2", "Optimus Prime", "Bumblebee", "HAL 9000", "Ultron", "Skynet",
            "Deep Blue", "Watson", "Robocop"
    };

    public Game(int gameMode) {
        this.gameMode = gameMode;
        this.board = new Board();
        this.wc = new WinChecker(this.board);
    }

    public void createPlayers(String name1, String name2) {
        boolean isPlayer1Bot = (gameMode == 2); // Bot only in Bot vs Bot mode
        boolean isPlayer2Bot = (gameMode == 1 || gameMode == 2); // Bot in Human vs Bot or Bot vs Bot

        this.player1 = new Player(name1, isPlayer1Bot);
        this.player2 = new Player(name2, isPlayer2Bot);
    }

    public String generateBotName(String existingName) {
        String botName;
        do {
            botName = botsNameList[r.nextInt(botsNameList.length)];
        } while (botName.equals(existingName));
        return botName;
    }

    public void decideFirstTurn() {
        this.isPlayer1Turn = r.nextBoolean();
    }

    public Player getCurrentPlayer() {
        return isPlayer1Turn ? player1 : player2;
    }

    public boolean makeMove(int square) {
        int playerValue = isPlayer1Turn ? 1 : -1;
        return board.setSquare(playerValue, square - 1);
    }

    public void nextTurn() {
        isPlayer1Turn = !isPlayer1Turn;
    }

    public int getWinner() {
        return wc.winner();
    }

    public boolean isDraw() {
        return board.isFull() && wc.winner() == 0;
    }

    public boolean isOver() {
        return wc.winner() != 0 || board.isFull();
    }

    public Board getBoard() {
        return board;
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