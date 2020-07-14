package com.revature.battleship.domain;

import java.util.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "games")
public class Game {
  
  @Id
  private String gameId;

  private Map<String, String> board;

  private Set<String> targetSquares;


  /**
   * Get gameId.
   *
   * @return gameId as String.
   */
  public String getGameId()
  {
    return gameId;
  }
  
  /**
   * Set gameId.
   *
   * @param gameId the value to set.
   */
  public void setGameId(String gameId)
  {
    this.gameId = gameId;
  }

  public Map<String, String> getBoard() {
    return this.board;
  }

  public void setBoard(Map<String, String> board) {
    this.board = board;
  }

  public Set<String> getTargetSquares() {
    return this.targetSquares;
  }

  public void setTargetSquares(Set<String> targetSquares) {
    this.targetSquares = targetSquares;
  }

}
