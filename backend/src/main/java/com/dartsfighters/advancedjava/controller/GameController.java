package com.dartsfighters.advancedjava.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Set;
import java.util.Collection;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dartsfighters.advancedjava.controller.dto.GameDto;
import com.dartsfighters.advancedjava.controller.dto.RowDto;
import com.dartsfighters.advancedjava.controller.dto.ThrowDto;
import com.dartsfighters.advancedjava.domain.Game;
import com.dartsfighters.advancedjava.domain.Row;
import com.dartsfighters.advancedjava.domain.Throw;
import com.dartsfighters.advancedjava.domain.User;
import com.dartsfighters.advancedjava.repository.GameRepository;
import com.dartsfighters.advancedjava.repository.RowRepository;
import com.dartsfighters.advancedjava.repository.ThrowRepository;
import com.dartsfighters.advancedjava.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequiredArgsConstructor
@RequestMapping("/game")
public class GameController {

    private final UserRepository userRepository;
    private final GameRepository gameRepository;
    private final RowRepository rowRepository;
    private final ThrowRepository throwRepository;

    @GetMapping("/new-game")
    public ResponseEntity<Map<String, String>> newGame(){
        Map<String, String> response = new HashMap<>();

        Game newGame = new Game();
        Game createdGame = gameRepository.save(newGame);

        response.put("message", "Successfully created a new game!");
        response.put("game", createdGame.getId().toString());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{gameId}/ready")
    public ResponseEntity<Map<String, String>> readyGame(@PathVariable("gameId") Integer gameId, @RequestBody List<String> players) {
        Map<String, String> response = new HashMap<>();
        Game game = this.gameRepository.findById(gameId).get();
        game.setStarTime(LocalDateTime.now());
        gameRepository.save(game);

        Row row = new Row();
        row.setGame(game);
        row = rowRepository.save(row);  
        for (String player: players) {
            System.out.println(player);
            if (!"empty".equals(player)) {
                User user = this.userRepository.getReferenceById(Integer.parseInt(player));
                for (Integer i = 1; i <= 3; i++) {
                    Throw uthrow = new Throw();
                    uthrow.setThrower(user);
                    uthrow.setRow(row);
                    uthrow.setScore(-1);
                    uthrow.setDartNumber(i);
                    this.throwRepository.save(uthrow);
                }
            }
        }

        response.put("message", "Game is starting...");
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{gameId}")
    public ResponseEntity<Map<String, GameDto>> getGame(@PathVariable("gameId") Integer gameId) {
        Map<String, GameDto> response = new HashMap<>();
        Game game = this.gameRepository.findById(gameId).get();
        List<Row> rows = this.rowRepository.findByGameId(gameId);
        List<RowDto> rowDtos = new ArrayList<>();
        Map<Integer, String> playerDetails = new HashMap<>();

        for (Row row : rows) {
            List<ThrowDto> throwDtos = new ArrayList<>();
            List<Throw> uthrows = this.throwRepository.findByRowId(row.getId());
            for (Throw uthrow : uthrows) {
                Integer player = uthrow.getThrower().getId();
                ThrowDto throwDto = new ThrowDto(uthrow.getDartNumber(), uthrow.getScore(), player);
                throwDtos.add(throwDto);
                if (playerDetails.get(player) == null) {
                    playerDetails.put(player, uthrow.getThrower().getUsername());
                }
            }
            RowDto rowDto = new RowDto(gameId, throwDtos);
            rowDtos.add(rowDto);
        }
        
        GameDto gameDto = new GameDto(gameId, rowDtos, playerDetails);
        response.put("game", gameDto);
        return ResponseEntity.ok(response);
    }

    
}
