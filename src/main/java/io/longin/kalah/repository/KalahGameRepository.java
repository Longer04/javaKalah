package io.longin.kalah.repository;

import io.longin.kalah.model.Game;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class KalahGameRepository {

    private Map<String, Game> games = new HashMap<>();

    public Game save(final Game game) {
        games.put(game.getId(), game);
        return game;
    }

    public Game getGame(final String gameId) {
        return games.get(gameId);
    }
}
