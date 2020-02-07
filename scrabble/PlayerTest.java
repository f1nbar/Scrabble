package scrabble;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PlayerTest {

	Player player = new Player("Test");

	// Testing what's accepted as a name.
	@Test
	public void validInputName() {
		player.setName("Conor");
		assertEquals("Conor", player.getName());
		player.setName("Conor Knowles");
		assertEquals("Conor Knowles", player.getName());
	}

	@Test
	public void testNameNumbers() throws Exception {
		assertThrows(IllegalArgumentException.class, () -> {
			player.setName("Conor1");
		});
	}

	@Test
	public void testNameSymbols() {
		assertThrows(IllegalArgumentException.class, () -> {
			player.setName("Conor$");
		});
	}

	@Test
	public void testNameThree() {
		assertThrows(IllegalArgumentException.class, () -> {
			player.setName("Conor James Knowles");
		});
	}

	@Test
	public void testNameSpaces() {
		assertThrows(IllegalArgumentException.class, () -> {
			player.setName("   ");
		});
	}

	@Test
	public void testNameEmpty() {
		assertThrows(IllegalArgumentException.class, () -> {
			player.setName("");
		});
	}

	// Score tests
	@Test
	public void testScoreIncrement() {
		player.increaseScore(10);
		assertEquals(10, player.getScore());
		player.increaseScore(15);
		assertEquals(25, player.getScore());
		player.increaseScore(0);
		assertEquals(25,player.getScore());
	}

	@Test
	public void testScoreNegative() {
		assertThrows(IllegalArgumentException.class, () -> {
			player.increaseScore(-5);
		});
	}

	@Test
	public void testScoreLarge() {
		assertThrows(IllegalArgumentException.class, () -> {
			player.increaseScore(10000);
		});
	}

	// Reset method test
	@Test
	public void testReset() {
		player.setName("Conor");
		player.increaseScore(20);
		assertEquals("Conor", player.getName());
		assertEquals(20, player.getScore());

		player.reset("Test");
		assertEquals("Test", player.getName());
		assertEquals(0, player.getScore());
	}

}
