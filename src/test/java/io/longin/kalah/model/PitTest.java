package io.longin.kalah.model;

import org.junit.jupiter.api.Test;

import static io.longin.kalah.constants.GameConstants.PLAYER_ONE_BASE;
import static io.longin.kalah.constants.GameConstants.ZERO;
import static org.assertj.core.api.Assertions.assertThat;

class PitTest {

    @Test
    public void testCreateHousePit() {
        Pit pit = new Pit(PLAYER_ONE_BASE);
        assertThat(pit.getStones()).isEqualTo(ZERO);
    }

    @Test
    public void testCreateNotHousePit() {
        Pit pit = new Pit(PLAYER_ONE_BASE + 1);
        assertThat(pit.getStones()).isEqualTo(6);
    }

}