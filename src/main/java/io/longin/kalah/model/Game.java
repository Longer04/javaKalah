package io.longin.kalah.model;

import java.util.UUID;


public class Game {


    private String id;
    private GameStatus status;
    private Board board;
    private Player move;
    private Player winner;

    public Game() {
        this.id = UUID.randomUUID().toString();
        this.board = new Board();
        this.status = GameStatus.STARTED;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Player getMove() {
        return move;
    }

    public void setMove(Player move) {
        this.move = move;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }
}
