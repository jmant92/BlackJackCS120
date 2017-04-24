import static org.junit.Assert.*;

import org.junit.Test;

/**
 * This holds the JUnit Test Cases for the BlackJackHand.
 */

public class BlackJackHandTests {

	@Test
	public void testForBlackJackHand() {
		Deck d=new Deck();
		BlackJackHand hand=new BlackJackHand(d);
		
		hand.addSpecific(new Card(3,8)); // lets add the 8 of hearts
		hand.addSpecific(new Card(1,1)); // lets add the ace of clubs
		assertTrue(hand.isSoft()); // is it a soft hand?
		
		assertFalse(hand.isBust()); // Still good
		
		hand.addSpecific(new Card(4,10)); // lets add the 10 of spades
		hand.addSpecific(new Card(2, 10)); // lets add the 10 of diamonds to give us a bust
		
		assertTrue(hand.isBust()); // Bust
	}
}
