package io.longin.kalah.rest;

import io.longin.kalah.dto.GameDTO;
import io.longin.kalah.model.Game;
import io.longin.kalah.service.KalahGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
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
        final ServletUriComponentsBuilder url = ServletUriComponentsBuilder.fromCurrentRequest();
        return ResponseEntity.status(HttpStatus.CREATED).body(new GameDTO(game.getId(), buildUrl(game.getId(), url)));
    }

    private String buildUrl(final String gameId, final ServletUriComponentsBuilder uriComponentsBuilder){
       return uriComponentsBuilder.pathSegment(gameId).build().toUriString();
    }
}
