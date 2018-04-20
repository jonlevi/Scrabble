import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.*;



public class GameBoardTest {
	
	private LetterBag letterBag = new LetterBag("letters.txt");
	
	@Test
	public void testFirstWordNotInCenter() {
		GameBoard g = new GameBoard("smallDictionary.txt", letterBag);
		ArrayList<Square> sqs = new ArrayList<Square>();
		sqs.add(new Square(7, 6, 'A'));
		sqs.add(new Square(8, 6, 'T'));
		assertEquals("Not centered --> -1", -1, g.addWord(sqs, true));
	}
	
	@Test
	public void testFirstWordOneLetter() {
		GameBoard g = new GameBoard("smallDictionary.txt", letterBag);
		ArrayList<Square> sqs = new ArrayList<Square>();
		sqs.add(new Square(7, 7, 'A'));
		assertEquals("One Letter --> -1", -1, g.addWord(sqs, true));
	}
	
	
	
	@Test
	public void testFirstWordStartsInCenter() {
		GameBoard g = new GameBoard("smallDictionary.txt", letterBag);
		ArrayList<Square> sqs = new ArrayList<Square>();
		sqs.add(new Square(7, 7, 'A'));
		sqs.add(new Square(7, 8, 'T'));
		assertEquals("AT --> 2 points", 2, g.addWord(sqs, true));
	}
	
	@Test
	public void testFirstWordEndsInCenter() {
		GameBoard g = new GameBoard("smallDictionary.txt", letterBag);
		ArrayList<Square> sqs = new ArrayList<Square>();
		sqs.add(new Square(7, 6, 'A'));
		sqs.add(new Square(7, 7, 'T'));
		assertEquals("AT --> 2 points", 2, g.addWord(sqs, true));
	}
	
	@Test
	public void testFirstWordEndsInCenterColumns() {
		GameBoard g = new GameBoard("smallDictionary.txt", letterBag);
		ArrayList<Square> sqs = new ArrayList<Square>();
		sqs.add(new Square(7, 7, 'A'));
		sqs.add(new Square(8, 7, 'T'));
		assertEquals("AT --> 2 points", 2, g.addWord(sqs, true));
	}
	
	@Test
	public void testFirstWordMiddleInCenter() {
		GameBoard g = new GameBoard("smallDictionary.txt", letterBag);
		ArrayList<Square> sqs = new ArrayList<Square>();
		sqs.add(new Square(7, 6, 'B'));
		sqs.add(new Square(7, 7, 'A'));
		sqs.add(new Square(7, 8, 'T'));
		assertEquals("BAT --> 5 points", 5, g.addWord(sqs, true));
	}
	
	@Test
	public void testFirstWordMiddleInCenterColumns() {
		GameBoard g = new GameBoard("smallDictionary.txt", letterBag);
		ArrayList<Square> sqs = new ArrayList<Square>();
		sqs.add(new Square(6, 7, 'B'));
		sqs.add(new Square(7, 7, 'A'));
		sqs.add(new Square(8, 7, 'T'));
		assertEquals("BAT --> 5 points", 5, g.addWord(sqs, true));
	}
	
	@Test
	public void testFirstWordNotAWord() {
		GameBoard g = new GameBoard("smallDictionary.txt", letterBag);
		ArrayList<Square> sqs = new ArrayList<Square>();
		sqs.add(new Square(6, 7, 'B'));
		sqs.add(new Square(7, 7, 'B'));
		sqs.add(new Square(8, 7, 'X'));
		assertEquals("BBX --> not a word", -1, g.addWord(sqs, true));
	}
	
	@Test
	public void testSecondWordInvalidPlacement1() {
		GameBoard g = new GameBoard("smallDictionary.txt", letterBag);
		ArrayList<Square> sqs1 = new ArrayList<Square>();
		sqs1.add(new Square(6, 7, 'B'));
		sqs1.add(new Square(7, 7, 'A'));
		sqs1.add(new Square(8, 7, 'T'));
		g.addWord(sqs1, true);
		
		ArrayList<Square> sqs2 = new ArrayList<Square>();
		sqs2.add(new Square(6, 7, 'B'));
		sqs2.add(new Square(6, 8, 'E'));
		sqs2.add(new Square(6, 9, 'L'));
		sqs2.add(new Square(6, 10, 'L'));
		assertEquals("Invalid Placement", -1, g.addWord(sqs2, false));
	}
	
	@Test
	public void testSecondWordInvalidPlacement2() {
		GameBoard g = new GameBoard("smallDictionary.txt", letterBag);
		ArrayList<Square> sqs1 = new ArrayList<Square>();
		sqs1.add(new Square(6, 7, 'B'));
		sqs1.add(new Square(7, 7, 'A'));
		sqs1.add(new Square(8, 7, 'T'));
		g.addWord(sqs1, true);
		ArrayList<Square> sqs2 = new ArrayList<Square>();
		sqs2.add(new Square(3, 5, 'B'));
		sqs2.add(new Square(3, 6, 'E'));
		assertEquals("Invalid Placement", -1, g.addWord(sqs2, false));
	}
	
	@Test
	public void testSecondWordInvalidPlacement3() {
		GameBoard g = new GameBoard("smallDictionary.txt", letterBag);
		ArrayList<Square> sqs1 = new ArrayList<Square>();
		sqs1.add(new Square(6, 7, 'B'));
		sqs1.add(new Square(7, 7, 'A'));
		sqs1.add(new Square(8, 7, 'T'));
		g.addWord(sqs1, true);
		ArrayList<Square> sqs2 = new ArrayList<Square>();
		sqs2.add(new Square(6, 8, 'E'));
		sqs2.add(new Square(5, 8, 'B'));
		sqs2.add(new Square(5, 9, 'E'));
		sqs2.add(new Square(5, 10, 'L'));
		
		assertEquals("Invalid Placement", -1, g.addWord(sqs2, false));
	} 
	
	@Test
	public void testSecondWordValid() {
		GameBoard g = new GameBoard("smallDictionary.txt", letterBag);
		ArrayList<Square> sqs1 = new ArrayList<Square>();
		sqs1.add(new Square(6, 7, 'B'));
		sqs1.add(new Square(7, 7, 'A'));
		sqs1.add(new Square(8, 7, 'T'));
		g.addWord(sqs1, true);
		ArrayList<Square> sqs2 = new ArrayList<Square>();
		sqs2.add(new Square(9, 7, 'S'));
		assertEquals("BATS -> ", 6, g.addWord(sqs2, false));
	}
	
	@Test
	public void testSecondWordValid0() {
		GameBoard g = new GameBoard("smallDictionary.txt", letterBag);
		ArrayList<Square> sqs1 = new ArrayList<Square>();
		sqs1.add(new Square(6, 7, 'L'));
		sqs1.add(new Square(7, 7, 'I'));
		sqs1.add(new Square(8, 7, 'T'));
		g.addWord(sqs1, true);
		ArrayList<Square> sqs2 = new ArrayList<Square>();
		sqs2.add(new Square(6, 4, 'M'));
		sqs2.add(new Square(6, 5, 'A'));
		sqs2.add(new Square(6, 6, 'I'));
		
		assertEquals("MAIL -> ", 6, g.addWord(sqs2, false));
	}
	
	
	@Test
	public void testSecondWordCrossing() {
		GameBoard g = new GameBoard("smallDictionary.txt", letterBag);
		ArrayList<Square> sqs1 = new ArrayList<Square>();
		sqs1.add(new Square(6, 7, 'A'));
		
		sqs1.add(new Square(8, 7, 'E'));
		ArrayList<Square> sqs2 = new ArrayList<Square>();
		sqs2.add(new Square(7, 8, 'E'));
		sqs2.add(new Square(7, 6, 'A'));
		sqs2.add(new Square(7, 7, 'X'));
		g.addWord(sqs2, true);
		
		assertEquals("AXE -> 10 ", 10, g.addWord(sqs1, false));
	}
	
	
	
	@Test
	public void testSecondWordValid1() {
		GameBoard g = new GameBoard("smallDictionary.txt", letterBag);
		ArrayList<Square> sqs1 = new ArrayList<Square>();
		sqs1.add(new Square(6, 7, 'B'));
		sqs1.add(new Square(7, 7, 'A'));
		sqs1.add(new Square(8, 7, 'T'));
		g.addWord(sqs1, true);
		ArrayList<Square> sqs2 = new ArrayList<Square>();
		sqs2.add(new Square(6, 8, 'E'));
		sqs2.add(new Square(6, 9, 'L'));
		sqs2.add(new Square(6, 10, 'L'));
		assertEquals("BELL -> ", 6, g.addWord(sqs2, false));
	}
	
	@Test
	public void testSecondWordValid2() {
		GameBoard g = new GameBoard("smallDictionary.txt", letterBag);
		ArrayList<Square> sqs1 = new ArrayList<Square>();
		sqs1.add(new Square(6, 7, 'B'));
		sqs1.add(new Square(7, 7, 'A'));
		sqs1.add(new Square(8, 7, 'T'));
		g.addWord(sqs1, true);
		ArrayList<Square> sqs2 = new ArrayList<Square>();
		sqs2.add(new Square(6, 8, 'E'));
		sqs2.add(new Square(5, 8, 'B'));
		assertEquals("BE && BE -> ", 8, g.addWord(sqs2, false));
	}
	
	@Test
	public void testSecondWordValid3() {
		GameBoard g = new GameBoard("smallDictionary.txt", letterBag);
		ArrayList<Square> sqs1 = new ArrayList<Square>();
		sqs1.add(new Square(6, 7, 'B'));
		sqs1.add(new Square(7, 7, 'A'));
		sqs1.add(new Square(8, 7, 'T'));
		g.addWord(sqs1, true);
		ArrayList<Square> sqs2 = new ArrayList<Square>();
		sqs2.add(new Square(8, 6, 'A'));
		sqs2.add(new Square(8, 8, 'E'));
		assertEquals("ATE and not BAT -> 3 ", 3, g.addWord(sqs2, false));
	}
	
	@Test
	public void testSecondWordValid4() {
		GameBoard g = new GameBoard("smallDictionary.txt", letterBag);
		ArrayList<Square> sqs1 = new ArrayList<Square>();
		sqs1.add(new Square(6, 7, 'B'));
		sqs1.add(new Square(7, 7, 'A'));
		sqs1.add(new Square(8, 7, 'T'));
		g.addWord(sqs1, true);
		ArrayList<Square> sqs2 = new ArrayList<Square>();
		sqs2.add(new Square(9, 7, 'S'));
		sqs2.add(new Square(9, 8, 'O'));
		assertEquals("BATS and SO -> 8", 8, g.addWord(sqs2, false));
	}
	
	@Test
	public void testSecondWordValid5() {
		GameBoard g = new GameBoard("smallDictionary.txt", letterBag);
		ArrayList<Square> sqs1 = new ArrayList<Square>();
		sqs1.add(new Square(6, 7, 'B'));
		sqs1.add(new Square(7, 7, 'A'));
		sqs1.add(new Square(8, 7, 'T'));
		g.addWord(sqs1, true);
		ArrayList<Square> sqs2 = new ArrayList<Square>();
		sqs2.add(new Square(5, 8, 'B'));
		sqs2.add(new Square(6, 8, 'E'));
		sqs2.add(new Square(7, 8, 'T'));
		assertEquals("BET and BE and AT -> 11 ", 11, g.addWord(sqs2, false));
	}
	
	@Test
	public void testSecondWordValid6() {
		GameBoard g = new GameBoard("smallDictionary.txt", letterBag);
		ArrayList<Square> sqs1 = new ArrayList<Square>();
		sqs1.add(new Square(6, 7, 'B'));
		sqs1.add(new Square(7, 7, 'A'));
		sqs1.add(new Square(8, 7, 'T'));
		g.addWord(sqs1, true);
		ArrayList<Square> sqs2 = new ArrayList<Square>();
		sqs2.add(new Square(5, 8, 'B'));
		sqs2.add(new Square(6, 8, 'E'));
		sqs2.add(new Square(7, 8, 'E'));
		assertEquals("BET and BE and AE is not word -> -1 ", -1, g.addWord(sqs2, false));
	}
	
	@Test
	public void testMulitpleWordsValid1() {
		GameBoard g = new GameBoard("smallDictionary.txt", letterBag);
		ArrayList<Square> sqs1 = new ArrayList<Square>();
		sqs1.add(new Square(6, 7, 'B'));
		sqs1.add(new Square(7, 7, 'A'));
		sqs1.add(new Square(8, 7, 'T'));
		g.addWord(sqs1, true);
		ArrayList<Square> sqs2 = new ArrayList<Square>();
		sqs2.add(new Square(8, 6, 'A'));
		sqs2.add(new Square(8, 8, 'E'));
		g.addWord(sqs2, false);
		ArrayList<Square> sqs3 = new ArrayList<Square>();
		sqs3.add(new Square(6, 5, 'L'));
		sqs3.add(new Square(7, 5, 'O'));
		sqs3.add(new Square(8, 5, 'G'));
		assertEquals("GATE and LOG -> 11 ", 9,  g.addWord(sqs3, false));
	}
	
	@Test
	public void testMulitpleWordsValid2() {
		GameBoard g = new GameBoard("smallDictionary.txt", letterBag);
		ArrayList<Square> sqs1 = new ArrayList<Square>();
		sqs1.add(new Square(6, 7, 'B'));
		sqs1.add(new Square(7, 7, 'A'));
		sqs1.add(new Square(8, 7, 'T'));
		g.addWord(sqs1, true);
		ArrayList<Square> sqs2 = new ArrayList<Square>();
		sqs2.add(new Square(8, 6, 'A'));
		sqs2.add(new Square(8, 8, 'E'));
		g.addWord(sqs2, false);
		ArrayList<Square> sqs3 = new ArrayList<Square>();
		sqs3.add(new Square(6, 5, 'L'));
		sqs3.add(new Square(7, 5, 'O'));
		sqs3.add(new Square(8, 5, 'G'));
		g.addWord(sqs3, false);
		ArrayList<Square> sqs4 = new ArrayList<Square>();
		sqs4.add(new Square(6, 6, 'O'));
		sqs4.add(new Square(6, 8, 'E'));
		assertEquals("LOBE -> 6 ", 6,  g.addWord(sqs4, false));
	}
	
	@Test
	public void testMulitpleWordsValid3() {
		GameBoard g = new GameBoard("smallDictionary.txt", letterBag);
		ArrayList<Square> sqs1 = new ArrayList<Square>();
		sqs1.add(new Square(6, 7, 'B'));
		sqs1.add(new Square(7, 7, 'A'));
		sqs1.add(new Square(8, 7, 'T'));
		g.addWord(sqs1, true);
		ArrayList<Square> sqs2 = new ArrayList<Square>();
		sqs2.add(new Square(8, 6, 'A'));
		sqs2.add(new Square(8, 8, 'E'));
		g.addWord(sqs2, false);
		ArrayList<Square> sqs3 = new ArrayList<Square>();
		sqs3.add(new Square(6, 5, 'L'));
		sqs3.add(new Square(7, 5, 'O'));
		sqs3.add(new Square(8, 5, 'G'));
		g.addWord(sqs3, false);
		ArrayList<Square> sqs4 = new ArrayList<Square>();
		sqs4.add(new Square(6, 6, 'O'));
		sqs4.add(new Square(6, 8, 'E'));
		g.addWord(sqs4, false);
		ArrayList<Square> sqs5 = new ArrayList<Square>();
		sqs5.add(new Square(8, 9, 'S'));
		sqs5.add(new Square(9, 9, 'T'));
		sqs5.add(new Square(10, 9, 'O'));
		sqs5.add(new Square(11, 9, 'P'));
		assertEquals("GATES and STOP -> 12 ", 12,  g.addWord(sqs5, false));
	}
	
	@Test
	public void testMulitpleWordsValid5() {
		GameBoard g = new GameBoard("smallDictionary.txt", letterBag);
		ArrayList<Square> sqs1 = new ArrayList<Square>();
		sqs1.add(new Square(6, 7, 'B'));
		sqs1.add(new Square(7, 7, 'A'));
		sqs1.add(new Square(8, 7, 'T'));
		g.addWord(sqs1, true);
		ArrayList<Square> sqs2 = new ArrayList<Square>();
		sqs2.add(new Square(8, 6, 'A'));
		sqs2.add(new Square(8, 8, 'E'));
		g.addWord(sqs2, false);
		ArrayList<Square> sqs3 = new ArrayList<Square>();
		sqs3.add(new Square(6, 5, 'L'));
		sqs3.add(new Square(7, 5, 'O'));
		sqs3.add(new Square(8, 5, 'G'));
		g.addWord(sqs3, false);
		ArrayList<Square> sqs4 = new ArrayList<Square>();
		sqs4.add(new Square(6, 6, 'O'));
		sqs4.add(new Square(6, 8, 'E'));
		g.addWord(sqs4, false);
		ArrayList<Square> sqs5 = new ArrayList<Square>();
		sqs5.add(new Square(7, 6, 'B'));
		assertEquals("OBA and OBA -> 10 ", 10,  g.addWord(sqs5, false));
	}
	
	@Test
	public void testMulitpleWordsValid6() {
		GameBoard g = new GameBoard("smallDictionary.txt", letterBag);
		ArrayList<Square> sqs1 = new ArrayList<Square>();
		sqs1.add(new Square(7, 7, 'B'));
		sqs1.add(new Square(8, 7, 'A'));
		sqs1.add(new Square(9, 7, 'T'));
		g.addWord(sqs1, true);
		ArrayList<Square> sqs2 = new ArrayList<Square>();
		sqs2.add(new Square(9, 6, 'A'));
		sqs2.add(new Square(10, 6, 'P'));
		sqs2.add(new Square(11, 6, 'P'));
		sqs2.add(new Square(12, 6, 'L'));
		sqs2.add(new Square(13, 6, 'E'));
		sqs2.add(new Square(14, 6, 'S'));
		g.addWord(sqs2, false);
		ArrayList<Square> sqs3 = new ArrayList<Square>();
		sqs3.add(new Square(14, 7, 'O'));
		assertEquals("SO -> 2 ", 2,  g.addWord(sqs3, false));
	}
	
	@Test
	public void testMulitpleWordsInvalid() {
		GameBoard g = new GameBoard("smallDictionary.txt", letterBag);
		ArrayList<Square> sqs1 = new ArrayList<Square>();
		sqs1.add(new Square(7, 7, 'B'));
		sqs1.add(new Square(8, 7, 'A'));
		sqs1.add(new Square(9, 7, 'T'));
		g.addWord(sqs1, true);
		ArrayList<Square> sqs2 = new ArrayList<Square>();
		sqs2.add(new Square(9, 6, 'A'));
		sqs2.add(new Square(10, 6, 'P'));
		sqs2.add(new Square(11, 6, 'P'));
		sqs2.add(new Square(12, 6, 'E'));
		sqs2.add(new Square(13, 6, 'A'));
		sqs2.add(new Square(14, 6, 'L'));
		sqs2.add(new Square(15, 6, 'S'));
		assertEquals("off the board -> -1 ", -1,  g.addWord(sqs2, false));
		
	}
}
