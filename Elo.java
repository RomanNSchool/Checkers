import java.io.*;
import java.util.Scanner;

class Elo {
  public void addPlayer(String player){
    
  }
  public void adjustEloScores(String player1, String player2){
    
  }
  public void sortEloScores(){
    
  }
  public void printFile(){
    // http://www.matterofstats.com/mafl-stats-journal/2013/10/13/building-your-own-team-rating-system.htm
    
    String eloRatings = "";
     String separator = "\n--------------------------------------------------\n";
    Scanner s = null;{
    try {
      s = new Scanner(new BufferedReader(new FileReader("elo.txt"))); // Obtain initial text from the text file
      while (s.hasNext()) {
        eloRatings += (s.nextLine() + ",");
      } 
    } catch (IOException e) {
      System.out.println(e);
    } 
    finally {
      if (s != null) {
        s.close();
      }//makes sure the textfile only reads and the program only writes valid chrarcters
    }
      String[] eloRatingArray = eloRatings.split(",");
      String[] playerNames = new String[eloRatingArray.length / 2];
      String[] playerScores = new String[eloRatingArray.length / 2];
      int k = 0;
      int v = 0;
      try {
        for (int i = 0; i < eloRatingArray.length; i += 2) {
          playerNames[k] = eloRatingArray[i];
          k++;
        }
        for (int i = 1; i < eloRatingArray.length; i+=2) {
            playerScores[v] = eloRatingArray[i];
        }
      } catch (ArrayIndexOutOfBoundsException e) {
        System.out.println("No elo rating");//If the first 2 players that ever run the code, they won't break the code after the code checks the empty file
      }
     

      int j = 0;
      for (int i = 1; i < eloRatingArray.length; i += 2) {
        playerScores[j] = eloRatingArray[i];
        j++;
      } // With the player names, they will use an array to help sort out new elo rating
        // for each players

          for (int i = 0; i < playerNames.length; i++) {
            System.out.println(playerNames[i] + " - " + playerScores[i]);
    }
   }
  }
}
  