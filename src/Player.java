import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;

public class Player extends JComponent {
	
	private List<Character> letterBench;
	private String name;
	private int score;
	private boolean isMyTurn;
	
	public Player(String name, List<Character> startBench, boolean isMyTurn) {
		this.name = name;
		letterBench = new ArrayList<Character>(startBench);
		score = 0;
		this.isMyTurn = isMyTurn;
	
		
	}
	
	
	/**
	 * @return the isMyTurn
	 */
	public boolean isMyTurn() {
		return isMyTurn;
	}



	/**
	 * @param isMyTurn the isMyTurn to set
	 */
	public void setMyTurn(boolean isMyTurn) {
		this.isMyTurn = isMyTurn;
	}
	
	/**
	 * 
	 * @param index
	 * @return
	 */
	public char getLetter(int index) {
		return letterBench.get(index);
	}
	
	/**
	 * 
	 * @return all current letters
	 */
	public List<Character> getAll() {
		return new ArrayList<Character>(letterBench);
	}
	
	/**
	 * clears bench
	 */
	public void clear() {
		letterBench.clear();
	}
	
	/**
	 * 
	 * @param points - points to add to current score
	 */
	public void addToScore(int points) {
		score += points;
	}
	
	/**
	 * 
	 * @return - size of bench
	 */
	public int getBenchSize() {
		return letterBench.size();
	}
	
	/**
	 * 
	 * @param toUse letters to be "used"
	 * and deleted from current letter bench
	 */
	
	public void useLetters(List<Character> toUse) {
		for (char c : toUse) {
			this.useLetter(c);
		}
		
	}
	/**
	 * 
	 * @param c char to "use"
	 * helper function for public method useLetters
	 */
	
	private void useLetter(Character c) {
		letterBench.remove(c);
	}
	
	/**
	 * 
	 * @param toAdd letters to add to bench
	 */
	
	public void addLetters(List<Character> toAdd) {
		letterBench.addAll(toAdd);
	}
	
	/**
	 * @return player's name
	 */

	public String getName() {
		return name;
	}
	
	/**
	 * 
	 * @return player's score
	 */

	public int getScore() {
		return score;
	}
	

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		System.out.println("here");
		g.fillRect(0, 0, 100, 100000);
		g.setFont(new Font("TimesRoman", Font.PLAIN, GameBoard.SQUARE_SIZE-(GameBoard.SQUARE_SIZE/2))); 
		int index = 0;
		for (char c: letterBench) {
			g.drawString(Character.toString(c), index*GameBoard.SQUARE_SIZE, GameBoard.SQUARE_SIZE/2);
		}
		
		

	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(GameBoard.SQUARE_SIZE*7, GameBoard.SQUARE_SIZE);
	}
	
	@Override
	public Dimension getMinimumSize() {
		return new Dimension(GameBoard.SQUARE_SIZE*7, GameBoard.SQUARE_SIZE);
	}
}
