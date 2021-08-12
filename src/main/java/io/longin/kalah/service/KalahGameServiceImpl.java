package io.longin.kalah.service;

import io.longin.kalah.model.Game;
import io.longin.kalah.model.GameStatus;
import io.longin.kalah.model.Player;
import io.longin.kalah.repository.KalahGameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static io.longin.kalah.constants.GameConstants.*;

@Service
public class KalahGameServiceImpl implements KalahGameService{

    private KalahGameRepository repository;

    @Autowired
    public KalahGameServiceImpl(KalahGameRepository repository) {
        this.repository = repository;
    }

    @Override
    public Game createGame() {
        return repository.save(new Game());
    }

    @Override
    public Game playGame(final String gameId, final int pitId) {
        final Game game = repository.getGame(gameId);
        if(game.getStatus().equals(GameStatus.FINISHED)){

        }
        // check game status
        // validate move
        isValidMove(game, pitId);
        // move stones
        // check game over
        // set winner
        // set game status

        return repository.getGame(gameId);
    }

    private void isValidMove(final Game game, final int pitId){
        if(pitId == PLAYER_ONE_BASE || pitId == PLAYER_TWO_BASE){
            throw new IllegalArgumentException("Can't move from house pit.");
        }
        if(game.getBoard().getPit(pitId).getStones() == ZERO){
            throw new IllegalArgumentException("Can't move from empty pit.");
        }
        if(game.getMove() == null){
            game.setMove(definePlayerMove(pitId));
        }
        if((game.getMove().getBasePitIndex() == PLAYER_ONE_BASE) && !(pitId < PLAYER_TWO_BASE)){
            throw new IllegalArgumentException("Opposite player turn.");
        }
        if((game.getMove().getBasePitIndex() == PLAYER_TWO_BASE) && !(pitId > PLAYER_TWO_BASE)){
            throw new IllegalArgumentException("Opposite player turn.");
        }
    }

    private Player definePlayerMove(final int pitId){
        if(pitId < PLAYER_TWO_BASE){
            return new Player(PLAYER_ONE_BASE);
        }else{
            return new Player(PLAYER_TWO_BASE);
        }
    }
}
