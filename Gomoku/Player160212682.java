import java.awt.Color;
import java.util.*;

/*
This AI was created by me, Mikail Tekneci.
Some sections are from Chimnoy007's Gomoku AI, but formatted to work with the GomokuReferee class provided.
Link to Chimnoy007 Gomoku AI: https://github.com/Chinmoy007/Gomoku-AI
*/
class Player160212682 extends GomokuPlayer {
	private final int MAX_DEPTH = 2;
	private boolean FIRST_MOVE = true;
	
	private HashMap<String, Integer> patternME = new HashMap<String, Integer>();
	private Color myColor;
	private HashMap<String, Integer> patternOPP = new HashMap<String, Integer>();
	private Color oppColor;
	
	private String[] patternListWhite = { 
					"WWWWW", "-WWWW-", "WWWW-", "WW-WW", "WWW-W", 
					"W-WWW-", "-WWW-", "WWW--", "-WW-W", "WW-W-", 
					"-WW-W-", "--WW-", "WW---", "W---W", "W----", 
					"--W--", "-W---" };
	private String[] patternListBlack = { 
					"BBBBB", "-BBBB-", "BBBB-", "BB-BB", "BBB-B", 
					"B-BBB-", "-BBB-", "BBB--", "-BB-B", "BB-B-", 
					"-BB-B-", "--BB-", "BB---", "B---B", "B----", 
					"--B--", "-B---" };
	private int[] scoring = { 
					200000000, 50000, 15000, 420, 420, 480, 350, 
					300, 100, 100, 100, 25, 25, 25, 2, 2, 2 };
					
	private int[] allDirX = {0, 0, 1, -1, -1, 1, -1, 1};
	private int[] allDirY = {-1, 1, 0, 0, 1, 1, -1, -1};
	private int[] directionX = {0, 1, 1, -1};
	private int[] directionY = {1, 0, 1, 1};

	public Move chooseMove(Color[][] board, Color me) {
		myColor = me;
		oppColor = (me == Color.white) ? Color.black : Color.white;
		if(patternME.isEmpty())
			generatePatternLists();
		if(FIRST_MOVE) {
			FIRST_MOVE = false;
			if(board[4][4] == null)
				return new Move(4, 4);
			else
				return new Move(4, 3);
		}
		int[] chosenMove = minimaxMAX(board, true, 0, 
							Integer.MIN_VALUE, Integer.MAX_VALUE);
		return new Move(chosenMove[1], chosenMove[2]);
	}
	
	private void generatePatternLists() {
		for(int i = 0; i < patternListWhite.length; i++) {
			if(myColor == Color.white) {
				patternME.put(patternListWhite[i], scoring[i]);
				patternOPP.put(patternListBlack[i], -1*scoring[i]);
			} else {
				patternME.put(patternListBlack[i], scoring[i]);
				patternOPP.put(patternListWhite[i], -1*scoring[i]);
			}
		}
	}
	
	public int[] minimaxMAX(Color[][] board, boolean myTurn, 
								int depth, int alpha, int beta) {
		ArrayList<Move> availableMoves = findMoves(board);
		int currentScore;
		int bestRow = -1;
		int bestCol = -1;
		
		if(availableMoves.isEmpty() || depth == MAX_DEPTH) {
			currentScore  = evaluate(board, myTurn);
			return new int[] {currentScore, bestRow, bestCol};
		} else {
			for(Move m : availableMoves) {
				if(myTurn) {
					board[m.row][m.col] = myColor;
					currentScore = minimaxMAX(board, !myTurn, 
										depth+1, alpha, beta)[0];
					if(currentScore > alpha) {
						alpha = currentScore;
						bestRow = m.row;
						bestCol = m.col;
					}
				} else {
					board[m.row][m.col] = oppColor;
					currentScore = minimaxMAX(board, myTurn, 
										depth+1, alpha, beta)[0];
					if(currentScore < beta) {
						beta = currentScore;
						bestRow = m.row;
						bestCol = m.col;
					}
				}
				board[m.row][m.col] = null;
				if(alpha >= beta)
					break;
			}
			return new int[] {(myTurn) ? alpha : beta, 
												bestRow, bestCol};
		}
	}
	
	private ArrayList<Move> findMoves(Color[][] board) {
		ArrayList<Move> availableMoves = new ArrayList<Move>();
		
		if(checkWinner(board))
			return availableMoves;

		for(int row = 0; row < board.length; row++) {
			for(int col = 0; col < board[0].length; col++) {
				if(board[row][col] == null) {
					availableMoves.add(new Move(row, col));
				}
			}
		}
		
		return availableMoves;
	}
	
	private boolean checkWinner(Color[][] board) {
		for(int row = 0; row < board.length; row++) {
			for(int col = 0; col < board[0].length; col++) {
				if(board[row][col] == myColor || board[row][col] == oppColor) {
					Color player = board[row][col];
					for(int dir = 0; dir < allDirX.length; dir++) {
						if(winHelper(row, col, allDirX[dir], allDirY[dir], board, player)) return true;
					}
				}
			}
		}
		return false;
	}
	
	private boolean winHelper(int row, int col, int dirX, int dirY, Color[][] board, Color player) {
		int count = 0;
		while(true) {
			if(player == board[row][col]) {
				count++;
			} else {
				return false;
			}
			
			if(count == 5) return true;
			
			row += dirX;
			col += dirY;
			
			if(isOutOfBounds(row, col)) return false;
		}
	}
	
	private int evaluate(Color[][] board, boolean myTurn) {
		int score = 0;
		for(int row = 0; row < board.length; row++) {
			for(int col = 0; col < board.length; col++) {
				for(int dir = 0; dir < directionX.length; dir++) {
					int patternScore = findPattern(row, col, 
							directionX[dir], directionY[dir], board, myTurn);
					if(patternScore != -1) {
						score += patternScore;
					}
				}
			}
		}
		return score;
	}
	
	private int findPattern(int row, int col, int dirX, 
								int dirY, Color[][] board, boolean myTurn) {
		String currentPatt = "";
		int storePattIndex = -1;
		int i = 0;
		while(i < 6){
			currentPatt += convPieceToStr(board[row][col]);
			if(currentPatt.length() >= 5 && currentPatt.length() <= 6) {
				int pattIndex;
				
				String who = whosePattern(currentPatt);
				if(who.equals("Mixed") && currentPatt.length() != 6)
					return -1;
				
				boolean isCPUPatt;
				if(who.equals("CPU")) 
					isCPUPatt = true;
				else 
					isCPUPatt = false;
				
				if(currentPatt.length() == 5) {
					pattIndex = checkPattExists(currentPatt, isCPUPatt);
					if(pattIndex == -1) 
						return -1;
					
					if(row == board.length - 1 || col == board[0].length - 1) {
						return getPatternScore(pattIndex, isCPUPatt, myTurn);
					}
					
					row += dirX;
					col += dirY;
					if(isOutOfBounds(row, col))
						return -1;
					
					currentPatt += board[row][col];
					storePattIndex = pattIndex;
					pattIndex = checkPattExists(currentPatt, false);
					
					if(pattIndex == -1) {
						return getPatternScore(storePattIndex, isCPUPatt, myTurn);
					} else {
						return getPatternScore(pattIndex, isCPUPatt, myTurn);
					}
					
				}
				
			}
			row += dirX;
			col += dirY;
			if(isOutOfBounds(row, col))
				return -1;
			i++;
		}
		return -1;
	}
	
	private String convPieceToStr(Color piece) {
		String convPiece = "";
		if(piece != null) {
			if(piece == Color.white) 
				convPiece = "W";
			else
				convPiece = "B";
		} else {
			convPiece = "-";
		}
		return convPiece;
	}
	
	private String whosePattern(String pattern) {
		HashSet<Character> charPatt = new HashSet<Character>();
		for(int i = 0; i < pattern.length(); i++)
			charPatt.add(pattern.charAt(i));
		if(charPatt.size() > 2)
			return "Mixed";
		else {
			if(myColor == Color.white) {
				if(charPatt.contains('W'))
					return "CPU";
				return "Other";
			} else {
				if(charPatt.contains('B'))
					return "CPU";
				return "Other";
			}
		}
	}
	
	public int checkPattExists(String pattern, boolean isCPU) {
		for(int i = 0; i < 17; i++) {
			String patt, pattReverse;
			if(myColor == Color.white) {
				if(isCPU)
					patt = patternListWhite[i];
				else
					patt = patternListBlack[i];
			} else {
				if(isCPU)
					patt = patternListBlack[i];
				else
					patt = patternListWhite[i];
			}
			pattReverse = new StringBuilder(patt).reverse().toString();
			if(pattern.equals(patt) || pattern.equals(pattReverse))
				return i;
		}
		return -1;
	}
	
	public int getPatternScore(int pattIndex, boolean isCPU, boolean isCPUTurn) {
		int finalScore;
		if(myColor == Color.white) {
			if(isCPU) {
				finalScore = patternME.get(patternListWhite[pattIndex]);
				if(isCPUTurn)
					finalScore *= 5;
			} else {
				finalScore = patternOPP.get(patternListBlack[pattIndex]);
				if(!isCPUTurn)
					finalScore *= 5;
			}
		} else {
			if(isCPU) {
				finalScore = patternME.get(patternListBlack[pattIndex]);
				if(isCPUTurn)
					finalScore *= 5;
			} else {
				finalScore = patternOPP.get(patternListWhite[pattIndex]);
				if(!isCPUTurn)
					finalScore *= 5;
			}
		}
		return finalScore;
	}
	
	private boolean isOutOfBounds(int row, int col) {
		if (row >= 8 || row < 0)
			return true;
		if (col >= 8 || col < 0)
			return true;
		return false;
	}
}