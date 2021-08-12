package io.longin.kalah.model;


import static io.longin.kalah.constants.GameConstants.BOARD_SIZE;
import static io.longin.kalah.constants.GameConstants.PLAYER_ONE_BASE;

public class Board {

    private Pit[] pits;

    public Board() {
        initializePitsToBoard();
    }

    private void initializePitsToBoard(){
        this.pits = new Pit[BOARD_SIZE + 1];
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
