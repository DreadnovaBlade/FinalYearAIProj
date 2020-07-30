import java.util.*;

/*
The code for this AI was written by me, Mikail Tekneci, but some sections are from Chua Hock-Chuan's Tic-Tac-Toe AI.
Link to Hock-Chuan's Tic-Tac-Toe AI: http://www.ntu.edu.sg/home/ehchua/programming/java/javagame_tictactoe_ai.html
*/

public class TicTacToeAI {
	private String myPiece;
	private String oppPiece;

	private int[] winningPatterns = { 
			0b111000000, 0b000111000, 0b000000111, 
			0b100100100, 0b010010010, 0b001001001,
			0b100010001, 0b001010100 };

	public TicTacToeAI(String plr) {
		this.myPiece = plr;
		this.oppPiece = (plr == "X") ? "O" : "X";
	}

	public int[] doMove(String[][] board) {
		int[] chosenMove = minimaxAB(board, 6, myPiece, Integer.MIN_VALUE, Integer.MAX_VALUE);
		return new int[] { chosenMove[1], chosenMove[2] };
	}
	
	private int[] minimaxAB(String[][] board, int depth, String plr, int alpha, int beta) {
		ArrayList<int[]> availMoves = findMoves(board);
		int currentScore;
		int bestRow = -1;
		int bestCol = -1;
		if (availMoves.isEmpty() || depth == 0) {
			currentScore = evaluate(board);
			return new int[] { currentScore, bestRow, bestCol };
		} else {
			for (int[] m : availMoves) {
				board[m[0]][m[1]] = plr;
				if (plr == myPiece) {
					currentScore = minimaxAB(board, depth - 1, oppPiece, alpha, beta)[0];
					if (currentScore > alpha) {
						alpha = currentScore;
						bestRow = m[0];
						bestCol = m[1];
					}
				} else {
					currentScore = minimaxAB(board, depth - 1, myPiece, alpha, beta)[0];
					if (currentScore < beta) {
						beta = currentScore;
						bestRow = m[0];
						bestCol = m[1];
					}
				}
				board[m[0]][m[1]] = "_";
				if (alpha >= beta)
					break;
			}
			return new int[] { (plr == myPiece) ? alpha : beta, bestRow, bestCol };
		}
	}

	private ArrayList<int[]> findMoves(String[][] board) {
		ArrayList<int[]> availableMoves = new ArrayList<int[]>();

		if (hasWon(board, myPiece) || hasWon(board, oppPiece))
			return availableMoves;

		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[0].length; col++) {
				if (board[row][col].equals("_")) {
					availableMoves.add(new int[] { row, col });
				}
			}
		}

		return availableMoves;
	}
	

	private int evaluate(String[][] board) {
		int score = 0;
		score += evaluateLine(board, 0, 0, 0, 1, 0, 2);
		score += evaluateLine(board, 1, 0, 1, 1, 1, 2);
		score += evaluateLine(board, 2, 0, 2, 1, 2, 2);
		score += evaluateLine(board, 0, 0, 1, 0, 2, 0);
		score += evaluateLine(board, 0, 1, 1, 1, 2, 1);
		score += evaluateLine(board, 0, 2, 1, 2, 2, 2);
		score += evaluateLine(board, 0, 0, 1, 1, 2, 2);
		score += evaluateLine(board, 0, 2, 1, 1, 2, 0);
		return score;
	}

	private int evaluateLine(String[][] board, int row1, int col1, 
			int row2, int col2, int row3, int col3) {
		int score = 0;

		if (board[row1][col1].equals(myPiece)) {
			score = 1;
		} else if (board[row1][col1].equals(oppPiece)) {
			score = -1;
		}

		if (board[row2][col2].equals(myPiece)) {
			if (score == 1) {
				score = 10;
			} else if (score == -1) {
				return 0;
			} else {
				score = 1;
			}
		} else if (board[row2][col2].equals(oppPiece)) {
			if (score == -1) {
				score = -10;
			} else if (score == 1) {
				return 0;
			} else {
				score = -1;
			}
		}

		if (board[row3][col3].equals(myPiece)) {
			if (score > 0) {
				score *= 10;
			} else if (score < 0) {
				return 0;
			} else {
				score = 1;
			}
		} else if (board[row3][col3].equals(oppPiece)) {
			if (score < 0) {
				score *= 10;
			} else if (score > 1) {
				return 0;
			} else {
				score = -1;
			}
		}
		return score;
	}

	private boolean hasWon(String[][] board, String plr) {
		int pattern = 0b000000000;
		for (int row = 0; row < board.length; ++row) {
			for (int col = 0; col < board[0].length; ++col) {
				if (board[row][col].equals(plr)) {
					pattern |= (1 << (row * board[0].length + col));
				}
			}
		}
		for (int winningPattern : winningPatterns) {
			if ((pattern & winningPattern) == winningPattern)
				return true;
		}
		return false;
	}

	public String getPiece() {
		return myPiece;
	}
}
