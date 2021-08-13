package io.longin.kalah.rest;

import io.longin.kalah.dto.GameDTO;
import io.longin.kalah.model.Game;
import io.longin.kalah.model.Pit;
import io.longin.kalah.service.KalahGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.InetAddress;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@ControllerAdvice
public class KalahGameWebService {

    private KalahGameService kalahGameService;
    private Environment environment;

    @Autowired
    public KalahGameWebService(final KalahGameService kalahGameService, final Environment environment){
        this.kalahGameService = kalahGameService;
        this.environment = environment;
    }

    @PostMapping("/games")
    public ResponseEntity<GameDTO> newGame(){
        final Game game = this.kalahGameService.createGame();
        return ResponseEntity.status(HttpStatus.CREATED).body(new GameDTO(game.getId(), buildUrl(game.getId())));
    }

    @PutMapping("/games/{gameId}/pits/{pitId}")
    public ResponseEntity<GameDTO> playGame(@PathVariable final String gameId, @PathVariable final int pitId){
        final Game game = this.kalahGameService.playGame(gameId, pitId);
        final Map<Integer, String> gameStatus = createResponseStatus(game);
        return ResponseEntity.status(HttpStatus.OK).body(new GameDTO(game.getId(), buildUrl(game.getId()), gameStatus));
    }

    private String buildUrl(final String gameId){
        final String port = environment.getProperty("server.port");
        final String host = InetAddress.getLoopbackAddress().getHostName();
        return String.format("http://%s:%s/games/%s", host, port, gameId);
    }

    private Map<Integer, String> createResponseStatus(Game game){
        return Arrays.stream(game.getBoard().getPits()).collect(Collectors.toMap(Pit::getId, value -> Integer.toString(value.getStones())));
    }
}
