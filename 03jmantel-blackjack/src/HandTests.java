import static org.junit.Assert.*;

import org.junit.Test;

/**
 * This holds the JUnit Test Cases for the Hand class. It tests mainly the rank sort and face sort.
 * In those tests however, it also tests the smaller methods.
 */

public class HandTests {

	/**
	 *  Does the rank sort actually work? If it does, then the cards will
	 *  be sorted from the largest to the smallest rank.
	 */
	@Test
	public void rankSortTest() {
		Deck d = new Deck(); // test deck
		Hand h = new Hand(d); // test hand
		
		System.out.println("Cards in the deck"); // I'm keeping these print lines because
		// it'll be easier to know what's going on by reading the console
		// However, the tests will still be done.
		for(int i=0; i<10; i++) {
			System.out.println(d.getCard(i)); // what are the first 10 cards in the deck post-shuffling
			// Even when two hands are made (The dealer's and players)
			// the deck being shuffled twice doesn't matter because we only care
			// about the cards we can see
		}
		
		// Give the hand four cards in addition to the initial card
		// for a total of five
		h.addCard(d);
		h.addCard(d);
		h.addCard(d);
		h.addCard(d);
		h.addCard(d);
		
		System.out.println();
		System.out.println("Cards in hand");
		for(int i=0; i<h.getHand().size(); i++) {
			System.out.println(h.getCard(i)); // tell me the cards in the hand. They should be the same as the
		}
		System.out.println();
		
		h.rankSort();
		
		// The sorting should be first by number:
		// ace, king, queen,...two.
		// Then suit:
		// Spades, Hearts, Diamonds, Clubs
		System.out.println();
		System.out.println("Now lets sort them!");
		for(int i=0; i<h.getHand().size(); i++) {
			System.out.println(h.getCard(i));
			if(i<h.getHand().size()-1) { // if it's not on the last card
				assertTrue(h.getCard(i).getRank()>h.getCard(i+1).getRank());
				// check to see if the cards are placed in the right order
				// with respect to rank
			}
		}
	}

	/**
	 * Face sort tests
	 */
	@Test
	public void faceSortTest() {
		Deck d = new Deck(); // test deck
		Hand h = new Hand(d); // test hand
		
		// Add 5 cards to the hand
		h.addCard(d);
		h.addCard(d);
		h.addCard(d);
		h.addCard(d);
		h.addCard(d);
		
		System.out.println("Cards in hand");
		for(int i=0; i<h.getHand().size(); i++) { // go through the hand
			System.out.println(h.getCard(i)); // tell me the cards in the hand. They should be the same as the
		}
		
		// sort the deck by face value (King high, aces low)
		h.faceSort();
		
		System.out.println();
		System.out.println("Now lets face sort them!");
		System.out.println("The hand size: "+h.getHand().size());
		System.out.println(h.getHand());
		for(int i=0; i<h.getHand().size(); i++) {
			if(i<h.getHand().size()-1) {
				assertTrue(h.getCard(i).getFaceNum()>=h.getCard(i+1).getFaceNum()); 
				// does the one prior have a higher or equal face value?
			}
		}
		System.out.println();
	}
}
