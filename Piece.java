
/*
* Abstract piece object with essential methods for every piece
* @author Roman Navarrete, Lucas Huang, Mohamed Jammal, Jeremy Kwan
* @version 17.0.5
* @since 2023-01-14
*/
import java.util.*;

abstract class Piece {
  protected String team;
  protected int[] position;

  /*
   * Get team of piece
   * 
   * @return String - team of piece
   */
  public String getTeam() {
    return this.team;
  }

  /*
   * Get position of piece
   * 
   * @return int[] - position of piece
   */
  public int[] getPosition() {
    return this.position;
  }

  /*
   * Returns list of every valid move the piece can currently make
   * 
   * @param gameBoard - Gameboard type - gameBoard the piece is on
   * 
   * @return List<int[]> - Every valid move the piece can currently make
   */
  public List<int[]> validMoves(Gameboard gameBoard) {
    List<int[]> possibleCaptures = possibleCaptures(gameBoard);
    List<int[]> possibleRepositioning = possibleRepositioning(gameBoard);
    if (possibleCaptures.isEmpty()) {
      return possibleRepositioning;
    }
    return possibleCaptures;
  }

  /*
   * Set position of piece
   * 
   * @param position - int[] type - position to set piece to
   */
  public void setPosition(int[] position) {
    this.position = position;
  }

  public abstract List<int[]> possibleRepositioning(Gameboard gameBoard);

  public abstract List<int[]> possibleCaptures(Gameboard gameBoard);
}
