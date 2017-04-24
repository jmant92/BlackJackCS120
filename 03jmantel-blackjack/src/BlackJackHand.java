/**
 * This class inherits most methods from the superclass Hand. The purpose of the methods in this class is to
 * give some basic info for the BlackJack Hand
 */

public class BlackJackHand extends Hand {

	/**
	 * Initialize the BlackJackHand using the constructor from the superclass. It also passes through the deck
	 */
	public BlackJackHand(Deck d) {
		super(d);
	}
	
	/**
	 * What is the value of the current hand?
	 */
	public int value() {
		int faceTotal=0; // start at 0 so every time the value is check, we can start it over
		
		for(int i=0; i<this.getHand().size(); i++) { // got through the hand
			if(this.getCard(i).getFaceNum()>10) { // if it's a face card
				faceTotal+=10; // just add 10
			} else {
				if(this.getCard(i).isAce()&&(faceTotal+11>21)) { // if the card is an ace and the hand exceeds 21
					faceTotal+=this.getCard(i).getFaceNum(); // add the face value (1)
				} else if(this.getCard(i).isAce()&&(faceTotal+11<=21)) { // if the card is an ace but
					// is less than or equal to 21
					faceTotal+=11; // add 11 to the value
				} else { // if none of the above
					faceTotal+=this.getCard(i).getFaceNum(); // just add the face value to it
				}
				
			}
		}
		
		return faceTotal; // return the final value of the hand
			
	}
	
	/**
	 * This method returns a boolean that says whether or not the hand is bust
	 */
	public boolean isBust() {
		return (this.value()>21); // if it's above 21, bust. If not, no bust
	}
	
	/**
	 *  This method returns a boolean that says whether or not it's a soft hand
	 */
	public boolean isSoft() {
		if(!this.isBust()) { // to make sure the hand isn't a bust
			for(int i=0; i<this.getHand().size(); i++) { // go through the hand
				if(this.getCard(i).isAce()) return true; // if any cards in the hand is an ace
			}
		}
		return false; // not a single ace was found in the hand.
	}

}
