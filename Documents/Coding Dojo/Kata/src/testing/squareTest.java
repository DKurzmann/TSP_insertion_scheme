package testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

class squareTest {
	private BowlingGame g = new BowlingGame();;

	private void rollMany(int n, int pins) {
		for (int i = 0; i < n; i++)
			g.roll(pins);
	}
	
	@Test
	void testGutterGame() {
		rollMany(20, 0);
		assertEquals(0, g.score());
	}
	
	@Test
	void testAllOnes() {
		rollMany(20,1);
		assertEquals(20, g.score());
	}
	
	@Test
	public void testOneSpare() {
		rollSquare();
		g.roll(3);
		rollMany(17,0);
		assertEquals(16, g.score());
	}
	
	@Test
	public void testOneStrike() {
		rollStrike();
		g.roll(3);
		g.roll(3);
		rollMany(16,0);
		assertEquals(22, g.score());
	}
	
	@Test
	public void testPerfectGame() {
		rollMany(12,10);
		assertEquals(300,g.score());
	}

	private void rollSquare() {
		g.roll(5);
		g.roll(5);
	}
	
	private void rollStrike() {
		g.roll(10);
	}
	
}
