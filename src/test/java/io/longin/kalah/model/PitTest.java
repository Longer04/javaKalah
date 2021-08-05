package io.longin.kalah.model;

import org.junit.jupiter.api.Test;

import static io.longin.kalah.constants.GameConstants.PLAYER_ONE_BASE;
import static org.assertj.core.api.Assertions.assertThat;

class PitTest {

    @Test
    public void testCreateHousePit(){
        Pit pit = new Pit(0);
        assertThat(pit.getStones()).isEqualTo(PLAYER_ONE_BASE);
    }

    @Test
    public void testCreateNotHousePit(){
        Pit pit = new Pit(4);
        assertThat(pit.getStones()).isEqualTo(6);
    }

}