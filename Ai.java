import java.util.*;
import com.rits.cloning.*;
class Ai {
  Cloner cloner=new Cloner();
  // check every piece's moves
  // get highest move for each piece
  //
  // return piece and move
  public List<int[]> miniMax(Gameboard gameBoard) {
    int[] bestMove = null;
    int[] bestPiece = null;
    int[] location = null;
    int[] move = null;
    int greatestHeurisiticValue = Integer.MIN_VALUE;
    List<Piece> possiblePieces = gameBoard.piecesThatCanMove("mouse");
    for (int i = 0; i < possiblePieces.size(); i++) {
      List<int[]> possibleMoves = possiblePieces.get(i).validMoves(gameBoard);
      for (int j = 0; j < possibleMoves.size(); j++) {
        Gameboard gameBoardTemp = cloner.deepClone(gameBoard);
        List<int[]> possibleMovesTemp = cloner.deepClone(possibleMoves);
        List<Piece> possiblePiecesTemp = cloner.deepClone(possiblePieces);
        System.out.println(gameBoardTemp);
        location = possiblePieces.get(i).getPosition();
        move = possibleMoves.get(j);
        //System.out.println(possiblePieces.get(i));
        //System.out.println(Arrays.toString(possiblePieces.get(i).getPosition()) +""+Arrays.toString(possibleMoves.get(j)));
        System.out.println(Arrays.toString(possibleMoves.get(j)) +""+Arrays.toString(possiblePieces.get(i).getPosition()));
        gameBoardTemp.setPiece(possibleMovesTemp.get(j), possiblePiecesTemp.get(i));
        /*
        if (miniMaxHelper(gameBoardTemp, 10, false) > greatestHeurisiticValue) {
          bestPiece = location;
          bestMove = move;
        }
*/
      }
    }
    List<int[]> pieceAndMove = new ArrayList<int[]>();
    pieceAndMove.add(bestPiece);
    pieceAndMove.add(bestMove);
    return pieceAndMove;
  }

  public int miniMaxHelper(Gameboard gameBoard, int depth, Boolean isMaximizing){
    Game game = new Game("cat","mouse");
    if(game.hasWon(gameBoard).equals("cat")){
      return -12;
    }else if(game.hasWon(gameBoard).equals("mouse")){
      return 12;
    }else if(depth == 0){
      return (gameBoard.getMousePieces().size()-gameBoard.getCatPieces().size());
    }
    Gameboard gameBoardTemp;
    if(isMaximizing){
      int value = Integer.MIN_VALUE;
      List<List<int[]>> allPieceAndMoves = new ArrayList<List<int[]>>();
      for(int i = 0; i<gameBoard.piecesThatCanMove("mouse").size();i++){
        List<int[]> possibleMoves = gameBoard.piecesThatCanMove("mouse").get(i).validMoves(gameBoard);
        for(int j = 0; j < possibleMoves.size(); j++){
          List<int[]> pieceAndMove = new ArrayList<int[]>();
          int[]location = gameBoard.piecesThatCanMove("mouse").get(i).getPosition();
          int[]move = possibleMoves.get(j);
          pieceAndMove.add(location);
          pieceAndMove.add(move);
          allPieceAndMoves.add(pieceAndMove);
        }
      }

      for(int i = 0;i<allPieceAndMoves.size();i++){
        gameBoardTemp = cloner.deepClone(gameBoard);
        int noOfCatPieces = gameBoard.getCatPieces().size();
        int noOfMousePieces = gameBoard.getMousePieces().size();
        int totalPieces = noOfCatPieces + noOfMousePieces;
        gameBoardTemp.setPiece(allPieceAndMoves.get(i).get(1),gameBoardTemp.getPiece(allPieceAndMoves.get(i).get(0)));
        int newNoOfCatPieces = gameBoard.getCatPieces().size();
        int newNoOfMousePieces = gameBoard.getMousePieces().size();
        int newTotalPieces = newNoOfCatPieces + newNoOfMousePieces;
        if(newTotalPieces == totalPieces || gameBoard.getLastPlayedPiece().possibleCaptures(gameBoard).isEmpty() == true ){
          value = Math.max(value, miniMaxHelper(gameBoardTemp, depth - 1, false));
        }else{
          value = Math.max(value, miniMaxHelper(gameBoardTemp, depth - 1, true));
        }
      }
      
    }else{
      int value = Integer.MAX_VALUE;
      List<List<int[]>> allPieceAndMoves = new ArrayList<List<int[]>>();
      for(int i = 0; i<gameBoard.piecesThatCanMove("cat").size();i++){
        List<int[]> possibleMoves = gameBoard.piecesThatCanMove("cat").get(i).validMoves(gameBoard);
        for(int j = 0; j < possibleMoves.size(); j++){
          List<int[]> pieceAndMove = new ArrayList<int[]>();
          int[]location = gameBoard.piecesThatCanMove("cat").get(i).getPosition();
          int[]move = possibleMoves.get(j);
          pieceAndMove.add(location);
          pieceAndMove.add(move);
          allPieceAndMoves.add(pieceAndMove);
        }
      }

      for(int i = 0;i<allPieceAndMoves.size();i++){
        gameBoardTemp = cloner.deepClone(gameBoard);
        int noOfCatPieces = gameBoard.getCatPieces().size();
        int noOfMousePieces = gameBoard.getMousePieces().size();
        int totalPieces = noOfCatPieces + noOfMousePieces;
        gameBoardTemp.setPiece(allPieceAndMoves.get(i).get(1),gameBoardTemp.getPiece(allPieceAndMoves.get(i).get(0)));
        int newNoOfCatPieces = gameBoard.getCatPieces().size();
        int newNoOfMousePieces = gameBoard.getMousePieces().size();
        int newTotalPieces = newNoOfCatPieces + newNoOfMousePieces;
        if(newTotalPieces == totalPieces || gameBoard.getLastPlayedPiece().possibleCaptures(gameBoard).isEmpty() == true ){
          value = Math.max(value, miniMaxHelper(gameBoardTemp, depth - 1, true));
        }else{
          value = Math.max(value, miniMaxHelper(gameBoardTemp, depth - 1, false));
        }
      }
      return value;
    }
    return 21;
  }


  
}
/*
 * function minimax( node, depth, maximizingPlayer ) is
 * if depth = 0 or node is a terminal node then
 * return the heuristic value of node
 * if maximizingPlayer then
 * value := −∞
 * for each child of node do
 * value := max( value, minimax( child, depth − 1, FALSE ) )
 * return value
 * else (* minimizing player *)
 * value := +∞
 * for each child of node do
 * value := min( value, minimax( child, depth − 1, TRUE ) )
 * return value
 * 
 * 
 * 
 * 
 * 
 * public static int minimax(int stones) {
 * // make arraylist allMoves of size 3, with the values 1, 2, 3 representing
 * the
 * // possible moves respectively for each index
 * ArrayList<Integer> allMoves = new ArrayList<Integer>(Arrays.asList(1, 2, 3));
 * // remove moves that aren't possible (moves that would cause the pile to go
 * // below 0)
 * for (int i = allMoves.size() - 1; i >= 0; i--) {
 * if (stones - allMoves.get(i) < 0) {
 * allMoves.remove(i);
 * }
 * }
 * // calls minimaxHelper on each possible move and replaces it with the value
 * // minimaxHelper returns
 * for (int i = 0; i < allMoves.size(); i++) {
 * allMoves.set(i, minimaxHelper(stones - allMoves.get(i), false));
 * }
 * // return the index with the highest minimaxHelper score + 1 (as indexes
 * start
 * // at 0)
 * return allMoves.indexOf(Collections.max(allMoves)) + 1;
 * }
 * 
 * /*
 * Give the minimax score of the current game
 * 
 * @param node - int type - number of stones remaining in the pile
 * 
 * @param maximizingPlayer - Boolean type - if the current player is the
 * maximizing player
 * 
 * @return int - minimax score of current game
 */
/*
 * public static int minimaxHelper(int node, boolean maximizingPlayer) {
 * if (node == 0) {
 * if (maximizingPlayer) {
 * return -1;
 * } else {
 * return 1;
 * }
 * }
 * if (maximizingPlayer) {
 * int value = Integer.MIN_VALUE;
 * for (int i = 1; i <= 3; i++) {
 * if (node - i >= 0) {
 * value = Math.max(value, minimaxHelper(node - i, false));
 * }
 * }
 * return value;
 * } else {
 * int value = Integer.MAX_VALUE;
 * for (int i = 1; i <= 3; i++) {
 * if (node - i >= 0) {
 * value = Math.min(value, minimaxHelper(node - i, true));
 * }
 * }
 * return value;
 * }
 * }
 */