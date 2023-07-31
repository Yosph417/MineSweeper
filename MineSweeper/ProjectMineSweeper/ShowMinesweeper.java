package ProjectMineSweeper;

public class ShowMinesweeper {
	
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				MinesweeperGUI gui = new MinesweeperGUI();
			}
			
		}
		
	);

}
}
