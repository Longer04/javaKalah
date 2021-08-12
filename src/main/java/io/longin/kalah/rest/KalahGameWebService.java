package io.longin.kalah.rest;

import io.longin.kalah.dto.GameDTO;
import io.longin.kalah.model.Game;
import io.longin.kalah.service.KalahGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class KalahGameWebService {

    private KalahGameService kalahGameService;

    @Autowired
    public KalahGameWebService(final KalahGameService kalahGameService){
        this.kalahGameService = kalahGameService;
    }

    @PostMapping("/games")
    public ResponseEntity<GameDTO> newGame(){
        final Game game = this.kalahGameService.createGame();
        final ServletUriComponentsBuilder requestUri = ServletUriComponentsBuilder.fromCurrentRequest();
        return ResponseEntity.status(HttpStatus.CREATED).body(new GameDTO(game.getId(), buildUrl(game.getId(), requestUri)));
    }

    private String buildUrl(final String gameId, final ServletUriComponentsBuilder uriComponentsBuilder){
       return uriComponentsBuilder.pathSegment(gameId).build().toUriString();
    }
}
