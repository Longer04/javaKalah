package io.longin.kalah.service;

import io.longin.kalah.model.Game;
import io.longin.kalah.model.GameStatus;
import io.longin.kalah.model.Pit;
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
        moveStones(game, pitId);
        // move stones
        // check game over
        // set winner
        // set game status

        return repository.getGame(gameId);
    }

    private void isValidMove(final Game game, final int pitId){
        if(isBase(pitId)){
            throw new IllegalArgumentException("Can't move from house pit.");
        }
        if(game.getBoard().getPit(pitId).getStones() == ZERO){
            throw new IllegalArgumentException("Can't move from empty pit.");
        }
        if(game.getMove() == null){
            game.setMove(definePlayerMove(pitId));
        }
        if((game.getMove().getBasePitIndex() == PLAYER_ONE_BASE) && !(pitId < PLAYER_ONE_BASE)){
            throw new IllegalArgumentException("Opposite player turn. Player One Turn.");
        }
        if((game.getMove().getBasePitIndex() == PLAYER_TWO_BASE) && !(pitId > PLAYER_ONE_BASE)){
            throw new IllegalArgumentException("Opposite player turn. Player Two Turn.");
        }
    }

    private Player definePlayerMove(final int pitId){
        if(pitId < PLAYER_ONE_BASE){
            return new Player(PLAYER_ONE_BASE);
        }else{
            return new Player(PLAYER_TWO_BASE);
        }
    }

    private void moveStones(final Game game, int pitId){
        final Pit pit = game.getBoard().getPit(pitId);
        int availableStones = pit.getStones();
        pit.setStones(0);
        while(availableStones > 0){
            final Pit currentPit = game.getBoard().getPit(++pitId);
            if(isValidPit(game.getMove(), currentPit.getId())){
                currentPit.setStones(currentPit.getStones() + 1);
                availableStones--;
            }
        }
        checkLastPit(game, pitId);
    }

    private boolean isValidPit(final Player player, final int pitId){
        if((player.getBasePitIndex() == PLAYER_ONE_BASE) && isBase(pitId)){
            return false;
        }else if((player.getBasePitIndex() == PLAYER_TWO_BASE) && isBase(pitId)){
            return false;
        }
        return true;
    }

    private void checkLastPit(final Game game, final int pitId) {
        final Pit lastPit = game.getBoard().getPit(pitId);
        final Pit basePit;
        final Pit oppositePit;
        if (!isBase(pitId) && lastPit.getStones() == 1) {
            oppositePit = game.getBoard().getPit(PLAYER_TWO_BASE - lastPit.getId());
            if ((game.getMove().getBasePitIndex() == PLAYER_TWO_BASE) && (pitId > PLAYER_ONE_BASE)) {
                basePit = game.getBoard().getPit(PLAYER_TWO_BASE);
                basePit.setStones(basePit.getStones() + oppositePit.getStones() + lastPit.getStones());
            } else if ((game.getMove().getBasePitIndex() == PLAYER_ONE_BASE) && (pitId < PLAYER_ONE_BASE)) {
                basePit = game.getBoard().getPit(PLAYER_ONE_BASE);
                basePit.setStones(basePit.getStones() + oppositePit.getStones() + lastPit.getStones());
            }
            oppositePit.setStones(0);
            lastPit.setStones(0);
        }
    }

    private boolean isBase(final int pitId){
        return pitId == PLAYER_ONE_BASE || pitId == PLAYER_TWO_BASE;
    }
}
