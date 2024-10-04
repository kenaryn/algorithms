import scala.io.StdIn.readLine
import scala.util.Random

def throwDice(): Int = 
    Random.between(1, 7)

def updatePlayerPos(playerPos: Int, roll: Int, gameboard: Array[Int], currentPlayer: Int): Int = 
    val newPos = playerPos + roll
    gameboard(currentPlayer) = newPos
    newPos

@main def run: Unit =
    var numRound = 1
    var playerPos = 0
    val nbPlayers = { print("Enter a number of players: "); readLine().toInt }
    val gameboard = new Array[Int](nbPlayers)
    var currentPlayer = 0
    for turn <- 1 to nbPlayers do
        
        while playerPos != 63 do
            val dice1 = throwDice()
            val dice2 = throwDice()
            val roll = dice1 + dice2
            println(s"You throw a ${roll}.")
            playerPos = updatePlayerPos(playerPos, roll, gameboard, currentPlayer)
            if playerPos > 63 then playerPos = 63 - ((playerPos + roll) - 63)

            playerPos match
                case 9 if numRound == 1 && (dice1 == 3 || dice2 == 3) => { 
                    println("You throw a 9 at the first round with 3 and 6, so you go straight away to case 26.")
                    playerPos = 26 }
                    
                case 9 if (dice1 == 4 || dice2 == 4) => { 
                    println("You throw a 9 at the first round with 4 and 5, so you go straight away to case 53.")
                    playerPos = 26 }

                // Goose locations.
                case 9 | 36 | 45 | 54 => { 
                    println(s"You encountered a goose! You may move again for $roll cases.")
                    playerPos += roll }
                    
                case 19 => { 
                    println("You stumbled upon a hotel and decided to take a vacation for 2 turns!")
                    numRound += 2 }
                    
                case 31 => { println("You fell into a well! As the only player, you have definitely lost.")
                    System.exit(0) }
                    
                case 40 => { 
                    println("You lost yourself in a maze and fell back to case 30!")
                    playerPos = 30 }
                    
                case 52 => { println("You fell into a well! As the only player, you have definitely lost.")
                    System.exit(0) }

                case 15 | 58 => { println("You encountered Death itself! Fall back to the very beginning at case 1.")
                    playerPos = 1 }
                
                // case _ => { println(s"No thing special happened at position ${playerPos}.")}
                case _ => println(s"You move to the case ${playerPos}.")
                
                numRound += 1
                // Rotate the players.
                if currentPlayer == nbPlayers then currentPlayer = 0
                else currentPlayer += 1
        end while

        println("You have reached the final destination. Congratulations!")