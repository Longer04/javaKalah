package io.longin.kalah.service;

import io.longin.kalah.model.Game;
import io.longin.kalah.model.GameStatus;
import io.longin.kalah.model.Pit;
import io.longin.kalah.model.Player;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static io.longin.kalah.constants.GameConstants.*;
import static org.assertj.core.api.Assertions.*;
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
        game.setMove(Player.PLAYER_ONE);
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
        game.setMove(Player.PLAYER_TWO);
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> kalahGameService.playGame(game.getId(), 2))
                .withMessage("Opposite player turn. Player Two Turn.");
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionForStartFromPlayerTwoPitWhenItsPlayerOneTurnTest() {
        Game game = kalahGameService.createGame();
        game.setMove(Player.PLAYER_ONE);
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> kalahGameService.playGame(game.getId(), 10))
                .withMessage("Opposite player turn. Player One Turn.");
    }

    @Test
    public void shouldMoveStonesToPlayerOneBaseTest() {
        Game game = kalahGameService.createGame();
        game.setMove(Player.PLAYER_ONE);
        game.getBoard().getPit(4).setStones(2);
        game.getBoard().getPit(6).setStones(0);
        int stonesInOppositePit = game.getBoard().getPit(BOARD_SIZE - 6).getStones();

        kalahGameService.playGame(game.getId(), 4);

        assertThat(stonesInOppositePit + 1).isEqualTo(game.getBoard().getPit(PLAYER_ONE_BASE).getStones());
    }

    @Test
    public void shouldMoveStonesToNextPitsForPlayerOneTest() {
        Game game = kalahGameService.createGame();
        game.setMove(Player.PLAYER_ONE);
        int startingPit = 4;
        int expectedStones = 7;
        int expectedBaseStones = 1;
        kalahGameService.playGame(game.getId(), startingPit);

        assertThat(game.getBoard().getPit(startingPit + 1).getStones()).isEqualTo(expectedStones);
        assertThat(game.getBoard().getPit(PLAYER_ONE_BASE).getStones()).isEqualTo(expectedBaseStones);
        assertThat(game.getBoard().getPit(startingPit + 6).getStones()).isEqualTo(expectedStones);
    }

    @Test
    public void shouldMoveStonesToNextPitsForPlayerTwoTest() {
        Game game = kalahGameService.createGame();
        game.setMove(Player.PLAYER_TWO);
        int startingPit = 10;
        int expectedStones = 7;
        int expectedBaseStones = 1;
        kalahGameService.playGame(game.getId(), startingPit);

        assertThat(game.getBoard().getPit(startingPit + 1).getStones()).isEqualTo(expectedStones);
        assertThat(game.getBoard().getPit(PLAYER_TWO_BASE).getStones()).isEqualTo(expectedBaseStones);
        assertThat(game.getBoard().getPit(startingPit + 6).getStones()).isEqualTo(expectedStones);
    }

    @Test
    public void shouldChangePlayerTurnFromPlayerOneToPlayerTwoTest() {
        Game game = kalahGameService.createGame();
        game.setMove(Player.PLAYER_TWO);
        int startingPit = 10;
        int expectedStones = 7;
        int expectedBaseStones = 1;
        kalahGameService.playGame(game.getId(), startingPit);

        assertThat(game.getBoard().getPit(startingPit + 1).getStones()).isEqualTo(expectedStones);
        assertThat(game.getBoard().getPit(PLAYER_TWO_BASE).getStones()).isEqualTo(expectedBaseStones);
        assertThat(game.getBoard().getPit(startingPit + 6).getStones()).isEqualTo(expectedStones);
        assertThat(game.getMove().getBasePitIndex()).isEqualTo(PLAYER_ONE_BASE);
    }

    @Test
    public void shouldChangePlayerTurnFromPlayerTwoToPlayerOneTest() {
        Game game = kalahGameService.createGame();
        game.setMove(Player.PLAYER_ONE);
        int startingPit = 2;
        int expectedStones = 7;
        int expectedBaseStones = 1;
        kalahGameService.playGame(game.getId(), startingPit);

        assertThat(game.getBoard().getPit(startingPit + 1).getStones()).isEqualTo(expectedStones);
        assertThat(game.getBoard().getPit(PLAYER_ONE_BASE).getStones()).isEqualTo(expectedBaseStones);
        assertThat(game.getBoard().getPit(startingPit + 4).getStones()).isEqualTo(expectedStones);
        assertThat(game.getMove().getBasePitIndex()).isEqualTo(PLAYER_TWO_BASE);
    }

    @Test
    public void shouldRepeatSamePlayerTurnForPlayerOneTest() {
        Game game = kalahGameService.createGame();
        game.setMove(Player.PLAYER_ONE);
        game.getBoard().getPit(5).setStones(2);
        int startingPit = 5;
        int expectedStones = 7;
        int expectedBaseStones = 1;
        kalahGameService.playGame(game.getId(), startingPit);

        assertThat(game.getBoard().getPit(startingPit).getStones()).isEqualTo(ZERO);
        assertThat(game.getBoard().getPit(PLAYER_ONE_BASE).getStones()).isEqualTo(expectedBaseStones);
        assertThat(game.getBoard().getPit(startingPit + 1).getStones()).isEqualTo(expectedStones);
        assertThat(game.getMove().getBasePitIndex()).isEqualTo(PLAYER_ONE_BASE);
    }

    @Test
    public void shouldRepeatSamePlayerTurnWithManyStonesForPlayerTwoTest() {
        Game game = kalahGameService.createGame();
        game.setMove(Player.PLAYER_TWO);
        game.getBoard().getPit(10).setStones(17);
        int startingPit = 10;
        int expectedStones = 8;
        int expectedBaseStones = 2;
        kalahGameService.playGame(game.getId(), startingPit);
        assertThat(game.getBoard().getPit(startingPit + 1).getStones()).isEqualTo(expectedStones);
        assertThat(game.getBoard().getPit(PLAYER_TWO_BASE).getStones()).isEqualTo(expectedBaseStones);
        assertThat(game.getBoard().getPit(PLAYER_ONE_BASE).getStones()).isEqualTo(ZERO);
        assertThat(game.getBoard().getPit(PLAYER_TWO_BASE + 1).getStones()).isEqualTo(7);
        assertThat(game.getBoard().getPit(startingPit + 6).getStones()).isEqualTo(7);
        assertThat(game.getMove().getBasePitIndex()).isEqualTo(PLAYER_TWO_BASE);
    }

    @Test
    public void shouldThrowExceptionWhenPlayingFinishedGameTest() {
        Game game = kalahGameService.createGame();
        game.setStatus(GameStatus.FINISHED);
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> kalahGameService.playGame(game.getId(), 2))
                .withMessage("Game is already finished.");
    }

    @Test
    public void shouldChangeGameStatusWhenFinishedGameTest() {
        Game game = prepareGameWithEmptyPits();
        game.setMove(Player.PLAYER_TWO);
        game.getBoard().getPit(PLAYER_ONE_BASE).setStones(17);
        game.getBoard().getPit(13).setStones(1);
        kalahGameService.playGame(game.getId(), 13);
        assertThat(game.getStatus()).isEqualTo(GameStatus.FINISHED);
    }

    @Test
    public void playerOneShouldWinTest() {
        Game game = prepareGameWithEmptyPits();
        game.setMove(Player.PLAYER_TWO);
        game.getBoard().getPit(PLAYER_ONE_BASE).setStones(17);
        game.getBoard().getPit(13).setStones(1);
        kalahGameService.playGame(game.getId(), 13);
        assertThat(game.getStatus()).isEqualTo(GameStatus.FINISHED);
        assertThat(game.getWinner()).isEqualTo(Player.PLAYER_ONE);
    }


    private Game prepareGameWithEmptyPits(){
        final Game game = kalahGameService.createGame();
        for (Pit p: game.getBoard().getPits()) {
            p.setStones(0);
        }
        return game;
    }

}