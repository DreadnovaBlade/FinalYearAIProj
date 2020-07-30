import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/*
The code for this GUI was modified by me, Mikail Tekneci.
The original is from the AlgoJava blog.
Link to AlgoJava's GUI: https://algojava.blogspot.com/2012/05/tic-tac-toe-game-swingjava.html
*/

public class TTTUITest extends JFrame implements ActionListener {
	 private JButton[][] buttons = new JButton[3][3];
	 private String[][] gameBoard;
	 private JButton playButton = new JButton("Play");
	 private JLabel statusLabel = new JLabel("");
	 private TicTacToeAI game = null;
	 private String human;
	 private boolean isPlay = false;

	 private void setStatus(String s) {
		 statusLabel.setText(s);
	 }

	 private void setButtonsEnabled(boolean enabled) {
		 for(int i=0;i<3;i++) {
			 for(int j=0;j<3;j++) {
				 buttons[i][j].setEnabled(enabled);
				 if(enabled) buttons[i][j].setText(" ");
			 }
		 }
	 }

	 public TTTUITest() {
		 setTitle("Tic Tac Toe");
	 	 setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	 	 setResizable(false);

	 	 JPanel centerPanel = new JPanel(new GridLayout(3,3));
	 	 Font font = new Font("Arial",Font.BOLD, 32);
	 	 for(int i=0;i<3;i++) {
	 		 for(int j=0;j<3;j++) {
	 			 buttons[i][j] = new JButton(" ");
	 			 buttons[i][j].setFont(font);
	 			 buttons[i][j].addActionListener(this);
	 			 buttons[i][j].setFocusable(false);
	 			 centerPanel.add(buttons[i][j]);
	 		 }
	 	 }

	 	 playButton.addActionListener(this);

	 	 JPanel northPanel = new JPanel();
	 	 northPanel.add(statusLabel);
	 	 JPanel southPanel = new JPanel();
	 	 southPanel.add(playButton);
	 	 setStatus("Click 'Play' To Start");
	 	 setButtonsEnabled(false);
	 	 add(northPanel,"North");
	 	 add(centerPanel,"Center");
	 	 add(southPanel,"South");
	 	 setSize(300,300);
	 	 
	 	 setLocationRelativeTo(null);
	 }

	 public static void main(String []args) {
		 new TTTUITest().setVisible(true);
	 }

	 private void computerTurn() {
		 if(!isPlay) return;

		 int[] getMove = game.doMove(gameBoard);
		 if(getMove != null) {
			 buttons[getMove[0]][getMove[1]].setText(game.getPiece());
			 gameBoard[getMove[0]][getMove[1]] = game.getPiece();
		 }

		 checkState();
	 }

	 private void gameOver(String s) {
		 setStatus(s);
		 setButtonsEnabled(false);
		 isPlay = false;
	 }

	 private void checkState() {
		 if(checkForWin(human)) {
			 gameOver("Congratulations, You've Won!");
		 }
		 if(checkForWin(game.getPiece())) {
			 gameOver("Sorry, You Lose!");
		 }
		 if(checkForTie()) {
			 gameOver("Draw, Click 'Play' For Rematch!");
		 }
	 }

	 private void click(int i,int j) {
		 if(gameBoard[i][j].equals("_")) {
			 buttons[i][j].setText(human);
			 gameBoard[i][j] = human;
			 checkState();
			 computerTurn();
		 }
	 }

	 public void actionPerformed(ActionEvent event) {
		 if(event.getSource()==playButton) {
			 play();
		 }else {
			 for(int i=0;i<3;i++)
				 for(int j=0;j<3;j++)
					 if(event.getSource()==buttons[i][j])
						 click(i,j);
		 }
	 }
	 
	 private Boolean checkForTie() {
		 for(int row = 0; row < 3; row++) {
			 for(int col = 0; col < 3; col++) {
				 if(gameBoard[row][col].equals("_"))
					 return false;
			 }
		 }
		 return true;
	 }
	 
	 private Boolean checkForWin(String nextPlayer) {
		int n = 3;
		for(int row = 0; row < n; row++) {
			for(int i = 0; i < n; i++) {
				if(!gameBoard[row][i].equals(nextPlayer)) break;
				if(i == n-1) return true;
			}
		}
		for(int col = 0; col < n; col++) {
			for(int i = 0; i < n; i++) {
				if(!gameBoard[i][col].equals(nextPlayer)) break;
				if(i == n-1) return true;
			}
		}
		for(int i = 0; i < n; i++) {
			if(!gameBoard[i][i].equals(nextPlayer)) break;
			if(i == n-1) return true;
		}
		for(int i = 0; i < n; i++) {
			if(!gameBoard[i][(n-1)-i].equals(nextPlayer)) break;
			if(i == n-1) return true;
		}
		return false;
	}

	 private void play() {
		 gameBoard = new String[][] {{"_", "_", "_"}, {"_", "_", "_"}, {"_", "_", "_"}};
		 game = new TicTacToeAI("O");
		 human = (game.getPiece() == "X") ? "O" : "X";
		 setStatus("Your Turn");
		 setButtonsEnabled(true);
		 isPlay = true;
	 }
	}
