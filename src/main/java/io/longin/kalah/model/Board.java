package io.longin.kalah.model;


import static io.longin.kalah.constants.GameConstants.BOARD_SIZE;

public class Board {

    private Pit[] pits;

    public Board() {
        initializePitsToBoard();
    }

    private void initializePitsToBoard(){
        this.pits = new Pit[BOARD_SIZE];
        for(int i = 0; i < BOARD_SIZE; i++){
            this.pits[i] = new Pit(i + 1);
        }
    }

    public Pit[] getPits() {
        return pits;
    }

    public Pit getPit(int index){
        return pits[(index - 1) % BOARD_SIZE];
    }
}
