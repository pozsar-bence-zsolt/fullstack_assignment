package com.dartsfighters.advancedjava.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

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
import com.dartsfighters.advancedjava.repository.CustomThrowRepository;
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
    private final CustomThrowRepository customThrowRepository;

    @GetMapping("/new-game")
    public ResponseEntity<Map<String, String>> newGame(){
        Map<String, String> response = new HashMap<>();

        Game newGame = new Game();
        Game createdGame = gameRepository.save(newGame);

        response.put("message", "Successfully created a new game!");
        response.put("game", createdGame.getId().toString());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{gameId}")
    public ResponseEntity<Map<String, GameDto>> getGame(@PathVariable("gameId") Integer gameId) {
        Map<String, GameDto> response = new HashMap<>();
        Game game = this.gameRepository.getReferenceById(gameId);
        List<Row> rows = this.rowRepository.findByGameId(gameId);
        List<RowDto> rowDtos = new ArrayList<>();
        Map<Integer, String> playerDetails = new HashMap<>();

        for (Row row : rows) {
            List<ThrowDto> throwDtos = new ArrayList<>();
            List<Throw> uthrows = this.throwRepository.findByRowIdOrderByDartNumber(row.getId());
            for (Throw uthrow : uthrows) {
                Integer player = uthrow.getThrower().getId();
                ThrowDto throwDto = new ThrowDto(uthrow.getId(), uthrow.getDartNumber(), uthrow.getScore(), player);
                throwDtos.add(throwDto);
                if (playerDetails.get(player) == null) {
                    playerDetails.put(player, uthrow.getThrower().getUsername());
                }
            }
            RowDto rowDto = new RowDto(gameId, throwDtos);
            rowDtos.add(rowDto);
        }
        
        GameDto gameDto = new GameDto(gameId, rowDtos, playerDetails);
        if (game.getWinner() != null) {
            gameDto.setWinner(game.getWinner().getId());
        }
        response.put("game", gameDto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{gameId}/ready")
    public ResponseEntity<Map<String, String>> readyGame(@PathVariable("gameId") Integer gameId, @RequestBody List<String> players) {
        Map<String, String> response = new HashMap<>();
        Game game = this.gameRepository.findById(gameId).get();
        if (game.getStartTime() != null) {
            response.put("message", "Game has already started!");
            return ResponseEntity.status(404).body(response);
        }
        game.setStartTime(LocalDateTime.now());
        gameRepository.save(game);

        Row row = new Row();
        row.setGame(game);
        row.setRowNumber(1);
        row = rowRepository.save(row);  
        for (String player: players) {
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

    @PostMapping("/{gameId}/throw")
    public ResponseEntity<Map<String, String>> playerThrow(@PathVariable("gameId") Integer gameId, @RequestBody HashMap<String, Integer> request) {
        Map<String, String> response = new HashMap<>();
        Integer throwId = request.get("throwId");
        Integer score = request.get("score");
        Boolean newRow = request.get("newRow") == 1;
        Game game = this.gameRepository.getReferenceById(gameId);
        Throw uthrow = this.throwRepository.getReferenceById(throwId);
        uthrow.setScore(score);
        this.throwRepository.save(uthrow);

        if (newRow) {
            Row currentRow = this.rowRepository.findMaxRowNumberByGameId(gameId).get(); 
            Row createRow = new Row();
            createRow.setRowNumber(currentRow.getRowNumber() + 1);
            createRow.setGame(game);

            createRow = this.rowRepository.save(createRow);
        
            List<User> players = this.customThrowRepository.findUsersByRowId(currentRow.getId());
            for (User player : players) {
                for (Integer i = 1; i <= 3; i++) {
                    Throw newThrow = new Throw();
                    newThrow.setThrower(player);
                    newThrow.setRow(createRow);
                    newThrow.setScore(-1);
                    newThrow.setDartNumber(i);
                    this.throwRepository.save(newThrow);
                }
            }
        }

        response.put("message", "Throw saved");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{gameId}/win")
    public ResponseEntity<Map<String, String>> winGame(@PathVariable("gameId") Integer gameId, @RequestBody HashMap<String, Integer> request) {
        Map<String, String> response = new HashMap<>();
        Integer playerId = request.get("winner");
        User winner = this.userRepository.findById(playerId).get();
        Game game = this.gameRepository.getReferenceById(gameId);
        game.setWinner(winner);
        game.setEndTime(LocalDateTime.now());
        this.gameRepository.save(game);

        response.put("message", "Winner saved successfully!");
        return ResponseEntity.ok(response);
    }
}
