package io.longin.kalah.service;

import io.longin.kalah.model.*;
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
            throw new IllegalArgumentException("Game is already finished.");
        }
        isValidMove(game, pitId);
        moveStones(game, pitId);
        if(isGameFinished(game)){
            sumWinnerStones(game);
            setWinner(game);
            game.setStatus(GameStatus.FINISHED);
        }
        return repository.getGame(gameId);
    }

    private boolean isGameFinished(final Game game) {
        final int playerOneStones = game.getBoard().getStonesFromNonHousePits(Player.PLAYER_ONE);
        final int playerTwoStones = game.getBoard().getStonesFromNonHousePits(Player.PLAYER_TWO);
        return playerOneStones == 0 || playerTwoStones == 0;
    }

    private void sumWinnerStones(final Game game){
        final int playerOneStones = game.getBoard().getStonesFromNonHousePits(Player.PLAYER_ONE);
        final int playerTwoStones = game.getBoard().getStonesFromNonHousePits(Player.PLAYER_TWO);
        game.getBoard().getPit(PLAYER_ONE_BASE).setStones(playerOneStones + game.getBoard().getPit(PLAYER_ONE_BASE).getStones());
        game.getBoard().getPit(PLAYER_TWO_BASE).setStones(playerTwoStones + game.getBoard().getPit(PLAYER_TWO_BASE).getStones());
    }

    private void setWinner(final Game game){
        if ( game.getBoard().getPit(PLAYER_ONE_BASE).getStones() > game.getBoard().getPit(PLAYER_TWO_BASE).getStones()) {
            game.setWinner(Player.PLAYER_ONE);
        } else if (game.getBoard().getPit(PLAYER_ONE_BASE).getStones() < game.getBoard().getPit(PLAYER_TWO_BASE).getStones()) {
            game.setWinner(Player.PLAYER_TWO);
        }
    }


    private void isValidMove(final Game game, final int pitId){
        if(pitId < 1 || pitId > BOARD_SIZE){
            throw new IllegalArgumentException("Select valid pit from 1 to 14;");
        }
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
            return Player.PLAYER_ONE;
        }else{
            return Player.PLAYER_TWO;
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
        setNextMove(game, pitId);
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

    private void setNextMove(Game game, int pitId) {
        final Pit pit = game.getBoard().getPit(pitId);
        if(pit.getId() == PLAYER_ONE_BASE && game.getMove().getBasePitIndex() == PLAYER_ONE_BASE){
            game.setMove(Player.PLAYER_ONE);
        }else if (pit.getId() == PLAYER_TWO_BASE && game.getMove().getBasePitIndex() == PLAYER_TWO_BASE){
            game.setMove(Player.PLAYER_TWO);
        }else{
            if(game.getMove().getBasePitIndex() == PLAYER_TWO_BASE){
                game.setMove(Player.PLAYER_ONE);
            }else{
                game.setMove(Player.PLAYER_TWO);
            }
        }
    }

    private boolean isValidPit(final Player player, final int pitId){
        if((player.getBasePitIndex() == PLAYER_ONE_BASE) && pitId == PLAYER_TWO_BASE){
            return false;
        }else
            return (player.getBasePitIndex() != PLAYER_TWO_BASE) || pitId != PLAYER_ONE_BASE;
    }

    private boolean isBase(final int pitId){
        return pitId == PLAYER_ONE_BASE || pitId == PLAYER_TWO_BASE;
    }
}
