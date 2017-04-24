import static org.junit.Assert.*;

import org.junit.Test;

/**
 * This holds the JUnit Test Cases for the Deck
 */

public class DeckTests {

	/**
	 * Is the deck created correctly?
	 */
	@Test
	public void createDeckTest() {
		Deck d = new Deck();
		
		// Was the deck constructed correctly?
		assertTrue("Ace of Clubs".equals(d.getCard(0).toString()));
		assertTrue("Nine of Hearts".equals(d.getCard(34).toString()));
		assertTrue("King of Spades".equals(d.getCard(51).toString()));
		
		// Shuffling is a bit difficult to test if it's done right without a println
		// because it is entirely possible that a card doesn't change places, albeit unlikely
		d.shuffle(51);
		for(int i=0; i<26; i++) d.deal(); // deal 26 cards
		assertFalse(d.isEmpty()); // it shouldn't be empty yet
		
		assertTrue(d.numCardsLeft()==26); // because there should be 26 cards left
		d.returnCard(new Card(1,1)); // lets add another card, the ace of clubs
		
		for(int i=0; i<27; i++) d.deal(); // now lets take the rest away
		assertTrue(d.isEmpty()); // is it empty now?
		
		d.returnCard(new Card(1,1)); // lets return a card to the now empty deck
		assertTrue("Ace of Clubs".equals(d.getCard(51).toString()));
		// was the right card returned?
		
		d.returnCard(new Card(4, 13)); // lets return a second card to the deck
		assertTrue("King of Spades".equals(d.getCard(50).toString()));
		// it should be the second to last card there
		
		d.returnCard(new Card(2, 3)); // one last card to confirm it works
		assertTrue("Three of Diamonds".equals(d.getCard(49).toString()));
		// it should be the third to last card in the deck
		
		// It's fine that these cards are at the "end" of the deck because the
		// returnCard method goes backward from 51 to 0
	}
}
