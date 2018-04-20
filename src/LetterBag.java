import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import javax.swing.JOptionPane;


/* LetterBag object. Initializes a letter bag based on file input
 * File Format <letters.txt>
 * Letter/Value/AmountInBag
 * ex)
 * A/1/9
 * B/3/2
 * ...
 */
public class LetterBag {
	private Map<Character, Integer> letterToAmountLeft = new TreeMap<Character, Integer>();
	private Map<Character, Integer> letterToPointValue = new TreeMap<Character, Integer>();
	
	
	/**
	 * 
	 * @param filename <file.txt> containing the values
	 * and frequencies of letters
	 */
	public LetterBag(String filename) {
		try {
		readFile(filename);
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null,"File Not Found",
					"Letters File Not Found", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
			
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,"Error",
					"Problem with Letters File", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
		
	}

	/**
	 * helper function for constructor to read File
	 * @param filename - file to read values and frequency of letters
	 */
	private void readFile(String filename) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		String line;
		while ((line = reader.readLine()) != null) {

			String[] list = line.split("/");
			char letter = list[0].toCharArray()[0];
			int pointValue = Integer.parseInt(list[1]);
			int amount = Integer.parseInt(list[2]);

			letterToAmountLeft.put(letter, amount);
			letterToPointValue.put(letter, pointValue);
		}
		reader.close();
	}
	
	/**
	 * 
	 * @param c character to find value
	 * @return point value of char c based on
	 * what was in original Input file
	 */

	public int getPointValue(char c) {
		return letterToPointValue.get(c);
	}
	
	/**
	 * 
	 * @return amount of tiles left in bag
	 */
	
	public int getTilesLeft() {
		int sum = 0;
		for (int i : letterToAmountLeft.values()) {
			sum += i;
		}
		return sum;
	}
	
	/**
	 * 
	 * @return random letter from the "bag"
	 * which is really a map of char --> amount left
	 */
	
	private char drawTile() {
		Random r = new Random();
		int size = this.getTilesLeft();
		if (size < 1) throw new RuntimeException("No Tiles Left");
		
		int pick = r.nextInt(size);
		char toDraw = (char)(-1);
		for (char c: letterToAmountLeft.keySet()) {
			int amountLeft = letterToAmountLeft.get(c);
			pick -= letterToAmountLeft.get(c);
			if (pick <= 0) {
				toDraw = c;
				letterToAmountLeft.put(c, amountLeft-1);
				break;
			}
			
		}
		return toDraw;
	}
	
	/**
	 * 
	 * @param amount of letters to draw
	 * @return list of characters of size amount
	 * where amount is the minimum of amount param and
	 * the amount of letters left
	 */
	
	public List<Character> drawTiles(int amount) {
		int amountLeft = this.getTilesLeft();
		if (amount > amountLeft) {
			amount = amountLeft;
		}
		
		List<Character> tgt = new ArrayList<Character>();
		for (int i = 0; i < amount; i++) {
			
			tgt.add(this.drawTile());
		}
		return tgt;
	}
	
	/**
	 * 
	 * @param oldLetters to put back in bag
	 * @return same amount of new letters from bag
	 */
	
	public List<Character> swapTiles(List<Character> oldLetters) {
		for (char c: oldLetters) {
			int oldValue = letterToAmountLeft.get(c);
			letterToAmountLeft.put(c, oldValue+1);
		}
		return this.drawTiles(oldLetters.size());
		
	}

}
