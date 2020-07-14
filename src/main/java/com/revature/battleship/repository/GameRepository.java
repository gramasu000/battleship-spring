package com.revature.battleship.repository;

import com.revature.battleship.domain.Game;
import org.springframework.data.mongodb.repository.MongoRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends MongoRepository<Game, String> {
  public Game findByGameId(String gameId);
} 
