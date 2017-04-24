/**
 * This class is the entire deck. It forms a deck of 52 cards. It constructs them in order.
 * It can shuffle the deck, deal a card, return a card to the deck, tell if it's empty, or the
 * number of cards left, and get the value of a specific card.
 */

public class Deck {
	private Card[] deck; // the deck of cards
	
	/**
	 * Construct the deck
	 */
	public Deck() {
		deck= new Card[52]; // each deck has 52 cards in it
		
		/*
		 * We create the deck by going through creating each numbered card of each suit
		 */
		int onCard = 0; // This variable tells us which card we're on
		
		for(int i=0; i<4; i++) { // go through each suit
			for(int j=0; j<13; j++) { // and each card within the suit
				deck[onCard]=new Card(i+1, j+1); // add that to the deck
				// it has to be i+1 and j+1 because 0 is neither a suit nor a card
				onCard++; // go to the next card
			}
		}
	}
	
	/**
	 * Shuffle the deck swapLeft times.
	 */
	public void shuffle(int swapLeft) {
		// Swap left is how many times the cards will be switched.
		// It always starts at 51 and will decrease by 1 through each iteration
		// It starts at 51 because the array starts at 0 and ends at 51
		
		// to shuffle we will take two random cards and switch their places
		
		if(swapLeft>=0) {
			int otherCard=(int)(Math.random()*50); // Take one random card, this will switch with the current card
			Card tempCard = deck[swapLeft]; // keep the data of the card the list is on
			
			deck[swapLeft]=deck[otherCard]; // make current card now the same as the second card
			deck[otherCard]=tempCard; // make the second card now the same as the first card
			
			swapLeft--; // go to the card before
			shuffle(swapLeft); // shuffle again
		}
	}
	
	/**
	 * Deal a card from the top of the deck
	 */
	public Card deal() {
		int cardDealt = 0;
		// the integer cardDealt will always be the first available card in the deck
		while(deck[cardDealt]==null) cardDealt++;

		// Once a card has been decided on, set the chosen card to be that card
		Card cardChosen = deck[cardDealt];
		// empty that place in the deck
		deck[cardDealt]=null;
		// return the card that is to be dealt
		return cardChosen;
	}
	
	/**
	 * Return cardReturned to the deck. It goes backwards through the deck
	 */
	public void returnCard(Card cardReturned) {
		int deckPlace = deck.length-1; // Start at the last index in the deck
		
		while((deck[deckPlace]!=null)&&(deckPlace>0)) { // while there is a card in index deckPlace
			// and the index is still larger than 0
			deckPlace--; // go to the spot one less
		}
		// Essentially, go backwards through the deck
		// and find the next spot in the deck that doesn't already have a card in it.
		
		
		if(deckPlace<1) { // if the index ever gets lower than 1
			deck[0]=cardReturned; // then the first card must be the one returned
		} else { // otherwise
			deck[deckPlace]=cardReturned; // that index should be where the card is returned
		}
	}
	
	/**
	 * Return a boolean saying if the deck is empty
	 */
	public boolean isEmpty() {
		if (deck[deck.length-1]==null) return true; // is the deck empty?
		else return false; // if not say so
	}
	
	/**
	 * Return an integer saying how many cards are left
	 */
	public int numCardsLeft() {
		int numCards=0; // start at 0
		for(int i=0; i<deck.length; i++) { // go through the deck
			if(deck[i]!=null) { // every card that is still there
				numCards++; // counts as 1
			}
		}
		return numCards; // return that number
	}
	
	/**
	 * This getter is to test if the deck was correctly made
	 */
	public Card getCard(int i) {
		return deck[i]; // return a specific card from the deck
	}
}
