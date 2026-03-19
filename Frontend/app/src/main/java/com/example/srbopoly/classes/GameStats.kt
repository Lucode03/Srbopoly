package com.example.srbopoly.classes

import java.util.Date

data class GameStats (val uid: String,
                      val maxTurns: Int,
                      val currentTurn: Int,
                      val currentPlayer: String,
                      val players: List<String>,
//                      val date: Date
                      )