package io.longin.kalah.model;


import static io.longin.kalah.constants.GameConstants.*;

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

    public int getStonesFromNonHousePits(final Player player){
        int stones = 0;
        if(player.equals(Player.PLAYER_ONE)){
            for(int i = 1; i < PLAYER_ONE_BASE; i++){
                stones += this.pits[i - 1].getStones();
            }
        }else{
            for(int i = PLAYER_ONE_BASE + 1; i < PLAYER_TWO_BASE; i++){
                stones += this.pits[i - 1].getStones();
            }
        }
        return stones;
    }
}
