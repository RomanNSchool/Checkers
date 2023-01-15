/*
* Creates a King piece that can move backwards and forwards
* @author Roman Navarrete, Lucas Huang, Mohamed Jammal, Jeremy Kwan
* @version 17.0.5
* @since 2023-01-14
*/
import java.util.*;
public class King extends Piece {
  public King(String team, int[] position) {
    this.team = team;
    this.position = position;
  }

 /*
   * Get list of all possible repositioning moves the piece can make
   * @param gameBoard - Gameboard type - gameBoard the piece is on
   * @return List<int[]> - list of possible repositioning moves the piece can make
   */
  @Override
  public List<int[]> possibleRepositioning(Gameboard gameBoard) {
    List<int[]> moves = new ArrayList<int[]>();
    int advanceRowPosition;
      advanceRowPosition = position[0] + 1;
      //check diagonal to the bottom right
      if(position[0] !=7 && position[1] != 7 &&
      gameBoard.getPiece(new int[]{advanceRowPosition, position[1] + 1}) == null){
        moves.add(new int[]{advanceRowPosition,position[1]+1});
      } 
      //check diagonal to the bottom left
      if(position[0] !=7 && position[1] != 0 &&
      gameBoard.getPiece(new int[]{advanceRowPosition, position[1] - 1}) == null){
        moves.add(new int[]{advanceRowPosition,position[1]-1});
      } 
      advanceRowPosition = position[0] - 1;
      //check diagonal to the top right
      if(position[0] !=0 && position[1] != 7 &&
      gameBoard.getPiece(new int[]{advanceRowPosition, position[1] + 1}) == null){
        moves.add(new int[]{advanceRowPosition,position[1]+1});
      } 
      //check diagonal to the top left
      if(position[0] !=0 && position[1] != 0 &&
      gameBoard.getPiece(new int[]{advanceRowPosition, position[1] - 1}) == null){
        moves.add(new int[]{advanceRowPosition,position[1]-1});
      }  
    
    return moves;
  }
 /*
   * Get list of all possible capture moves the piece can make
   * @param gameBoard - Gameboard type - gameBoard the piece is on
   * @return List<int[]> - list of possible capture moves the piece can make
   */
  @Override
  public List<int[]> possibleCaptures(Gameboard gameBoard) {
    List<int[]> moves = new ArrayList<int[]>();
    String opposingTeam;
    if(team.equals("mouse")){
      opposingTeam = "cat";
    }else{
      opposingTeam = "mouse";
    }
    int advanceRowPosition;
      advanceRowPosition = position[0] + 1;
      //check diagonal to the bottom right
      if(position[0] < 6 && position[1] < 6 &&
        gameBoard.getPiece(new int[]{advanceRowPosition, position[1] + 1}) != null && //check if diagonal to the bottom right isn't empty
        gameBoard.getPiece(new int[]{advanceRowPosition, position[1] + 1}).getTeam().equals(opposingTeam) && // if diagonal to the bottom right isn't empty check to see if the piece is of the opposing team
        gameBoard.getPiece(new int[]{advanceRowPosition + 1, position[1] + 2}) == null){ //check to see if diagonal across is empty 
        moves.add(new int[]{advanceRowPosition+1,position[1]+2});
      }
      //check diagonal to the bottom left
      if(position[0] < 6 && position[1] > 1 &&
        gameBoard.getPiece(new int[]{advanceRowPosition, position[1] - 1}) != null && //check if diagonal to the bottom left isn't empty
        gameBoard.getPiece(new int[]{advanceRowPosition, position[1] - 1}).getTeam().equals(opposingTeam) && // if diagonal to the bottom left isn't empty check to see if the piece is of the opposing team
        gameBoard.getPiece(new int[]{advanceRowPosition + 1, position[1] - 2}) == null){ //check to see if diagonal across is empty 
        moves.add(new int[]{advanceRowPosition+1,position[1]-2});
      }
      advanceRowPosition = position[0] - 1;
      //check diagonal to the top right
      if(position[0] > 1 && position[1] < 6 &&
        gameBoard.getPiece(new int[]{advanceRowPosition, position[1] + 1}) != null && //check if diagonal to the top right isn't empty
        gameBoard.getPiece(new int[]{advanceRowPosition, position[1] + 1}).getTeam().equals(opposingTeam) && // if diagonal to the top right isn't empty check to see if the piece is of the opposing team
        gameBoard.getPiece(new int[]{advanceRowPosition - 1, position[1] + 2}) == null){ //check to see if diagonal across is empty 
        moves.add(new int[]{advanceRowPosition-1,position[1]+2});

      }
      //check diagonal to the top left
      if(position[0] > 1 && position[1] > 1 &&
        gameBoard.getPiece(new int[]{advanceRowPosition, position[1] - 1}) != null && //check if diagonal to the top left isn't empty
        gameBoard.getPiece(new int[]{advanceRowPosition, position[1] - 1}).getTeam().equals(opposingTeam) && // if diagonal to the top right isn't empty check to see if the piece is of the opposing team
        gameBoard.getPiece(new int[]{advanceRowPosition - 1, position[1] - 2}) == null){ //check to see if diagonal across is empty 
        moves.add(new int[]{advanceRowPosition-1,position[1]-2});
      }
    
    return moves;
  }

}