package ProjectMineSweeper;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.GridBagConstraints;
import java.awt.Color;
import java.awt.Dimension;

public class MinesweeperGUI extends JFrame {
	private Grid mineGrid;
	JPanel JPMain;
	private MinesweeperBoard mineBoard;
	private int numRows;
	private int numColumns;
	private int bombNumber;
	private int totalSpacesLeft;
	private int selectPrompt;
	private String numRowsString;
	private String numColString;
	private String numBombString;
	private JOptionPane ConstructorPrompt;
	
	public MinesweeperGUI() {
	String [] ConstructorSelection = {"Default", "int Row, int Col", "int Row, int Col, int BombNumber"};
	
	selectPrompt = JOptionPane.showOptionDialog(null, "Please choose which set of elements you wish to modify.", "Mode Selection", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, 
			null, ConstructorSelection, ConstructorSelection[0]);{
	
if(selectPrompt == 0) {
	mineGrid = new Grid();
	numRows = 10;
	numColumns = 10;
	bombNumber = 25;
}

else if (selectPrompt == 1) {
	try {
		 numRowsString = JOptionPane.showInputDialog("Please enter the number of Rows:");
		if(numRowsString == null) {
			System.exit(0);
		}
		
		numRows = Integer.parseInt(numRowsString);
		
		numColString = JOptionPane.showInputDialog("Please enter the number of Columns");
		if(numColString == null) {
			System.exit(0);
		}
		numColumns = Integer.parseInt(numColString);
		
		if(numRows < 0 || numColumns < 0) {
			JOptionPane.showMessageDialog(null, "Please enter positive numbers!", "Error", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}

	}
		
		catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Please enter numbers!", "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	
		
	
	mineGrid = new Grid(numRows, numColumns);
	bombNumber = 25;
			}
			
else if (selectPrompt == 2) {
	try {
		 numRowsString = JOptionPane.showInputDialog("Please enter the number of Rows:");
			if(numRowsString == null) {
				System.exit(0);
			}
			
			numRows = Integer.parseInt(numRowsString);
			
			numColString = JOptionPane.showInputDialog("Please enter the number of Columns");
			if(numColString == null) {
				System.exit(0);
			}
			numColumns = Integer.parseInt(numColString);
			
			numBombString = JOptionPane.showInputDialog("Please enter the number of Bombs");
			if(numBombString == null) {
				System.exit(0);
			}
			
		bombNumber = Integer.parseInt(numBombString);
		
		if(numRows < 0 || numColumns < 0 || bombNumber < 0) {
			JOptionPane.showMessageDialog(ConstructorPrompt, "Please enter positive numbers!", "Error", JOptionPane.ERROR_MESSAGE);
		}
		}
		
		catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(ConstructorPrompt, "Please enter numbers!", "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	
		catch(Exception e) {
			e.printStackTrace();
		}
	
	mineGrid = new Grid(numRows, numColumns, bombNumber);
	
	
			}

	JPMain = new JPanel();

	mineBoard = new MinesweeperBoard();
	this.setTitle("Minesweeper");
	JPMain.add(mineBoard);

	add(JPMain);
	pack();
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setVisible(true);
	totalSpacesLeft = mineGrid.getNumRows() * mineGrid.getNumColumns();
	
			}
	


}
			
private class MinesweeperBoard extends JPanel implements ActionListener, MinesweeperBoardInterFace{
	private JButton[][] board;
	private ImageIcon mine = new ImageIcon("Images/SweepMine.png");
	private ImageIcon resizedMine = new ImageIcon(mine.getImage().getScaledInstance(50, 50, Image.SCALE_FAST));
	
	public MinesweeperBoard() {
		setLayout(new GridLayout(numRows, numColumns));
	
	boardViewer();
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton sourceFinder = (JButton) e.getSource();
		for(int i = 0; i < mineGrid.getNumRows(); ++i) {
			for(int o = 0; o < mineGrid.getNumColumns(); ++o) {
				if(sourceFinder == board[i][o]) {
					if(board[i][o].getText().equals("")) {
					
						if(mineGrid.isBombAtLocation(i, o)) {
						displayAllSpaces();
						JOptionPane.showMessageDialog(null, "You blew up!");
						playAgain();
						return;
						
					}
					
					else if(mineGrid.getCountAtLocation(i, o) != 0){							
						board[i][o].setText("" + mineGrid.getCountAtLocation(i, o));
						--totalSpacesLeft;
						}
					
					else {
						zeroTileRevealer(i,o);
						
					}
						
					}
				}
			}
		}
		
		if(playerWin()) {
			JOptionPane.showMessageDialog(null, "Congrats! You've evaded the bombs!");
			playAgain();
		}
		
		
		
		//Utilize Grid Methods to Display Bomb and End Game, or Display Bomb Count.
	}

	private void displayAllSpaces() {
			for (int i = 0; i < mineGrid.getNumRows(); ++i) {
				for(int o = 0; o < mineGrid.getNumColumns(); ++o) {
					if(!mineGrid.isBombAtLocation(i, o)) {
						board[i][o].setText("" + mineGrid.getCountAtLocation(i, o));
					}
					
					else {
						board[i][o].setIcon(resizedMine);
					}
				}
			}
		}
		
	



	@Override
	public void boardViewer() {
		board = new JButton[numRows][numColumns];
		for(int i = 0; i < numRows; ++i) {
			for(int o = 0; o < numColumns; ++o) {
				board[i][o] = new JButton();
				board[i][o].setBackground(Color.GRAY);
				board[i][o].addActionListener(this);
				board[i][o].setEnabled(true);
				board[i][o].setPreferredSize(new Dimension(50,50));
				this.add(board[i][o]);
			
			}
		}
		
	}
	
	private void zeroTileRevealer(int row, int col) {
		for(int m = row - 1; m <= row + 1; ++m) {
			for(int n = col - 1; n <= col + 1; ++n) {
				if(mineGrid.arrayInBounds(m, n)) {
				if(!mineGrid.isBombAtLocation(m, n)) {
					if(board[m][n].getText().equals("")){
						if(mineGrid.getCountAtLocation(m, n) == 0) {
						board[m][n].setText("" + mineGrid.getCountAtLocation(m, n));
						--totalSpacesLeft;
						zeroTileRevealer(m,n);
					}
					
						else {
							board[m][n].setText("" + mineGrid.getCountAtLocation(m, n));
							--totalSpacesLeft;
						}

					}
				}
			}
		}
	}
}
	

	@Override
	public boolean playerWin() {
		if(totalSpacesLeft == mineGrid.getNumBombs()) {
			return true;
		}
		
		return false;
	}
	
	public void playAgain() {
		int prompt = JOptionPane.showConfirmDialog(null, "Would you like to play again?","Yes or no",JOptionPane.YES_NO_OPTION);
		if(prompt == JOptionPane.YES_OPTION) {
			clearBoard();
		}
		
		else {
			System.exit(EXIT_ON_CLOSE);
		}
	}
	
	public void clearBoard() {
		for(int i = 0; i < mineGrid.getNumRows(); ++i) {
			for(int o = 0; o < mineGrid.getNumColumns(); ++o) {
				if(!mineGrid.isBombAtLocation(i, o)) {
					board[i][o].setText("");
				}
				
				else {
					board[i][o].setIcon(null);
				}
				
				
			}
		}
		
		if(selectPrompt == 0) {
			mineGrid = new Grid();
		}
		
		else if (selectPrompt == 1) {
			mineGrid = new Grid(mineGrid.getNumRows(),mineGrid.getNumColumns());
		}
		
		else if (selectPrompt == 2) {
			mineGrid = new Grid(mineGrid.getNumRows(),mineGrid.getNumColumns(),mineGrid.getNumBombs());
		}
		
		totalSpacesLeft = mineGrid.getNumRows() * mineGrid.getNumColumns();

	}


}









}


