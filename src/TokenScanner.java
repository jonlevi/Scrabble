
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.io.IOException;
import java.io.Reader;

/**
 * Provides a token Iterator for a given Reader.
 * <p>
 * Hint: See the code for the WordScanner from Lecture.
 */
public class TokenScanner implements Iterator<String> {
	private int c;
	private Reader r;
	
  /**
   * Creates a TokenScanner for a given Reader.
   * <p>
   * As an Iterator, the TokenScanner should only read from the Reader as much
   * as is necessary to determine getNext() and next(). The TokenScanner should
   * NOT read the entire stream and compute all of the tokens in advance.
   * <p>
   *
   * @param in the source Reader for character data
   * @throws IOException if there is an error in reading
   * @throws IllegalArgumentException when the provided Reader is null
   */
  public TokenScanner(java.io.Reader in) throws IOException {
	  if (in==null) throw new IllegalArgumentException();
	  this.r = in;
	  c = r.read();
	  
  }

  /**
   * Determines whether a given character is a valid word character.
   * <p>
   * Valid word characters are letters (according to
   * Character.isLetter) and single quote '\''.
   *
   * @param c the character to check
   * @return true if the character is a word character
   */
  public static boolean isWordCharacter(int c) {
    return (Character.isLetter(c) || ((char)c)=='\'');
  }


   /**
   * Determines whether a given String is a valid word
   * <p>
   * Valid words are not null or the empty string. They 
   * only contain word characters.
   *
   * @param s the string to check
   * @return true if the string is a word
   */
  public static boolean isWord(String s) {
		if (s==null || s.length()<=0) return false;
		for (int i = 0; i < s.length(); i++) {
			if (!(isWordCharacter(s.codePointAt(i)))) {
				return false;
			}
		}
		return true;
  }

  /**
   * Determines whether there is another token available.
   */
  public boolean hasNext() {
    return c != -1;
  }

  /**
   * Returns the next token, or throws a NoSuchElementException if none remain.
   *
   * @throws NoSuchElementException when the end of stream is reached
   */
  public String next() {
		if (!hasNext()) {
			throw new NoSuchElementException();
		}
		String answer = "";
		boolean isWordChar = isWordCharacter(c);
		
		try {
			if (isWordChar) {
				while(isWordCharacter(c) && c != -1) {
					answer += (char)c;
					c = r.read();
				}	
			} else {
				while(!isWordCharacter(c) && c != -1) {
					answer += (char)c;
					c = r.read();
				}
			}	
		} catch (IOException e) {
			throw new NoSuchElementException();
		}
		return answer;
  }

  /**
   * We don't support this functionality with TokenScanner, but since
   * the method is required if implementing Iterator, we just
   * <code>throw new UnsupportedOperationException();</code>
   *
   * @throws UnsupportedOperationException since we do not support 
   * this functionality
   */
  public void remove() {
    throw new UnsupportedOperationException();
  }
}
