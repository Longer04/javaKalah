package io.longin.kalah.model;


import static io.longin.kalah.constants.GameConstants.*;

public class Pit {

    private int id;
    private int stones;

    public Pit(int id) {
        this.id = id;
        fillPit(id);
    }

    private void fillPit(final int id) {
        if (id != PLAYER_ONE_BASE && id != PLAYER_TWO_BASE) {
            this.stones = INITIAL_AMOUNT_OF_STONES;
        } else {
            this.stones = 0;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStones() {
        return stones;
    }

    public void setStones(int stones) {
        this.stones = stones;
    }
}
