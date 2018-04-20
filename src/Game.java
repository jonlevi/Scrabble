import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;


public class Game implements Runnable{
	
	
	final String instructionsMessage = 
			"Welcome to Scrabble! \n"
			+ "There are a few things you need to know before you play. \n"
			+ "When it is your turn, you can opt to do a few things: \n"
			+ "Pass, Swap Tiles, or Submit a move. \n"
			+ "1) Pass: It will now be the other player's turn \n"
			+ "2) Swap Tiles: You can trade all of your tiles in \n"
			+ "but forfeit your turn. On the next turn you will start"
			+ "with fresh tiles. \n"
			+ "3) Tiles Left: This will tell you how many tiles are left in the bag \n"
			+ "4) Submit: \n"
			+ "In order to play tiles, you first click on the tile on your tile bench, \n"
			+ "and then click again on the spot on the board where you want to place the tile. \n"
			+ "If at any point you realize you want to change your placement on the board, \n"
			+ "simply click the undo button to reset your bench to where it was \n"
			+ "when your turn started. After you finish your move, click submit \n"
			+ "to submit your move. \n"
			+ "Make sure to look at the picture below to see the letters' point values! \n"
			+ "If at any point you want to see the instructions, press the instructions button. \n"
			+ "Click OK to continue playing!";
	
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Game());
	}
	

	
	/**
	 * 
	 * @param tempBoard - current state of board
	 * @param actualBoard - board to reset to
	 */
	private void resetBoard(GameBoard tempBoard, GameBoard actualBoard) {
		Square[][]  currBoard = tempBoard.getCurrentBoard();
		Square[][] oldBoard = actualBoard.getCurrentBoard();
		for (int row = 0; row < currBoard.length; row++) {
			for (int col = 0; col < currBoard[row].length; col++) {
				Square sq = currBoard[row][col];
				Square oldSq = oldBoard[row][col];
				sq.setContent(oldSq.getContent());
				sq.repaint();
			}
		}
	}
	
	private String getUsername(String player) {
		String tgt = JOptionPane.showInputDialog(null, player + ", Enter Your Name:");
		if (tgt==null) return getUsername(player);
		else return tgt;
	}
		
	public void run() {
		JOptionPane.showMessageDialog(null,instructionsMessage,
				"Instructions", JOptionPane.INFORMATION_MESSAGE);
		
		String name1 = getUsername("Player 1");
		String name2 = getUsername("Player 2");
		
		//Top level frame
		final JFrame frame = new JFrame("Scrabble");
		frame.setLocation(500, 500);
		frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));
		
		
		
		//initialize game objects
		final LetterBag letterBag = new LetterBag("letters.txt");

		final Player p1 = new Player(name1, letterBag.drawTiles(7), true);
		final Player p2 = new Player(name2, letterBag.drawTiles(7), false);
		
		
		//score board panel
		final JPanel scoreBoard = new JPanel();
		scoreBoard.setLayout(new GridLayout(1, 3));
		final JLabel score1 = new JLabel("\t\t\t\t"+ p1.getName() + "'s Score is " + p1.getScore() + " points");
		final JLabel score2 = new JLabel(p2.getName() + "'s Score is " + p2.getScore() + " points");
		final JLabel turn = new JLabel("\t\t\t\t\t  It's " + p1.getName() + "'s Turn");
		
		
		scoreBoard.add(score1);
		scoreBoard.add(turn);
		scoreBoard.add(score2);
		
		
		//game panel
		//final JPanel gamePanel = new JPanel();
		//gamePanel.setLayout(new GridLayout(2, 1));
		
		//variables to help with player input
		final Square selectedLetter = new Square(-1, -1);
		final List<Square> squaresToSubmit = new LinkedList<Square>();
		
		
		//interactive tile bench
		//click on letter to select it
		//then click on square to place it
		final JPanel tileBenchPanel = new JPanel();
		Player currPlayer = (p1.isMyTurn() ? p1 : p2);
		for (int i = 0; i < currPlayer.getBenchSize(); i++) {

			char c = currPlayer.getLetter(i);
			final JButton b = new JButton(Character.toString(c));
			tileBenchPanel.add(b);
			
			b.addActionListener( new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					boolean blank = b.getText().equals("");
					boolean selected = selectedLetter.hasContent();
					if (!blank && !selected) {
						selectedLetter.setContent(b.getText().charAt(0));
						b.setText("");
					}
				}
			});
		}
		
		//create game board for actual state
		//create temporary board for pre-submission state
		
		final GameBoard board = new GameBoard("ScrabbleDictionary.txt", letterBag);
		final GameBoard tempBoard = new GameBoard("ScrabbleDictionary.txt", letterBag);
		Square[][]  currBoard = tempBoard.getCurrentBoard();
		for (int row = 0; row < currBoard.length; row++) {
			for (int col = 0; col < currBoard[row].length; col++) {
				final Square sq = currBoard[row][col];
				sq.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						if ((!sq.hasContent()) && (selectedLetter.hasContent())) {
							sq.setContent(selectedLetter.getContent());
							sq.repaint();
							squaresToSubmit.add(sq);
							selectedLetter.setContent((char)-1);
						}
					}
				});
			}
		}
		
		//game button intialization
		
		final JPanel gameButtonPanel = new JPanel();
		
		
		//undo resets the tileRack to the player's bench
		//also resets the game board
		final JButton undo = new JButton("Undo");
		undo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				resetBoard(tempBoard, board);
				selectedLetter.setContent((char)-1);
				tileBenchPanel.removeAll();
				squaresToSubmit.clear();
				Player currPlayer = (p1.isMyTurn() ? p1 : p2);
				for (int i = 0; i < currPlayer.getBenchSize(); i++) {
					char c = currPlayer.getLetter(i);
					final JButton b = new JButton(Character.toString(c));
					tileBenchPanel.add(b);
					
					b.addActionListener( new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							if (!(b.getText().equals("")) && 
									(!selectedLetter.hasContent())) {
								selectedLetter.setContent(b.getText().charAt(0));
								b.setText("");
							}
						}
					});
				}
				frame.getContentPane().validate();
				frame.getContentPane().repaint();
				
			}
		});
		
		//player can opt to pass instead of submitting a move
		final JButton pass = new JButton("Pass");
		pass.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {	
				resetBoard(tempBoard, board);
				
				selectedLetter.setContent((char)(-1));
				Player currPlayer = (p1.isMyTurn()) ? p2 : p1; //opposite
				p1.setMyTurn(!p1.isMyTurn());
				p2.setMyTurn(!p2.isMyTurn());
				turn.setText("It's " + currPlayer.getName() + "'s Turn");
				
				tileBenchPanel.removeAll();
				for (int i = 0; i < currPlayer.getBenchSize(); i++) {
					char c = currPlayer.getLetter(i);
					final JButton b = new JButton(Character.toString(c));
					tileBenchPanel.add(b);
					
					b.addActionListener( new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							if (!(b.getText().equals("")) &&
									(!selectedLetter.hasContent())) {
								selectedLetter.setContent(b.getText().charAt(0));
								b.setText("");
							}
						}
					});
				}
				//to repack and paint all changes
				frame.getContentPane().validate();
				frame.getContentPane().repaint();
				
				
				
			}
		});
		
		//swap tiles, but give up your turn
		final JButton swap = new JButton("Swap Tiles");
		swap.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {		
				resetBoard(tempBoard, board);
				selectedLetter.setContent((char)(-1));
				
				Player currPlayer = (p1.isMyTurn()) ? p1 : p2;
				List<Character> newLetters = letterBag.swapTiles(currPlayer.getAll());
				currPlayer.clear();
				currPlayer.addLetters(newLetters);
				
				currPlayer = (p1.isMyTurn()) ? p2 : p1; //opposite
				turn.setText("It's " + currPlayer.getName() + "'s Turn");
				p1.setMyTurn(!p1.isMyTurn());
				p2.setMyTurn(!p2.isMyTurn());
				tileBenchPanel.removeAll();
				selectedLetter.setContent((char)-1);
				for (int i = 0; i < currPlayer.getBenchSize(); i++) {
					char c = currPlayer.getLetter(i);
					final JButton b = new JButton(Character.toString(c));
					tileBenchPanel.add(b);
					
					b.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							if (!(b.getText().equals("")) &&
									(!selectedLetter.hasContent())) {
								selectedLetter.setContent(b.getText().charAt(0));
								b.setText("");
							}
						}
					});
				}
				frame.getContentPane().validate();
				frame.getContentPane().repaint();
				
				
			}
		});
		
		//submit button
		
		final JButton submit = new JButton("Submit");
		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (squaresToSubmit.isEmpty()) {
					JOptionPane.showMessageDialog(null,"Please Make a Move "
							+ "Before Submitting",
							"Invalid Move", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				boolean firstTurn = ((p1.getScore()==0 && p2.getScore()==0));
				int pointsScored = board.addWord(squaresToSubmit, firstTurn);
				
				if (pointsScored > 0) {
					//update scores and score board
					Player currPlayer = (p1.isMyTurn()) ? p1 : p2;
					
					currPlayer.addToScore(pointsScored);
					score1.setText("\t" + p1.getName() + "'s Score is " + p1.getScore() + " points");
					score2.setText(p2.getName() + "'s Score is " + p2.getScore() + " points");
					
					List<Character> lettersUsed = new ArrayList<Character>();
					for (Square s: squaresToSubmit) {
						lettersUsed.add(s.getContent());
					}
					
					squaresToSubmit.clear();
					selectedLetter.setContent((char)(-1));
					currPlayer.useLetters(lettersUsed);
					currPlayer.addLetters(letterBag.drawTiles(lettersUsed.size()));
					
					//when the game ends
					if (currPlayer.getBenchSize()==0) {
						boolean pOneWinner = (p1.getScore()>p2.getScore());
						String winner = (pOneWinner) ? p1.getName() : p2.getName();
						winner = "The winner is " + winner + "!\n";
						winner += p1.getName() + " had " + p1.getScore() + " points \n"
								+ p2.getName() + " had " + p2.getScore() + " points";
						
						JOptionPane.showMessageDialog(null, winner, "Game Over",
								JOptionPane.INFORMATION_MESSAGE);
						System.exit(1);
					}
					
					lettersUsed.clear();
					
					//change the tilerack to the second player's
					currPlayer = (p1.isMyTurn()) ? p2 : p1; //opposite
					turn.setText("It's " + currPlayer.getName() + "'s Turn");
					p1.setMyTurn(!p1.isMyTurn());
					p2.setMyTurn(!p2.isMyTurn());
					
					tileBenchPanel.removeAll();
					for (int i = 0; i < currPlayer.getBenchSize(); i++) {
						char c = currPlayer.getLetter(i);
						final JButton b = new JButton(Character.toString(c));
						tileBenchPanel.add(b);
						
						b.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								if (!(b.getText().equals(""))&&
										(!selectedLetter.hasContent())) {
									selectedLetter.setContent(b.getText().charAt(0));
									b.setText("");
								}
							}
						});
					}
					
					//repack and repaints
					frame.getContentPane().validate();
					frame.getContentPane().repaint();
					
					
				} else {
					JOptionPane.showMessageDialog(null,"Invalid Move. Try Again",
							"Invalid Move", JOptionPane.ERROR_MESSAGE);
					//undoes everything if the move was invalid
					undo.doClick();
				}
				
				
				
				
			}
		});
		
		
		final JButton instructions = new JButton("Instructions");
		instructions.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null,instructionsMessage,
						"Instructions", JOptionPane.INFORMATION_MESSAGE);
			}
			
			
		});
		
		final JButton checkTilesLeft = new JButton("Tiles Left");
		checkTilesLeft.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null,"There are " + letterBag.getTilesLeft() +
						" tiles left in the game",
						"Tiles Left", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		//add scoreBoard to frame
		frame.add(scoreBoard);
		//add board to game panel
		frame.add(tempBoard);
		frame.add(tileBenchPanel);
		
		//add all of the buttons
		gameButtonPanel.add(undo);
		gameButtonPanel.add(submit);
		gameButtonPanel.add(pass);
		gameButtonPanel.add(swap);
		gameButtonPanel.add(instructions);
		gameButtonPanel.add(checkTilesLeft);
		
		//add the game and game button panel
		//frame.add(gamePanel);
		
		//pack everything into containers
		//gamePanel.add(tileBenchPanel);
		
		frame.add(gameButtonPanel);
		
		
		//add the picture of tiles and their worth
		try { 
			BufferedImage myPicture = ImageIO.read(new File("values.png"));
			JLabel picLabel = new JLabel(new ImageIcon(myPicture));
			JPanel picPanel = new JPanel();
			picPanel.add(picLabel);
			frame.add(picPanel);
		} catch (Exception e) {
			//do nothing
		}
		

		//top level stuff
		frame.validate();
		frame.setResizable(false);
		frame.setSize(620, 790);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
}
