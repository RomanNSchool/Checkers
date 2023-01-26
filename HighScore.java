/*
* Creates a highscore object that manipulates the highscore file
* @author Roman Navarrete, Lucas Huang, Mohamed Jammal, Jeremy Kwan
* @version 17.0.5
* @since 2023-01-20
*/
import java.io.*;

class HighScore {
  // instance variable
  private String file = "highscore.txt";

  /*
   * Adds player to file
   * Runs if addScore does not find player
   * 
   * @param player - String with player's name
   */
  public void addPlayer(String player) {
    PrintWriter pw = null;
    try {
      pw = new PrintWriter(new FileWriter(file, true));
      // append player and their score of 1 to the end
      pw.print(player + ":1\n");
    } catch (IOException e) {
      System.out.println(e);
    } finally {
      if (pw != null) {
        pw.close();
      }
    }
  }

  /*
   * Increments player's score if player is found
   * 
   * @param player - String with player's name
   */
  public void addScore(String player) {

    String[][] highScoreArray = toArray();
    boolean playerFound = false;
    // loop through array to see if player exists in array if not playerFound
    // remains false
    for (int i = 0; i < highScoreArray.length; i++) {
      if (highScoreArray[i][0].equals(player)) {
        playerFound = true;
        // increase player's score by 1
        highScoreArray[i][1] = Integer.toString(Integer.parseInt(highScoreArray[i][1]) + 1);
      }
    }

    if (playerFound) {
      // Insertion Sort
      // Sorts array by the value of the 1st column in reversed order
      int index;
      String[] key; // value of the element to compare
      for (int i = 1; i < highScoreArray.length; i++) {
        key = highScoreArray[i];
        index = i - 1;
        while (index >= 0 && Integer.parseInt(key[1]) > Integer.parseInt(highScoreArray[index][1])) {
          highScoreArray[index + 1] = highScoreArray[index]; // move value up one
          index--; // continue to the last index in sorted section of the array
        }
        highScoreArray[index + 1] = key; // place the key in the sorted section of the array
      }

      // convert array back into file
      String highScoreString = "";
      // Make string containing all the names and numbers in the proper format
      for (int i = 0; i < highScoreArray.length; i++) {
        highScoreString = highScoreString + highScoreArray[i][0] + ":" + highScoreArray[i][1] + "\n";
      }

      // set the whole file to the string previously made
      PrintWriter pw = null;
      try {
        pw = new PrintWriter(new FileWriter(file));
        pw.print(highScoreString);
      } catch (IOException e) {
        System.out.println(e);
      } finally {
        if (pw != null) {
          pw.close();
        }
      }
    } else {
      // if player isn't found, append player to the file
      addPlayer(player);
    }
  }

  /*
   * Converts highscore.txt into array
   * 
   * @return String 2D Array containing name of players in column 0 and score in
   * column 1
   */
  public String[][] toArray() {
    String highScoreString = toString();
    // split highScoreString where there is a colon or newline
    String[] highScoreWords = highScoreString.split("[\n:]");
    // takes care of case where split on empty string returns a string array with
    // one element, [""]
    if (highScoreString == "") {
      highScoreWords = new String[0];
    }
    // array should only have as many rows as the number of words divided by 2
    // because there are 2 words in each row (name and number)
    String[][] highScoreArray = new String[highScoreWords.length / 2][2];
    int count = 0;
    for (int i = 0; i < highScoreWords.length; i++) {
      if (i % 2 == 0) {
        // even indexes of highScoreWords are the names
        highScoreArray[count][0] = highScoreWords[i];
      } else {
        // odd indexes of highScoreWords are the numbers
        highScoreArray[count][1] = highScoreWords[i];
        // increase count to move to next row as current row is now full
        count++;
      }
    }
    return highScoreArray;
  }

  /*
   * Gets highscore.txt as string
   * Overrides toString() method
   * 
   * @return String containing the contents of highscore.txt
   */
  @Override
  public String toString() {
    String returnString = "";
    BufferedReader br = null;
    try {
      br = new BufferedReader(new FileReader(file));
      int c = 0;
      while ((c = br.read()) != -1) {
        // add each character to string until there are no more
        returnString = returnString + ((char) c);
      }
    } catch (Exception e) {
      System.out.println("There was an IOException thrown");
    } finally {
      try {
        br.close();
      } catch (IOException e) {
        System.out.println("There was an IOException thrown");
      }
    }
    return returnString;

  }
}