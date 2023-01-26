/*
* Creates a AI object that gives the best move
* @author Roman Navarrete, Lucas Huang, Mohamed Jammal, Jeremy Kwan
* @version 17.0.5
* @since 2023-01-14
*/

import java.util.*;
import com.rits.cloning.*;

class Ai {
  private Cloner cloner = new Cloner();
  // easier to have strings as hashmap keys
  private HashMap<String, Integer> gameStates = new HashMap<String, Integer>();

  /*
   * Return best possible move for ai
   * 
   * @param gameBoard - Gameboard type - current gameboard
   * 
   * @return List<int[]> - holds best piece and move
   */
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
        // loop through every possible move each piece can make
        // save piece and move with the highest minimax value
        List<Piece> possiblePiecesTemp = gameBoardTemp.piecesThatCanMove("mouse");
        List<int[]> possibleMovesTemp = possiblePiecesTemp.get(i).validMoves(gameBoardTemp);
        location = possiblePiecesTemp.get(i).getPosition();
        move = possibleMovesTemp.get(j);
        gameBoardTemp.setPiece(possibleMovesTemp.get(j), possiblePiecesTemp.get(i));
        int miniMaxOnMove = miniMaxHelper(gameBoardTemp, 8, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
        if (miniMaxOnMove > greatestHeurisiticValue) {
          bestPiece = location;
          bestMove = move;
          greatestHeurisiticValue = miniMaxOnMove;
        }
      }
    }
    List<int[]> pieceAndMove = new ArrayList<int[]>();
    pieceAndMove.add(bestPiece);
    pieceAndMove.add(bestMove);
    // return best piece and move
    return pieceAndMove;
  }

  /*
   * Evaluate minimax value of current position
   * 
   * @param gameBoard - Gameboard type - current gameboard
   * 
   * @param depth - how many moves left to search
   * 
   * @param alpha - alpha value
   * 
   * @param beta - beta value
   * 
   * @param isMaximizing - boolean - if current player is maximizing
   */
  public int miniMaxHelper(Gameboard gameBoard, int depth, int alpha, int beta, Boolean isMaximizing) {
    Game game = new Game("cat", "mouse");
    if (game.hasWon(gameBoard).equals("cat")) {
      return -1000;
    } else if (game.hasWon(gameBoard).equals("mouse")) {
      return 1000;
    } else if (depth == 0) {
      // heuristic value
      // give points based on how many pieces are left
      int currentMouseScore = 0;
      List<Piece> mousePieces = gameBoard.getMousePieces();
      for (int i = 0; i < mousePieces.size(); i++) {
        if (mousePieces.get(i) instanceof King) {
          currentMouseScore += 10;
        } else {
          currentMouseScore += 5;
        }
      }
      int currentCatScore = 0;
      List<Piece> catPieces = gameBoard.getCatPieces();
      for (int i = 0; i < catPieces.size(); i++) {
        if (catPieces.get(i) instanceof King) {
          currentCatScore += 10;
        } else {
          currentCatScore += 5;
        }
      }
      // give points for controlling middle
      List<int[]> middleSquares = new ArrayList<int[]>();
      middleSquares.add(new int[] { 2, 3 });
      middleSquares.add(new int[] { 2, 5 });
      middleSquares.add(new int[] { 3, 2 });
      middleSquares.add(new int[] { 3, 4 });
      middleSquares.add(new int[] { 4, 3 });
      middleSquares.add(new int[] { 4, 5 });
      middleSquares.add(new int[] { 5, 2 });
      middleSquares.add(new int[] { 5, 4 });
      for (int i = 0; i < middleSquares.size(); i++) {
        Piece currentSquare = gameBoard.getPiece(middleSquares.get(i));
        if (currentSquare != null && currentSquare.getTeam().equals("cat")) {
          currentCatScore += 1;
        } else if (currentSquare != null && currentSquare.getTeam().equals("mouse")) {
          currentMouseScore += 1;
        }

      }
      // give points for keeping back row intact
      Piece backRowCat1 = gameBoard.getPiece(new int[] { 7, 2 });
      if (backRowCat1 != null && backRowCat1.getTeam().equals("cat")) {
        currentCatScore += 3;
      }
      Piece backRowCat2 = gameBoard.getPiece(new int[] { 7, 6 });
      if (backRowCat2 != null && backRowCat2.getTeam().equals("cat")) {
        currentCatScore += 3;
      }
      Piece backRowMouse1 = gameBoard.getPiece(new int[] { 0, 1 });
      if (backRowMouse1 != null && backRowMouse1.getTeam().equals("mouse")) {
        currentMouseScore += 3;
      }
      Piece backRowMouse2 = gameBoard.getPiece(new int[] { 0, 5 });
      if (backRowMouse2 != null && backRowMouse2.getTeam().equals("mouse")) {
        currentMouseScore += 3;
      }
      // include depth to encourage faster wins
      return currentMouseScore - currentCatScore + depth;
    }

    Gameboard gameBoardTemp = null;

    if (isMaximizing) {
      int value = Integer.MIN_VALUE;
      List<List<int[]>> allPiecesAndMoves = new ArrayList<List<int[]>>();
      List<Piece> possiblePieces = gameBoard.piecesThatCanMove("mouse");
      // collect all possible moves
      for (int i = 0; i < possiblePieces.size(); i++) {
        Piece currentPiece = possiblePieces.get(i);
        List<int[]> possibleMoves = currentPiece.validMoves(gameBoard);
        for (int j = 0; j < possibleMoves.size(); j++) {
          List<int[]> pieceAndMove = new ArrayList<int[]>();
          int[] location = currentPiece.getPosition();
          int[] move = possibleMoves.get(j);
          pieceAndMove.add(location);
          pieceAndMove.add(move);
          allPiecesAndMoves.add(pieceAndMove);
        }
      }
      // go through every possible move
      for (int i = 0; i < allPiecesAndMoves.size(); i++) {
        gameBoardTemp = cloner.deepClone(gameBoard);
        int noOfCatPieces = gameBoard.getCatPieces().size();
        int noOfMousePieces = gameBoard.getMousePieces().size();
        int totalPieces = noOfCatPieces + noOfMousePieces;

        gameBoardTemp.setPiece(allPiecesAndMoves.get(i).get(1),
            gameBoardTemp.getPiece(allPiecesAndMoves.get(i).get(0)));
        int newNoOfCatPieces = gameBoardTemp.getCatPieces().size();
        int newNoOfMousePieces = gameBoardTemp.getMousePieces().size();
        int newTotalPieces = newNoOfCatPieces + newNoOfMousePieces;
        String currentGameState = Arrays.deepToString(gameBoardTemp.boardToString());
        if (newTotalPieces == totalPieces
            || gameBoardTemp.getLastPlayedPiece().possibleCaptures(gameBoardTemp).isEmpty() == true) {

          if (gameStates.containsKey(currentGameState)) {
            value = Math.max(value, gameStates.get(currentGameState));
          } else {
            value = Math.max(value, miniMaxHelper(gameBoardTemp, depth - 1, alpha, beta, false));
            // add to hashmap if not found
            gameStates.put(Arrays.deepToString(gameBoardTemp.boardToString()), value);
          }

        } else {
          if (gameStates.containsKey(currentGameState)) {
            value = Math.max(value, gameStates.get(currentGameState));
          } else {
            value = Math.max(value, miniMaxHelper(gameBoardTemp, depth - 1, alpha, beta, true));
            gameStates.put(Arrays.deepToString(gameBoardTemp.boardToString()), value);
          }
        }
        if (value > beta) {
          i = allPiecesAndMoves.size();
        }
        alpha = Math.max(alpha, value);
      }
      return value;
    } else {
      // same as above but for opposing player
      int value = Integer.MAX_VALUE;
      List<List<int[]>> allPiecesAndMoves = new ArrayList<List<int[]>>();
      List<Piece> possiblePieces = gameBoard.piecesThatCanMove("cat");
      for (int i = 0; i < possiblePieces.size(); i++) {
        Piece currentPiece = possiblePieces.get(i);
        List<int[]> possibleMoves = currentPiece.validMoves(gameBoard);
        for (int j = 0; j < possibleMoves.size(); j++) {
          List<int[]> pieceAndMove = new ArrayList<int[]>();
          int[] location = currentPiece.getPosition();
          int[] move = possibleMoves.get(j);
          pieceAndMove.add(location);
          pieceAndMove.add(move);
          allPiecesAndMoves.add(pieceAndMove);
        }
      }

      for (int i = 0; i < allPiecesAndMoves.size(); i++) {
        gameBoardTemp = cloner.deepClone(gameBoard);
        int noOfCatPieces = gameBoard.getCatPieces().size();
        int noOfMousePieces = gameBoard.getMousePieces().size();
        int totalPieces = noOfCatPieces + noOfMousePieces;
        gameBoardTemp.setPiece(allPiecesAndMoves.get(i).get(1),
            gameBoardTemp.getPiece(allPiecesAndMoves.get(i).get(0)));
        int newNoOfCatPieces = gameBoardTemp.getCatPieces().size();
        int newNoOfMousePieces = gameBoardTemp.getMousePieces().size();
        int newTotalPieces = newNoOfCatPieces + newNoOfMousePieces;
        String currentGameState = Arrays.deepToString(gameBoardTemp.boardToString());
        if (newTotalPieces == totalPieces
            || gameBoardTemp.getLastPlayedPiece().possibleCaptures(gameBoardTemp).isEmpty() == true) {
          if (gameStates.containsKey(currentGameState)) {
            value = Math.min(value, gameStates.get(currentGameState));
          } else {
            value = Math.min(value, miniMaxHelper(gameBoardTemp, depth - 1, alpha, beta, true));
            gameStates.put(Arrays.deepToString(gameBoardTemp.boardToString()), value);
          }
        } else {
          if (gameStates.containsKey(currentGameState)) {
            value = Math.min(value, gameStates.get(currentGameState));
          } else {
            value = Math.min(value, miniMaxHelper(gameBoardTemp, depth - 1, alpha, beta, false));
            gameStates.put(Arrays.deepToString(gameBoardTemp.boardToString()), value);
          }
        }
        if (value < alpha) {
          i = allPiecesAndMoves.size();
        }
        beta = Math.min(beta, value);
      }
      return value;
    }

  }
}
