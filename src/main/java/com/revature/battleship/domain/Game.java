package com.revature.battleship.domain;

import java.util.Map;
import java.util.HashMap;

import javax.persistence.Entity;
import javax.io.Serializable;

public class Game implements Serializable {
  
  private int questionId;

  Map<String, String> board;

  Set<String> unattackedPoints;

  Set<String> attackedPoints;
  

    
}
