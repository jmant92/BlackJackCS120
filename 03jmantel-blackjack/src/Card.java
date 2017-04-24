import java.lang.reflect.InvocationTargetException;

/**
 * This class is the card class. It holds methods for getting the suit number, face number, and rank.
 * It also gives the suit and face value as a string. It can be compared to other cards based on
 * both rank and face value. It can tell whether the card is an ace or not. It can also return the
 * name of the card as a toString.
 */

public class Card implements Comparable {
	private int suit;
	private int faceNum; // face value of the card
	private int rank;
	// list of constants for suits and face value cards
	private static final int CLUBS = 1;
	private static final int DIAMONDS = 2;
	private static final int HEARTS = 3;
	private static final int SPADES = 4;
	private static final int ACE = 1;
	private static final int JACK = 11;
	private static final int QUEEN = 12;
	private static final int KING = 13;
	
	/**
	 * Initialize the card with a suit and a faceNumber entered
	 */
	public Card(int suit, int faceNum) {
		/*
		 * The suits are as follows: 1 = clubs, 2 = diamonds, 3 = hearts,
		 * 4 = spades
		 */
		this.suit = suit;
		
		/*
		 * The card values are as follows ace=1, 2=2, 3=3...
		 * jack=11, queen=12, king=13
		 */
		this.faceNum = faceNum;
		
		// give the rank, with the special case for aces
		if(faceNum == 1) rank=12*4+this.suit;
		else rank=(faceNum-2)*4+this.suit;
	}
	
	public int getSuitNum() {
		return this.suit;
	}
	
	public int getFaceNum() {
		return this.faceNum;
	}
	
	public int getRank() {
		return this.rank;
	}
	
	/**
	 * Tell me the name of the suit
	 */
	public String getSuitString() {
		switch(this.suit) {
		case CLUBS:
			return "Clubs";
		case DIAMONDS:
			return "Diamonds";
		case HEARTS:
			return "Hearts";
		case SPADES:
			return "Spades";
		default:
			return "Couldn't assign suit";
		}
	}
	
	/**
	 * Tell me the name of the face
	 */
	public String getFaceString() {
		switch(faceNum) {
		case ACE:
			return "Ace";
		case 2:
			return "Two";
		case 3:
			return "Three";
		case 4:
			return "Four";
		case 5:
			return "Five";
		case 6:
			return "Six";
		case 7:
			return "Seven";
		case 8:
			return "Eight";
		case 9:
			return "Nine";
		case 10:
			return "Ten";
		case JACK:
			return "Jack";
		case QUEEN:
			return "Queen";
		case KING:
			return "King";
		default:
			return "Couldn't assign card";
		}
	}

	/**
	 * This method compares one card to another and returns the higher of the two ranks
	 */
	@Override
	public int compareTo(Object o) {
		int otherRank; // we need to quantify the rank of the other card
		try {
			otherRank = (int) o.getClass().getMethod("getRank", null).invoke(o, null);
			// have to invoke a specific method in a specific class
			
			if(otherRank > this.getRank()) return otherRank; // return the other rank
			// if it's bigger
			else if(this.getRank() > otherRank) return this.rank; // otherwise
			// return this rank
			else { 
				return this.rank; // just in case the object is being compared to itself
				// or something else not previously mentioned
			}
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}

	}
	
	/**
	 * Method that just returns whether a card is an ace or not
	 */
	public boolean isAce() {
		if (this.faceNum==ACE) return true; // if the face number is an ace
		else return false; // if it isn't
	}

	/**
	 * toString to say the name of the card
	 */
	@Override
	public String toString() {
		return this.getFaceString() + " of " + this.getSuitString();
	}
	
	/**
	 * An equals method to see if the face number is equal to a certain value
	 * It only compares the face values of the cards, not the actual cards
	 * themselves
	 */
	public boolean equals(Object c) {
		int otherFaceNum; // we need something to compare it to
		
		try {
			try {
				otherFaceNum = (int) (c.getClass().getMethod("getFaceNum", null).invoke(c, null));
				// invoke a specific method within a specific class
				return (otherFaceNum==this.getFaceNum()); // return true if equal, false if not
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
}
