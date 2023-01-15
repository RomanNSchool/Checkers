/*
* Creates a Game object that handles gameplay
* @author Roman Navarrete, Lucas Huang, Mohamed Jammal, Jeremy Kwan
* @version 17.0.5
* @since 2023-01-14
*/
import java.util.*;

class Game {
  Scanner sc = new Scanner(System.in);
  HashMap<String, String> playerTeam =  new HashMap<String, String>();
  Gameboard gameBoard = new Gameboard();
  public Game(String player1, String player2) {
    playerTeam.put("cat", player1); 
    playerTeam.put("mouse", player2); 
  }
  /*
   * Handles gameplay 
   */
  public void play(Boolean playAi) {
    Ai ai = new Ai();
    String team = "cat";
    String winner;
    // only start next round if nobody has won
    do {
      // get initial number of pieces
      int noOfCatPieces = gameBoard.getCatPieces().size();
      int noOfMousePieces = gameBoard.getMousePieces().size();
      int totalPieces = noOfCatPieces + noOfMousePieces;
      if(team.equals("cat") || !playAi){
          round(team);
      } else{
        List<int[]> aiMove = ai.miniMax(gameBoard);
        gameBoard.setPiece(aiMove.get(1),gameBoard.getPiece(aiMove.get(0)));
      }
      // get number of pieces after round
      int newNoOfCatPieces = gameBoard.getCatPieces().size();
      int newNoOfMousePieces = gameBoard.getMousePieces().size();
      int newTotalPieces = newNoOfCatPieces + newNoOfMousePieces;
      // switch teams for next round if no captures have been made or last played piece cannot capture any other pieces
      if(newTotalPieces == totalPieces || gameBoard.getLastPlayedPiece().possibleCaptures(gameBoard).isEmpty() == true ){
        if (gameBoard.getLastPlayedPiece().getTeam().equals("cat")){
          team = "mouse";
        } else {
          team = "cat";
        }
      }
      winner = hasWon(gameBoard);
    } while (winner == "");
    System.out.print("\033[H\033[2J");
    System.out.flush();
    gameBoard.displayBoard();
    System.out.println();
    System.out.println(playerTeam.get(winner) + " has won!");
  }
  /*
   * Check if any player has won
   * @return String - the winning team, or empty string if no team has won
   */
  public String hasWon(Gameboard gameBoard) {
      int noOfCatPieces = gameBoard.getCatPieces().size();
      int noOfMousePieces = gameBoard.getMousePieces().size();
      List<Piece> moveableCatPieces = gameBoard.piecesThatCanMove("cat");
      List<Piece> moveableMousePieces = gameBoard.piecesThatCanMove("mouse");
      if (noOfCatPieces == 0){
        // mouse team wins if cat team has no pieces
        return "mouse";
      } else if(noOfMousePieces == 0){
        // cat team wins if mouse team has no pieces
        return "cat";
      } else if(moveableCatPieces.isEmpty() && moveableMousePieces.isEmpty()){
        // if there is a stalemate, the team that moved last wins
        return gameBoard.getLastPlayedPiece().getTeam();
      } else{
        return "";
      }
  }
  /*
   * Handles each round of checkers
   * @param team - String type - team that is playing in the round
   */
  public void round(String team) {
    //clear console
    System.out.print("\033[H\033[2J");
    //Reset the console with .flush, and output new display board
    System.out.flush();
    gameBoard.displayBoard();
    System.out.println(playerTeam.get(team) + "'s turn (" + team +").");
    String userPositionInput;
    Piece piece = null;
    List<int[]> validMoves = null;
    Boolean validUserInput = false;
    do {
      System.out.print("Select piece: ");
      userPositionInput = sc.nextLine().toLowerCase();
      //ensure that input is valid
      if(userPositionInput.length() == 2 && (userPositionInput.charAt(0) == 'a' || userPositionInput.charAt(0) == 'b' ||userPositionInput.charAt(0) == 'c' ||userPositionInput.charAt(0) == 'd' ||userPositionInput.charAt(0) == 'e' ||userPositionInput.charAt(0) == 'f' ||userPositionInput.charAt(0) == 'g' ||userPositionInput.charAt(0) == 'h') && (userPositionInput.charAt(1) == '1' || userPositionInput.charAt(1) == '2' ||userPositionInput.charAt(1) == '3' ||userPositionInput.charAt(1) == '4' ||userPositionInput.charAt(1) == '5' ||userPositionInput.charAt(1) == '6' ||userPositionInput.charAt(1) == '7' ||userPositionInput.charAt(1) == '8')){
        piece = gameBoard.getPiece(playerInputToPos(userPositionInput));
        // ensure that piece exists, is of the same team as the player, and has valid moves
        if (piece != null && piece.getTeam().equals(team) && !(piece.validMoves(gameBoard).isEmpty())){
          List<Piece> piecesThatCanCapture = gameBoard.piecesThatCanCapture(team); 
          // if team has pieces that are able to capture other pieces, ensure that the user selected one of those pieces (force capture)
          if(piecesThatCanCapture.isEmpty() == false){
            for(int i = 0; i < piecesThatCanCapture.size();i++){
              if(piecesThatCanCapture.get(i) ==(piece)){
                validUserInput = true;
              }
            }
          }else{
            validUserInput = true;
          }
        }
      }      
    } while (!validUserInput);
    validMoves = piece.validMoves(gameBoard);
    int[] position = null;
    userPositionInput = "";
    validUserInput = false;
    do{
      System.out.print("Move to: ");
      userPositionInput = sc.nextLine().toLowerCase();
      //ensure that input is valid
      if(userPositionInput.length() == 2 && (userPositionInput.charAt(0) == 'a' || userPositionInput.charAt(0) == 'b' ||userPositionInput.charAt(0) == 'c' ||userPositionInput.charAt(0) == 'd' ||userPositionInput.charAt(0) == 'e' ||userPositionInput.charAt(0) == 'f' ||userPositionInput.charAt(0) == 'g' ||userPositionInput.charAt(0) == 'h') && (userPositionInput.charAt(1) == '1' || userPositionInput.charAt(1) == '2' ||userPositionInput.charAt(1) == '3' ||userPositionInput.charAt(1) == '4' ||userPositionInput.charAt(1) == '5' ||userPositionInput.charAt(1) == '6' ||userPositionInput.charAt(1) == '7' ||userPositionInput.charAt(1) == '8')){
        position = playerInputToPos(userPositionInput);
        //ensure that position is a valid move for the piece
        for(int i = 0; i<validMoves.size();i++){
          if(Arrays.equals(validMoves.get(i),position)){
            validUserInput = true;
          }
        }
      }
    }while(!validUserInput);
    //move piece
    gameBoard.setPiece(position,piece);
  }
  /*
   * convert player input into int array
   * @param input - String type - position as string 
   * @return int[] - position as int array
   */
  public int[] playerInputToPos(String input) {
    String[] characters = input.split("");
    int[] position = new int[2];
    if (characters[0].equals("a")) {
      position[1] = 0;
    } else if (characters[0].equals("b")) {
      position[1] = 1;
    } else if (characters[0].equals("c")) {
      position[1] = 2;
    } else if (characters[0].equals("d")) {
      position[1] = 3;
    } else if (characters[0].equals("e")) {
      position[1] = 4;
    } else if (characters[0].equals("f")) {
      position[1] = 5;
    } else if (characters[0].equals("g")) {
      position[1] = 6;
    } else if (characters[0].equals("h")) {
      position[1] = 7;
    }
    // subtract one because the numbers on the board start at 1, then subtract it from 7 because the order is reversed on the board
    position[0] = 7 - (Integer.parseInt(characters[1]) - 1);
    return position;
  }
}