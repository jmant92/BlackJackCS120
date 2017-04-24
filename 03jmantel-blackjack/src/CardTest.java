import static org.junit.Assert.*;

import org.junit.Test;

/**
 * This holds the JUnit Test Cases for the Card Class
 */

public class CardTest {

	/**
	 * Are the values for the cards correct?
	 */
	
	@Test
	public void cardValTest() {
		Card c1 = new Card(1,1);
		
		assertTrue("Clubs".equals(c1.getSuitString())); // it should be clubs
		assertTrue("Ace".equals(c1.getFaceString())); // it should be an ace
		assertTrue(c1.getRank()==49); // which totals to 49 as the rank
		assertTrue(c1.isAce()); // it is an ace
		
		Card c2 = new Card(4, 13);
		assertTrue("Spades".equals(c2.getSuitString())); // it should be Spades
		assertTrue("King".equals(c2.getFaceString())); // it should be a King
		assertTrue(c2.getRank()==48); // which totals to 48 as the rank
		assertFalse(c2.isAce()); // it is not an ace
		
		Card c3 = new Card(5,14);
		assertTrue("Couldn't assign suit".equals(c3.getSuitString())); // this suit can't be read
		assertTrue("Couldn't assign card".equals(c3.getFaceString())); // this card can't be read
		assertTrue(c3.getRank()==53); // but it will still have a rank, which is 53
		
		assertFalse(c1.equals(c2)); // These cards do not have the same face value
		
		Card c4 = new Card(3,1); // 
		assertTrue(c1.equals(c4)); // These cards do have the same face value
		
		assertTrue(c1.compareTo(c2)==49); // c1 has the larger rank
		assertTrue(c2.compareTo(c1)==49); // c1 still has the larger rank
		
		System.out.println("The face of c1 is: "+c1.getFaceNum());
	}

}
