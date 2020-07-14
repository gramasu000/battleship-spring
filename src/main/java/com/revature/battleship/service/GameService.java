package com.revature.battleship.service;

import java.util.*;
import com.revature.battleship.domain.Game;

@Service
public class GameService {
  
  String strAllowedCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
  String[] cols = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J" };
  String[] rows = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" };

  private Map<String, String> initBoard() {
    Map<String, String> board = new HashMap<>();
    for (String col : cols) {
      for (String row : rows) {
        board.put(col+row, "unattacked");
      }
    }
    return board; 
  }

  private Set<String> initTargets() {
    Set<String> targets = new HashSet<>();
    targets.add("A1");
    targets.add("B1");
    targets.add("C1");
    targets.add("D1");
    return targets;
  }

  public Game createNewGame() {
    Game game = new Game();
    game.setGameId("223431ddddffffjjjj");
    game.setBoard(this.initBoard());
    game.setTargetsSquares(this.initTargets());
    return game;
  }

}
