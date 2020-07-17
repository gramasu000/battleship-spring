package com.revature.battleship.service;

import java.util.*;
import com.revature.battleship.domain.Game;
import com.revature.battleship.repository.GameRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService {
  
  String strAllowedCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
  String[] cols = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J" };
  String[] rows = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" };

  private GameRepository gameRepository;

  @Autowired
  public void setGameRepository(GameRepository gameRepository) {
    this.gameRepository = gameRepository;
  }

  private Map<String, String> initBoard() {
    Map<String, String> board = new HashMap<>();
    for (String col : cols) {
      for (String row : rows) {
        board.put(col+row, "unattacked");
      }
    }
    return board; 
  }

  private String[] generateShip(int length) {
    Random rand = new Random();
    int dir = rand.nextInt(2);
    String[] potentialTargets = new String[length];
    if (dir == 0) {
      int maxcol = cols.length - length + 1;
      int maxrow = rows.length;
      int col = rand.nextInt(maxcol);
      int row = rand.nextInt(maxrow);
      for (int i = col; i < col + length; i++) {
        String square = this.cols[i] + this.rows[row];
        potentialTargets[i-col] = square;
      }
    } else {
      int maxcol = cols.length;
      int maxrow = rows.length - length + 1;
      int col = rand.nextInt(maxcol);
      int row = rand.nextInt(maxrow);
      for (int i = row; i < row + length; i++) {
        String square = this.cols[col] + this.rows[i];
        potentialTargets[i-row] = square;
      }
    }
    return potentialTargets;
  }

  private boolean checkOverlap(String[] potentialTargets, Set<String> targets) {
    for (String target : potentialTargets) {
      if (targets.contains(target)) {
        return true;
      }
    }
    return false;
  } 

  private Set<String> placeShip(Set<String> targets, int length) {
    String[] shipTargets = this.generateShip(length);
    while (this.checkOverlap(shipTargets, targets)) {
      shipTargets = this.generateShip(length);
    } 
    for (String target : shipTargets) {
      targets.add(target);
    }
    return targets;
  }

  private Set<String> initTargets() {
    Set<String> targets = new HashSet<>();
    targets = this.placeShip(targets, 2);
    targets = this.placeShip(targets, 3);
    targets = this.placeShip(targets, 3);
    targets = this.placeShip(targets, 4);
    targets = this.placeShip(targets, 5);
    return targets;
  }

  public Game createNewGame(String gameId) {
    Game game = new Game();
    game.setGameId(gameId);
    game.setBoard(this.initBoard());
    game.setTargetSquares(this.initTargets());
    return game;
  }

  public void saveGame(Game game) {
    this.gameRepository.save(game);
  }

  public Game getGame(String gameId) {
    return this.gameRepository.findByGameId(gameId);
  }

  public void deleteGame(Game game) {
    this.gameRepository.delete(game);
  }

  public Game makeMove(Game game, String move) {
    Map<String,String> board = game.getBoard();
    Set<String> targetSquares = game.getTargetSquares();
    if (targetSquares.contains(move)) {
      targetSquares.remove(move);
      board.put(move, "hit");
    } else {
      board.put(move, "miss");
    }
    game.setTargetSquares(targetSquares);
    game.setBoard(board);
    return game;
  }

}
