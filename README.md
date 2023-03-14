# Bulls and Cows
This is a command line program for the game "Bulls and Cows" (https://en.wikipedia.org/wiki/Bulls_and_Cows), in which you have to guess a secret code and you receive a hint in the form of "X bulls, Y cows" after every guess.
_Bull_ means one of the symbols is correct and at the correct position. _Cow_ means one of the symbols is correct, but at a wrong position. The game ends as soon as the player guesses the correct code (=all bulls).

For example, assume the secret code is 4297. Guessing 1234 would yield 1 bull, 1 cow. Guessing 7892 would yield 1 bull, 2 cows. And so on.

The game is usually played with a 4 digit code consisting of numbers from 0-9, but this program supports a code length of up to 36 (with symbols 0-9 and a-z). No symbol can appear in the code more than once.
