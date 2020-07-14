package com.revature.battleship.controller;

import java.util.*;

import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;

import com.revature.battleship.service.GameService;
import com.revature.battleship.domain.Game;

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

}
