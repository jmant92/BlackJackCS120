import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * BlackJack is the main class. Using the other class, it recreates the card game
 * black jack as a java applet. It will take care of all the graphical features and
 * rules of the game.
 * 
 * @author JonathanMantel
 */

public class BlackJack extends JApplet {
	// APPLET WINDOW SIZE
	private final int MAX_WIDTH = 800; // Width of the applet
	private final int MAX_HEIGHT = 800; // Height of the applet

	// EXTRA CONSTANTS
	private final int SHUFFLE = 51; // there are 52 cards in the deck, but the deck
	// index starts at 0.
	private final int DECK = 52; // for the 52 cards in a deck

	// DECK AND HANDS
	private Deck d; // The deck to be used
	private BlackJackHand player; // the player's hand
	private BlackJackHand dealer; // the dealer's hand
	private int place;

	// CARD IMAGES
	private Image back;
	private ArrayList<Image> cards;
	private ArrayList<Image> playerCards;
	private ArrayList<Image> dealerCards;

	// JButtons and JLabels
	private JButton btnRestart;
	private JButton btnStand;
	private JButton btnHitMe;
	private JButton btnQuit;
	private JLabel lblInstructions;

	// MISC
	private int onCard; // This is so the images will be assigned
	// to the appropriate card
	private boolean stand; // is the player standing
	private boolean dealerStand; // is the dealer standing

	public BlackJack()  {
		this.getContentPane().setLayout(new BorderLayout());

		JPanel pnlButtons = new JPanel();
		getContentPane().add(pnlButtons, BorderLayout.SOUTH);

		btnRestart = new JButton("Restart");
		btnRestart.addActionListener(new ButtonHelper());
		pnlButtons.add(btnRestart);

		btnStand = new JButton("I Stand");
		btnStand.addActionListener(new ButtonHelper());
		pnlButtons.add(btnStand);

		btnHitMe = new JButton("Hit Me");
		btnHitMe.addActionListener(new ButtonHelper());
		pnlButtons.add(btnHitMe);

		btnQuit = new JButton("Quit");
		btnQuit.addActionListener(new ButtonHelper());
		pnlButtons.add(btnQuit);

		JPanel pnlMain = new SpecialPanel();
		getContentPane().add(pnlMain, BorderLayout.CENTER);

		JPanel panelTop = new JPanel();
		getContentPane().add(panelTop, BorderLayout.NORTH);

		lblInstructions = new JLabel("instructions");
		panelTop.add(lblInstructions);
	}

	/**
	 * Initialization of all the variables
	 */
	public void init() {
		this.setSize(MAX_WIDTH, MAX_HEIGHT);

		cards = new ArrayList<Image>();
		playerCards = new ArrayList<Image>();
		dealerCards = new ArrayList<Image>();

		onCard=0; // start at the first card
		stand=false; // the player has not hit stand yet
		dealerStand=false; // the dealer hasn't stopped drawing cards

		// Read the back of the card image
		try {
			back = ImageIO.read(new File("back.jpg"));
		} catch (IOException e) {
			System.out.println("Could not load image.");
		}

		// resize the back of the card image
		back = back.getScaledInstance(73, 100, 0);

		// create a new deck, player hand, and dealer hand
		d=new Deck();
		player=new BlackJackHand(d);
		dealer=new BlackJackHand(d);

		// Shuffle the deck
		d.shuffle(SHUFFLE);

		/*
		 * Because we just shuffled the deck, the cards are in a random order
		 * When we create the images, they will be assigned to the right card
		 * in the deck, because their file name is derived from the names of the
		 * cards.
		 */

		// A way to connect all of the images to a particular card
		for(int j=0; j<52; j++) { // go through every card in the deck
			try {
				// get that card's suit and face value.
				// then assign it to an image in order
				// then add it to the cards Image ArrayList
				// This only works because the file reader ignores case, whew.
				cards.add(ImageIO.read(new File(d.getCard(j).getSuitString()+d.getCard(j).getFaceNum()+".jpg")));
			} catch (IOException e) {
				System.out.println("Could not load image.");
				System.out.println("Misread card: "+d.getCard(j).getSuitString()+d.getCard(j).getFaceNum()+".jpg");
			}
		}

		// start the game
		gameStart();
	}

	/**
	 * This method will be called after each round finishes. If the deck has less than
	 * 20 cards left, then it's time to reset the deck.
	 */
	public boolean resetDeck() {
		if(d.numCardsLeft()<20) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Restarts the game, hands, and deck
	 */
	public void startOver() {
		
			while(!cards.isEmpty()) { // while the list of card images has cards in it
				cards.remove(0); // remove the first card
			}
			
			// Empty out the player's hand
			while(!playerCards.isEmpty()) {
				playerCards.remove(0);
			}
			player.emptyHand();
			
			// Empty out the dealer's hand
			while(!dealerCards.isEmpty()) {
				dealerCards.remove(0);
			}
			dealer.emptyHand();
			
			// Empty the deck
			d=null;

			// make a new deck
			d = new Deck();

			// Shuffle it
			d.shuffle(SHUFFLE);

			// assign a card image to each card in the deck
			for(int j=0; j<52; j++) {
				try {
					// get that card's suit and face value.
					// then assign it to an image in order
					// then add it to the cards Image ArrayList
					// This only works because the file reader ignores case, whew.
					cards.add(ImageIO.read(new File(d.getCard(j).getSuitString()+d.getCard(j).getFaceNum()+".jpg")));
				} catch (IOException a) {
					System.out.println("Could not load image.");
					System.out.println("Misread card: "+d.getCard(j).getSuitString()+d.getCard(j).getFaceNum()+".jpg");
				}
			}
			
			stand = false; // the player doesn't stand
			dealerStand = false; // the dealer doesn't stand
			// Start the game over
			onCard = 0;
			gameStart();
	}
	
	
	/**
	 * For when you want to start a new game but not reset the deck.
	 */
	public void newGame() {
		while(!playerCards.isEmpty()) { // remove the player hand
			playerCards.remove(0);
		}
		player.emptyHand();

		while(!dealerCards.isEmpty()) { // remove the dealer's hand
			dealerCards.remove(0);
		}
		dealer.emptyHand();
		
		stand = false; // the player doesn't stand
		dealerStand = false; // the dealer doesn't stand
		gameStart();
	}

	/**
	 * Start the game by adding two cards to the player's hand and two cards to the dealers hand
	 */
	public void gameStart() {
		
		int playersInitCards = onCard+2; // set up so the user gets the first two cards
		int dealersInitCards = onCard+4; // and the dealer gets the next two
		while(onCard<playersInitCards) { // first two cards
			player.addCard(d); // give to the user
			playerCards.add(cards.get(onCard));
			onCard++;
		}

		while (onCard<dealersInitCards) { // next two, give to the dealer
			dealer.addCard(d);
			dealerCards.add(cards.get(onCard));
			onCard++;
		}
	}
	
	/**
	 * A special panel that paints the entire field
	 */
	private class SpecialPanel extends JPanel {

		public SpecialPanel(){
			super();
		}

		@Override 
		public void paint(Graphics g) {
			super.paint(g);

			// Make the background green and cover
			g.setColor(Color.green);
			g.fillRect(0, 0, MAX_WIDTH, MAX_HEIGHT);

			// if there are cards left in the deck
			if (d.numCardsLeft()>0) {
				// draw the image of the back of a card, to simulate a deck being present
				g.drawImage(back, MAX_WIDTH/2-back.getWidth(null)/2, MAX_HEIGHT/2-back.getHeight(null)/2, null);
			}


			int i=0; // this variable is going to go through each element in the cards ArrayList
			// it will be compared with the player's and dealer's hands
			place = MAX_WIDTH/2; // placement of the cards
			while(i<player.getHand().size()) { // go through the player's hand
				// and draw that card
				g.drawImage(playerCards.get(i), place-cards.get(i).getWidth(null)/2, MAX_HEIGHT-cards.get(i).getHeight(null)-100, null);
				// move the card over half a card width
				place+=cards.get(i).getWidth(null)/2;
				// move onto the next card
				i++;
			}

			// reset placement for the dealer
			place = MAX_WIDTH/2;
			i=0;
			while(i<dealer.getHand().size()) {
				if(i==0) { // if it's the dealer's first card
					g.drawImage(back, place-back.getWidth(null), back.getHeight(null), null); // draw the back
				} else { // otherwise
					g.drawImage(dealerCards.get(i), place-cards.get(i).getWidth(null), cards.get(0).getHeight(null), null); // draw the card face
				}
				if (dealerStand) { // when the dealer stands
					g.drawImage(dealerCards.get(i), place-cards.get(i).getWidth(null), cards.get(i).getHeight(null), null); // flip over his first card
				}
				place+=cards.get(i).getWidth(null)/2; // move over half a card width
				i++;
			}

			// This next bit of code will show the status of the game at the end
			if(stand) {
				g.setColor(Color.black);
				if(player.isBust()) { // if the player busts
					g.drawString("Player Busts", MAX_WIDTH/2+50, MAX_HEIGHT/2); 
					g.drawString("Dealer Win", MAX_WIDTH/2+50, MAX_HEIGHT/2+20);
				} else if (dealer.isBust()) { // dealer busts
					g.drawString("Dealer Busts", MAX_WIDTH/2+50, MAX_HEIGHT/2);
					g.drawString("Player Win", MAX_WIDTH/2+50, MAX_HEIGHT/2+20);
				} else if (dealer.value()>player.value()) { // dealer has higher end game cards
					g.drawString("Dealer Win", MAX_WIDTH/2+50, MAX_HEIGHT/2);
				} else if (dealer.value()<player.value()) { // dealer has lower end game cards
					g.drawString("Player Win", MAX_WIDTH/2+50, MAX_HEIGHT/2);
				} else if (dealer.value()==player.value() ){ // both have equal cards
					g.drawString("Push", MAX_WIDTH/2+50, MAX_HEIGHT/2);
				}
				
				if(resetDeck()) { // if there are less than 20 cards in the deck
					startOver(); // start the game over with a new deck
				} else { // otherwise
					newGame(); // start the game over with the same deck
				}
			}
		}

	}

	/**
	 * Does all the Button actions
	 */
	private class ButtonHelper implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			// RESTART
			if(e.getSource()==btnRestart) {
				if(resetDeck()) { // if there are less than 20 cards in the deck
					startOver(); // start the game over with a new deck
				} else { // otherwise
					newGame(); // start the game over with the same deck
				}
				repaint();
			}

			// STAND
			if(e.getSource()==btnStand) {
				stand = true;
				while(dealer.value()<17) { // while the dealer is still under the value 17
					dealer.addCard(d); // add a card to the dealer's hand
					dealerCards.add(cards.get(onCard)); // and the image
					onCard++; // move onto the next card
					repaint();
				}
				dealerStand=true; // the dealer stands
				repaint();
			}

			// HIT ME
			if(e.getSource()==btnHitMe) {
				player.addCard(d); // add a card to the player's hand
				playerCards.add(cards.get(onCard)); // add the image
				onCard++; // move onto the next card in the list
				
				if(player.value()>=21) {
					stand = true; // the player stands automatically once they 
					// reach 21 or bust
				}
				repaint();
			}

			// QUIT
			if(e.getSource()==btnQuit) {
				System.out.println("Quitting Game");
				System.exit(0);
			}
		}

	}
}
