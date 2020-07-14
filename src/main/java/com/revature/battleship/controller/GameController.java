package com.revature.battleship.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class GameController {

  @GetMapping("/game/description")
  public String restTest() {
    return "Documentation Here";
  } 

}
