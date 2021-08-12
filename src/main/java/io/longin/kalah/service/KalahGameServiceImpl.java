package io.longin.kalah.service;

import io.longin.kalah.model.Game;
import io.longin.kalah.repository.KalahGameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return repository.getGame(gameId);
    }
}
