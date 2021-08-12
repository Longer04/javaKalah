package io.longin.kalah.repository;

import io.longin.kalah.model.Game;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
class KalahGameRepositoryTest {

    @Autowired
    KalahGameRepository kalahGameRepository;

    @Test
    public void gameIsSavedTest(){
        final Game game = kalahGameRepository.save(new Game());
        assertNotNull(game);
    }

    @Test
    public void gameIsPlayedTest(){
        final Game game = kalahGameRepository.save(new Game());
        final Game playedGame = kalahGameRepository.getGame(game.getId());
        assertNotNull(playedGame);
        assertThat(game).isEqualTo(playedGame);
    }

}