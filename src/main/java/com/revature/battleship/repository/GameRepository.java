package com.revature.battleship.repository;

import com.revature.battleship.domain.Game;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GameRepository extends MongoRepository<Game, String> {
  public Game findByGameId(String gameId);
} 
