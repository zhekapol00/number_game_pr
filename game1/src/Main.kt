import java.util.*
import kotlin.random.Random

class GuessNumberGame(private val maxNumber: Int = 100) {
    private var secretNumber: Int = 0

    fun setSecretNumber(number: Int) {
        if (number in 0..maxNumber) {
            secretNumber = number
        } else {
            println("Invalid number. Please choose a number between 0 and $maxNumber.")
        }
    }

    fun guess(number: Int): GuessResult {
        return when {
            number == secretNumber -> GuessResult.CORRECT
            number < secretNumber -> GuessResult.HIGHER
            else -> GuessResult.LOWER
        }
    }

    fun restart() {
        secretNumber = Random.nextInt(maxNumber + 1)
    }

    enum class GuessResult {
        CORRECT,
        HIGHER,
        LOWER
    }
}

fun main() {
    println("Welcome to Guess the Number game!")
    var isPlayerTurn = true
    val game = GuessNumberGame()

    while (true) {
        game.restart()

        if (isPlayerTurn) {
            println("I've picked a number between 0 and 100. Can you guess it?")
            var attempts = 0

            while (true) {
                print("Enter your guess: ")
                val guess = readlnOrNull()?.toIntOrNull()

                if (guess == null) {
                    println("Invalid input. Please enter a valid number.")
                    continue
                }

                val result = game.guess(guess)
                attempts++

                when (result) {
                    GuessNumberGame.GuessResult.CORRECT -> {
                        println("Congratulations! You've guessed the number $guess in $attempts attempts.")
                        isPlayerTurn = false
                        break
                    }
                    GuessNumberGame.GuessResult.HIGHER -> println("Try a higher number.")
                    GuessNumberGame.GuessResult.LOWER -> println("Try a lower number.")
                }
            }
        } else {
            println("Enter a secret number for the computer to guess between 0 and 100:")
            val secretNumber = readlnOrNull()?.toIntOrNull()

            if (secretNumber != null) {
                game.setSecretNumber(secretNumber)
            } else {
                println("Invalid input. Please enter a valid number.")
                continue
            }

            var min = 0
            var max = 100
            var attempts = 0

            println("Now it's my turn to guess your number.")

            while (true) {
                val guess = Random.nextInt(min, max + 1)
                println("My guess is $guess.")

                val result = game.guess(guess)
                attempts++

                when (result) {
                    GuessNumberGame.GuessResult.CORRECT -> {
                        println("I've guessed your number $guess in $attempts attempts.")
                        isPlayerTurn = true
                        break
                    }
                    GuessNumberGame.GuessResult.HIGHER -> {
                        println("My guess was lower than your number.")
                        min = guess + 1
                    }
                    GuessNumberGame.GuessResult.LOWER -> {
                        println("My guess was higher than your number.")
                        max = guess - 1
                    }
                }
            }
        }

        println("Do you want to play again? (yes/no)")
        val playAgain = readlnOrNull()?.lowercase(Locale.getDefault())

        if (playAgain != "yes" && playAgain != "y") {
            println("Thanks for playing!")
            break
        }
    }
}