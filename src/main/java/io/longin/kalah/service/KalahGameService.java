package io.longin.kalah.service;

import io.longin.kalah.model.Game;

public interface KalahGameService {
    Game createGame();
    Game playGame(String gameId, int pitId);
}
