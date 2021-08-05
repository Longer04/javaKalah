package io.longin.kalah.model;


public class Board {

    public final static int PLAYER_ONE_BASE = 0;
    public final static int PLAYER_TWO_BASE = 7;
    public final static int BOARD_SIZE = 14;

    private Pit[] pits;

    public Board() {
        initializePitsToBoard();
    }

    private void initializePitsToBoard(){
        this.pits = new Pit[BOARD_SIZE];
        for(int i = PLAYER_ONE_BASE; i <= BOARD_SIZE - 1; i++){
            this.pits[i] = new Pit(i);
        }
    }

    public Pit[] getPits() {
        return pits;
    }

    public Pit getPit(int index){
        return pits[index];
    }
}
