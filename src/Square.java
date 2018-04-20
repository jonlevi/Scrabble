import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JComponent;


public class Square extends JComponent implements Comparable<Square>  {
	private char content;
	private int row;
	private int col;
	private final int SIZE = GameBoard.SQUARE_SIZE;
	
	/**
	 * Constructor 1
	 * @param row - initial row
	 * @param col - initial column
	 * sets null char to be (char) -1
	 * @see catch all contructor below
	 */
	public Square(int row, int col) {
		this(row, col, (char)-1);
	}
	
	/**
	 * Constructor 2
	 * @param row - initial row
	 * @param col - initial column
	 * @param content - initial content
	 */
	public Square(int row, int col, char content) {
		this.row = row;
		this.col = col;
		this.content = content;

	}
	
	/**
	 * @param content - sets content
	 */
	public void setContent(char content) {
		this.content = content;
	}
	
	/**
	 * 
	 * @return this.content is not empty
	 */
	public boolean hasContent() {
		return content!=((char)-1);
	}
	
	/**
	 * 
	 * @return this.content
	 */
		
	public char getContent() {
		return content;
	}
	
	/**
	 * 
	 * @return this.row
	 */
	
	public int getRow() {
		return row;
	}
	
	/**
	 * 
	 * @return this.col
	 */
	
	public int getColumn() {
		return col;
	}
	
	/**
	 * @param o - square to compare with
	 * @return 0 if same row and same col
	 * 1 if this.row > o.row
	 * -1 if this.row < o.row
	 * else if row=row check columns
	 * Written to implement comparable for sorting
	 * purposes in GameBoard.addWord method
	 */

	@Override
	public int compareTo(Square o) {
		if (this.row!=o.getRow()) {
			if (this.row > o.getRow()) return 1;
			return -1;
		} else if (this.col != o.getColumn()) {
			if (this.col > o.getColumn()) return 1;
			return -1;
		} else { return 0;}
	}
	
	/**
	 * see super class documentation
	 */
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (row==7 && col==7) {
			g.setColor(Color.red);
			g.drawRect(0, 0, SIZE+1, SIZE-2);
		} else {
			g.setColor(Color.black);
			g.drawRect(0, 0, SIZE+1, SIZE-2);
		}
		if (this.hasContent()) {
			g.setColor(Color.black);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 20)); 
			g.drawString(Character.toString(content), SIZE/3, SIZE-(SIZE/3));
		}
	}
	
	/**
	 * see super class documentation
	 */
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(SIZE, SIZE);
	}
	
	/**
	 * see super class documentation
	 */
	@Override
	public Dimension getMinimumSize() {
		return new Dimension(SIZE, SIZE);
	}
}
