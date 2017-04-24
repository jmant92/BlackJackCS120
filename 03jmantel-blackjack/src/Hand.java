import java.util.ArrayList;

/**
 * This Class is the hand. It is both the player's and the Dealer's hand. It will 
 * be a super class for BlackJackHand. It can add the next card in the deck, or a specific card.
 * It can remove a card from the deck. It can empty the hand. It can sort the cards in the hand by rank or
 * by face value. It can show a specific card in the hand or the whole hand.
 */
public class Hand {
	private ArrayList<Card> holding; // All of the cards currently in hand

	/**
	 * The deck is passed through the constructor to make sure the players share 
	 * the same deck.
	 */
	public Hand(Deck d) {
		holding = new ArrayList<Card>();
	}

	/**
	 * This method adds a random card to the player's hand
	 */
	public void addCard(Deck d) {
		this.holding.add(d.deal());
		// This deals a card from the deck to the player's hand
	}
	
	/**
	 * This method adds a specific card to the player's hand
	 */
	public void addSpecific(Card c) {
		this.holding.add(c);
	}

	/**
	 * This method removes a specific card from the player's hand and places it into
	 * a specific deck.
	 */
	public void removeCard(Deck d, Card c) {
		this.holding.remove(c);
		// remove the card c from the hand
		d.returnCard(c);
		// and return it to the deck.
	}

	/**
	 * This method ditches the whole hand
	 */
	public void emptyHand() {
		while(!holding.isEmpty()) {
			this.holding.remove(0);
		}
	}

	/**
	 * This method sorts the cards by their rank
	 */
	public void rankSort() {
		Card[] tempHand = new Card[holding.size()]; // For temp storage of the largest ranks
		int i=0; // we'll need this for the number of iterations

		while(holding.size()>0) {
			int bigRank = holding.get(0).getRank(); // tells us the bigger number in the list
			Card bigCard = holding.get(0); // assume the first card in the array list
			// is the one with the highest rank

			if(holding.size()>1) { // if the hand has more than one card in it
				// go through all of this to find the order of the cards from the
				// largest to the smallest ranks.
				for(int j=0; j<holding.size(); j++) { // go through the hand
					bigRank = bigCard.compareTo(holding.get(j));
					// set the biggest rank to be the one that is returned

					for(Card c: holding) { // for every card c in the arraylist holding
						if(bigRank == c.getRank()) { // if what was found to be the biggest
							// rank is equal to that card c
							bigCard = c; // make that card c the biggest card so it can be compared to the next
						}
					}
				}
			}
			// It has exited the for loop and the biggest card is decided
			// we want to add that card to the tempHand and delete it from holding

			tempHand[i] = bigCard; // add that card to the tempArray

			for(int j=0; j<holding.size(); j++) { // for every card c in the arraylist holding
				if(holding.get(j).getRank() == bigCard.getRank()) { // if 
					holding.remove(j);
				}
			}

			/*
			 * What this code is essentially saying is: 
			 * Go through every card in the deck up until the second to last one.
			 * Compare a card with the one after it.
			 * Now go through the list of cards in the hand and find which card has
			 * the rank that was found to be the bigger rank.
			 * Set bigger card to be that one with the bigger rank.
			 * Go through the list again and compare it with the next one.
			 * Once it finishes going through the hand and finds the biggest one,
			 * add it to the temporary hand and remove that from holding
			 * This while loop will make the list go through the for loop until
			 * there is only one card left in holding. When that is the only card
			 * left then the biggest card will be the only one left.
			 * (if there was only one card in the hand in the first place,
			 * then that one card will be set as the biggest)
			 */

			i++;
		}
		
		// Now refill the hand
		for(int j=0; j<tempHand.length; j++) {
			holding.add(tempHand[j]);
		}
	}

	/**
	 * This method sorts cards by their face value
	 */
	public void faceSort() {
		// the highest face value
		int betterRank = holding.get(0).getRank(); // here in case of a tie
		Card[]  tempHand=new Card[holding.size()];
		int i=0;

		while(!this.holding.isEmpty()) { // as long as the hand isn't empty.
			Card bigCard = this.holding.get(0); // assume the first card in hand has the highest
			// face value
				for(int j=0; j<this.holding.size(); j++) {
					if(bigCard.equals(this.holding.get(j))) { // See if the two cards have the same face values
						betterRank = bigCard.compareTo(this.holding.get(j)); // which has the better rank?

						for(Card c: this.holding) {
							if(c.getRank()==betterRank) {
								bigCard = c;
							}
						}

					} else if(bigCard.getFaceNum()<this.holding.get(j).getFaceNum()) { // if not
						// is the card number that the
						// list is on bigger than the current biggest card?
						bigCard = this.holding.get(j); // set the new biggest card to be
						// the one the list is on
					}
				}
				
				// Once it has gone through the entire hand, it has found the biggest card
				// Add that to index i of the temp hand
				tempHand[i] = bigCard;
				for(int j=0; j<this.holding.size(); j++) { // for every card c in the arraylist holding
					if(this.holding.get(j).getRank() == bigCard.getRank()) { // if the card is found in the hand
						this.holding.remove(j); // remove that from the hand so that card won't be counted more than once
					}
				}
				i++; // move to the next spot in the tempHand
		}
		
		// Once all of that is done, copy the tempHand back to the player's hand
		for(int j=0; j<tempHand.length; j++) {
			this.holding.add(tempHand[j]);
		}
	}

	
	/**
	 * Get a specific card from the hand
	 */
	public Card getCard(int i) {
		return holding.get(i);
	}

	/**
	 * Shows what the whole hand looks like
	 */
	public ArrayList<Card> getHand() {
		return this.holding;
	}
}
