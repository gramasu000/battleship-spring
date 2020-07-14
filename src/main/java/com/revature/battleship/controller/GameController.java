package com.revature.battleship.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.beans.factory.annotation.Autowired;

import com.revature.battleship.service.GameService;

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

  @PostMapping("/game/{gameid}")
  public String createGame(@PathVariable(name = "gameid") String gameId) { 
    this.gameService.saveGame(this.gameService.createNewGame(gameId));
    return "success";
  }

}
