package io.longin.kalah.model;


import static io.longin.kalah.constants.GameConstants.INITIAL_AMOUNT_OF_STONES;
import static io.longin.kalah.model.Board.PLAYER_ONE_BASE;
import static io.longin.kalah.model.Board.PLAYER_TWO_BASE;

public class Pit {

    private long id;
    private int stones;

    public Pit(int id) {
        this.id = id;
        fillPit(id);
    }

    private void fillPit(final int id){
        if(id != PLAYER_ONE_BASE && id != PLAYER_TWO_BASE){
            this.stones = INITIAL_AMOUNT_OF_STONES;
        }else{
            this.stones = 0;
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getStones() {
        return stones;
    }

    public void setStones(int stones) {
        this.stones = stones;
    }
}
