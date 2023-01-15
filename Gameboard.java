/*
* Creates a Gameboard object that holds the pieces and manipulates their positions
* @author Roman Navarrete, Lucas Huang, Mohamed Jammal, Jeremy Kwan
* @version 17.0.5
* @since 2023-01-14
*/

import java.util.*;

class Gameboard {
  // 2D Piece Array 
  private Piece[][] board;
  private List<Piece> mousePieces = new ArrayList<Piece>();
  private List<Piece> catPieces = new ArrayList<Piece>();
  private Piece lastPlayedPiece = null;
  
  public Gameboard() {
    //create all the piece objects
    resetPieces();
    //set board to base state
    board = resetBoard(this.mousePieces, this.catPieces);
  }
  //copy constructor
  public Gameboard(Gameboard gameBoard){
    this.board = gameBoard.getBoard();
    this.mousePieces = gameBoard.getMousePieces();
    this.catPieces = gameBoard.getCatPieces();
    this.lastPlayedPiece = gameBoard.getLastPlayedPiece();
  }
  public Piece[][] getBoard(){
    return board;
  }
  /*
   * Get mouse pieces on board
   * 
   * @return List<Piece>  - mouse pieces on board
   */
  public List<Piece> getMousePieces(){
    return mousePieces;
  }
  /*
   * Get cat pieces on board
   * 
   * @return List<Piece>  -  cat pieces on board
   */
  public List<Piece> getCatPieces(){
    return catPieces;
  }
  /*
   * Get last played piece
   * 
   * @return Piece - last played piece
   */
  public Piece getLastPlayedPiece(){
    return lastPlayedPiece;
  }
  /*
   * Get piece at position
   * @param position - int[] type - position of piece to get
   * @return Piece - Piece at position
   */
  public Piece getPiece(int[] position) {
    int row = position[0];
    int column = position[1];
    return this.board[row][column];
  }
  /*
   * Get all the pieces of a team that can capture a piece of another team
   * @param team - String type - team to check
   * @return List<Piece> - list of pieces of a team that can capture a piece of another team
   */
  public List<Piece> piecesThatCanCapture(String team){
    List<Piece> pieces = new ArrayList<Piece>();
    if(team.equals("mouse")){
      for(int i = 0; i < mousePieces.size(); i++){
        //add to list if piece can capture another piece
        if(mousePieces.get(i).possibleCaptures(this).isEmpty() == false){
          pieces.add(mousePieces.get(i));
        }
      }
    }else{
      for(int i = 0; i < catPieces.size(); i++){
        if(catPieces.get(i).possibleCaptures(this).isEmpty() == false){
          pieces.add(catPieces.get(i));
        }
      }    
    }
    return pieces;
  }
  /*
   * Get all the pieces of a team that have a move
   * @param team - String type - team to check
   * @return List<Piece> - list of pieces of a team that have a move
   */
  public List<Piece> piecesThatCanMove(String team){
    List<Piece> pieces = new ArrayList<Piece>();
    if(team.equals("mouse")){
      for(int i = 0; i < mousePieces.size(); i++){
        if(mousePieces.get(i).validMoves(this).isEmpty() == false){
          pieces.add(mousePieces.get(i));
        }
      }
    }else{
      for(int i = 0; i < catPieces.size(); i++){
        if(catPieces.get(i).validMoves(this).isEmpty() == false){
          pieces.add(catPieces.get(i));
        }
      }    
    }
    return pieces;
  }

  /*
   * Create all the piece objects and add them to the list
   * 
   */ 
  public void resetPieces() {
    mousePieces.add(new Normal("mouse", new int[] { 0, 1}));
    mousePieces.add(new Normal("mouse", new int[] { 0, 3 }));
    mousePieces.add(new Normal("mouse", new int[] { 0, 5 }));
    mousePieces.add(new Normal("mouse", new int[] { 0, 7 }));
    mousePieces.add(new Normal("mouse", new int[] { 1, 0 }));
    mousePieces.add(new Normal("mouse", new int[] { 1, 2 }));
    mousePieces.add(new Normal("mouse", new int[] { 1, 4 }));
    mousePieces.add(new Normal("mouse", new int[] { 1, 6 }));
    mousePieces.add(new Normal("mouse", new int[] { 2, 1 }));
    mousePieces.add(new Normal("mouse", new int[] { 2, 3 }));
    mousePieces.add(new Normal("mouse", new int[] { 2, 5 }));
    mousePieces.add(new Normal("mouse", new int[] { 2, 7 }));
    catPieces.add(new Normal("cat", new int[] { 5, 0 }));
    catPieces.add(new Normal("cat", new int[] { 5, 2 }));
    catPieces.add(new Normal("cat", new int[] { 5, 4 }));
    catPieces.add(new Normal("cat", new int[] { 5, 6 }));
    catPieces.add(new Normal("cat", new int[] { 6, 1 }));
    catPieces.add(new Normal("cat", new int[] { 6, 3 }));
    catPieces.add(new Normal("cat", new int[] { 6, 5 }));
    catPieces.add(new Normal("cat", new int[] { 6, 7 }));
    catPieces.add(new Normal("cat", new int[] { 7, 0 }));
    catPieces.add(new Normal("cat", new int[] { 7, 2 }));
    catPieces.add(new Normal("cat", new int[] { 7, 4 }));
    catPieces.add(new Normal("cat", new int[] { 7, 6 }));
  }

  /*
   * Return board in starting state
   * @param mousePieces - List<Piece> type - list of all mouse pieces
   * @param catPieces - List<Piece> type - list of all cat pieces
   * @return Piece[][] - 2D Array of board base state
   */
  public Piece[][] resetBoard(List<Piece> mousePieces, List<Piece> catPieces) {
    Piece[][] startingPositions = new Piece[][] {
        { null, mousePieces.get(0), null, mousePieces.get(1), null, mousePieces.get(2), null, mousePieces.get(3) },
        { mousePieces.get(4), null, mousePieces.get(5), null, mousePieces.get(6), null, mousePieces.get(7), null },
        { null, mousePieces.get(8), null, mousePieces.get(9), null, mousePieces.get(10), null, mousePieces.get(11) },
        { null, null, null, null, null, null, null, null },
        { null, null, null, null, null, null, null, null },
        { catPieces.get(0), null, catPieces.get(1), null, catPieces.get(2), null, catPieces.get(3), null },
        { null, catPieces.get(4), null, catPieces.get(5), null, catPieces.get(6), null, catPieces.get(7) },
        { catPieces.get(8), null, catPieces.get(9), null, catPieces.get(10), null, catPieces.get(11), null }
    };
    return startingPositions;
  }

  /*
   * Set a piece to a specific position, remove pieces that it captured, and promote it if necessary
   * @param position - int[] type - position where piece will be set to, position[0] is row, position[1] is column
   * @param piece - Piece type - piece to set
   */
  public void setPiece(int[] position, Piece piece) {
    int row = position[0];
    int column = position[1];
      // check to see if it captured a piece when moving upwards diagonally to the right
      if(position[0] == piece.getPosition()[0]-2 && position[1] == piece.getPosition()[1]+2){
        removePiece(new int[]{piece.getPosition()[0]-1,piece.getPosition()[1]+1},true);
        //check to see if piece was captured moving upwards diagonally to the left
      } else if(position[0] == piece.getPosition()[0]-2 && position[1] == piece.getPosition()[1]-2){
        removePiece(new int[]{piece.getPosition()[0]-1,piece.getPosition()[1]-1},true);
        //check to see if piece was captured moving downwards diagonally to the right
      } else if(position[0] == piece.getPosition()[0]+2 && position[1] == piece.getPosition()[1]+2){
        removePiece(new int[]{piece.getPosition()[0]+1,piece.getPosition()[1]+1},true);
        //check to see if piece was captured moving downwards diagonally to the left
      } else if(position[0] == piece.getPosition()[0]+2 && position[1] == piece.getPosition()[1]-2){
        removePiece(new int[]{piece.getPosition()[0]+1,piece.getPosition()[1]-1},true);
      }
      // remove piece from its current square, second parameter is false because piece is still in play
      removePiece(piece.getPosition(),false);
      this.board[row][column] = piece;
      // update piece's position
      piece.setPosition(position);
      // if piece can be promoted, promote it
      if((piece.getTeam().equals("cat") && position[0] == 0) || (piece.getTeam().equals("mouse") && position[0] == 7)){
        promotion(position);
      } 
      lastPlayedPiece = piece;
  }
  /*
   * Promote piece at position
   * @param position - int[] type - position of piece to promote
   */
  public void promotion(int[] position){
    Piece piece = getPiece(position);
    String team = piece.getTeam();
    int index; 
    if(team.equals("cat")){
      index = catPieces.indexOf(piece);
      catPieces.set(index,new King(team, position));
      board[position[0]][position[1]] = catPieces.get(index);
    }else{
      index = mousePieces.indexOf(piece);
      mousePieces.set(mousePieces.indexOf(piece),new King(team, position));
      board[position[0]][position[1]] = mousePieces.get(index);
    }
  }
  /*
   * Remove piece at position
   * @param position - int[] type - position of piece to remove
   * @param captured - Boolean type - has piece been captured
   */
  public void removePiece(int[] position, boolean captured){
    Piece piece = board[position[0]][position[1]];
    //if piece is captured, remove it from its team's list of pieces
    if(captured){
      if(piece.getTeam().equals("cat")){
        catPieces.remove(piece);
      }else{
        mousePieces.remove(piece);
      }
    }
    board[position[0]][position[1]] = null;
  }
  /*
   * Display board in console
   * 
   */
  public void displayBoard(){
    String[][] textBoard = new String[8][8];
    // recreate 2D array in string format
    for(int i = 0; i < 8; i++){
      for(int z = 0; z < 8; z++){
        Piece pieceAtIndex = board[i][z];
        if(pieceAtIndex == null){
          textBoard[i][z] = " ";
        } else if(pieceAtIndex.getTeam().equals("mouse") && pieceAtIndex instanceof Normal){
          textBoard[i][z] = "m";
        } else if(pieceAtIndex.getTeam().equals("cat") && pieceAtIndex instanceof Normal){
          textBoard[i][z] = "c";
        } else if(pieceAtIndex.getTeam().equals("mouse") && pieceAtIndex instanceof King){
          textBoard[i][z] = "M";
        } else if(pieceAtIndex.getTeam().equals("cat") && pieceAtIndex instanceof King){
          textBoard[i][z] = "C";
        }
      }
    }
    System.out.println();
    System.out.println("  A B C D E F G H ");
    System.out.println(" _________________");
    System.out.println(
      "8|"+ textBoard[0][0] +"|"+ textBoard[0][1] + "|"+ textBoard[0][2] + "|"+ textBoard[0][3] +"|"+textBoard[0][4] +"|"+ textBoard[0][5] +"|"+ textBoard[0][6] +"|"+ textBoard[0][7] + "|"+ "\n" +   
      "7|"+textBoard[1][0] + "|"+ textBoard[1][1] + "|"+ textBoard[1][2] +"|"+textBoard[1][3] +"|"+textBoard[1][4] + "|"+ textBoard[1][5] + "|"+ textBoard[1][6] + "|"+ textBoard[1][7] + "|"+"\n" + 
      "6|"+textBoard[2][0] + "|"+ textBoard[2][1] + "|"+ textBoard[2][2] + "|"+ textBoard[2][3] + "|"+textBoard[2][4] + "|"+ textBoard[2][5] +"|"+ textBoard[2][6] + "|"+ textBoard[2][7] +"|"+"\n" + 
      "5|"+textBoard[3][0] + "|"+ textBoard[3][1] + "|"+ textBoard[3][2] +"|"+textBoard[3][3] +"|"+textBoard[3][4] + "|"+textBoard[3][5] + "|"+textBoard[3][6] + "|"+ textBoard[3][7] +"|"+"\n" + 
      "4|"+textBoard[4][0] + "|"+textBoard[4][1] + "|"+ textBoard[4][2] +"|"+textBoard[4][3] +"|"+textBoard[4][4] +"|"+ textBoard[4][5] + "|"+textBoard[4][6] + "|"+ textBoard[4][7] +"|"+"\n" + 
      "3|"+textBoard[5][0] + "|"+ textBoard[5][1] +"|"+ textBoard[5][2] +"|"+textBoard[5][3] +"|"+textBoard[5][4] + "|"+textBoard[5][5] + "|"+textBoard[5][6] + "|"+textBoard[5][7] +"|"+"\n" + 
      "2|"+textBoard[6][0] +"|"+ textBoard[6][1] + "|"+textBoard[6][2] +"|"+textBoard[6][3] +"|"+textBoard[6][4] + "|"+textBoard[6][5] + "|"+textBoard[6][6] + "|"+textBoard[6][7] +"|"+"\n" + 
      "1|"+textBoard[7][0] + "|"+textBoard[7][1] + "|"+textBoard[7][2] +"|"+textBoard[7][3] +"|"+textBoard[7][4] + "|"+textBoard[7][5] + "|"+textBoard[7][6] + "|"+textBoard[7][7]+ "|");
    System.out.println(" -----------------");
  }
}