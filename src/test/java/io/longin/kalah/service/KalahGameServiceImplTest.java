package io.longin.kalah.service;

import io.longin.kalah.model.Game;
import io.longin.kalah.model.GameStatus;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest
class KalahGameServiceImplTest {

    @Autowired
    private KalahGameService kalahGameService;

    @Test
    public void createGameTest(){
        Game game = kalahGameService.createGame();
        assertThat(game.getStatus()).isEqualTo(GameStatus.STARTED);
        assertThat(game.getBoard().getPit(0).getStones()).isEqualTo(0);
        assertNull(game.getMove());
        assertNull(game.getWinner());
    }

}