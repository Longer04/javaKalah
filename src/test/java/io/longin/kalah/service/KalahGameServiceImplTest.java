package io.longin.kalah.service;

import io.longin.kalah.model.Game;
import io.longin.kalah.model.GameStatus;
import io.longin.kalah.model.Player;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static io.longin.kalah.constants.GameConstants.PLAYER_ONE_BASE;
import static io.longin.kalah.constants.GameConstants.PLAYER_TWO_BASE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest
class KalahGameServiceImplTest {

    @Autowired
    private KalahGameService kalahGameService;

    @Test
    public void createGameTest() {
        Game game = kalahGameService.createGame();
        assertThat(game.getStatus()).isEqualTo(GameStatus.STARTED);
        assertThat(game.getBoard().getPit(1).getStones()).isEqualTo(0);
        assertNull(game.getMove());
        assertNull(game.getWinner());
    }

    @Test
    public void shouldFetchGameWhenPlayTest() {
        Game game = kalahGameService.createGame();
        String id = game.getId();
        game.setMove(new Player(PLAYER_ONE_BASE));
        Game game1 = kalahGameService.playGame(id, 3);
        assertThat(game1.getId()).isEqualTo(game.getId());
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionForStartFromHousePitTest() {
        Game game = kalahGameService.createGame();
        String id = game.getId();
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> kalahGameService.playGame(id, 1))
                .withMessage("Can't move from house pit.");
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionForStartFromEmptyPitTest() {
        Game game = kalahGameService.createGame();
        game.getBoard().getPit(2).setStones(0);
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> kalahGameService.playGame(game.getId(), 2))
                .withMessage("Can't move from empty pit.");
    }
    @Test
    public void shouldThrowIllegalArgumentExceptionForStartFromPlayerOnePitWhenItsPlayerTwoTurnTest() {
        Game game = kalahGameService.createGame();
        game.setMove(new Player(PLAYER_TWO_BASE));
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> kalahGameService.playGame(game.getId(), 2))
                .withMessage("Opposite player turn.");
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionForStartFromPlayerTwoPitWhenItsPlayerOneTurnTest() {
        Game game = kalahGameService.createGame();
        game.setMove(new Player(PLAYER_ONE_BASE));
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> kalahGameService.playGame(game.getId(), 10))
                .withMessage("Opposite player turn.");
    }

}