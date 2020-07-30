Submission Directory Structure:
Gomoku:
	GomokuBoard.class - Helper class used by the GomokuReferee (provided by Prof. Simon Dixon)
	GomokuPlayer.class - Helper class used by the HumanPlayer (provided by Prof. Simon Dixon)
	GomokuReferee$1.class - Internal class used by the GomokuReferee (provided by Prof. Simon Dixon)
	GomokuReferee.class - Gomoku Test Platform (provided by Prof. Simon Dixon)
	HumanPlayer.class - Class that allows human input for playing (provided by Prof. Simon Dixon)
	Move.class - Stores board move data, used by GomokuReferee (provided by Prof. Simon Dixon)
	Player160212682.java - My Gomoku AI
	Player160212682.class - Compiled version of the Gomoku AI

Tic-Tac-Toe:
	TicTacToeAI.class - Compiled version of the Tic-Tac-Toe AI
	TicTacToeAI.java - My Tic-Tac-Toe AI
	TTTUITest.class - Compiled version of the Tic-Tac-Toe GUI
	TTTUITest.java - Modified Tic-Tac-Toe GUI from AlgoJava

README.txt - the current text document.

To run the GOMOKU AI:
1) Use command prompt/terminal to navigate to the Gomoku directory or open the shell whilst inside the Gomoku directory.
2) Use the command "javac Player160212682.java" to compile the Gomoku AI.
	NB: This was coded using the Java Development Kit "jdk1.8.0_201", and may cause errors if the JDK is not up to date.
	A .class file of the AI will be provided in case there is a compilation error.
3) Use the command "java GomokuReferee" to initialise the Gomoku GUI.
4) To begin a game versus the AI, press one of the drop down menus and select Player160212682 and press the 'New Game' button.
5) To restart at any time, press the 'New Game' button.

To run the TIC-TAC-TOE AI:
1) Use command prompt/terminal to navigate to the Tic-Tac-Toe directory or open the shell whilst inside the Tic-Tac-Toe directory.
2) Use the command "javac TicTacToe.java" to compile the Tic-Tac-Toe AI.
	NB: This was coded using the Java Development Kit "jdk1.8.0_201", and may cause errors if the JDK is not up to date.
	A .class file of the AI will be provided in case there is a compilation error.
3) Use the command "javac TTTUITest.java" to compile the Tic-Tac-Toe GUI.
	NB: This was coded using the Java Development Kit "jdk1.8.0_201", and may cause errors if the JDK is not up to date.
	A .class file of the GUI will be provided in case there is a compilation error.
4) Use the command "java TTTUITest" to initialise the Tic-Tac-Toe GUI.
5) Press the 'Play' button to begin a game versus the AI.
6) To restart at any time, press the 'Play' button.