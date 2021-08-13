package io.longin.kalah.service;

import io.longin.kalah.model.Game;
import io.longin.kalah.model.GameStatus;
import io.longin.kalah.model.Player;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static io.longin.kalah.constants.GameConstants.*;
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
        assertThat(game.getBoard().getPit(PLAYER_ONE_BASE).getStones()).isEqualTo(ZERO);
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
                .isThrownBy(() -> kalahGameService.playGame(id, PLAYER_ONE_BASE))
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
                .withMessage("Opposite player turn. Player Two Turn.");
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionForStartFromPlayerTwoPitWhenItsPlayerOneTurnTest() {
        Game game = kalahGameService.createGame();
        game.setMove(new Player(PLAYER_ONE_BASE));
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> kalahGameService.playGame(game.getId(), 10))
                .withMessage("Opposite player turn. Player One Turn.");
    }

    @Test
    public void shouldMoveStonesToPlayerOneBase() {
        Game game = kalahGameService.createGame();
        game.setMove(new Player(PLAYER_ONE_BASE));
        game.getBoard().getPit(4).setStones(2);
        game.getBoard().getPit(6).setStones(0);
        int stonesInOppositePit = game.getBoard().getPit(BOARD_SIZE - 6).getStones();

        kalahGameService.playGame(game.getId(), 4);

        assertThat(stonesInOppositePit + 1).isEqualTo(game.getBoard().getPit(PLAYER_ONE_BASE).getStones());
    }

    @Test
    public void shouldMoveStonesToNextPitsForPlayerOne() {
        Game game = kalahGameService.createGame();
        game.setMove(new Player(PLAYER_ONE_BASE));
        int startingPit = 4;
        int expectedStones = 7;
        int expectedBaseStones = 1;
        kalahGameService.playGame(game.getId(), 4);

        assertThat(game.getBoard().getPit(startingPit + 1).getStones()).isEqualTo(expectedStones);
        assertThat(game.getBoard().getPit(PLAYER_ONE_BASE).getStones()).isEqualTo(expectedBaseStones);
        assertThat(game.getBoard().getPit(startingPit + 6).getStones()).isEqualTo(expectedStones);
    }

    @Test
    public void shouldMoveStonesToNextPitsForPlayerTwo() {
        Game game = kalahGameService.createGame();
        game.setMove(new Player(PLAYER_TWO_BASE));
        int startingPit = 10;
        int expectedStones = 7;
        int expectedBaseStones = 1;
        kalahGameService.playGame(game.getId(), 10);

        assertThat(game.getBoard().getPit(startingPit + 1).getStones()).isEqualTo(expectedStones);
        assertThat(game.getBoard().getPit(PLAYER_TWO_BASE).getStones()).isEqualTo(expectedBaseStones);
        assertThat(game.getBoard().getPit(startingPit + 6).getStones()).isEqualTo(expectedStones);
    }



}