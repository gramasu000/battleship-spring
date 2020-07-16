package com.revature.battleship.controller;

import java.util.*;

import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;

import com.revature.battleship.service.GameService;
import com.revature.battleship.domain.Game;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
public class GameController {

  GameService gameService;

  @Autowired
  public void setGameService(GameService gameService) {
    this.gameService = gameService;
  }
  
  @GetMapping("/description")
  public String restTest() {
    return "Documentation Here";
  } 

  @GetMapping("/game/{gameid}")
  public Map<String, String> getGame(@PathVariable(name = "gameid") String gameId) {
    Game game = this.gameService.getGame(gameId);
    return game.getBoard(); 
  }

  @GetMapping("/game/{gameid}/status")
  public String gameWin(@PathVariable(name = "gameid") String gameId) {
    Game game = this.gameService.getGame(gameId);
    if (game.getTargetSquares().isEmpty()) {
      return "over";
    } else {
      return "playing";
    }
  }

  @DeleteMapping("/game/{gameid}")
  public String deleteGame(@PathVariable(name = "gameid") String gameId) {
    Game game = this.gameService.getGame(gameId);
    this.gameService.deleteGame(game);
    return "Delete Successful";
  }
  
  @PostMapping("/game/{gameid}")
  public Map<String, String> createGame(@PathVariable(name = "gameid") String gameId) { 
    Game game = this.gameService.createNewGame(gameId);
    this.gameService.saveGame(game);
    return game.getBoard();
  }

  @PutMapping("/game/{gameid}/{move}")
  public Object makeMove(@PathVariable(name = "gameid") String gameId, 
      @PathVariable(name = "move") String move) 
  {
    Game game = this.gameService.getGame(gameId);
    game = this.gameService.makeMove(game, move);
    this.gameService.saveGame(game);
    return game.getBoard();
  }

}
