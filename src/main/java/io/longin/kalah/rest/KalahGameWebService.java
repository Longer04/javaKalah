package io.longin.kalah.rest;

import io.longin.kalah.model.Game;
import io.longin.kalah.service.KalahGameService;
import io.longin.kalah.dto.GameDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return ResponseEntity.status(HttpStatus.CREATED).body(new GameDTO(game.getId(), "URL"));
    }
}
