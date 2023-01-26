
/*
* Checkers game
* @author Roman Navarrete, Mohamed Jammal
* Lucas Huang, Jeremy Kwan
* @version 17.0.5
* @since 2023-01-09
*/
import java.io.*;
import java.util.Scanner;
import java.util.*;

class Main {

  public static void main(String[] args) {
    String separator = "\n---------------------------------------------------\n";
    String input;
    Scanner sc = new Scanner(System.in);
    /*
     * Ask user for input and ensure that it is either start, rules, or score, if
     * not ask user again
     */
    do {

      System.out.println(separator);
      System.out.println(
          "Enter Start to play a game of checkers.\nEnter Rules to read the rules.\nEnter Score to view the high scores.");
      System.out.println(separator);
      System.out.print("Input: ");
      // ignore capitalization by converting input to lowercase
      // ignore leading or trailing whitespace using trim
      input = sc.nextLine().toLowerCase().trim();
      // print out the rules if the player inputs rules
      if (input.equals("rules")) {
        System.out.println(separator
            + "\nCheckers is a game played on a 8 by 8 board. The game starts with 12 checker pieces for each team, placed in the squares of the first 3 rows. Two players alternate moves, attempting to capture each other's pieces. Players must always capture another piece when possible. The game ends when a player captures all of the opposing pieces or when the game reaches stalemate.");
      } else if (input.equals("score")) {
        HighScore highScore = new HighScore();
        System.out.println(separator);
        String score = highScore.toString();
        if (score.isEmpty()) {
          System.out.println("<EMPTY>");
        }
        System.out.print(score.toString());
      }
    } while (!((input.equals("start"))));
    System.out.println(separator);
    System.out.println("Enter 1v1 for a 2 player game.\nEnter AI to play against a bot.");
    do {
      System.out.println(separator);
      System.out.print("Input: ");
      input = sc.nextLine().toLowerCase().trim();
    } while (!(input.equals("1v1") || input.equals("ai")));
    // hold player names in array
    String[] players = new String[2];
    if (input.equals("1v1")) {
      System.out.println(separator);
      System.out.println("What is Player 1's name?");
      System.out.println(separator);

      // get rid of any trailing or leading whitespace, lowercase everything then
      // capitalize the first letter
      do {
        System.out.print("Input: ");
        players[0] = sc.nextLine().trim().toLowerCase();
      } while (players[0].isBlank());
      players[0] = players[0].substring(0, 1).toUpperCase() + players[0].substring(1);
      System.out.println(separator);
      System.out.println("What is Player 2's name?");
      System.out.println(separator);
      do {
        System.out.print("Input: ");
        players[1] = sc.nextLine().trim().toLowerCase();
        if (!(players[1].isBlank())) {
          players[1] = players[1].substring(0, 1).toUpperCase() + players[1].substring(1);
        }
      } while (players[1].isBlank() || players[1].equals(players[0]));

      Game game = new Game(players[0], players[1]);
      game.play(false);
    } else {
      System.out.println(separator);
      System.out.println("What is Player 1's name?");
      System.out.println(separator);

      // get rid of any trailing or leading whitespace, lowercase everything then
      // capitalize the first letter
      do {
        System.out.print("Input: ");
        players[0] = sc.nextLine().trim().toLowerCase();
      } while (players[0].isBlank());
      players[0] = players[0].substring(0, 1).toUpperCase() + players[0].substring(1);
      players[1] = "AI";
      Game game = new Game(players[0], players[1]);
      game.play(true);
    }
  }
}