package io.longin.kalah.model;

import org.junit.jupiter.api.Test;

import static io.longin.kalah.constants.GameConstants.BOARD_SIZE;
import static org.assertj.core.api.Assertions.assertThat;

class BoardTest {

    @Test
    public void testBoardCreatedSuccessfully() {
        Board board = new Board();
        assertThat(board.getPits().length).isEqualTo(BOARD_SIZE);
    }

    @Test
    public void getStonesFromPits() {
        Board board = new Board();
        final int stones = board.getStonesFromNonHousePits(Player.PLAYER_ONE);
        assertThat(stones).isEqualTo(36);
    }

}