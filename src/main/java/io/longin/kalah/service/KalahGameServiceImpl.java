package io.longin.kalah.service;

import io.longin.kalah.model.Game;
import org.springframework.stereotype.Service;

@Service
public class KalahGameServiceImpl implements KalahGameService{

    @Override
    public Game createGame() {
        return new Game();
    }
}
