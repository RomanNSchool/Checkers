
/*
* Creates a Normal piece that can only move forward diagonally
* @author Roman Navarrete, Lucas Huang, Mohamed Jammal, Jeremy Kwan
* @version 17.0.5
* @since 2023-01-14
*/
import java.util.*;
  /*
   * Creates a normal piece object with the specified team and position
   * 
   * @param team - String type - team of piece, cat or mouse
   * @param position - int[] type - position of piece on board
   */
public class Normal extends Piece {
  public Normal(String team, int[] position) {
    this.team = team;
    this.position = position;
  }

  /*
   * Get list of all possible repositioning moves the piece can make
   * 
   * @param gameBoard - Gameboard type - gameBoard the piece is on
   * 
   * @return List<int[]> - list of possible repositioning moves the piece can make
   */
  @Override
  public List<int[]> possibleRepositioning(Gameboard gameBoard) {
    List<int[]> moves = new ArrayList<int[]>();
    int advanceRowPosition;
    if (team.equals("mouse")) {
      // mice move down
      advanceRowPosition = position[0] + 1;
      // check diagonal to the right
      if (position[0] != 7 && position[1] != 7 &&
          gameBoard.getPiece(new int[] { advanceRowPosition, position[1] + 1 }) == null) {
        moves.add(new int[] { advanceRowPosition, position[1] + 1 });
      }
      // check diagonal to the left
      if (position[0] != 7 && position[1] != 0 &&
          gameBoard.getPiece(new int[] { advanceRowPosition, position[1] - 1 }) == null) {
        moves.add(new int[] { advanceRowPosition, position[1] - 1 });
      }
    } else {
      // cats move up
      advanceRowPosition = position[0] - 1;
      // check diagonal to the right
      if (position[0] != 0 && position[1] != 7 &&
          gameBoard.getPiece(new int[] { advanceRowPosition, position[1] + 1 }) == null) {
        moves.add(new int[] { advanceRowPosition, position[1] + 1 });
      }
      // check diagonal to the left
      if (position[0] != 0 && position[1] != 0 &&
          gameBoard.getPiece(new int[] { advanceRowPosition, position[1] - 1 }) == null) {
        moves.add(new int[] { advanceRowPosition, position[1] - 1 });
      }
    }
    return moves;
  }

  /*
   * Get list of all possible capture moves the piece can make
   * 
   * @param gameBoard - Gameboard type - gameBoard the piece is on
   * 
   * @return List<int[]> - list of possible capture moves the piece can make
   */
  @Override
  public List<int[]> possibleCaptures(Gameboard gameBoard) {
    List<int[]> moves = new ArrayList<int[]>();
    int advanceRowPosition;
    if (team.equals("mouse")) {
      // mice move down
      advanceRowPosition = position[0] + 1;
      // check diagonal to the right
      if (position[0] < 6 && position[1] < 6 &&
          gameBoard.getPiece(new int[] { advanceRowPosition, position[1] + 1 }) != null && // check if diagonal to the
                                                                                           // right isn't empty
          gameBoard.getPiece(new int[] { advanceRowPosition, position[1] + 1 }).getTeam().equals("cat") && // check if enemy
          gameBoard.getPiece(new int[] { advanceRowPosition + 1, position[1] + 2 }) == null) { // check to see if
                                                                                               // diagonal across is
                                                                                               // empty
        moves.add(new int[] { advanceRowPosition + 1, position[1] + 2 });
      }
      // check diagonal to the left
      if (position[0] < 6 && position[1] > 1 &&
          gameBoard.getPiece(new int[] { advanceRowPosition, position[1] - 1 }) != null && // check if diagonal to the
                                                                                           // left isn't empty
          gameBoard.getPiece(new int[] { advanceRowPosition, position[1] - 1 }).getTeam().equals("cat") && // check if enemy
          gameBoard.getPiece(new int[] { advanceRowPosition + 1, position[1] - 2 }) == null) { // check to see if
                                                                                               // diagonal across is
                                                                                               // empty
        moves.add(new int[] { advanceRowPosition + 1, position[1] - 2 });
      }
    } else {
      // cats move down
      advanceRowPosition = position[0] - 1;
      // check diagonal to the right
      if (position[0] > 1 && position[1] < 6 &&
          gameBoard.getPiece(new int[] { advanceRowPosition, position[1] + 1 }) != null && // check if diagonal to the
                                                                                           // right isn't empty
          gameBoard.getPiece(new int[] { advanceRowPosition, position[1] + 1 }).getTeam().equals("mouse") && // check if enemy
          gameBoard.getPiece(new int[] { advanceRowPosition - 1, position[1] + 2 }) == null) { // check to see if
                                                                                               // diagonal across is
                                                                                               // empty
        moves.add(new int[] { advanceRowPosition - 1, position[1] + 2 });

      }
      // check diagonal to the left
      if (position[0] > 1 && position[1] > 1 &&
          gameBoard.getPiece(new int[] { advanceRowPosition, position[1] - 1 }) != null && // check if diagonal to the
                                                                                           // left isn't empty
          gameBoard.getPiece(new int[] { advanceRowPosition, position[1] - 1 }).getTeam().equals("mouse") && // check if enemy
          gameBoard.getPiece(new int[] { advanceRowPosition - 1, position[1] - 2 }) == null) { // check to see if
                                                                                               // diagonal across is
                                                                                               // empty
        moves.add(new int[] { advanceRowPosition - 1, position[1] - 2 });
      }
    }
    return moves;
  }

}