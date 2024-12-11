package com.dartsfighters.advancedjava.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
import com.dartsfighters.advancedjava.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/history")
public class HistoryController {

    private final UserRepository userRepository;
    private final GameRepository gameRepository;
    
    @GetMapping("/list")
    public ResponseEntity<List<GameDto>> list(Principal principal) {        
        String username = principal.getName();
        User user = this.userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        List<Game> games = this.gameRepository.findGamesByPlayer(user.getId());
        List<GameDto> gameDtos = new ArrayList<>();

        for (Game game: games) {
            Map<Integer, String> players = this.gameRepository.findPlayers(game.getId())
                .stream().collect(Collectors.toMap(row -> (Integer) row[0], row -> (String) row[1]));
            List<Row> rows = game.getRows();
            List<RowDto> rowDtos = new ArrayList<>();
            
            for (Row row: rows) {
                List<Throw> uthrows = row.getThrowsList();
                List<ThrowDto> throwDtos = new ArrayList<>();

                for (Throw uthrow: uthrows) {
                    Integer player = uthrow.getThrower().getId();
                    ThrowDto throwDto = new ThrowDto(
                        uthrow.getId(),
                        uthrow.getDartNumber(),
                        uthrow.getScore(),
                        player
                    );
                    throwDtos.add(throwDto);
                }

                RowDto rowDto = new RowDto(row.getId(), throwDtos);
                rowDtos.add(rowDto);
            }
            
            GameDto gameDto = new GameDto(game.getId(), rowDtos, players);
            gameDto.setWinner(game.getWinner().getId());
            gameDtos.add(gameDto);
        }

        return ResponseEntity.ok(gameDtos);
    }
}
